package com.ly.service;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Builder
@Setter
@Getter
@ToString
@AllArgsConstructor
@Component
@ConfigurationProperties("spring.datasource")
public class DataSourcePropertiesBean {
    private String name;
    private String url;
    private String username;
    private String password;

    public DataSourcePropertiesBean() {
    }
}