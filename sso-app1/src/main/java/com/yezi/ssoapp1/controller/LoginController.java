package com.yezi.ssoapp1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @Description:
 * @Author: yezi
 * @Date: 2019/12/24 16:21
 */
@Controller
public class LoginController {


    @RequestMapping("/app1")
    public String index() {
        return "index";
    }

    @RequestMapping("/app1/logOut")
    public String logout(HttpSession session) {
        System.out.println("app1登出");
        session.invalidate();
        return "redirect:/app1";
    }


}
