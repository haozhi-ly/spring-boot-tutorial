package com.ly;

import com.github.pagehelper.PageInfo;
import com.ly.entity.Role;
import com.ly.service.PermissionService;
import com.ly.service.RoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootPropertiesTest {
	@Autowired
	private RoleService roleService;
	@Autowired
	private PermissionService permissionService;

	@Test
	public void test(){
		PageInfo<Role> list = roleService.page(null,1,10);
		System.out.println(list.getList());
	}

	@Test
	public void getPermissionTreeTest(){
		Role r = new Role();
		r.setId(1);
		List<Map<String,Object>> permissionList = permissionService.getPermissionTreeToJson(r);
		System.out.println(permissionList);
	}

	public static void main(String[] args){
		System.out.println("");
	}
}	
