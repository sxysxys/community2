package com.shen.mycommunity.dto;

import lombok.Data;

/**
 *  @Author: shenge
 *  @Date: 2020-03-26 00:03
 */
@Data
public class GithubUser {
    private Long id;   //拿到的用户id
    private String bio;
    private String name;    //github的登录名
}
