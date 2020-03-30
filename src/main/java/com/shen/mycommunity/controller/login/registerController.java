package com.shen.mycommunity.controller.login;

import com.shen.mycommunity.shiro.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Author: shenge
 * @Date: 2020-03-30 10:15
 */

@Controller
public class registerController {

    @Autowired
    RegisterService registerService;


    @GetMapping("/register")
    public String register() {
        return "login/register";
    }

    @PostMapping("/register")
    public String postRegister(String name,String password) {
        registerService.register(name,password);
        return "redirect:/login";
    }

}
