package com.chenhao.sell.service;

import com.chenhao.sell.dataObject.ProductCategory;

import java.util.List;

public interface ICategoryService
{
    //卖家端
    /**根据主键查询对应的种类*/
    ProductCategory findById(Integer categoryId);

    /**查询所有的种类*/
    List<ProductCategory> findAll();

    /**新增种类*/
    ProductCategory save(ProductCategory category);

    //买家端
    /**根据种类编号获得对应的种类*/
    List<ProductCategory> findByCategoryTypeIn (List<Integer> categoryTypes);
}
