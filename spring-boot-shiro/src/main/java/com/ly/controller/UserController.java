package com.ly.controller;

import com.github.pagehelper.PageInfo;
import com.ly.entity.User;
import com.ly.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping("/page")
    public Map<String,Object> page(User user, int rows, int page) {
        Map<String,Object> result = new HashMap<>();
        PageInfo<User> list = userService.page(user,page,rows);
        result.put("dataList",list.getList());
        result.put("currPage",list.getPageNum());
        result.put("totalPages",list.getPages());
        result.put("totalCount",list.getTotal());
        return result;
    }




}
