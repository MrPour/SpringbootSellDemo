package com.chenhao.sell.converter;

import com.chenhao.sell.dataObject.OrderMaster;
import com.chenhao.sell.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

public class OrderDTO2OrderMasterConverter
{
    public static OrderMaster convert(OrderDTO orderDTO)
    {
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        return orderMaster;
    }
}
