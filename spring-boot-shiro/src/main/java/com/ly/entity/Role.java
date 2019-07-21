package com.ly.entity;

import lombok.Data;

import java.util.Date;
@Data
public class Role {
    private Integer id;

    private String roleName;

    private String remark;

    private Byte hasDelete;

    private Date createTime;




}