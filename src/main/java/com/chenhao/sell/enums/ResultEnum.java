package com.chenhao.sell.enums;

import lombok.Getter;

@Getter
public enum ResultEnum
{
    SUCCESS(0,"操作成功"),
    PARAM_ERROR(1,"参数不正确"),
    PRODUCT_NOT_EXISTS(10,"商品不存在"),
    PRODUCT_STOCK_ERROR(11,"商品库存不正确"),
    ORDER_NOT_EXISTS(12,"订单不存在"),
    ORDER_DETAIL_NOT_EXISTS(13,"订单详情不存在"),
    ORDER_STATUS_ERROR(14,"订单状态不正确"),
    ORDER_UPDATE_FAIL(15,"订单更新失败"),
    ORDER_DETAIL_EMPTY(16,"订单详情为空"),
    PAY_STATUS_ERROR(17,"支付状态不正确"),
    CART_EMPTY_ERROR(18,"购物车为空"),
    ORDER_OWNER_ERROR(19,"该订单不属于当前用户"),
    WX_MP_ERROR(20,"微信公众账号错误"),
    WX_PAY_NOTIFY_MONEY_VERIFY_ERROR(21,"微信支付异步通知金额校验不通过"),
    ORDER_STATUS_CANCEL_SUCCESS(22,"订单取消成功！");
    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg)
    {
        this.code = code;
        this.msg = msg;
    }



}
