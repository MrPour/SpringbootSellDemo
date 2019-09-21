package com.chenhao.sell.service;

import com.chenhao.sell.dataObject.ProductCategory;

import java.util.List;

public interface ICategoryService
{
    //卖家端
    ProductCategory findById(Integer categoryId);

    List<ProductCategory> findAll();

    ProductCategory save(ProductCategory category);

    //买家端
    List<ProductCategory> findByCategoryTypeIn (List<Integer> categoryTypes);
}
