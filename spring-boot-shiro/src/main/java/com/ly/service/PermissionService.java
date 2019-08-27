package com.ly.service;

import com.ly.entity.Permission;
import com.ly.entity.Role;

import java.util.List;
import java.util.Map;

public interface PermissionService {

    List<Permission> selectPermission(Permission permission);
    List<Permission> getPermissionTree(Role role);

    List<Map<String,Object>> getPermissionTreeToJson(Role role);

    List<Permission> queryPermissionByPermission(Permission permission);

    int addPermission(Permission p);

    int allocatePermission(Integer roleId, List<Integer> pidList);

    int clearPermissionByRoleId(int roleId);
}
