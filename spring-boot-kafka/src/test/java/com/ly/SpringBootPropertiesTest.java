package com.ly;
import com.ly.controller.HelloController;
import com.ly.kafka.KafkaConsumerTest;
import com.ly.kafka.KafkaProducerTest;
import com.ly.service.GoodService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

@RunWith(SpringRunner.class)
@SpringBootTest
public class  SpringBootPropertiesTest {
	//@Autowired
	private GoodService goodService;

	@Autowired
	private HelloController controller;

	/*@Autowired
	private ImportTestService importTestService;*/

	@Autowired
	private KafkaProducerTest kafkaProducerTest;

	@Autowired
	private KafkaConsumerTest kafkaConsumerTest;

	@Test
	public void helloController() throws InterruptedException {
		//System.out.println(goodService);
		//System.out.println(controller.hello1("yes"));
		//importTestService.importTest();
		kafkaProducerTest.send();
		kafkaConsumerTest.consumer();
		CountDownLatch countDownLatch = new CountDownLatch(1);




		countDownLatch.await();
	}


	/*@Test
	public void test(){
		PageInfo<Good> list = goodService.page(null,1,10);
		System.out.println(list.getList());
	}
	@Test
	public void a(){
		PageInfo<Good> list = goodService.page(null,1,10);
		System.out.println(list.getList());
	}
	@Test
	public void b(){
		PageInfo<Good> list = goodService.page(null,1,10);
		System.out.println(list.getList());
	}*/
}	
