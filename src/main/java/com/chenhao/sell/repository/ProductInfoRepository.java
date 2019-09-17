package com.chenhao.sell.repository;

import com.chenhao.sell.dataObject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInfoRepository extends JpaRepository<ProductInfo,String>
{
    List<ProductInfo> findByProductStatus(Integer status);
}
