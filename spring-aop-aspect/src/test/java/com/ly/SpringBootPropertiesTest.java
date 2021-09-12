package com.ly;

import com.common.service.UserService;
import com.ly.controller.HelloController;
import com.ly.service.GoodService;
import com.ly.service.ImportTestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class  SpringBootPropertiesTest {
	//@Autowired
	private GoodService goodService;

	@Autowired
	private HelloController controller;

	//@Autowired
	private ImportTestService importTestService;

	@Autowired
	UserService userService;

	@Test
	public void helloController(){
		userService.info();
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
