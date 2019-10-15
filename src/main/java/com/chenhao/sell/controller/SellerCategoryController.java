package com.chenhao.sell.controller;

import com.chenhao.sell.dataObject.ProductCategory;
import com.chenhao.sell.enums.ResultEnum;
import com.chenhao.sell.exception.SellException;
import com.chenhao.sell.form.CategoryForm;
import com.chenhao.sell.service.impl.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

/**
 * @title 卖家端类目
 * @author Chenhao
 * @date 2019/10/15
 */
@Controller
@RequestMapping("/seller/category")
@Slf4j
public class SellerCategoryController
{
    @Autowired
    private CategoryService categoryService;
    /**
     * @title 查询类目列表
     * @Description 由于类目不是很多，所以不需要分页显示
     * @author Chenhao
     * @date 2019/10/15
     */
    @RequestMapping("/list")
    public ModelAndView list(Map<String,Object> map)
    {
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList",categoryList);
        return new ModelAndView("category/list",map);
    }

    /**
     * @title 待修改/新增类目展示与编辑
     * @param categoryId 类目编号
     * @author Chenhao
     * @date 2019/10/15
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(name = "categoryId",required = false) Integer categoryId,
                              Map<String,Object> map)
    {
        //不为空，说明是修改
        if(null != categoryId)
        {
            ProductCategory productCategory = categoryService.findById(categoryId);
            map.put("category",productCategory);
        }
        return new ModelAndView("category/index",map);

    }

    /**
     * @title 修改/新增类目提交
     * @param categoryForm 类目表单数据
     * @author Chenhao
     * @date 2019/10/15
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid CategoryForm categoryForm,
                             BindingResult bindingResult,
                             Map<String,Object> map)
    {
        /**如果前端页面数据输入错误，继续留在新增界面*/
        if(bindingResult.hasErrors())
        {
            log.info("【卖家新增/修改类目】前端数据校验失败，productId={}",categoryForm.getCategoryId());
            map.put("errorMsg",bindingResult.getFieldError().getDefaultMessage());
            map.put("returnUrl","/sell/seller/category/index");
            return new ModelAndView("/common/error",map);
        }

        //不破坏自增的顺序
        ProductCategory productCategory = null;
        try
        {
            if(null!=categoryForm.getCategoryId())
            {
                /**如果是修改,需要保留创建时间*/
                productCategory = categoryService.findById(categoryForm.getCategoryId());
            }else
            {
                /**如果是新建，则需要创建实体*/
                productCategory = new ProductCategory();
            }
            BeanUtils.copyProperties(categoryForm,productCategory);
            categoryService.save(productCategory);
        }
        catch (SellException e)
        {
            log.info("【卖家修改/更新类目】更新失败，categoryId={}",categoryForm.getCategoryId());
            map.put("errorMsg",e.getMessage());
            map.put("returnUrl","/sell/seller/category/index");
            return new ModelAndView("common/error",map);
        }
        map.put("successMsg", ResultEnum.SUCCESS.getMsg());
        map.put("returnUrl","/sell/seller/category/list");
        return new ModelAndView("common/success",map);
    }
}
