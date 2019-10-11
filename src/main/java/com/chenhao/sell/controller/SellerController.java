package com.chenhao.sell.controller;

import com.chenhao.sell.dto.OrderDTO;
import com.chenhao.sell.enums.ResultEnum;
import com.chenhao.sell.exception.SellException;
import com.chenhao.sell.service.impl.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
* @title 卖家端订单
* @author Chenhao
* @date 2019/10/10
*/
@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellerController
{
    @Autowired
    private OrderService orderService;

    /**
    * @title 查询订单列表
    * @param page 当前第几页，从1开始
    * @param size 每页多少条
    * @author Chenhao
    * @date 2019/10/10
    */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value = "size",defaultValue = "10") Integer size,
                             Map<String,Object> map)
    {
        PageRequest pageRequest = PageRequest.of(page-1, size);
        Page<OrderDTO> pageResult = orderService.findList(pageRequest);
        map.put("pageResult",pageResult);
        map.put("currentPageNo",page);
        map.put("pageSize",size);
        return new ModelAndView("order/list",map);
    }

    /**
    * @title 卖家取消订单
    * @param orderId 订单号
    * @param page 当前页
    * @author Chenhao
    * @date 2019/10/11
    */
    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam(value = "orderId") String orderId,
                               @RequestParam(value = "page") Integer page,
                               Map<String,Object> map)
    {
        OrderDTO orderDTO = null;
        //订单取消异常的情况
        try
        {
            orderDTO = orderService.findByOrderId(orderId);
            orderService.cancel(orderDTO);
        }
        catch (SellException e)
        {
            log.info("【卖家取消订单】订单取消失败，orderId={}",orderId);
            map.put("errorMsg", e.getMessage());
            map.put("returnUrl","/sell/seller/order/list?page="+page);
            return new ModelAndView("common/error",map);
        }

        map.put("successMsg",ResultEnum.SUCCESS.getMsg());
        map.put("returnUrl","/sell/seller/order/list?page="+page);
        return new ModelAndView("common/success",map);
    }

    /**
    * @title 卖家查询订单详情
    * @param  orderId
    * @author Chenhao
    * @date 2019/10/11
    */
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam(value = "orderId") String orderId,
                               @RequestParam(value = "page") Integer page,
                               Map<String,Object> map)
    {
        OrderDTO orderDTO = null;
        try
        {
            orderDTO = orderService.findByOrderId(orderId);
        }
        catch (SellException e)
        {
            log.info("【卖家查询订单】订单查询失败，orderId={}",orderId);
            map.put("errorMsg", e.getMessage());
            map.put("returnUrl","/sell/seller/order/list?page="+page);
            return new ModelAndView("common/error",map);
        }
        map.put("orderDTO",orderDTO);
        map.put("currentPage",page);
        return new ModelAndView("order/detail",map);
    }
}
