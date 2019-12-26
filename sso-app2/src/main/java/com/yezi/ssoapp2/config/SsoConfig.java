package com.yezi.ssoapp2.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description: 单点登录配置
 * @Author: yezi
 * @Date: 2019/12/24 16:51
 */
@Data
@Component
@ConfigurationProperties(prefix = "sso")
public class SsoConfig {
    /**
     * 单点登录服务端
     */
    private String serverUrl;
    /**
     * 单点登录客户端
     */
    private String clientUrl;
}
