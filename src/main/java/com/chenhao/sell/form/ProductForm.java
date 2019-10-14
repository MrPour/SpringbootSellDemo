package com.chenhao.sell.form;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ProductForm
{
    @NotNull(message = "ID必填")
    private String productId;
    /** 商品名 */
    private String productName;
    /** 商品单价 */
    private BigDecimal productPrice;
    /** 商品库存 */
    private Integer productStock;
    /** 商品介绍 */
    private String productDescription;
    /** 商品小图 */
    private String productIcon;
    /** 类目信息 */
    private Integer categoryType;
}
