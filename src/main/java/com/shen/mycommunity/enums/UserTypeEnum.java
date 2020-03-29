package com.shen.mycommunity.enums;
/**
 *  @Author: shenge
 *  @Date: 2020-03-28 23:39
 */

public enum UserTypeEnum {

    GITHUB_USER("1"),
    LOGIN_USER("2");

    private String userState;

    public String getUserState() {
        return userState;
    }

    UserTypeEnum(String userState){
        this.userState=userState;
    }
}
