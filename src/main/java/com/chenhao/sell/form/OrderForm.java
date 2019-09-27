package com.chenhao.sell.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**个人信息+购物车信息*/
@Data
public class OrderForm
{
    /**字段根据前端定*/
    //validation-api只是一套标准，hibernate-validator实现了这套标准,但是被弃用了，使用java自带的
    @NotEmpty(message = "名字必填")
    private String name;
    @NotEmpty(message = "电话必填")
    private String phone;
    @NotEmpty(message = "地址必填")
    private String address;
    @NotEmpty(message = "openid必填")
    private String openid;

    //前端以json的形式传入，在java中是json字符串
    @NotEmpty(message = "购物车不为空")
    private String items;
}
