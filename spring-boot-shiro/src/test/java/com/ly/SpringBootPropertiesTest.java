package com.ly;

import com.github.pagehelper.PageInfo;
import com.ly.entity.Permission;
import com.ly.entity.Role;
import com.ly.entity.User;
import com.ly.service.PermissionService;
import com.ly.service.RoleService;
import com.ly.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootPropertiesTest {
	@Autowired
	private RoleService roleService;
	@Autowired
	private PermissionService permissionService;

	@Autowired
	private UserService userService;

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

	@Test
	public void selectPermission(){
		Permission permission = new Permission();
		List<Integer> roleIds = new ArrayList<>();
		roleIds.add(1);
		roleIds.add(2);

		permission.setRoleIds(roleIds);
		List<Permission> permissionList = permissionService.selectPermission(permission);
		System.out.println(permissionList);
	}

	@Test
	public void login(){
		User user = new User();
		user.setUsername("ly");
		user.setPassword("a");

		User result = userService.login(user);
		System.out.println(result);
	}

	@Test
	public void forTest(){
		List<Permission> list = null;
		for(Permission p : list){
			System.out.println(p.toString());
		}
	}

	public static void main(String[] args){
		System.out.println("");
	}
}	
