package com.chenhao.sell.service.impl;

import com.chenhao.sell.Utils.JsonUtil;
import com.chenhao.sell.Utils.MathUtil;
import com.chenhao.sell.dto.OrderDTO;
import com.chenhao.sell.enums.ResultEnum;
import com.chenhao.sell.exception.SellException;
import com.chenhao.sell.service.IPayService;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PayService implements IPayService
{
    private static final String ORDER_NAME = "微信点餐订单";

    @Autowired
    private BestPayServiceImpl bestPayService;

    @Autowired
    private OrderService orderService;

    @Override
    public PayResponse create(OrderDTO orderDTO)
    {
        /**整合支付所需要的数据，包括openid、订单信息*/
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【发起支付】request={}", JsonUtil.toJson(payRequest));

        /**信息整合完毕后，调用微信支付接口实现支付过程*/
        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("【发起支付】reponse={}",JsonUtil.toJson(payResponse));
        return payResponse;
    }

    @Override
    public PayResponse notify(String notifyData)
    {
        /**支付完毕后，接收微信的异步通知，拿到本次支付的流水号、商家订单号等信息*/
        //【通知校验】：这里可能微信的异步通知出现失败，async这个方法已经帮我们实现了，包括是否是因为签名对不上的问题，或是接收微信放的错误
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("【微信异步通知】payResponse={}",JsonUtil.toJson(payResponse));
        /**修改订单支付状态前的校验*/
        OrderDTO orderDTO = orderService.findByOrderId(payResponse.getOrderId());
        //【支付校验】：可能出现支付过程中的错误导致实际支付的金额和订单金额不一致或订单号不一致，因此需要对实际支付数据进行验证
        //校验订单号是否错误
        if(null == orderDTO)
        {
            log.error("【微信异步通知】订单不存在，orderId={}",payResponse.getOrderId());
            throw new SellException(ResultEnum.ORDER_NOT_EXISTS);
        }
        //校验上一步支付的金额是否正确
        //【数据比较】：1、BigDecimal类型之间是否相等要使用compareTo，因为使用equals它会先判定小数点位数，只要位数不一致就不相等
        //             2、微信回传的数据，金额可能会有很微小的误差，这时候会校验失败，需要使用Util去校验。
        if(!MathUtil.doubleEquals(orderDTO.getOrderAmount().doubleValue(),payResponse.getOrderAmount()))
        {
            log.error("【微信异步通知】支付金额与订单金额不一致，orderId={},微信金额={},系统金额={}",
                    payResponse.getOrderId(),
                    payResponse.getOrderAmount(),
                    orderDTO.getOrderAmount());
            throw new SellException(ResultEnum.WX_PAY_NOTIFY_MONEY_VERIFY_ERROR);
        }

        /**检验成功，修改订单支付状态*/
        orderService.paid(orderDTO);

        return payResponse;
    }

    @Override
    public RefundResponse refund(OrderDTO orderDTO)
    {
        //【退款系统】真实的企业案例需要一个退款系统不停的对账
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderId(orderDTO.getOrderId());
        refundRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信退款】refundRequest={}",JsonUtil.toJson(refundRequest));

        RefundResponse refundResponse = bestPayService.refund(refundRequest);
        log.info("【微信退款】refundResponse={}",JsonUtil.toJson(refundResponse));

        return refundResponse;
    }

}
