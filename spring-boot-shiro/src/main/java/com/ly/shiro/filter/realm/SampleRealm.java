package com.ly.shiro.filter.realm;/*
    @author ${user}
    @time 14:55
*/

import com.ly.entity.User;
import com.ly.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashSet;
import java.util.Set;

public class SampleRealm extends AuthorizingRealm {

    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        if(username.equals("zhang")){
            Set<String> roleSets = new HashSet<>();
            roleSets.add("admin");
            simpleAuthorizationInfo.setRoles(roleSets);
            Set<String> premissionsSet = new HashSet<>();
            premissionsSet.add("user:*");
            premissionsSet.add("menu:*");
            simpleAuthorizationInfo.setStringPermissions(premissionsSet);
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

       /* boolean result = userService.login(user);
        if(!result){
            throw new AccountException("账号不存在或密码错误");
        }*/

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
}
