package com.wuhao.sell.repository;

import com.wuhao.sell.domain.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<SellerInfo,String> {
    SellerInfo findSellerInfoBySellerId(String sellerId);
    SellerInfo findSellerInfoBySellerNameAndSellerPassword(String sellerName,String sellerPassword);
}
