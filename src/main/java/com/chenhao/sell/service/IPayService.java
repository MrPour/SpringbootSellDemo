package com.chenhao.sell.service;

import com.chenhao.sell.dto.OrderDTO;
import com.lly835.bestpay.model.PayResponse;

public interface IPayService
{
    /**根据订单信息进行支付*/
    public PayResponse create(OrderDTO orderDTO);
}
