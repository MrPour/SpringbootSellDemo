package com.chenhao.sell.repository;

import com.chenhao.sell.dataObject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMasterRepository extends JpaRepository<OrderMaster,String>
{
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenId,Pageable pageable);
}
