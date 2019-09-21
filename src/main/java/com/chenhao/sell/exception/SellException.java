package com.chenhao.sell.exception;

import com.chenhao.sell.enums.ResultEnum;

public class SellException extends RuntimeException
{
    private Integer code;

    public SellException(ResultEnum resultEnum)
    {
        //调用父类构造器，初始化msg属性
        super(resultEnum.getMsg());
        //自身构造器，初始化code属性
        this.code = resultEnum.getCode();
    }
}
