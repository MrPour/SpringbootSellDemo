package com.chenhao.sell.vo;

import lombok.Data;

@Data
public class ResultVO<T>
{
    /**状态码*/
    private Integer code;
    /**提示信息*/
    private String msg;
    /**返回内容*/
    private T data;

}
