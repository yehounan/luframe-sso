package com.yezi.ssoserver.listener;

import com.yezi.ssocommon.util.HttpRequestUtil;
import com.yezi.ssoserver.mockdb.ClientInfo;
import com.yezi.ssoserver.mockdb.MockDB;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.List;

/**
 * @Description:
 * @Author: yezi
 * @Date: 2019/12/25 16:56
 */
@WebListener
public class SsoSessionListner implements HttpSessionListener {


    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("sso session创建");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("sso session销毁");
        HttpSession httpSession = se.getSession();
        String token = (String) httpSession.getAttribute("token");
        //清除sso服务端数据库token
        MockDB.T_TOKEN.remove(token);
        List<ClientInfo> clientInfos = MockDB.T_CLIENT_INFO.get(token);
        //便利服务端保存客户端信息，通知客户端清除登录信息
        for (ClientInfo clientInfo : clientInfos) {

            try {
                System.out.println("通知退出客户端url=" + clientInfo.getClientUrl());
                HttpRequestUtil.sendHttpRequest(clientInfo.getClientUrl(), clientInfo.getJsessionid());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
