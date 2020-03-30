package com.shen.mycommunity.shiro.service;

import com.shen.mycommunity.mapper.UserMapper;
import com.shen.mycommunity.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: shenge
 * @Date: 2020-03-29 20:00
 */
@Component
public class LoginService {
    @Autowired
    UserMapper userMapper;

    public User findByName(String userName, String userState) {
        return userMapper.findByName(userName,userState);
    }

    public void updateUser(User user) {
        userMapper.updateUser(user);
    }
}
