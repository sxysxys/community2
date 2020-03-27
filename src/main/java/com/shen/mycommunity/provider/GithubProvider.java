package com.shen.mycommunity.provider;


import com.alibaba.fastjson.JSON;
import com.shen.mycommunity.dto.AccessDto;
import com.shen.mycommunity.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * github登录的类
 */
@Component
public class GithubProvider {
    /**
     * 通过okhttp模拟客户端给服务器的请求，并且接受到相应的数据。
     * @param accessDto
     * @return
     */
    public String getToken(AccessDto accessDto) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessDto));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String token = response.body().string();
            String split = token.split("&")[0].split("=")[1];
            return split;  //默认拿到了access_token
        }catch (Exception e){
            System.out.println("没拿到token");
        }
        return null;
    }

    public GithubUser getUser(String token){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + token)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String jsonstr = response.body().string();
            GithubUser githubUser = JSON.parseObject(jsonstr, GithubUser.class);
            return githubUser;
        }catch (IOException e){
            System.out.println("没拿到用户");
        }
        return null;
    }

}
