package com.ly.service;

import com.github.pagehelper.PageInfo;
import com.ly.entity.Role;

import java.util.List;

/*
    @author ${user}
    @time 22:32
*/
public interface RoleService {
    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    Role selectByPrimaryKey(Long id);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);

    PageInfo<Role> page(Role role,int pageNumber,int pageSize);
}
