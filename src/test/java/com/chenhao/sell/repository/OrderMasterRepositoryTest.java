package com.chenhao.sell.repository;

import com.chenhao.sell.dataObject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository repository;

    private String OPENID = "abc";

    @Test
    public void findByBuyerOpenid()
    {
        PageRequest pageRequest = PageRequest.of(0, 1);
        Page<OrderMaster> result = repository.findByBuyerOpenid(OPENID, pageRequest);

        Assert.assertNotEquals(0,result.getTotalElements());
    }

    @Test
    public void saveTest()
    {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("123457");
        orderMaster.setBuyerOpenid(OPENID);
        orderMaster.setBuyerName("Kobe");
        orderMaster.setBuyerAddress("Lakers");
        orderMaster.setBuyerPhone("10010010012");
        orderMaster.setOrderAmount(new BigDecimal(124.00));

        OrderMaster save = repository.save(orderMaster);
        Assert.assertNotNull(save);
    }
}