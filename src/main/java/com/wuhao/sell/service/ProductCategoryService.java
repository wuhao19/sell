package com.wuhao.sell.service;

import com.wuhao.sell.domain.ProductCategory;

import java.util.List;

public interface ProductCategoryService {
    /**
     * 查询一个类目
     * @param categoryId
     * @return
     */
    ProductCategory findOne(Integer categoryId);

    /**
     * 查询所有类目
     * @return
     */
    List<ProductCategory> findALL();

    /**
     * 根据类目类型查询
     * @param categoryTypeList
     * @return
     */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    /**
     * 更新和保存类目
     * @param productCategory
     * @return
     */
    ProductCategory save(ProductCategory productCategory);
}
