package com.ly;

import com.github.pagehelper.PageInfo;
import com.ly.dao.test.TblTestMapper;
import com.ly.entity.Good;
import com.ly.entity.TblTest;
import com.ly.service.DataSourcePropertiesBean;
import com.ly.service.GoodService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class SpringBootPropertiesTest {
	@Autowired
	private GoodService goodService;

	@Autowired
	private TblTestMapper mapper;

	@Autowired
	private DataSourcePropertiesBean dataSourcePropertiesBean;

	@Test
	public void test(){
		/*PageInfo<Good> list = goodService.page(null,1,10);
		System.out.println(list.getList());*/

		System.out.println(0%4);
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

		List<String> l=new ArrayList<>();
		//l.subList()

	}
	@Test
	public void c(){
		/*TblTest tblTest = mapper.selectByPrimaryKey(151);
		System.out.println(tblTest);
		TblTest test = new TblTest();
		test.setTname("ly");
		mapper.insert(test);*/
		System.out.println(mapper.selectAll(TblTest.builder()
        .address("1").build()));
	}
	@Test
	public void d(){
		System.out.println(dataSourcePropertiesBean);
	}
}	
