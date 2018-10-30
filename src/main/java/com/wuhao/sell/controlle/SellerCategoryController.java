package com.wuhao.sell.controlle;

import com.wuhao.sell.domain.ProductCategory;
import com.wuhao.sell.excption.SellExcption;
import com.wuhao.sell.form.CategoryForm;
import com.wuhao.sell.service.ProductCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/seller/category")
@Slf4j
public class SellerCategoryController {
    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 查询所有的商品类目
     * @param page
     * @param size
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "0",required = false)Integer page,
                             @RequestParam(value = "size",defaultValue = "10",required = false)Integer size,
                             Map<String,Object> map){
        try {
            List<ProductCategory> categoryList = productCategoryService.findALL();
            map.put("categoryList",categoryList);
        }catch (SellExcption e){
            log.error("【买家端类目】查询所有类目出现错误");
            map.put("msg", e.getMessage());
            map.put("url","/sell/seller/category/list");
            return new ModelAndView("common/error",map);
        }
        return new ModelAndView("category/list",map);
    }

    /**
     * 跳转填写表单页面，修改或者新增
     * @param categoryId
     * @param map
     * @return
     */
    @GetMapping("/addCategory")
    public ModelAndView addCategory(@RequestParam(value = "categoryId",required = false) Integer categoryId,Map<String,Object> map){
        ProductCategory category = new ProductCategory();
        try {
            if (null != categoryId){
                category = productCategoryService.findOne(categoryId);
                map.put("category",category);
            }else {
                map.put("category",category);
            }
        }catch (SellExcption e){
            log.error("【买家端类目】查询1个类目出现错误");
            map.put("msg", e.getMessage());
            map.put("url","/sell/seller/category/list");
            return new ModelAndView("common/error",map);
        }
        return new ModelAndView("category/index",map);
    }

    /**
     * 保存类目
     * @param form
     * @param map
     * @return
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid CategoryForm form, Map<String, Object> map){
        ProductCategory productCategory = new ProductCategory();
        BeanUtils.copyProperties(form,productCategory);
        try {
            productCategoryService.save(productCategory);
            map.put("msg","操作类目成功！");
            map.put("url","/sell/seller/category/list");
        }catch (SellExcption e){
            log.error("【卖家端类目】保存类目出现错误");
            map.put("msg", e.getMessage());
            map.put("url","/sell/seller/category/list");
            return new ModelAndView("common/error",map);
        }
       return new ModelAndView("common/success",map);
    }
}
