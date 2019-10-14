package com.chenhao.sell.service.impl;

import com.chenhao.sell.Utils.KeyUtil;
import com.chenhao.sell.converter.OrderDTO2OrderMasterConverter;
import com.chenhao.sell.converter.OrderMaster2OrderDTOConverter;
import com.chenhao.sell.dataObject.OrderDetail;
import com.chenhao.sell.dataObject.OrderMaster;
import com.chenhao.sell.dataObject.ProductInfo;
import com.chenhao.sell.dto.CartDTO;
import com.chenhao.sell.dto.OrderDTO;
import com.chenhao.sell.enums.OrderStatusEnum;
import com.chenhao.sell.enums.PayStatusEnum;
import com.chenhao.sell.enums.ResultEnum;
import com.chenhao.sell.exception.SellException;
import com.chenhao.sell.repository.OrderDetailRepository;
import com.chenhao.sell.repository.OrderMasterRepository;
import com.chenhao.sell.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j   //Lombok自动创建log对象
public class OrderService implements IOrderService
{
    @Autowired
    private OrderMasterRepository masterRepository;

    @Autowired
    private OrderDetailRepository detailRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private PayService payService;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO)
    {
        //定义累加的中间变量
        BigDecimal orderSum = new BigDecimal(BigInteger.ZERO);
        //生成订单id
        String orderId = KeyUtil.createKey();
        //回写给orderDTO
        orderDTO.setOrderId(orderId);

        /**  1、去数据库查询订单详情中对应的商品信息*/
        for(OrderDetail orderDetail:orderDTO.getOrderDetails())
        {
            ProductInfo productInfo = productService.findById(orderDetail.getProductId());

            if(null == productInfo)
            {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXISTS);
            }
            /** 2、累加计算订单总价 */
            //注：价钱必须要去数据库里取，不能从前端拿。
            orderSum = productInfo.getProductPrice()
                    .multiply(BigDecimal.valueOf(orderDetail.getProductQuantity()))
                    .add(orderSum);
            /** 3、订单详情装配和写入数据库 */
            //由于前端只传商品id和商品数量，其他的都从productInfo中拷贝
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetail.setOrderId(orderId);
            orderDetail.setDetailId(KeyUtil.createKey());

            detailRepository.save(orderDetail);
        }
        /** 4、订单的装配和写入数据库 */
        //这里的顺序要注意，根据Test出错，他会把null值复制过去，从而覆盖前面确定的值。
        // 因此顺序是转换放最前面！！！
        OrderMaster orderMaster = OrderDTO2OrderMasterConverter.convert(orderDTO);
        orderMaster.setOrderAmount(orderSum);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());

        masterRepository.save(orderMaster);

        /** 5、减库存 */
        //尽量把这部分代码单独写，而不是写在上述的for循环中，这样更清晰
        List<CartDTO> cartDTOList = orderDTO.getOrderDetails().stream()
                .map(e -> new CartDTO(e.getProductId(),e.getProductQuantity()))
                    .collect(Collectors.toList());

        productService.decreaseStock(cartDTOList);

        //由于是创建订单，返回值意义不大，因此这个更新也不一定要做
        //BeanUtils.copyProperties(orderMaster,orderDTO);
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO)
    {
        //先拷贝，再修改状态，否则容易被覆盖上错误的状态
        /**1、判断该订单的状态*/
        Integer orderStatus = orderDTO.getOrderStatus();
        //不允许取消
        //包装类要注意使用equals而不是==
        if(!OrderStatusEnum.NEW.getCode().equals(orderStatus))
        {
            log.error("【取消订单】当前订单状态不能被取消,orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        /**2、满足条件，完成取消*/
        orderDTO.setOrderStatus(OrderStatusEnum.CANCELED.getCode());
        OrderMaster orderMaster = OrderDTO2OrderMasterConverter.convert(orderDTO);
        OrderMaster updateResult = masterRepository.save(orderMaster);
        if(null == updateResult)
        {
            log.error("【取消订单】当前订单状态取消更新失败,orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        /**3、取消成功的订单，返回库存*/
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetails()))
        {
            log.error("【取消订单】当前订单中无商品,orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<OrderDetail> orderDetailList = orderDTO.getOrderDetails();
        //生成加库存所需要的数据结构
        List<CartDTO> cartDTOList = orderDetailList.stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        //执行加库存
        productService.increaseStock(cartDTOList);
        /**4、如果已支付，还要退款*/
        if(PayStatusEnum.SUCCESS.getCode().equals(orderDTO.getPayStatus()))
        {
            payService.refund(orderDTO);
        }
        return orderDTO;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO)
    {
        /**1、判断订单状态*/
        if(!OrderStatusEnum.NEW.getCode().equals(orderDTO.getOrderStatus()))
        {
            log.error("【完结订单】当前订单状态不能被完结,orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        /**2、修改订单状态*/
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = OrderDTO2OrderMasterConverter.convert(orderDTO);
        OrderMaster updateResult = masterRepository.save(orderMaster);
        if(null == updateResult)
        {
            log.error("【完结订单】当前订单状态完结更新失败,orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        /**1、判断订单状态*/
        if(!OrderStatusEnum.NEW.getCode().equals(orderDTO.getOrderStatus()))
        {
            log.error("【支付订单】当前订单状态不能被支付,orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        /**2、判断支付状态*/
        if(!PayStatusEnum.WAIT.getCode().equals(orderDTO.getPayStatus()))
        {
            log.error("【支付订单】当前支付状态不正确,orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.PAY_STATUS_ERROR);
        }
        /**3、扣款*/

        //TODO

        /**4、修改支付状态*/
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = OrderDTO2OrderMasterConverter.convert(orderDTO);
        OrderMaster updateResult = masterRepository.save(orderMaster);
        if(null == updateResult)
        {
            log.error("【支付订单】当前订单支付状态更新失败,orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    public OrderDTO findByOrderId(String orderId)
    {
        OrderDTO orderDTO = new OrderDTO();

        //【Optional】这里要注意，已经帮我们处理了值为null的情况，会抛出异常，因此需要自己再抛出我们自己的异常。
        OrderMaster orderMaster = null;
        try
        {
            orderMaster = masterRepository.findById(orderId).get();
        }
        catch (Exception e)
        {
            if ("No value present".equals(e.getMessage()))
            {
                throw new SellException(ResultEnum.ORDER_NOT_EXISTS);
            }
        }

        List<OrderDetail> orderDetailList = detailRepository.findByOrderId(orderId);

        if(CollectionUtils.isEmpty(orderDetailList))
        {
            throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXISTS);
        }

        orderDTO.setOrderDetails(orderDetailList);
        BeanUtils.copyProperties(orderMaster,orderDTO);

        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findByOpenId(String buyerOpenId, Pageable pageable)
    {
        Page<OrderMaster> orderMasterPage = masterRepository.findByBuyerOpenid(buyerOpenId, pageable);
        List<OrderMaster> orderMasterList = orderMasterPage.getContent();
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convertList(orderMasterList);
        Page<OrderDTO> orderDTOPage = new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());

        return orderDTOPage;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = masterRepository.findAll(pageable);
        List<OrderMaster> orderMasterList = orderMasterPage.getContent();
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convertList(orderMasterList);
        //重新装配Page对象（list、分页信息和总记录数）
        Page<OrderDTO> orderDTOPage = new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());
        return orderDTOPage;
    }


}
