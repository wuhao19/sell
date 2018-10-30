package com.wuhao.sell.repository;

import com.wuhao.sell.domain.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {

    Page<OrderMaster> findByOrderOpenid(String orderOpenid, Pageable pageable);
    OrderMaster findOrderMasterByOrderId(String orderId);

}
