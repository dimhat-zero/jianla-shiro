/**
 * HangZhou Jianla Network Technology Co., Ltd.
 * Copyright (c) 2014-2015 All Rights Reserved.
 */
package com.jianla.shiro.core;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.util.SavedRequest;

/**
 * session中要保存的请求对象，登录成功后重定向
 * 
 * @author dimhat
 * @date 2015年12月3日 下午2:19:19
 * @version 1.0
 */
public class ClientSavedRequest extends SavedRequest {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	private String scheme;
	private String domain;
	private int port;
	private String contextPath;
	private String backUrl;

	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param request
	 */
	public ClientSavedRequest(HttpServletRequest request, String backUrl) {
		super(request);
		this.scheme = request.getScheme();//可以返回当前页面使用的协议，http 或是 https;
		this.domain = request.getServerName();
		this.port = request.getServerPort();
		this.contextPath = request.getContextPath();//得到项目名，比如myapp
		this.backUrl = backUrl;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public String getBackUrl() {
		return backUrl;
	}

	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}

}
