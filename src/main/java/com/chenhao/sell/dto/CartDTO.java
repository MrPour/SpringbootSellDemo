package com.chenhao.sell.dto;

import lombok.Data;

/**
* @title 新增库存、扣减购物车的传输体
* @description 将库存修改的两个对象组合成了一个
* @author Chenhao
* @date 2019/9/21
*/
@Data
public class CartDTO
{
    private String productId;

    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity)
    {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }

}
