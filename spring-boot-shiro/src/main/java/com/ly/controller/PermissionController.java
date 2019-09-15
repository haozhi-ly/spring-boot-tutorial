package com.ly.controller;

import com.ly.entity.Permission;
import com.ly.entity.Role;
import com.ly.entity.User;
import com.ly.service.PermissionService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @ResponseBody
    @RequestMapping("/getPermissionTree")
    public List<Map<String,Object>> getPermissionTree(Role role, HttpServletRequest request) {
        Session session = SecurityUtils.getSubject().getSession();
        User user = (User) session.getAttribute("loginUserInfo");
        role.setCurrentRid(user.getRoleList().get(0).getId());
        return permissionService.getPermissionTreeToJson(role);
    }

    @RequestMapping("/addPermission")
    public String addPermission(@ModelAttribute Permission permission){
        permissionService.addPermission(permission);
        return "redirect:/index.jsp";
    }

    @ResponseBody
    @RequestMapping("/allocatePermission")
    public Integer allocatePermission(@RequestParam(value = "pidList[]",required = false) List<Integer> pidList, Integer roleId){
        int result = 0;
        result = permissionService.allocatePermission(roleId,pidList);
        return result;
    }

    @ResponseBody
    @RequestMapping("/checkPermissionIsExist")
    public boolean checkPermissionIsExist(String permissionUrl){
        return permissionService.checkPermissionIsExist(permissionUrl);
    }





}
