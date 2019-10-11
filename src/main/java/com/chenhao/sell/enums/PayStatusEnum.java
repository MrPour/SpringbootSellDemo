package com.chenhao.sell.enums;

import lombok.Getter;

@Getter
public enum PayStatusEnum implements CodeEnums
{
    WAIT(0,"等待支付"),
    SUCCESS(1,"支付成功"),
    CANCEL(2,"已取消");

    private Integer code;
    private String msg;

    PayStatusEnum(Integer code , String msg)
    {
        this.code = code;
        this.msg = msg;
    }
}
