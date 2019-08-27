package com.ly.shiro.filter.realm;

import com.ly.entity.Permission;
import com.ly.entity.Role;
import com.ly.entity.User;
import com.ly.service.PermissionService;
import com.ly.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SampleRealm extends AuthorizingRealm {

    private UserService userService;

    private PermissionService permissionService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        Session session = SecurityUtils.getSubject().getSession();
        User user = (User) session.getAttribute("loginUserInfo");

        if(user != null){
            Set<String> roleSets = new HashSet<>();
            List<Integer> roleIds = new ArrayList<>();
            if(user.getRoleList() != null){
                for(Role role:user.getRoleList()){
                    roleSets.add(role.getRoleName());
                    roleIds.add(role.getId());
                }
            }
            simpleAuthorizationInfo.setRoles(roleSets);

            Permission permission = new Permission();
            permission.setRoleIds(roleIds);
            List<Permission> permissionList = permissionService.selectPermission(permission);
            Set<String> permissionSet = new HashSet<>();
            if(permissionList != null){
                for(Permission p:permissionList){
                    permissionSet.add(p.getUrl());
                }
            }
            simpleAuthorizationInfo.setStringPermissions(permissionSet);

        }

        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String)authenticationToken.getPrincipal();
        String password = new String((char[]) authenticationToken.getCredentials());

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        User result = userService.login(user);
        if(result == null){
            throw new AccountException("账号不存在或密码错误");
        }

        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("loginUserInfo",result);

        return new SimpleAuthenticationInfo(
            username,password,getName()
        );
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

    public PermissionService getPermissionService() {
        return permissionService;
    }

    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }
}
