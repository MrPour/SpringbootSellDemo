package com.chenhao.sell.enums;

import lombok.Getter;

@Getter
public enum ProductEnumStatus
{
    UP(0,"在架"),
    DOWN(1,"下架");

    private Integer code;
    private String status;

    ProductEnumStatus(Integer code, String status) {
        this.code = code;
        this.status = status;
    }
}
