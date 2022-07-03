package com.ly;

import com.ly.entity.Good;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootPropertiesTest {

	@Autowired(required = false)
	private RedisTemplate<String,Object> redisTemplate;

	@Test
	public void pbTest(){
		Map<String,Object> map = new HashMap<>();
		map.put("one",1);
		map.put("two","yes");
		map.put("three",true);
		List<Good> goodList = new ArrayList<>();
		goodList.add(Good.builder().id(1).price(100.0).build());
		goodList.add(Good.builder().id(2).price(200.0).build());
		map.put("four",goodList);
		for(Map.Entry<String,Object> entry:map.entrySet()){
			byte[] serilizeBytes = ProtoStuffUtil.serializer(entry.getValue());
			Object deserilizeObj = null;
			Class clazz = entry.getValue().getClass();
			if(clazz.isAssignableFrom(Integer.class)){
				deserilizeObj = ProtoStuffUtil.deserializer(serilizeBytes,Integer.class);
			}else if(clazz.isAssignableFrom(String.class)){
				deserilizeObj = ProtoStuffUtil.deserializer(serilizeBytes,String.class);
			}else if(clazz.isAssignableFrom(ArrayList.class)){
				deserilizeObj = ProtoStuffUtil.deserializer(serilizeBytes,List.class);
			}
			System.out.println(String.format("key:%s,value:%s",entry.getKey(),deserilizeObj));

		}

		byte[] sers = ProtoStuffUtil.serializer(map);
		Map newMap = (Map) ProtoStuffUtil.deserializerToObj(sers,HashMap.class);
		System.out.println(newMap);
	}



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
