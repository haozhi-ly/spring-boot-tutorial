package com.ly.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

@Configuration
public class FilterConfig{
    @Bean
    public FilterRegistrationBean shiroFilterRegisterBean(){
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        registration.addInitParameter("targetFilterLifecycle","true");
        registration.setEnabled(true);
        registration.setOrder(Integer.MAX_VALUE-1);
        registration.addUrlPatterns("/*");
        return registration;
    }

}
