package com.chenhao.sell.service.impl;

import com.chenhao.sell.dataObject.ProductCategory;
import com.chenhao.sell.repository.ProductCategoryRepository;
import com.chenhao.sell.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
* @title 类目相关

* @author Chenhao
* @date 2019/9/17
*/
@Service
public class CategoryService implements ICategoryService
{
    @Autowired
    private ProductCategoryRepository repository;

    @Override
    public ProductCategory findById(Integer categoryId)
    {
        return repository.findById(categoryId).get();
    }

    @Override
    public List<ProductCategory> findAll()
    {
        return repository.findAll();
    }

    @Override
    public ProductCategory save(ProductCategory category)
    {
        return repository.save(category);
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypes)
    {
        return repository.findByCategoryTypeIn(categoryTypes);
    }
}
