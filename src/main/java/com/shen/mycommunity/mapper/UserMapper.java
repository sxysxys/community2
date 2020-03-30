package com.shen.mycommunity.mapper;

        import com.shen.mycommunity.model.User;
        import org.apache.ibatis.annotations.Mapper;
        import org.apache.ibatis.annotations.Param;

/**
 * @Author: shenge
 * @Date: 2020-03-27 20:17
 */
@Mapper
public interface UserMapper {

    void insertUser(User user);
    //登录时候验证
    User findByName(@Param("userName") String userName, @Param("userState") String userState);

    void updateGithubUser(User user);

    void updateUser(User user);

}
