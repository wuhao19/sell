package com.wuhao.sell.service.serviceImpl;

import com.wuhao.sell.domain.SellerInfo;
import com.wuhao.sell.enums.ResultEnum;
import com.wuhao.sell.excption.SellExcption;
import com.wuhao.sell.repository.SellerRepository;
import com.wuhao.sell.service.SellerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SellerServiceImpl implements SellerService {
    @Autowired
    private SellerRepository sellerRepository;
    /**
     * 查找一个买家端用户
     * @param sellerId
     * @return
     */
    @Override
    public SellerInfo findOneSellerInfoById(String sellerId) {
        SellerInfo sellerInfo = sellerRepository.findSellerInfoBySellerId(sellerId);
        if (null==sellerInfo){
            throw new SellExcption(ResultEnum.SELLER_NOT_EXIST.getCode(),ResultEnum.SELLER_NOT_EXIST.getMessage());
        }
        return sellerInfo;
    }
    /**
     * 查找所有的用户列表
     * @return
     */
    @Override
    public List<SellerInfo> findAllSeller() {
        List<SellerInfo> sellerInfoList = sellerRepository.findAll();
        if (sellerInfoList.size()==0){
            throw new SellExcption(ResultEnum.SELLER_NOT_EXIST.getCode(),ResultEnum.SELLER_NOT_EXIST.getMessage());
        }
        return sellerInfoList;
    }

    /**
     * 保存和更新用户
     * @param sellerInfo
     * @return
     */
    @Override
    public SellerInfo saveSellerInfo(SellerInfo sellerInfo) {
        SellerInfo result = sellerRepository.save(sellerInfo);
        if (null == result){
            throw new SellExcption(ResultEnum.SELLER_NOT_EXIST.getCode(),ResultEnum.SELLER_NOT_EXIST.getMessage());
        }
        return result;
    }

    /**
     * 用户登录验证
     * @param sellerName
     * @param sellerPassword
     * @return
     */
    @Override
    public SellerInfo login(String sellerName, String sellerPassword) {
        SellerInfo sellerInfo = sellerRepository.findSellerInfoBySellerNameAndSellerPassword(sellerName, sellerPassword);
        if(sellerInfo==null){
            throw new SellExcption(ResultEnum.SELLER_NOT_EXIST);
        }
        return sellerInfo;
    }
}
