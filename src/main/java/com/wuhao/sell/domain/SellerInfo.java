package com.wuhao.sell.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class SellerInfo {
    @Id
    private String sellerId;
    private String sellerName;
    private String sellerPassword;
}
