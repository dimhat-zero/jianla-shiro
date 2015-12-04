package com.jianla.shiro.web.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.jianla.shiro.Constants;
import com.jianla.shiro.service.UserService;

/**
 * 从shiro中获取username，在request设置user对象
 * 在spring-config-shiro中提供给Shiro的Web过滤器
 * 
 * @author dimhat
 * @date 2015年12月4日 下午4:28:56
 * @version 1.0
 */
public class SysUserFilter extends PathMatchingFilter {

	@Autowired
	private UserService userService;

	@Override
	protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {

		String username = (String) SecurityUtils.getSubject().getPrincipal();
		request.setAttribute(Constants.CURRENT_USER, userService.findByUsername(username));
		return true;
	}
}
