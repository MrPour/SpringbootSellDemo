package com.chenhao.sell.converter;

import com.chenhao.sell.dataObject.OrderMaster;
import com.chenhao.sell.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMaster2OrderDTOConverter
{
    public static OrderDTO convert(OrderMaster orderMaster)
    {
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        return orderDTO;
    }

    public static List<OrderDTO> convertList(List<OrderMaster> orderMasterList)
    {
        List<OrderDTO> orderDTOList = orderMasterList.stream().map(e ->
                convert(e)
        ).collect(Collectors.toList());
        return orderDTOList;
    }
}
