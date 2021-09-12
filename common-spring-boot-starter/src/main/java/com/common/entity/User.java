package com.common.entity;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ToString
@ConfigurationProperties(prefix = "com.common.user",ignoreUnknownFields = true)
public class User {
    private Integer id;

    private String name;
    private int age;



}