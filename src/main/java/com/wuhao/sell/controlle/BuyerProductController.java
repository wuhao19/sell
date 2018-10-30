package com.wuhao.sell.controlle;

import com.wuhao.sell.VO.ProductInfoVo;
import com.wuhao.sell.VO.ProductVo;
import com.wuhao.sell.VO.ResultVo;
import com.wuhao.sell.domain.ProductCategory;
import com.wuhao.sell.domain.ProductInfo;
import com.wuhao.sell.service.ProductCategoryService;
import com.wuhao.sell.service.ProductService;
import com.wuhao.sell.util.ResultVoUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;


/**
 * 买家商品控制
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService categoryService;

    @GetMapping("/list")
    public ResultVo list(){
        //1.查询所有的上架商品
        List<ProductInfo> productInfoList = productService.findUpAll();
        System.out.println(productInfoList);
        //2.查询类目
        List<Integer> categoryTypeList = new ArrayList<>();
        for (ProductInfo productInfo : productInfoList){
            categoryTypeList.add(productInfo.getCategoryType());
        }
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);
        //3.数据的拼装
        List<ProductVo> productVoList = new ArrayList<>();
        for (ProductCategory productCategory:productCategoryList){
            ProductVo productVo = new ProductVo();
            productVo.setCategoryType(productCategory.getCategoryType());
            productVo.setCategoryName(productCategory.getCategoryName());

            List<ProductInfoVo> productInfoVoList = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList){
                if(productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoVo productInfoVo = new ProductInfoVo();
                    BeanUtils.copyProperties(productInfo,productInfoVo);
                    productInfoVoList.add(productInfoVo);
                }
            }
            productVo.setCategoryFood(productInfoVoList);
            productVoList.add(productVo);
        }
        return ResultVoUtil.success(productVoList);
    }
}
