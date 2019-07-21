package com.ly.dao;

import com.ly.entity.RolePermission;
import java.util.List;

public interface RolePremissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RolePermission record);

    RolePermission selectByPrimaryKey(Long id);

    List<RolePermission> selectAll();

    int updateByPrimaryKey(RolePermission record);
}