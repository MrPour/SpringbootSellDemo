package com.chenhao.sell.dto;


import com.chenhao.sell.Utils.serializer.Date2LongSerializer;
import com.chenhao.sell.dataObject.OrderDetail;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
* @title 订单传输层对象
* @description 加入了订单详情，让每次查询的结果更加丰富
*              查一个人所有订单时，可以只对接OrderMaster；
*              查单独的订单时，也可以对接OrderMaster+OrderDetail；
*              生成订单时，还可以只携带一些来自前端的较重要的信息；
* @author Chenhao
* @date 2019/9/21
*/
@Data
public class OrderDTO
{
    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerOpenid;

    private BigDecimal orderAmount;

    private Integer orderStatus;

    private Integer payStatus;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    /**新增订单详情列表*/
    private List<OrderDetail> orderDetails;
}
