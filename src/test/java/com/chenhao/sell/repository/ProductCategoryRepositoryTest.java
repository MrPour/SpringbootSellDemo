package com.chenhao.sell.repository;

import com.chenhao.sell.dataObject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest
{
    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void findOneCategory()
    {
        Optional<ProductCategory> c = repository.findById(1);
        System.out.println(c.get().getCategoryId());
    }

    @Test
    @Transactional //测试里使用事务注解，在执行完毕后会自动回滚
    public void saveCategory()
    {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("汽车");
        productCategory.setCategoryType(5);
        repository.save(productCategory);
    }

    @Test
    public void findAndUpdate()
    {
        Optional<ProductCategory> category = repository.findById(1);
        ProductCategory productCategory = category.get();
        productCategory.setCategoryType(100);
        repository.save(productCategory);
    }

    @Test
    public void findByCategoryTypeInTest()
    {
        List<Integer> list = Arrays.asList(3,100);
        List<ProductCategory> result = repository.findByCategoryTypeIn(list);
        Assert.assertNotNull(result);
    }
}