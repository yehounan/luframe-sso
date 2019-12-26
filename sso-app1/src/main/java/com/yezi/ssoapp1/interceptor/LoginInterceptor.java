package com.yezi.ssoapp1.interceptor;

import com.yezi.ssoapp1.config.SsoConfig;
import com.yezi.ssoapp1.util.SSOClientUtil;
import com.yezi.ssocommon.util.HttpRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * @Description:
 * @Author: yezi
 * @Date: 2019/12/24 16:39
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private SsoConfig ssoConfig;

    @Autowired
    private SSOClientUtil ssoClientUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession httpSession = request.getSession();
        Boolean isLogin = (Boolean) httpSession.getAttribute("isLogin");
        if (null != isLogin && isLogin) {
            return true;
        }
        // 客户端拦截器中，判断token参数
        String token = request.getParameter("token");
        System.out.println("单点登录服务器token=" + token);

        if (!StringUtils.isEmpty(token)) {
            //防止token信息，被伪造，我们还需要去服务器验证！
            System.out.println("检测token信息");
            //拿着这个token再一次去服务器请求验证,服务器验证方法
            String httpURL = ssoConfig.getServerUrl() + "/verify";
            //需要验证参数
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("token", token);
            params.put("clientUrl", ssoClientUtil.getClientLogOutUrl());
            params.put("jsessionid", httpSession.getId());
            try {
                //发起一个请求
                String isVerify = HttpRequestUtil.doRequest(httpURL, "POST", params);
                System.out.println("isVerify=" + isVerify);
                //说明这个 token 是统一认证中心产生的！
                if ("true".equals(isVerify)) {
                    System.out.println("验证通过");
                    httpSession.setAttribute("isLogin", true);
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        //不能放行，没有会话信息，登录
        ssoClientUtil.redirectToSSOURL(request, response);
        return false;
    }
}
