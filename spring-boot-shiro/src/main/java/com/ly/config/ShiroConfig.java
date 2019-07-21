package com.ly.config;


import com.ly.service.UserService;
import com.ly.shiro.filter.realm.SampleRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {


    @Bean
    public Realm realm(UserService userService){
        SampleRealm sampleRealm = new SampleRealm();
        sampleRealm.setUserService(userService);
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
    public SecurityManager securityManager(Realm realm,SessionManager sessionManager){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);
        Map<String,String> filterMap = new HashMap<>();
        filterMap.put("/login","anon");
        filterMap.put("/index.jsp","anon");
        filterMap.put("/unauthorized.jsp","anon");
        filterMap.put("/login.jsp","anon");
        filterMap.put("/logout","logout");
        filterMap.put("/**","user");
        factoryBean.setFilterChainDefinitionMap(filterMap);
        return factoryBean;
    }


}
