package com.shen.mycommunity.dto;

import lombok.Data;

/**
 * github登录获取access_token
 */

@Data
public class AccessDto {
    private String client_id;  //github上注册应用的用户id
    private String client_secret;
    private String code;
    private String redirect_uri;  //这个有点误导人的意思
    private String state;  //第一次传后回来的state，需要再发一次从而验证身份
}
