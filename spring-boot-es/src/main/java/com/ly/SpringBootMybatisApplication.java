package com.ly;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.elasticsearch.rest.RestClientAutoConfiguration;

@SpringBootApplication(exclude ={RestClientAutoConfiguration.class})
public class SpringBootMybatisApplication
{

	public static void main(String[] args){
		SpringApplication.run(SpringBootMybatisApplication.class, args);
	}
}
