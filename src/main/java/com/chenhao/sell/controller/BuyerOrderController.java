package com.chenhao.sell.controller;

import com.chenhao.sell.Utils.ResultVOUtil;
import com.chenhao.sell.converter.OrderForm2OrderDTOConverter;
import com.chenhao.sell.dto.OrderDTO;
import com.chenhao.sell.enums.ResultEnum;
import com.chenhao.sell.exception.SellException;
import com.chenhao.sell.form.OrderForm;
import com.chenhao.sell.service.impl.BuyerService;
import com.chenhao.sell.service.impl.OrderService;
import com.chenhao.sell.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController
{
    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    /**创建订单*/
    @PostMapping("/create")
    public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm,
                                                    BindingResult bindingResult)
    {
        /**1、校验前台表单是否满足要求*/
        if(bindingResult.hasErrors())
        {
         log.error("【创建订单】参数不正确，orderForm={}",orderForm);
         //返回OrderForm里自定义的错误信息
         throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                                bindingResult.getFieldError().getDefaultMessage());
        }

        /**2、表单数据转换成DTO*/
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);


        //判空（其实前端传入的时候已经做了validation了，所以很安全）
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetails()))
        {
           log.error("【创建订单】购物车不能为空");
           throw new SellException(ResultEnum.CART_EMPTY_ERROR);
        }

        /**3、创建订单*/
        OrderDTO result = orderService.create(orderDTO);

        /**4、返回结果*/
        Map<String,String> map = new HashMap<>();
        map.put("orderId",result.getOrderId());

        return ResultVOUtil.success(map);
    }

    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page",defaultValue = "0") Integer page,
                                         @RequestParam(value = "size",defaultValue = "10") Integer size)
    {
        if(StringUtils.isEmpty(openid))
        {
            log.error("【查询订单列表】openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<OrderDTO> pageResult = orderService.findByOpenId(openid, pageRequest);

        return ResultVOUtil.success(pageResult);

    }

    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId)
    {
        OrderDTO orderDTO = buyerService.findOrderOne(openid,orderId);
        return ResultVOUtil.success(orderDTO);
    }

    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId)
    {
        buyerService.cancelOrder(openid,orderId);
        return ResultVOUtil.success();
    }
}
