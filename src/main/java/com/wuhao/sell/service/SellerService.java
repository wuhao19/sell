package com.wuhao.sell.service;

import com.wuhao.sell.domain.SellerInfo;

import java.util.List;

public interface SellerService {
    SellerInfo findOneSellerInfoById(String sellerId);
    List<SellerInfo> findAllSeller();
    SellerInfo saveSellerInfo(SellerInfo sellerInfo);
    SellerInfo login(String sellerName,String sellerPassword);
}
