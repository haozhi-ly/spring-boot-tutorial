package com.ly.config;


import com.ly.service.PermissionService;
import com.ly.service.UserService;
import com.ly.shiro.filter.PermissionFilter;
import com.ly.shiro.filter.realm.SampleRealm;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    private final String AUTHENTICATION_CACHE_NAME = "authenticationCache";
    private final String AUTHORIZATION_CACHE_NAME = "authenticationCache";

    @Bean("sampleRealm")
    public Realm realm(UserService userService, PermissionService permissionService){
        SampleRealm sampleRealm = new SampleRealm();
        sampleRealm.setUserService(userService);
        sampleRealm.setPermissionService(permissionService);
        sampleRealm.setCachingEnabled(true);
        sampleRealm.setAuthenticationCachingEnabled(true);
        sampleRealm.setAuthenticationCacheName(AUTHENTICATION_CACHE_NAME);
        sampleRealm.setAuthorizationCachingEnabled(true);
        sampleRealm.setAuthorizationCacheName(AUTHORIZATION_CACHE_NAME);
        return sampleRealm;
    }

    @Bean
    public SessionManager sessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionIdCookieEnabled(true);
        return sessionManager;
    }

    @Bean
    public CacheManager cacheManager(){
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
        return  cacheManager;
    }

    @Bean
    public SecurityManager securityManager(Realm sampleRealm,SessionManager sessionManager,CacheManager cacheManager){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(sampleRealm);
        securityManager.setSessionManager(sessionManager);
        securityManager.setCacheManager(cacheManager);
        return securityManager;
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager, PermissionFilter permissionFilter){
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);


        //oauth过滤
        Map<String, Filter> filters = new HashMap<>();
        filters.put("permission", permissionFilter);
        factoryBean.setFilters(filters);

        Map<String,String> filterMap = new HashMap<>();
        filterMap.put("/login","anon");
        filterMap.put("/unauthorized.jsp","anon");
        filterMap.put("/login.jsp","anon");
        filterMap.put("/logout","logout");
        filterMap.put("/**","user,permission");
        factoryBean.setFilterChainDefinitionMap(filterMap);
        return factoryBean;
    }


}
