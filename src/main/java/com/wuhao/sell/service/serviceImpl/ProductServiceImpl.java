package com.wuhao.sell.service.serviceImpl;

import com.wuhao.sell.domain.ProductInfo;
import com.wuhao.sell.dto.CartDto;
import com.wuhao.sell.enums.ProductStatusEnums;
import com.wuhao.sell.enums.ResultEnum;
import com.wuhao.sell.excption.SellExcption;
import com.wuhao.sell.repository.ProductRepository;
import com.wuhao.sell.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    /**
     * 查找一个商品
     * @param productId
     * @return
     */
    @Override
    public ProductInfo findOne(String productId) {
        try {
            ProductInfo productInfo= productRepository.findProductInfoByProductId(productId);
            return productInfo;
        }catch (Exception e){
            throw new SellExcption(ResultEnum.PRODUCT_NOT_EXIST);
        }
    }

    /**
     * 查找所有的商品
     * @param pageable
     * @return
     */
    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    /**
     * 增加商品的库存
     * @param cartDtoList
     */
    @Override
    @Transactional
    public void increaseStock(List<CartDto> cartDtoList) {
        for (CartDto cartDto : cartDtoList){
            ProductInfo productInfo = productRepository.findById(cartDto.getProductId()).get();
            if (productInfo==null){
                throw new SellExcption(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result= productInfo.getProductStock()+cartDto.getProductQuantity();
            productInfo.setProductStock(result);
            productRepository.save(productInfo);
        }
    }

    /**
     * 减少商品的库存
     * @param cartDtoList
     */
    @Override
    @Transactional
    public void decreaserStock(List<CartDto> cartDtoList) {
        for (CartDto cartDto : cartDtoList){
            ProductInfo productInfo = productRepository.findById(cartDto.getProductId()).get();
            if(productInfo==null){
                throw new SellExcption(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result= productInfo.getProductStock() - cartDto.getProductQuantity();
            if (result<0){
                throw new SellExcption(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            productRepository.save(productInfo);
        }
    }

    /**
     * 查询所有上架的商品
     * @return
     */
    @Override
    public List<ProductInfo> findUpAll() {
        return productRepository.findByProductStatus(ProductStatusEnums.UP.getCode());
    }

    /**
     * 更新商品和增加商品
     * @param productInfo
     * @return
     */
    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return productRepository.save(productInfo);
    }

    /**
     * 商品的下架
     * @param productId
     */
    @Override
    public ProductInfo lowerFrame(String productId) {
        ProductInfo productInfo = productRepository.findProductInfoByProductId(productId);
        if(productInfo==null){
            throw new SellExcption(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if(productInfo.getProductStatusEnums().getMessage()=="下架"){
            throw new SellExcption(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(1);
        ProductInfo result = productRepository.save(productInfo);
        return result;
    }

    /**
     * 商品的上架
     * @param productId
     */
    @Override
    public ProductInfo upFrame(String productId) {
        ProductInfo productInfo = productRepository.findProductInfoByProductId(productId);
        if(productInfo==null){
            throw new SellExcption(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if(productInfo.getProductStatusEnums().getMessage()=="在架"){
            throw new SellExcption(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(0);
        ProductInfo result = productRepository.save(productInfo);
        return result;
    }
}
