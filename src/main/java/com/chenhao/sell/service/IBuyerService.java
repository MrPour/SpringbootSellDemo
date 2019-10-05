package com.chenhao.sell.service;

import com.chenhao.sell.dto.OrderDTO;

/**controller的逻辑抽出成新的service*/
public interface IBuyerService
{
    /**根据买家的openid查询单个订单*/
    OrderDTO findOrderOne(String openid,String orderId);

    /**根据买家的openid取消单个订单*/
    OrderDTO cancelOrder(String openid,String orderId);
}
