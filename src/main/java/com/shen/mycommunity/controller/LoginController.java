package com.shen.mycommunity.controller;

import com.shen.mycommunity.dto.UserToken;
import com.shen.mycommunity.enums.UserTypeEnum;
import com.shen.mycommunity.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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

    @PostMapping("/login")
    public String postLogin(String username, String password, Boolean remember){
        if (remember==null){
            remember=false;
        }
//        UsernamePasswordToken token = new UsernamePasswordToken(username, password, remember);  //创建一个token
        Subject subject = SecurityUtils.getSubject();
        subject.login(new UserToken(username,password,remember, UserTypeEnum.LOGIN_USER.getUserState()));
        User user = (User)subject.getPrincipal();
        subject.getSession().setAttribute("user",user);
        return "index";
    }

}
