package com.shen.mycommunity.dto;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @Author: shenge
 * @Date: 2020-03-29 19:20
 */
public class UserToken extends UsernamePasswordToken {
    //判断是哪种登录方法
    private String loginType;

    // 通过login登录
    public UserToken(String userName, String password, boolean rememberMe, String loginType) {
        super(userName, password, rememberMe);
        this.loginType = loginType;
    }
    // 通过github登录
    public UserToken(String userName, String password, String loginType) {
        super(userName, password);
        this.loginType = loginType;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }
}
