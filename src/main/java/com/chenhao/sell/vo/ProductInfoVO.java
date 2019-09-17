package com.chenhao.sell.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
* @title 视图类
* @description 需要返回给前端的数据
* @author Chenhao
* @date 2019/9/17
*/
@Data
public class ProductInfoVO
{
    @JsonProperty("id")
    private String productId;
    @JsonProperty("name")
    private String productName;
    @JsonProperty("price")
    private BigDecimal productPrice;
    @JsonProperty("description")
    private String productDescription;
    @JsonProperty("icon")
    private String productIcon;

}
