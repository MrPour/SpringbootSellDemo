package com.chenhao.sell.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/weixin")
@Slf4j
public class WeixinController
{
    @GetMapping("/auth")
    public void getAuth(@RequestParam("code") String code)
    {
        log.info("进入log方法");
        log.info("code={}",code);
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx45eba76af086fdc5&secret=c3559f39fdb32617f97b54c483e5333b&code="
                +code+"&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        log.info("response={}",response);

    }
}
