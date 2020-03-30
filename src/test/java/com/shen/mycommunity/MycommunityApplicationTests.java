package com.shen.mycommunity;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MycommunityApplicationTests {

    @Test
    void contextLoads() {
        String algorithmName = "md5";
        String username = "shen@c";
        String password = "123456";
        int hashIterations = 1024;
        ByteSource credentialsSalt = ByteSource.Util.bytes(username);  //通过username作为盐值
        SimpleHash hash = new SimpleHash(algorithmName, password,
                credentialsSalt, hashIterations);
        String encodedPassword = hash.toHex();
        System.out.println(encodedPassword);
    }

}
