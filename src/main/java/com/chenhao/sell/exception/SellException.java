package com.chenhao.sell.exception;

import com.chenhao.sell.enums.ResultEnum;

public class SellException extends RuntimeException
{
    //msg属性继承自父类
    private Integer code;

    public SellException(ResultEnum resultEnum)
    {
        //调用父类构造器，初始化msg属性
        super(resultEnum.getMsg());
        //自身构造器，初始化code属性
        this.code = resultEnum.getCode();
    }

        //如果要使用其他的报错信息(更具体的信息)，就是用这个构造器
    public SellException(Integer code,String msg)
    {
        super(msg);
        this.code = code;

    }
}
