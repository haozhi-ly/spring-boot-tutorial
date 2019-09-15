package com.ly.service;


import com.github.pagehelper.PageInfo;
import com.ly.entity.User;

public interface UserService {
    User login(User user);

    PageInfo<User> page(User user, int pageNumber, int pageSize);
}
