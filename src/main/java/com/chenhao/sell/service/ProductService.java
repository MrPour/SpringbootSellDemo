package com.chenhao.sell.service;

import com.chenhao.sell.dataObject.ProductInfo;
import com.chenhao.sell.enums.ProductStatusEnum;
import com.chenhao.sell.repository.ProductInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService
{
    @Autowired
    private ProductInfoRepository repository;

    @Override
    public ProductInfo findById(String id)
    {
        return repository.findById(id).get();
    }

    @Override
    public List<ProductInfo> findUpAll()
    {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable)
    {
        return repository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }
}
