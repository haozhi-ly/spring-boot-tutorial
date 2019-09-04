package com.ly.controller;

import com.ly.annotation.SystemLogAnnotation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {


    @ResponseBody
    @SystemLogAnnotation(menu = "test",operation = "access")
    @RequestMapping("/hello1")
    public String hello1(String name) {
        System.out.println("hello aop");
        return "success";
    }
    @ResponseBody
    @RequestMapping("/hello2")
    public String hello2() {
        return "success";
    }
}
