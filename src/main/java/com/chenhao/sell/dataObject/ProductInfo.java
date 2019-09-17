package com.chenhao.sell.dataObject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@DynamicUpdate
@Data  //此处使用lombok简化操作
public class ProductInfo
{
    /** ID */
    @Id
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
    /** 上架信息 */
    private Integer productStatus;
    /** 类目信息 */
    private Integer categoryType;

    private Date createTime;

    private Date updateTime;
}
