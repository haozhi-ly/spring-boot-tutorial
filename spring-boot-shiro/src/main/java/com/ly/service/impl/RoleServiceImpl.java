package com.ly.service.impl;/*
    @author ${user}
    @time 22:33
*/

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ly.dao.RoleMapper;
import com.ly.entity.Role;
import com.ly.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName RoleServiceImpl
 * @Author sky_
 * @Date 2019/6/9
 **/
@Service("roleService")
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public int insert(Role record) {
        return 0;
    }

    @Override
    public Role selectByPrimaryKey(Long id) {
        return null;
    }

    @Override
    public List<Role> selectAll() {
        return null;
    }

    @Override
    public int updateByPrimaryKey(Role record) {
        return 0;
    }

    @Override
    public PageInfo<Role> page(Role role, int pageNumber, int pageSize) {
        PageHelper.startPage(pageNumber,pageSize);
        List<Role> list = roleMapper.selectAll();
        return new PageInfo<>(list);
    }
}
