package com.chenhao.sell.controller;

import com.chenhao.sell.dto.OrderDTO;
import com.chenhao.sell.enums.ResultEnum;
import com.chenhao.sell.exception.SellException;
import com.chenhao.sell.service.impl.OrderService;
import com.chenhao.sell.service.impl.PayService;
import com.lly835.bestpay.model.PayResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/pay")
public class PayController
{
    @Autowired
    private OrderService orderService;

    @Autowired
    private PayService payService;

    /**支付订单*/

    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("returnUrl") String returnUrl,
                               Map<String,Object> map)
    {
        /**查询订单是否存在*/
        OrderDTO orderDTO = orderService.findByOrderId(orderId);
        if(null==orderDTO)
        {
            throw new SellException(ResultEnum.ORDER_NOT_EXISTS);
        }
        /**支付*/
        PayResponse payResponse = payService.create(orderDTO);
        map.put("payResponse",payResponse);
        //【FreeMarker】支付后的页面数据通过freemarker渲染，跳转地址通过freemarker跳转
        map.put("returnUrl",returnUrl);
        /**配置freemarker模板地址*/
//      return new ModelAndView("test",map);
        return new ModelAndView("createPay",map);
    }

    /**接收微信的异步通知(微信调用该接口)*/

    @PostMapping("/notify")
    public ModelAndView notify(@RequestBody String notifyData)
    {
        payService.notify(notifyData);
        /**告诉微信已经订单支付成功*/
        //【微信通知机制】1、异步调用是不停的进行，所以必须要按照格式回传给微信结果
        //              2、使用freemarker的形式传递比String更美观
        return new ModelAndView("success");
    }

}
