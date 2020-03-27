package com.shen.mycommunity.controller;

import com.shen.mycommunity.dto.AccessDto;
import com.shen.mycommunity.dto.GithubUser;
import com.shen.mycommunity.mapper.UserMapper;
import com.shen.mycommunity.model.User;
import com.shen.mycommunity.provider.GithubProvider;
import org.apache.ibatis.annotations.Mapper;
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
        System.out.println(accessToken);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser != null) {
            User user = new User();
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setName(githubUser.getName());
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setGmtCreate(new Date());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insertUser(user);   //如果登录成功将用户插入数据库
            response.addCookie(new Cookie("token", token));
//            request.getSession().setAttribute("user", githubUser);
        }
        return "redirect:/";
    }
}
