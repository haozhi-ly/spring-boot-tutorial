package com.ly.config;/*
    @author ${user}
    @time 21:30
*/

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.ly.dao")
public class DbConfig {
    /*@Bean
    @ConfigurationProperties(prefix="spring.datasource.druid")
    public DataSource datasource(){
        DruidDataSource dataSource = new DruidDataSource();
        return dataSource;
    }*/


}
