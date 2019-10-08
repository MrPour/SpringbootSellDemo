package com.chenhao.sell.Config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "wechat")
/**专门从从yml中获取前缀为wechat的数据，分离出来更方便*/
public class WechatAccountConfig
{
    /**配置测试号相关信息*/
    private String mpAppId;
    private String mpAppSecret;
    /**商户号*/
    private String mchId;
    /**商户密钥*/
    private String mchKey;
    /**商户证书路径*/
    private String keyPath;
    /**微信支付异步通知地址*/
    private String notifyUrl;

}
