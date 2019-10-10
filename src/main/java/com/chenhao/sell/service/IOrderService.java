package com.chenhao.sell.service;

import com.chenhao.sell.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOrderService
{
    /**创建订单,创建时把订单详情一起存进去*/
    OrderDTO create(OrderDTO orderDTO);

    /**取消订单*/
    OrderDTO cancel(OrderDTO orderDTO);

    /**完结订单*/
    OrderDTO finish(OrderDTO orderDTO);

    /**支付订单*/
    OrderDTO paid(OrderDTO orderDTO);

    /**查询单个订单,查询订单并显示出对应的订单详情*/
    OrderDTO findByOrderId(String orderId);

    /**查询订单列表，但不展示订单详情（因为要展示，所以使用Page）*/
    Page<OrderDTO> findByOpenId(String buyerOpenId, Pageable pageable);

    /**查询所有的订单列表*/
    Page<OrderDTO> findList(Pageable pageable);



}
