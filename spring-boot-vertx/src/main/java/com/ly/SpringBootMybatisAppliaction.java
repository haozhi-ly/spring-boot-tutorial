package com.ly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;

import java.util.concurrent.CountDownLatch;

@SpringBootApplication(exclude = {RedisAutoConfiguration.class})
public class SpringBootMybatisAppliaction
{

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(SpringBootMybatisAppliaction.class, args);
		CountDownLatch countDownLatch = new CountDownLatch(1);
		countDownLatch.await();
	}
}
