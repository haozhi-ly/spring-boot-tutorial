package com.ly.entity;

import lombok.*;

import java.util.Date;
@Builder
@Setter
@Getter
@ToString
@AllArgsConstructor
public class Good {
    private Integer id;

    private String gname;

    private String serialnumber;

    private Double price;

    private Integer stockNumber;

    private Date createTime;

    public Good() {
    }
}