package com.chenhao.sell.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum implements CodeEnums
{
    //下列实现类通过lombok已经实现get方法，不需要再override
    NEW(0,"新订单"),
    FINISHED(1,"已完结"),
    CANCELED(2,"已取消");

    private Integer code;
    private String msg;

    OrderStatusEnum(Integer code , String msg)
    {
         this.code = code;
         this.msg = msg;
    }

}
