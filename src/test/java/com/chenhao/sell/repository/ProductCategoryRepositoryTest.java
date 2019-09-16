package com.chenhao.sell.repository;

import com.chenhao.sell.dataObject.ProductCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
        System.out.println(c.get());
    }

    @Test
    public void saveCategory()
    {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("衣服");
        productCategory.setCategoryType(2);
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
}