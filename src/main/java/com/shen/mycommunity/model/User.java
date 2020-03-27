package com.shen.mycommunity.model;

import lombok.Data;

import java.util.Date;

/**
 *  @Author: shenge
 *  @Date: 2020-03-27 20:32
 */
@Data
public class User {

    private Long id;

    private String name;

    private String accountId;

    private String token;  //与cookie比对

    private Date gmtCreate;

    private Date gmtModified;

}
