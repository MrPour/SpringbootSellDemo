package com.chenhao.sell.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum
{
    NEW(0,"新订单"),
    FINISHED(1,"完结的订单"),
    CANCELED(2,"取消的订单");

    private Integer code;
    private String msg;

    OrderStatusEnum(Integer code , String msg)
    {
         this.code = code;
         this.msg = msg;
    }

}
