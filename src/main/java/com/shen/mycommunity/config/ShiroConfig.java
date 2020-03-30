package com.shen.mycommunity.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.shen.mycommunity.shiro.common.MyHashedCredentialsMatcher;
import com.shen.mycommunity.shiro.realm.UserRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

/**
 * @Author: shenge
 * @Date: 2020-03-28 22:36
 * shiro配置类
 */

@Configuration
public class ShiroConfig {

    // 身份认证失败地址
    @Value("${shiro.user.loginUrl}")
    private String loginUrl;

    // 权限认证失败地址
    @Value("${shiro.user.unauthorizedUrl}")
    private String unauthorizedUrl;

    /**
     * 密码校验规则HashedCredentialsMatcher
     * 这个类是为了对密码进行编码的 ,
     * 防止密码在数据库里明码保存 , 当然在登陆认证的时候 ,
     * 这个类也负责对form里输入的密码进行编码
     * 处理认证匹配处理器：如果自定义需要实现继承HashedCredentialsMatcher
     */
//    @Bean("hashedCredentialsMatcher")
//    public HashedCredentialsMatcher hashedCredentialsMatcher() {
//        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
//        //指定加密方式为MD5
//        credentialsMatcher.setHashAlgorithmName("MD5");
//        //加密次数
//        credentialsMatcher.setHashIterations(1024);
//        credentialsMatcher.setStoredCredentialsHexEncoded(true);
//        return credentialsMatcher;
//    }

    @Bean("MyHashedCredentialsMatcher")
    public MyHashedCredentialsMatcher myHashedCredentialsMatcher() {
        MyHashedCredentialsMatcher credentialsMatcher = new MyHashedCredentialsMatcher();
        //指定加密方式为MD5
        credentialsMatcher.setHashAlgorithmName("MD5");
        //加密次数
        credentialsMatcher.setHashIterations(1024);
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }

    @Bean("userRealm")
    public UserRealm userRealm(@Qualifier("MyHashedCredentialsMatcher") MyHashedCredentialsMatcher myhashedCredentialsMatcher) {
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(myhashedCredentialsMatcher);
        return userRealm;
    }

    /**
     * 不返回jsessionid。
     * @return
     */
    @Bean
    public DefaultWebSessionManager sessionManager(){
        DefaultWebSessionManager webSessionManager = new DefaultWebSessionManager();
        webSessionManager.setSessionIdUrlRewritingEnabled(false);
        return webSessionManager;
    }


    @Bean
    public SecurityManager securityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(userRealm);
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }


    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 身份认证失败，则跳转到登录页面的配置
        shiroFilterFactoryBean.setLoginUrl(loginUrl);
        // 权限认证失败，则跳转到指定页面
        shiroFilterFactoryBean.setUnauthorizedUrl(unauthorizedUrl);

        // Shiro连接约束配置，即过滤链的定义
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 不需要拦截的访问
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/fonts/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/callback", "anon");
        filterChainDefinitionMap.put("https://github.com/**", "anon");
        filterChainDefinitionMap.put("/register", "anon");

        //支持记住我认证
        filterChainDefinitionMap.put("/**", "user");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * thymeleaf模板引擎和shiro框架的整合
     */
    @Bean
    public ShiroDialect shiroDialect()
    {
        return new ShiroDialect();
    }
}
