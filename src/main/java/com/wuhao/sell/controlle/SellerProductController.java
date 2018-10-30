package com.wuhao.sell.controlle;

import com.wuhao.sell.domain.ProductCategory;
import com.wuhao.sell.domain.ProductInfo;
import com.wuhao.sell.enums.ProductStatusEnums;
import com.wuhao.sell.enums.ResultEnum;
import com.wuhao.sell.excption.SellExcption;
import com.wuhao.sell.form.ProductForm;
import com.wuhao.sell.service.ProductCategoryService;
import com.wuhao.sell.service.ProductService;
import com.wuhao.sell.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.startup.CatalinaProperties;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("seller/product")
@Slf4j
public class SellerProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 查询所有的商品
     * @param page
     * @param size
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                             @RequestParam(value = "size",defaultValue = "5")Integer size,
                             Map<String,Object> map){
        PageRequest pageRequest = new PageRequest(page-1,size);
        Page<ProductInfo> productInfoPage = productService.findAll(pageRequest);
        map.put("productInfoPage",productInfoPage);
        map.put("currentPage",page);
        map.put("size",size);
        return new ModelAndView("product/list",map);
    }

    /**
     * 下架商品
     * @param prouctId
     * @param map
     * @return
     */
    @GetMapping("/lowerFrame")
    public ModelAndView lowerFrame(@RequestParam("productId") String prouctId,Map<String,Object> map){
        try {
            productService.lowerFrame(prouctId);
        }catch (SellExcption e){
            log.error("【商品下架错误】message={}",e.getMessage());
            return new ModelAndView("common/error");
        }
        map.put("msg", ResultEnum.SUCCESS);
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }

    /**
     * 上架商品
     * @param productId
     * @param map
     * @return
     */
    @GetMapping("/upFrame")
    public ModelAndView upFrame(@RequestParam("productId") String productId,Map<String,Object> map){
        try {
            productService.upFrame(productId);
        }catch (SellExcption e){
            log.error("【商品上架错误】message={}",e.getMessage());
            return new ModelAndView("common/error");
        }
        map.put("msg", ResultEnum.SUCCESS);
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }

    /**
     * 新增和修改商品
     * @param productId
     * @param map
     * @return
     */
    @GetMapping("/addProduct")
    public ModelAndView addProduct(@RequestParam(value = "productId",required = false)String productId,
                           Map<String,Object> map){
        if(productId!=null){
            try {
                ProductInfo productInfo = productService.findOne(productId);
                map.put("productInfo",productInfo);
            }catch (SellExcption e){
                map.put("msg",e.getMessage());
            }
        }
        List<ProductCategory> productCategoryList = productCategoryService.findALL();
        map.put("productCategoryList",productCategoryList);
        return new ModelAndView("product/addProduct",map);
    }

    /**
     * 保存商品
     * @param form
     * @param bindingResult
     * @param map
     * @return
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid ProductForm form, BindingResult bindingResult,Map<String,Object> map){
        ProductInfo productInfo = new ProductInfo();
        if (bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/sell/seller/product/addProduct");
            return new ModelAndView("common/error",map);
        }
        try {
            if (!StringUtils.isEmpty(form.getProductId())){
                productInfo = productService.findOne(form.getProductId());
                BeanUtils.copyProperties(form,productInfo);
            }else {
                String key = KeyUtil.getUniqueKey();
                BeanUtils.copyProperties(form,productInfo);
                productInfo.setProductId(key);
            }
            productService.save(productInfo);
        }catch (SellExcption excption){
            map.put("msg",excption.getMessage());
            map.put("url","/sell/seller/product/addProduct");
            return new ModelAndView("common/error",map);
        }
        map.put("msg","操作商品数据成功！");
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }
}
