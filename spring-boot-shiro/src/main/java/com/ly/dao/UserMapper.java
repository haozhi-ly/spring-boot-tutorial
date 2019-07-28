package com.ly.dao;

import com.ly.entity.User;
import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    User selectByPrimaryKey(Long id);

    List<User> selectAll(User user);

    int updateByPrimaryKey(User record);

    boolean login(User user);
}