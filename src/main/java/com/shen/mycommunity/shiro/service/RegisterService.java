package com.shen.mycommunity.shiro.service;

import com.shen.mycommunity.enums.UserTypeEnum;
import com.shen.mycommunity.mapper.UserMapper;
import com.shen.mycommunity.model.User;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: shenge
 * @Date: 2020-03-30 10:26
 */

@Component
public class RegisterService {
    @Autowired
    UserMapper userMapper;

    public void register(String name, String password) {
        User user = new User();
        user.setUserType(UserTypeEnum.LOGIN_USER.getUserState());
        user.setName(name);
        String salt = new SecureRandomNumberGenerator().nextBytes().toHex();
        String password1 = new SimpleHash("MD5", password, name+salt, 1024).toHex();  //salt=username+salt
        user.setSalt(salt);  //根据username加盐。
        user.setPassword(password1);
        userMapper.insertUser(user);
    }
}
