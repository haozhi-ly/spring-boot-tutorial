package com.ly;

import com.ly.dao.CityMapper;
import com.ly.entity.City;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootPropertiesTest {


	@Autowired
	private CityMapper cityMapper;

	@Test
	public void a(){
		City cityList = cityMapper.selectByPrimaryKey(1);
		System.out.println(cityList);
	}


}	
