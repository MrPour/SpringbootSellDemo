package com.chenhao.sell.service;

import com.chenhao.sell.dataObject.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService
{
    public ProductInfo findById(String id);

    /**查找所有上架商品信息*/
    public List<ProductInfo> findUpAll();

    /**分页查询所有商品信息*/
    //这里返回的是page对象,而不是list
    public Page<ProductInfo> findAll(Pageable pageable);

    public ProductInfo save(ProductInfo productInfo);

    /**加库存*/
    /**减库存*/
}
