package com.chenhao.sell.controller;

import com.chenhao.sell.Utils.ResultVOUtil;
import com.chenhao.sell.dataObject.ProductCategory;
import com.chenhao.sell.dataObject.ProductInfo;
import com.chenhao.sell.repository.ProductCategoryRepository;
import com.chenhao.sell.service.ProductService;
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
    private ProductCategoryRepository productCategoryRepository;

    @GetMapping("/list")
    public ResultVO findUpAll()
    {
        List<ProductInfo> productInfos = productService.findUpAll();

        List<Integer> collect = productInfos.stream()
                .map(a -> a.getCategoryType())
                .collect(toList());

        List<ProductCategory> productCategories = productCategoryRepository.findByCategoryTypeIn(collect);

        List<ProductVO> productVOList = new ArrayList<>();

        /**VO的拼装*/
        for(ProductCategory category:productCategories)
        {
            ProductVO productVO = new ProductVO();
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
