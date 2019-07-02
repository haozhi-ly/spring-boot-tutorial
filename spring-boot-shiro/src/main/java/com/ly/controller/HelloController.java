package com.ly.controller;

import com.ly.entity.PropertisInject;
import com.ly.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//@Controller
public class HelloController {

    @Autowired
    private PropertisInject propertisInject;


    @RequestMapping("/login")
    public String hello1(User user) {
        UsernamePasswordToken token = new UsernamePasswordToken();
        token.setUsername(user.getUsername());
        token.setPassword(user.getPassword().toCharArray());
        SecurityUtils.getSubject().login(token);
        return "index";
    }


    @RequestMapping("/hello1")
    public String hello1() {
        SecurityUtils.getSubject().checkRole("admin");
        return "success";
    }

    @RequiresRoles("admin")
    @RequestMapping("/hello2")
    public String hello2() {
        return "success";
    }

    @ResponseBody
    @RequestMapping("/test")
    public String test(){
        return propertisInject.toString();
    }

}
