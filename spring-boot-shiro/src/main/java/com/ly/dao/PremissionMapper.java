package com.ly.dao;

import com.ly.entity.Premission;
import java.util.List;

public interface PremissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Premission record);

    Premission selectByPrimaryKey(Long id);

    List<Premission> selectAll();

    int updateByPrimaryKey(Premission record);
}