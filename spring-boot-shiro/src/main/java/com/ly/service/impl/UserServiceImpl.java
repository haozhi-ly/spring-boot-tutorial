package com.ly.service.impl;

import com.ly.dao.UserMapper;
import com.ly.entity.User;
import com.ly.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service("userService")
public class UserServiceImpl implements UserService {

   @Autowired
    private UserMapper userMapper;

    @Override
    public User login(User user) {
        List<User> list = userMapper.selectAll(user);

        return list != null && !list.isEmpty() ?  list.get(0) : null;
    }
}
