package com.ly;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.client.codec.StringCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootRedissonTest {
	@Autowired
	private Redisson redisson;
	@Autowired
	private RedisTemplate<String,String> redisTemplate;

	@Test
	public void test(){
		redisson.getKeys().flushall();
		RMap<String,String> rMap = redisson.getMap("map", StringCodec.INSTANCE);
		rMap.put("one","1");

		BoundHashOperations<String,String,String> hash = redisTemplate
		.boundHashOps("map");
		String mapOneVal = hash.get("one");
		System.out.println(mapOneVal);
	}



	@Test
	public void distributeLockTest() throws IOException {

		String distributeTestKey = "dis_test_key";

		Thread t = new Thread(new ConsumerLockTest(redisson,distributeTestKey));
		t.setName("thread 1");
		t.start();
		while (true){

		}
	}

	@Test
	public void distributeLockTest2() throws IOException {

		String distributeTestKey = "dis_test_key";

		Thread t = new Thread(new ConsumerLockTest(redisson,distributeTestKey));
		t.setName("thread 2");
		t.start();
		while (true){

		}

	}

	@Test
	public void distributeLockTest3() throws IOException {

		String distributeTestKey = "dis_test_key";

		Thread t = new Thread(new ConsumerLockTest(redisson,distributeTestKey));
		t.setName("thread 3");
		t.start();
		while (true){

		}

	}

	public class ConsumerLockTest implements Runnable{
		private Redisson redisson;
		private String distributeTestKey;

		public ConsumerLockTest(Redisson redisson, String distributeTestKey) {
			this.redisson = redisson;
			this.distributeTestKey = distributeTestKey;
		}
		@Override
		public void run() {

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			RLock lock = redisson.getLock(distributeTestKey);
			lock.lock();
			try {

				System.out.println(format.format(new Date())+","+Thread.currentThread().getName()+":获取了锁");
				Thread.sleep(25000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}finally {
				lock.unlock();
				System.out.println(format.format(new Date())+","+Thread.currentThread().getName()+":释放了锁");
			}
		}
	}

}	
