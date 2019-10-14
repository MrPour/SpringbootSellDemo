package com.chenhao.sell.service;

import com.chenhao.sell.dataObject.ProductInfo;
import com.chenhao.sell.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService
{
    /**根据id查询商品*/
    ProductInfo findById(String id);

    /**查找所有上架商品信息*/
    List<ProductInfo> findUpAll();

    /**分页查询所有商品信息*/
    Page<ProductInfo> findAll(Pageable pageable);

    /**新增商品信息*/
    ProductInfo save(ProductInfo productInfo);

    /**上架商品*/
    ProductInfo onSale(String orderId);

    /**下架商品*/
    ProductInfo offSale(String orderId);

    /**为一批商品加库存*/
    //按订单去批量加减效率更高
    void increaseStock(List<CartDTO> cartDTOList);

    /**为一批商品减库存*/
    void decreaseStock(List<CartDTO> cartDTOList);
}
