package com.ly.controller;

import com.ly.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class HelloController {


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

    @ResponseBody
    @RequestMapping("/jsonTest")
    public Map<String,Object> jsonTest() {
        Map<String,Object> map = new HashMap<>();
        map.put("name","ly\t");
        map.put("age",26);
        return map;
    }


    @RequiresRoles("admin")
    @RequestMapping("/hello2")
    public String hello2() {
        return "success";
    }


}
