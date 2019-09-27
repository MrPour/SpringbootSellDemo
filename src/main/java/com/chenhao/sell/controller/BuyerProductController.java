package com.chenhao.sell.controller;

import com.chenhao.sell.Utils.ResultVOUtil;
import com.chenhao.sell.dataObject.ProductCategory;
import com.chenhao.sell.dataObject.ProductInfo;
import com.chenhao.sell.service.impl.CategoryService;
import com.chenhao.sell.service.impl.ProductService;
import com.chenhao.sell.vo.ProductInfoVO;
import com.chenhao.sell.vo.ProductVO;
import com.chenhao.sell.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController
{
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ResultVO<List<ProductVO>> findUpAll()
    {
        /**这里是一次性查出，而不是单独按照type去查找，效率更高*/
        List<ProductInfo> productInfos = productService.findUpAll();

        List<Integer> collect = productInfos.stream()
                .map(a -> a.getCategoryType())
                .collect(toList());

        List<ProductCategory> productCategories = categoryService.findByCategoryTypeIn(collect);

        List<ProductVO> productVOList = new ArrayList<>();

        /**VO的拼装*/
        for(ProductCategory category:productCategories)
        {
            ProductVO productVO = new ProductVO();

            /**类目表提供类目的种类和名字*/
            productVO.setCategoryType(category.getCategoryType());
            productVO.setCategoryName(category.getCategoryName());

            List<ProductInfoVO> list = new ArrayList<>();
            for(ProductInfo productInfo:productInfos)
            {
                if(category.getCategoryType().equals(productInfo.getCategoryType()))
                {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    //把对应的属性拷贝过去
                    BeanUtils.copyProperties(productInfo,productInfoVO);
                    list.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(list);
            productVOList.add(productVO);
        }

        return ResultVOUtil.success(productVOList);
    }
}
