package com.chenhao.sell.service;

import com.chenhao.sell.dataObject.ProductCategory;

import java.util.List;

public interface ICategoryService
{
    //卖家端
    public ProductCategory findById(Integer categoryId);

    public List<ProductCategory> findAll();

    public ProductCategory save(ProductCategory category);

    //买家端
    public List<ProductCategory> findByCategoryTypeIn (List<Integer> categoryTypes);
}
