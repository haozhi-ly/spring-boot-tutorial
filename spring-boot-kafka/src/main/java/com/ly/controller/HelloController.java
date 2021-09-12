package com.ly.controller;

import com.ly.annotation.SystemLogAnnotation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

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

    @ResponseBody
    @GetMapping("/urlTest")
    public String urlTest(String p) {
        System.out.println("accept param:"+p);
        return "success";
    }

    @ResponseBody
    @RequestMapping ("/urlTestV2")
    public String urlTestV2(String p, HttpServletRequest request)  {
        System.out.println("accept param:"+p);
        return "success";
    }
}
