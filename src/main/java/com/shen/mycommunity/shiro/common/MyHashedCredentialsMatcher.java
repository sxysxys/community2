package com.shen.mycommunity.shiro.common;

import com.shen.mycommunity.dto.UserToken;
import com.shen.mycommunity.enums.UserTypeEnum;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

/**
 * @Author: shenge
 * @Date: 2020-03-30 15:50
 *
 * 为了解决github登录的密码验证。
 */

public class MyHashedCredentialsMatcher extends HashedCredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
        UserToken authcToken1 = (UserToken) authcToken;
        if (authcToken1.getLoginType().equals(UserTypeEnum.LOGIN_USER.getUserState())){
            return super.doCredentialsMatch(authcToken, info);
        }else if (authcToken1.getLoginType().equals(UserTypeEnum.GITHUB_USER.getUserState())){
            return true;
        }else {
            System.out.println("没拿到值");
            return false;
        }
    }
}
