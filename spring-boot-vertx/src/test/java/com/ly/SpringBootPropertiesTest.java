package com.ly;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootPropertiesTest {

	@Autowired
	private RedisTemplate<String,Object> redisTemplate;

	@Test
	public void redisTest() throws InterruptedException {

		CountDownLatch countDownLatch = new CountDownLatch(2);
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		for(int i=0;i<2;i++){
			int finalI = i;
			executorService.submit(()->{
				redisTemplate.opsForValue().set("test"+ finalI,"123");
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(redisTemplate.opsForValue().get("test"+ finalI));
				//Thread.sleep(10000);
				countDownLatch.countDown();
			});
		}
		countDownLatch.await();
		System.out.println("task execute success");

		redisTemplate.opsForValue().set("test","123");
	}
}	
