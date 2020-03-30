package com.shen.mycommunity.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *  @Author: shenge
 *  @Date: 2020-03-27 20:32
 */
@Data
public class User implements Serializable {

    private Long id;

    private String name;

    private String accountId;

    private String password;   //

    private String salt;

    private String userType;

    private Date gmtCreate;

    private Date gmtModified;

}
