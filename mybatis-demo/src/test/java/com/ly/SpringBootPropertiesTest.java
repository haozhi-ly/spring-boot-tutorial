package com.ly;
import com.github.pagehelper.PageInfo;
import com.ly.entity.Good;
import com.ly.service.GoodService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootPropertiesTest {
	@Autowired
	private GoodService goodService;

	@Test
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
	}
}	
