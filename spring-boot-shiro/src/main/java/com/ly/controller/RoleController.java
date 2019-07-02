package com.ly.controller;

import com.github.pagehelper.PageInfo;
import com.ly.entity.Role;
import com.ly.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ResponseBody
    @RequestMapping("/page")
    public Map<String,Object> page(Role role,int rows,int page) {
        Map<String,Object> result = new HashMap<>();
        PageInfo<Role> list = roleService.page(role,page,rows);
        result.put("dataList",list.getList());
        result.put("currPage",list.getPageNum());
        result.put("totalPages",list.getPages());
        result.put("totalCount",list.getTotal());
        return result;
    }




}
