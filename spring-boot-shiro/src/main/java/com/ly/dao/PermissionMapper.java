package com.ly.dao;

import com.ly.entity.Permission;
import com.ly.entity.Role;

import java.util.List;

public interface PermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Permission record);

    Permission selectByPrimaryKey(Long id);

    List<Permission> selectAll();

    int updateByPrimaryKey(Permission record);

    /**
     * 根据角色id 获取权限列表
     * @param role
     * @return
     */
    List<Permission> getPermissionTree(Role role);

    List<Permission> queryPermissionByPermission(Permission permission);
}