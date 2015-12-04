package com.jianla.shiro.web.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

/**
 * 重载了成功后的重定向方法
 * 如果session中没有authc.fallbackUrl，则使用默认的successUrl
 * 配置在spring-config-shiro中，提供给shiro的web过滤器
 * 
 * @author dimhat
 * @date 2015年12月4日 下午4:04:22
 * @version 1.0
 */
public class ServerFormAuthenticationFilter extends FormAuthenticationFilter {

	/** 
	 * @see org.apache.shiro.web.filter.authc.AuthenticationFilter#issueSuccessRedirect(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	@Override
	protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
		String fallbackUrl = (String) getSubject(request, response).getSession().getAttribute("authc.fallbackUrl");
		if (StringUtils.isEmpty(fallbackUrl)) {
			fallbackUrl = getSuccessUrl();
		}
		WebUtils.redirectToSavedRequest(request, response, fallbackUrl);
	}

}
