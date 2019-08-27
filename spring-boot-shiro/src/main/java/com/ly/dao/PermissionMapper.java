package com.ly.dao;

import com.ly.entity.Permission;
import com.ly.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionMapper {
    int deleteByPrimaryKey(Long id);

    Permission selectByPrimaryKey(Long id);

    List<Permission> selectAll(Permission permission);

    List<Permission> selectPermission(Permission permission);

    int updateByPrimaryKey(Permission record);

    /**
     * 根据角色id 获取权限列表
     * @param role
     * @return
     */
    List<Permission> getPermissionTree(Role role);

    List<Permission> queryPermissionByPermission(Permission permission);

    /**
     * 添加权限
     * @param p
     * @return
     */
    int addPermission(Permission p);

    /**
     * 根据角色id清除前面的权限
     * @param roleId
     * @return
     */
    int clearPermissionByRoleId(Integer roleId);

    int allocatePermission(@Param("roleId") Integer roleId,
                           @Param("pidList") List<Integer> pidList);
}