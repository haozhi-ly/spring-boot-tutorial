package com.ly.service;/*
    @author ${user}
    @time 16:52
*/

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ly.dao.GoodMapper;
import com.ly.entity.Good;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName GoodServiceImpl
 * @Author sky_
 * @Date 2019/6/7
 **/
//@Lazy
//@Service("goodService")
public class GoodServiceImpl implements GoodService{
    /**
     * 商品mapper
     */
    @Autowired
    private GoodMapper goodMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insert(Good record) {
        return 0;
    }

    @Override
    public Good selectByPrimaryKey(Integer id) {
        return null;
    }

    @Override
    public List<Good> selectAll() {
        return null;
    }

    @Override
    public int updateByPrimaryKey(Good record) {
        return 0;
    }

    @Override
    public PageInfo<Good> page(Good good,int pageNumber,int pageSize) {
        PageHelper.startPage(pageNumber,pageSize);
        List<Good> list = goodMapper.selectAll();
        return new PageInfo<>(list);
    }

}
