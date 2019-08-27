package com.ly.shiro.filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component("permissionFilter")
public class PermissionFilter extends AccessControlFilter implements InitializingBean {

	private Map<String,String> urlMappingMap;

	@Autowired
	private urlMappingPermissionReader reader;


	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

		Subject subject = SecurityUtils.getSubject();
		if(null != mappedValue){
			String[] arra = (String[])mappedValue;
			for (String permission : arra) {
				if(subject.isPermitted(permission)){
					return Boolean.TRUE;
				}
			}
		}

		HttpServletRequest httpRequest = ((HttpServletRequest)request);
		String uri = httpRequest.getRequestURI();//获取URI
		String basePath = httpRequest.getContextPath();//获取basePath
		if(null != uri && uri.startsWith(basePath)){
			uri = uri.replaceFirst(basePath, "");
			//从map中获取真实的url映射的权限url，从而判断用户的权限集合中是否存在对应的权限
			String permissionUrl = urlMappingMap.get(uri);
			if(!StringUtils.isEmpty(permissionUrl)){
				if(subject.isPermitted(permissionUrl)){
					return Boolean.TRUE;
				}
			}else{
				//如果没有真实的url没有建立，其对应关系，就返回true
				return true;
			}

		}



		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

		WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
		return false;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		urlMappingMap = reader.getMappingMap();
	}
}
