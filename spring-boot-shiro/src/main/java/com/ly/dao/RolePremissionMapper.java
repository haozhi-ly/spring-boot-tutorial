package com.ly.dao;

import com.ly.entity.RolePremission;
import java.util.List;

public interface RolePremissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RolePremission record);

    RolePremission selectByPrimaryKey(Long id);

    List<RolePremission> selectAll();

    int updateByPrimaryKey(RolePremission record);
}