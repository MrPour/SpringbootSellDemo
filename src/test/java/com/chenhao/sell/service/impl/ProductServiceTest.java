package com.chenhao.sell.service.impl;

import com.chenhao.sell.dataObject.ProductInfo;
import com.chenhao.sell.enums.ProductStatusEnum;
import com.chenhao.sell.service.impl.ProductService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService service;

    @Test
    public void findById()
    {
        ProductInfo profuctinfo = service.findById("12345");
        Assert.assertEquals("12345",profuctinfo.getProductId());
    }

    @Test
    public void findUpAll()
    {
        List<ProductInfo> all = service.findUpAll();
        Assert.assertNotEquals(0,all.size());
    }

    @Test
    public void findAll()
    {
        /**pageable的实现类*/
        PageRequest pageRequest = PageRequest.of(0, 2);
        Page<ProductInfo> productInfoPage = service.findAll(pageRequest);
        Assert.assertNotEquals(0,productInfoPage.getTotalElements());
    }

    @Test
    public void save()
    {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("12346");
        productInfo.setCategoryType(2);
        productInfo.setProductDescription("hotspot");
        productInfo.setProductName("火锅");
        productInfo.setProductPrice(new BigDecimal(125.00));
        productInfo.setProductStock(10);
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        productInfo.setProductIcon("http://baidu.com");

        ProductInfo info = service.save(productInfo);
        Assert.assertNotNull(info);
    }

    @Test
    public void offSale()
    {
        ProductInfo productInfo = service.offSale("12345");
        Assert.assertEquals(ProductStatusEnum.DOWN.getCode(),productInfo.getProductStatus());
    }

    @Test
    public void onSale()
    {
        ProductInfo productInfo = service.onSale("12345");
        Assert.assertEquals(ProductStatusEnum.UP.getCode(),productInfo.getProductStatus());
    }
}