package com.wuhao.sell.repository;

import com.wuhao.sell.domain.ProductCategory;
import com.wuhao.sell.util.Comme;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {
    @Autowired
    private ProductCategoryRepository repository;
    @Test
    public void findOneTest(){
        ProductCategory productCategory = repository.findById(2).get();
        productCategory.setCategoryType(5);
        repository.save(productCategory);
    }

    @Test
    public void addOneTest(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("女神最爱");
        productCategory.setCategoryType(4);
        repository.save(productCategory);
    }

    @Test
    @Transactional
    public void updateTest(){
        ProductCategory productCategory = new ProductCategory("小孩最爱",8);
        ProductCategory result = repository.save(productCategory);
        Assert.assertNotEquals(null,result);
    }

    @Test
    public void findByCategoryTypeInTest(){
        List<Integer> list = Arrays.asList(1);
        List<ProductCategory> result = repository.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0,result.size());
    }

}