package com.shen.mycommunity.controller;

import com.shen.mycommunity.dto.AccessDto;
import com.shen.mycommunity.dto.GithubUser;
import com.shen.mycommunity.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *  @Author: shenge
 *  @Date: 2020-03-26 10:29
 *  处理github认证重定向的controller
 *
 */

@Controller
public class AuthorizeController {

    @Autowired
    GithubProvider githubProvider;

    @Value("${github.client_id}")
    public String ciientId;

    @Value("${github.client_secret}")
    public String ciientSecret;




    @GetMapping("/callback")
    public String callback(@RequestParam(name="code", required=false) String code,
                           @RequestParam(name="state", required=false) String state){
        AccessDto accessDto = new AccessDto();
        accessDto.setClient_id(ciientId);
        accessDto.setClient_secret(ciientSecret);
        accessDto.setCode(code);
        accessDto.setState(state);
        String token = githubProvider.getToken(accessDto);
        System.out.println(token);
        GithubUser user = githubProvider.getUser(token);
        System.out.println(user);
        return "index";
    }
}
