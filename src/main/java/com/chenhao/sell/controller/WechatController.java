package com.chenhao.sell.controller;

import com.chenhao.sell.enums.ResultEnum;
import com.chenhao.sell.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;

@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController
{
    @Autowired
    private WxMpService wxMpService;

    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl )
    {
        /**设置授权的回调url*/
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl("http://mrpour.natapp1.cc/sell/wechat/userInfo", WxConsts.OAuth2Scope.SNSAPI_USERINFO, URLEncoder.encode(returnUrl));
        log.info("微信获取授权地址后获取code，生成的重定向地址redirectUrl={}",redirectUrl);
        return "redirect:"+redirectUrl; //重定向到生成的这个地址,使用Controller注解
    }

    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code,
                         @RequestParam("state") String returnUrl)
    {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = null;
        try
        {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException exception)
        {
            log.error("【微信网页授权】{}",exception);
            SellException sellException = new SellException(ResultEnum.WX_MP_ERROR.getCode(),exception.getError().getErrorMsg());
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();
        log.info("【获得用户信息】+{}",openId);
        return "redirect:" + returnUrl + "?openid" + openId;
    }
}
