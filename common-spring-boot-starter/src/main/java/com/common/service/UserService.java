package com.common.service;


import com.common.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private User user;
    public void info(){

        logger.info("hello world:"+user);
    }

}
