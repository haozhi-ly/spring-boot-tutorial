package com.ly.service.impl;

import com.ly.dao.PermissionMapper;
import com.ly.entity.Permission;
import com.ly.entity.Role;
import com.ly.service.PermissionService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> selectPermission(Permission permission) {
        return permissionMapper.selectPermission(permission);
    }

    @Override
    public List<Permission> getPermissionTree(Role role) {
        return permissionMapper.getPermissionTree(role);
    }

    @Override
    public List<Map<String,Object>> getPermissionTreeToJson(Role role) {
        List<Permission> permissionList = getPermissionTree(role);
        Permission paramPermission = new Permission();
        paramPermission.setRid(role.getCurrentRid());
        paramPermission.setIds("1,2,3");
        List<Permission> allocatePermissionList = permissionMapper.queryPermissionByPermission(paramPermission);
        boolean disable = (true ? allocatePermissionList == null || allocatePermissionList.size() != 3 : true);
        List<Map<String,Object>> mapList = new ArrayList<>();

        if(permissionList != null){
            for(Permission permission:permissionList){
                Map<String,Object> mapNode = new HashMap<>();
                mapNode.put("id",permission.getId());
                mapNode.put("pid",permission.getParentId());
                mapNode.put("name",permission.getName());
                mapNode.put("chkDisabled",disable);
                mapNode.put("isParent",permission.getType() == 0 ? true : false);

                if(permission.getType() == 0){
                    List<Map<String,Object>> childrenResult = getChildrenNodeToJosn(permission,disable);
                    mapNode.put("children",childrenResult);
                }
                mapNode.put("checked",(permission.getRpId() != null) ? true : false);
                mapList.add(mapNode);
            }
        }

        return mapList;
    }

    private List<Map<String,Object>> getChildrenNodeToJosn(Permission p,boolean disable){
        List<Map<String,Object>> mapList = new ArrayList<>();

        if(p.getChildrenPermission() != null) {

            for (Permission permission : p.getChildrenPermission()) {
                Map<String, Object> mapNode = new HashMap<>();
                mapNode.put("id", permission.getId());
                mapNode.put("pid", permission.getParentId());
                mapNode.put("name", permission.getName());
                mapNode.put("chkDisabled", disable);
                mapNode.put("isParent", permission.getType() == 0 ? true : false);
                if (permission.getType() == 0) {
                    List<Map<String, Object>> childrenResult = getChildrenNodeToJosn(p, disable);
                    mapNode.put("children", childrenResult);
                }
                mapNode.put("checked", (permission.getRpId() != null) ? true : false);
                mapList.add(mapNode);
            }
        }
        return mapList;
    }

    @Override
    public List<Permission> queryPermissionByPermission(Permission permission) {
        return permissionMapper.queryPermissionByPermission(permission);
    }

    @Override
    public int addPermission(Permission p) {
        return permissionMapper.addPermission(p);
    }

    @Override
    @Transactional
    public int allocatePermission(Integer roleId, List<Integer> pidList) {
        int result = 0;
        result = permissionMapper.clearPermissionByRoleId(roleId);

        if(pidList != null){
            result = permissionMapper.allocatePermission(roleId, pidList);
        }
        return result;
    }

    @Override
    public int clearPermissionByRoleId(int roleId) {
        return 0;
    }

    @Override
    public boolean checkPermissionIsExist(String permissionUrl) {
        Subject subject = SecurityUtils.getSubject();
        boolean result = false;
        return subject.isPermitted(permissionUrl);
    }


}
