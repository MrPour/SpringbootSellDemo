package com.chenhao.sell.Config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "wechat") //从yml中获取前缀为wechat的数据
public class WechatAccountConfig
{
    /**配置测试号相关信息*/
    private String mpAppId;
    private String mpAppSecret;
}
