package com.wuhao.sell.service.serviceImpl;

import com.wuhao.sell.domain.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryServiceImplTest {
    @Autowired
    private ProductCategoryServiceImpl productCategoryService;
    @Test
    public void findOne() {
        ProductCategory productCategory = productCategoryService.findOne(1);
        Assert.assertEquals(new Integer(1),productCategory.getCategoryId());
    }

    @Test
    public void findALL() {
        List<ProductCategory> list = productCategoryService.findALL();
        Assert.assertNotEquals(0,list.size());
    }

    @Test
    public void findByCategoryTypeIn() {
        List<Integer> list = Arrays.asList(2,3,4);
        List<ProductCategory> productCategoryList = productCategoryService.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0,productCategoryList.size());
    }

    @Test
    public void save() {
        ProductCategory result = productCategoryService.save(new ProductCategory("热辣榜",2));
        Assert.assertNotEquals(null,result);
    }
}