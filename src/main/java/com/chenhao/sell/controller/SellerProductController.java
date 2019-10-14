package com.chenhao.sell.controller;

import com.chenhao.sell.dataObject.ProductCategory;
import com.chenhao.sell.dataObject.ProductInfo;
import com.chenhao.sell.enums.ResultEnum;
import com.chenhao.sell.exception.SellException;
import com.chenhao.sell.form.ProductForm;
import com.chenhao.sell.service.impl.CategoryService;
import com.chenhao.sell.service.impl.ProductService;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/seller/product")
@Slf4j
public class SellerProductController
{
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    /**
     * @title 查询订单列表
     * @param page 当前第几页，从1开始
     * @param size 每页多少条
     * @author Chenhao
     * @date 2019/10/10
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value = "size",defaultValue = "10") Integer size,
                             Map<String,Object> map)
    {
        PageRequest pageRequest = PageRequest.of(page-1, size);
        Page<ProductInfo> pageResult = productService.findAll(pageRequest);
        map.put("pageResult",pageResult);
        map.put("currentPageNo",page);
        map.put("pageSize",size);
        return new ModelAndView("product/list",map);
    }

    /**
     * @title 商品上架
     * @param productId 商品编号
     * @param page 当前页
     * @author Chenhao
     * @date 2019/10/12
     */
    @GetMapping("/on_sale")
    public ModelAndView onSale(@RequestParam(name = "productId") String productId,
                               @RequestParam(value = "page") Integer page,
                               Map<String,Object> map)
    {
        try
        {
            ProductInfo productInfo = productService.onSale(productId);
        }
        catch (SellException e)
        {
            log.info("【卖家上架商品失败】商品上架失败，productId={}",productId);
            map.put("errorMsg",e.getMessage());
            map.put("returnUrl","/sell/seller/product/list?page="+page);
            return new ModelAndView("/common/error",map);
        }
        map.put("successMsg", ResultEnum.SUCCESS.getMsg());
        map.put("returnUrl","/sell/seller/product/list?page="+page);
        return new ModelAndView("/common/success",map) ;
    }

    /**
     * @title 商品下架
     * @param productId 商品编号
     * @param page 当前页
     * @author Chenhao
     * @date 2019/10/12
     */
    @GetMapping("/off_sale")
    public ModelAndView offSale(@RequestParam(name = "productId") String productId,
                               @RequestParam(value = "page") Integer page,
                               Map<String,Object> map)
    {
        try
        {
            ProductInfo productInfo = productService.offSale(productId);
        }
        catch (SellException e)
        {
            log.info("【卖家下架商品失败】商品下架失败，productId={}",productId);
            map.put("errorMsg",e.getMessage());
            map.put("returnUrl","/sell/seller/product/list?page="+page);
            return new ModelAndView("/common/error",map);
        }
        map.put("successMsg", ResultEnum.SUCCESS.getMsg());
        map.put("returnUrl","/sell/seller/product/list?page="+page);
        return new ModelAndView("/common/success",map) ;
    }

    /**
     * @title 待修改/新增商品展示与编辑
     * @param productId 商品编号
     * @author Chenhao
     * @date 2019/10/14
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(name = "productId",required = false) String productId,
                              Map<String,Object> map)
    {
        if(!StringUtils.isEmpty(productId))
        {
            ProductInfo productInfo = productService.findById(productId);
            map.put("productInfo",productInfo);
        }
        List<ProductCategory> productCategoryList = categoryService.findAll();
        map.put("categories",productCategoryList);
        return new ModelAndView("/product/index",map);
    }

    /**
     * @title 修改/新增商品提交
     * @param productForm 商品表单数据
     * @author Chenhao
     * @date 2019/10/14
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid ProductForm productForm,
                             BindingResult bindingResult,
                             Map<String,Object> map)
    {
        /**如果前端页面数据输入错误，继续留在新增界面*/
        if(bindingResult.hasErrors())
        {
            log.info("【卖家新增/修改商品】前端数据校验失败，productId={}",productForm.getProductId());
            map.put("errorMsg",bindingResult.getFieldError().getDefaultMessage());
            map.put("returnUrl","/sell/seller/product/index");
            return new ModelAndView("/common/error",map);
        }
        /**没有出错则存入*/
        try
        {
            ProductInfo productInfo = productService.findById(productForm.getProductId());
            BeanUtils.copyProperties(productForm,productInfo);
            productService.save(productInfo);
        }
        catch (SellException e)
        {
            log.info("【卖家新增/修改商品】新增/修改失败，productId={}",productForm.getProductId());
            map.put("errorMsg",e.getMessage());
            map.put("returnUrl","/sell/seller/product/index");
            return new ModelAndView("/common/error",map);
        }
        map.put("successMsg", ResultEnum.SUCCESS.getMsg());
        map.put("returnUrl","/sell/seller/product/list");
        return new ModelAndView("/common/success",map);
    }
}
