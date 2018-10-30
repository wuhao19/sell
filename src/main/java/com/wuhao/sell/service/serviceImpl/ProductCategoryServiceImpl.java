package com.wuhao.sell.service.serviceImpl;

import com.wuhao.sell.domain.ProductCategory;
import com.wuhao.sell.repository.ProductCategoryRepository;
import com.wuhao.sell.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品类目的实现类
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    private ProductCategoryRepository categoryRepository;

    @Override
    public ProductCategory findOne(Integer categoryId) {
        return categoryRepository.findById(categoryId).get();
    }

    @Override
    public List<ProductCategory> findALL() {
        return categoryRepository.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return categoryRepository.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return categoryRepository.save(productCategory);
    }
}
