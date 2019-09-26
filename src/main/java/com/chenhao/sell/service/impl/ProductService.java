package com.chenhao.sell.service.impl;

import com.chenhao.sell.dataObject.ProductInfo;
import com.chenhao.sell.dto.CartDTO;
import com.chenhao.sell.enums.ProductStatusEnum;
import com.chenhao.sell.enums.ResultEnum;
import com.chenhao.sell.exception.SellException;
import com.chenhao.sell.repository.ProductInfoRepository;
import com.chenhao.sell.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList)
    {
        for (CartDTO cartDTO : cartDTOList)
        {
            Optional<ProductInfo> byId = repository.findById(cartDTO.getProductId());
            ProductInfo productInfo = byId.get();

            if(null == productInfo)
            {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXISTS);
            }

            Integer result = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(result);

            repository.save(productInfo);
        }

    }

    @Override
    @Transactional //只要有一次出异常，就回滚
    public void decreaseStock(List<CartDTO> cartDTOList)
    {
        for (CartDTO cartDto : cartDTOList)
        {
            Optional<ProductInfo> byId = repository.findById(cartDto.getProductId());
            ProductInfo productInfo = byId.get();

            if(null == productInfo)
            {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXISTS);
            }

            Integer result = productInfo.getProductStock() - cartDto.getProductQuantity();

            if(result < 0)
            {
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }

            productInfo.setProductStock(result);

            repository.save(productInfo);
        }

    }
}
