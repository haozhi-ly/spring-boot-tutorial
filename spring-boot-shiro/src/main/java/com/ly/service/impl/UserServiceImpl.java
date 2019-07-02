package com.ly.service.impl;/*
    @author ${user}
    @time 14:50
*/

import com.ly.entity.User;
import com.ly.service.UserService;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

   /* @Autowired
    private UserDao userDao;*/

    @Override
    public boolean login(User user) {
        //return userDao.login(user);
        return true;
    }
}
