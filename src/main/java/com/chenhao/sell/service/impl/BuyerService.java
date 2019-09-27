package com.chenhao.sell.service.impl;

import com.chenhao.sell.dto.OrderDTO;
import com.chenhao.sell.enums.ResultEnum;
import com.chenhao.sell.exception.SellException;
import com.chenhao.sell.service.IBuyerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BuyerService implements IBuyerService
{
    @Autowired
    private OrderService orderService;

    /**验证是否是自己的订单*/
    public OrderDTO checkOrderId(String openid, String orderId)
    {
        OrderDTO orderDTO = orderService.findByOrderId(orderId);
        if(orderDTO == null)
        {
            return null;
        }
        //判定是否在修改自己的order
        if(!openid.equals(orderDTO.getBuyerOpenid()))
        {
            log.error("【查询订单】订单的openid与当前openid不一致，openid={},orderDTO={}",openid,orderDTO);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }

        return orderDTO;
    }

    @Override
    public OrderDTO findOrderOne(String openid, String orderId)
    {
        return checkOrderId(openid,orderId);
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderId)
    {
        OrderDTO orderDTO = checkOrderId(openid, orderId);
        if(null == orderDTO)
        {
            log.error("【取消订单】订单不存在,orderId={}",orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXISTS);
        }
        return  orderService.cancel(orderDTO);
    }
}
