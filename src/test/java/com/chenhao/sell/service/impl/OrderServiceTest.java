package com.chenhao.sell.service.impl;

import com.chenhao.sell.dataObject.OrderDetail;
import com.chenhao.sell.dto.OrderDTO;
import com.chenhao.sell.enums.OrderStatusEnum;
import com.chenhao.sell.enums.PayStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderService orderService;

    @Test
    public void create()
    {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductQuantity(1);
        orderDetail.setProductId("12345");

        OrderDetail orderDetail2 = new OrderDetail();
        orderDetail2.setProductQuantity(1);
        orderDetail2.setProductId("12346");

        List<OrderDetail> orderDetailListlist = Arrays.asList(orderDetail2,orderDetail);
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerAddress("成都成华区");
        orderDTO.setBuyerName("Tracy");
        orderDTO.setBuyerOpenid("abctracy");
        orderDTO.setBuyerPhone("123456788");
        orderDTO.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderDTO.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderDTO.setOrderDetails(orderDetailListlist);

        OrderDTO result = orderService.create(orderDTO);

        logger.info("【创建订单】result={}",result);
        Assert.assertNotNull(result);
    }

    @Test
    public void cancel()
    {
        OrderDTO orderDTO = orderService.findByOrderId("1569065017066342547");
        OrderDTO result = orderService.cancel(orderDTO);
        Assert.assertEquals(PayStatusEnum.WAIT.getCode(),result.getPayStatus());
    }

    @Test
    public void finish()
    {
        OrderDTO orderDTO = orderService.findByOrderId("1569064910892288300");
        OrderDTO result = orderService.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(),result.getOrderStatus());
    }

    @Test
    public void paid()
    {
        OrderDTO orderDTO = orderService.findByOrderId("1569063676808480477");
        OrderDTO result = orderService.paid(orderDTO);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(),result.getPayStatus());
    }

    @Test
    public void findByOrderId()
    {
        OrderDTO orderDTO = orderService.findByOrderId("1569065017066342547");
        logger.info("【查询单个订单结果】result={}",orderDTO);
        Assert.assertEquals("1569065017066342547",orderDTO.getOrderId());
    }

    @Test
    public void findByOpenId()
    {
        PageRequest pageRequest = PageRequest.of(0, 2);
        Page<OrderDTO> orderDTOPage = orderService.findByOpenId("abctracy", pageRequest);
        logger.info("【查询个人所有订单结果】total={}",orderDTOPage.getTotalElements());
        Assert.assertEquals(2,orderDTOPage.getTotalElements());
    }

    @Test
    public void findList()
    {
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<OrderDTO> orderDTOPage = orderService.findList(pageRequest);
        logger.info("【查询所有订单结果】total={}",orderDTOPage.getTotalElements());
        Assert.assertTrue("查询所有订单结果",orderDTOPage.getTotalElements()>0);


    }
}