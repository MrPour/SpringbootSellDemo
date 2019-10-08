package com.chenhao.sell.controller;

import com.chenhao.sell.dto.OrderDTO;
import com.chenhao.sell.enums.ResultEnum;
import com.chenhao.sell.exception.SellException;
import com.chenhao.sell.service.impl.OrderService;
import com.chenhao.sell.service.impl.PayService;
import com.lly835.bestpay.model.PayResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
//        PayResponse payResponse = new PayResponse();
//        payResponse.setAppId("12345");
//        payResponse.setOrderAmount(123.0);
          map.put("payResponse",payResponse);
          //支付后跳转
          map.put("returnUrl",returnUrl);
        /**配置freemarker模板地址*/
//        return new ModelAndView("test",map);
          return new ModelAndView("create",map);

    }
}
