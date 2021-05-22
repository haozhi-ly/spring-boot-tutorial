package com.ly.dao.shiro;

import com.ly.entity.Good;
import java.util.List;

public interface GoodMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Good record);

    Good selectByPrimaryKey(Integer id);

    List<Good> selectAll();

    int updateByPrimaryKey(Good record);
}