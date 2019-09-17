package com.chenhao.sell.service;
import com.chenhao.sell.dataObject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    public void findById()
    {
        ProductCategory category = categoryService.findById(1);
        Assert.assertEquals(new Integer(1),category.getCategoryId());
    }

    @Test
    public void findAll()
    {
        List<ProductCategory> categoryServiceAll = categoryService.findAll();
        Assert.assertNotEquals(0,categoryServiceAll.size());
    }

    @Test
    public void save()
    {
        ProductCategory category = new ProductCategory();
        category.setCategoryType(1);
        category.setCategoryName("裤子");
        ProductCategory newCategory = categoryService.save(category);
        Assert.assertNotNull(newCategory);
    }

    @Test
    public void findByCategoryTypeIn() {
    }
}