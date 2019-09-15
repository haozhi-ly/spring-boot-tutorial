package com.ly.interceptor;

import com.ly.shiro.filter.realm.SampleRealm;
import com.ly.util.SpringContextUtils;
import org.apache.shiro.cache.CacheManager;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限拦截器
 */
public class PermissionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        SampleRealm realm = (SampleRealm) SpringContextUtils.getContext().getBean("sampleRealm");
        CacheManager cacheManager =realm.getCacheManager();
        if(cacheManager != null){
            realm.clearAllCache();
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
