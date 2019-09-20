package com.chenhao.sell.repository;

import com.chenhao.sell.dataObject.OrderDetail;
import com.chenhao.sell.dataObject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;

    @Test
    public void findByOrderId()
    {
        List<OrderDetail> details = repository.findByOrderId("123456");
        Assert.assertNotEquals(0,details.size());
    }

    @Test
    public void saveTest()
    {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("111112");
        orderDetail.setOrderId("123456");
        orderDetail.setProductId("12345");
        orderDetail.setProductName("黑米粥");
        orderDetail.setProductQuantity(3);
        orderDetail.setProductPrice(new BigDecimal(5.00));
        orderDetail.setProductIcon("http://baidu.com");

        OrderDetail save = repository.save(orderDetail);
        Assert.assertNotNull(save);
    }
}