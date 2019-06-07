package com.ly.service;/*
    @author ${user}
    @time 16:46
*/

import com.github.pagehelper.PageInfo;
import com.ly.entity.Good;

import java.util.List;

public interface GoodService {
    int deleteByPrimaryKey(Integer id);

    int insert(Good record);

    Good selectByPrimaryKey(Integer id);

    List<Good> selectAll();

    int updateByPrimaryKey(Good record);

    /**
     * 分页查找
     * @param good
     * @return
     */
    PageInfo<Good> page(Good good,int pageNumber,int pageSize);
}
