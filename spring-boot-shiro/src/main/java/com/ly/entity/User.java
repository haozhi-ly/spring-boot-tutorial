package com.ly.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class User {
    private Long id;

    private String username;

    private String password;

    private String salt;

    private String email;

    private String mobile;

    private Byte status;

    private Byte hasDelete;

    private Date createTime;

    private List<Role> roleList;


}