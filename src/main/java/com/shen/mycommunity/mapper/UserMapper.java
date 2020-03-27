package com.shen.mycommunity.mapper;

import com.shen.mycommunity.model.User;
import org.apache.ibatis.annotations.Mapper;
/**
 *  @Author: shenge
 *  @Date: 2020-03-27 20:17
 */
@Mapper
public interface UserMapper {

    void insertUser(User user);

}
