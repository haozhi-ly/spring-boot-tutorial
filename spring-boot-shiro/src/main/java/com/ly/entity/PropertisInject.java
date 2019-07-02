package com.ly.entity;/*
    @author ${user}
    @time 21:07
*/

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@ToString

public class PropertisInject {
    private String url;
    private String myName;

    public void setName(String name) {
        this.myName = name;
    }
}
