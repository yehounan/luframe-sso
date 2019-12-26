package com.yezi.ssoserver.mockdb;

import java.util.*;

/**
 * @Description: 单点登陆模拟数据库
 * @Author: yezi
 * @Date: 2019/12/24 16:02
 */
public class MockDB {

    /**
     * 保存token信息
     */
    public static Set<String> T_TOKEN = new HashSet<String>();

    /**
     * 保存用户的信息, 登出的地址，session:ClientInfo    Map<String,List<ClientInfo>>
     */
    public static Map<String, List<ClientInfo>> T_CLIENT_INFO = new HashMap<String, List<ClientInfo>>();

}
