package com.ly.controller;

import com.ly.entity.Role;
import com.ly.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @ResponseBody
    @RequestMapping("/getPermissionTree")
    public List<Map<String,Object>> getPermissionTree(Role role) {
        return permissionService.getPermissionTreeToJson(role);
    }




}
