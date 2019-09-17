package com.chenhao.sell.repository;

import com.chenhao.sell.dataObject.ProductInfo;
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
public class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository repository;

    @Test
    public void saveTest()
    {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("12345");
        productInfo.setCategoryType(1);
        productInfo.setProductDescription("一碗粥");
        productInfo.setProductName("黑米粥");
        productInfo.setProductPrice(new BigDecimal(5.00));
        productInfo.setProductStock(20);
        productInfo.setProductStatus(0);
        productInfo.setProductIcon("http://baidu.com");
        ProductInfo productInfo1 = repository.save(productInfo);
        Assert.assertNotNull(productInfo1);
    }

    @Test
    public void findByProductStatus()
    {
        List<ProductInfo> result = repository.findByProductStatus(0);
        Assert.assertNotEquals(0,result.size());
    }
}