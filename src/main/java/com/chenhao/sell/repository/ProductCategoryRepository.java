package com.chenhao.sell.repository;

import com.chenhao.sell.dataObject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer>
{

}
