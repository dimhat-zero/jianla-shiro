package com.jianla.shiro.client;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;

import com.jianla.shiro.core.ClientSavedRequest;

/**
 * 客户端认证过滤器
 * 增加了backUrl的重定向功能
 * 
 * @author dimhat
 * @date 2015年12月6日 下午1:50:59
 * @version 1.0
 */
public class ClientAuthenticationFilter extends AuthenticationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject = getSubject(request, response);
        return subject.isAuthenticated();
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        String backUrl = request.getParameter("backUrl");
        saveRequest(request, backUrl, getDefaultBackUrl(WebUtils.toHttp(request)));
        redirectToLogin(request, response);
        return false;
    }

    /**
     * 在session中保存请求backUrl 和 fallbackUrl 
     */
    protected void saveRequest(ServletRequest request, String backUrl, String fallbackUrl) {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        session.setAttribute("authc.fallbackUrl", fallbackUrl);
        SavedRequest savedRequest = new ClientSavedRequest(httpRequest, backUrl);
        session.setAttribute(WebUtils.SAVED_REQUEST_KEY, savedRequest);
    }

    private String getDefaultBackUrl(HttpServletRequest request) {
        String scheme = request.getScheme();//可以返回当前页面使用的协议，http 或是 https;
        String domain = request.getServerName();
        int port = request.getServerPort();
        String contextPath = request.getContextPath();//得到项目名，比如myapp
        StringBuilder backUrl = new StringBuilder(scheme);
        backUrl.append("://");
        backUrl.append(domain);
        if ("http".equalsIgnoreCase(scheme) && port != 80) {
            backUrl.append(":").append(String.valueOf(port));
        } else if ("https".equalsIgnoreCase(scheme) && port != 443) {
            backUrl.append(":").append(String.valueOf(port));
        }
        backUrl.append(contextPath);
        backUrl.append(getSuccessUrl());
        return backUrl.toString();
    }

}
