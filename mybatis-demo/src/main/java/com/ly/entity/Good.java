package com.ly.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
@Builder
@Data
public class Good {
    private Integer id;

    private String gname;

    private String serialnumber;

    private Double price;

    private Integer stockNumber;

    private Date createTime;


}