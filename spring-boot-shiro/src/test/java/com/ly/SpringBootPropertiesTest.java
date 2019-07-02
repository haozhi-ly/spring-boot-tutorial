package com.ly;
import com.github.pagehelper.PageInfo;
import com.ly.entity.Role;
import com.ly.service.RoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootPropertiesTest {
	@Autowired
	private RoleService roleService;

	@Test
	public void test(){
		PageInfo<Role> list = roleService.page(null,1,10);
		System.out.println(list.getList());
	}
}	
