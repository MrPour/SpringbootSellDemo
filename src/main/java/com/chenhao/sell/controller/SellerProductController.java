package com.chenhao.sell.controller;

import com.chenhao.sell.dataObject.ProductInfo;
import com.chenhao.sell.enums.ResultEnum;
import com.chenhao.sell.exception.SellException;
import com.chenhao.sell.service.impl.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/seller/product")
@Slf4j
public class SellerProductController
{
    @Autowired
    private ProductService productService;

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
}
