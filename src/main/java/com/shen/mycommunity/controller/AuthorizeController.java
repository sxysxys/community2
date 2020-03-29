package com.shen.mycommunity.controller;

import com.shen.mycommunity.dto.AccessDto;
import com.shen.mycommunity.dto.GithubUser;
import com.shen.mycommunity.dto.UserToken;
import com.shen.mycommunity.enums.UserTypeEnum;
import com.shen.mycommunity.mapper.UserMapper;
import com.shen.mycommunity.model.User;
import com.shen.mycommunity.provider.GithubProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @Author: shenge
 * @Date: 2020-03-26 10:29
 * 处理github认证重定向的controller
 */

@Controller
public class AuthorizeController {

    @Autowired
    GithubProvider githubProvider;

    @Value("${github.client_id}")
    public String ciientId;

    @Value("${github.client_secret}")
    public String ciientSecret;

    @Autowired
    UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code", required = false) String code,
                           @RequestParam(name = "state", required = false) String state,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        AccessDto accessDto = new AccessDto();
        accessDto.setClient_id(ciientId);
        accessDto.setClient_secret(ciientSecret);
        accessDto.setCode(code);
        accessDto.setState(state);
        accessDto.setRedirect_uri("http://localhost:887/callback");
        String accessToken = githubProvider.getToken(accessDto);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser != null) {
            User user = userMapper.findByName(githubUser.getName(), UserTypeEnum.GITHUB_USER.getUserState()); //通过accountid去查
            if (user == null) {
                User user1 = new User();
                user1.setAccountId(String.valueOf(githubUser.getId()));
                user1.setName(githubUser.getName());
                user1.setUserType(UserTypeEnum.GITHUB_USER.getUserState());
                userMapper.insertUser(user1);   //如果登录成功将用户插入数据库
            }else {
                userMapper.updateUser(user);
            }
        }else {
            return "redirect:/";
        }

        //通过shiro进行管理
        Subject subject = SecurityUtils.getSubject();
        subject.login(new UserToken(githubUser.getName(), String.valueOf(githubUser.getId()), UserTypeEnum.GITHUB_USER.getUserState()));
        User user1 = (User)subject.getPrincipal();
        subject.getSession().setAttribute("user",user1);
        return "redirect:/";
    }
}
