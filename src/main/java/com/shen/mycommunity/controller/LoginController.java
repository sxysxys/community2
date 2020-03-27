package com.shen.mycommunity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 处理所有登录请求的controller
 */

@Controller
public class LoginController {

    @GetMapping("/login")
    public String Login(){
        return "login/log";
    }

    @GetMapping("/register")
    public String register(){
        return "login/register";
    }

    @GetMapping("/forgot")
    public String forgot(){
        return "login/forgot";
    }

}
