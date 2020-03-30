package com.shen.mycommunity.shiro.service;

import com.shen.mycommunity.enums.UserTypeEnum;
import com.shen.mycommunity.mapper.UserMapper;
import com.shen.mycommunity.model.User;
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
        user.setPassword(password);
        userMapper.insertUser(user);
    }
}
