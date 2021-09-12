package com.ly;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;

@Lazy
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DruidDataSourceAutoConfigure.class, MybatisAutoConfiguration.class})
//@Import(value = ImportTestService.class)
@EnableScheduling
public class SpringBootMybatisApplication
{

	public static void main(String[] args){
		SpringApplication.run(SpringBootMybatisApplication.class, args);
	}
}
