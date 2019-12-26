package com.yezi.ssoapp2.util;

import com.yezi.ssoapp2.config.SsoConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SSOClientUtil {

    @Autowired
    private SsoConfig ssoConfig;


    /**
     * 当客户端请求被拦截,跳往统一认证中心,需要带redirectUrl的参数,统一认证中心登录后回调的地址
     */
    public String getRedirectUrl(HttpServletRequest request) {
        //获取请求URL
        return ssoConfig.getClientUrl() + request.getServletPath();
    }

    /**
     * 根据request获取跳转到统一认证中心的地址,通过Response跳转到指定的地址
     */
    public void redirectToSSOURL(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String redirectUrl = getRedirectUrl(request);
        StringBuilder url = new StringBuilder(50)
                .append(ssoConfig.getServerUrl())
                .append("/checkLogin?redirectUrl=")
                .append(redirectUrl);
        response.sendRedirect(url.toString());
    }


    /**
     * 获取客户端的完整登出地址
     */
    public String getClientLogOutUrl() {
        return ssoConfig.getClientUrl() + "/app2/logOut";
    }

    /**
     * 获取认证中心的登出地址
     */
    public String getServerLogOutUrl() {
        return ssoConfig.getServerUrl() + "/logOut";
    }
}
