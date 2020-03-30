package com.shen.mycommunity.shiro.realm;

import com.shen.mycommunity.dto.UserToken;
import com.shen.mycommunity.enums.UserTypeEnum;
import com.shen.mycommunity.model.User;
import com.shen.mycommunity.shiro.service.LoginService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *  @Author: shenge
 *  @Date: 2020-03-28 19:20
 */

public class UserRealm extends AuthorizingRealm {

    @Autowired
    LoginService loginService;

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 认证，执行login后会进来这里
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UserToken token = (UserToken) authenticationToken;  //拿到用户创建的的token
        //此时有两种情况，github登录和账号登录
        if (token.getLoginType().equals(UserTypeEnum.LOGIN_USER.getUserState())){  //普通登录
            String username = token.getUsername();
            User user=loginService.findByName(username,UserTypeEnum.LOGIN_USER.getUserState());
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,user.getPassword(), getName());  //交给安全管理器进行管理了。
            return info;
        }else if (token.getLoginType().equals(UserTypeEnum.GITHUB_USER.getUserState())){  //github登录
            String username = token.getUsername();
            User user=loginService.findByName(username,UserTypeEnum.GITHUB_USER.getUserState());
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,user.getAccountId(), getName());
            return info;
        }
        return null;
    }
}
