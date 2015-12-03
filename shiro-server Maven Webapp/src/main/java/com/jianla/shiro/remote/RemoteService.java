/**
 * HangZhou Jianla Network Technology Co., Ltd.
 * Copyright (c) 2014-2015 All Rights Reserved.
 */
package com.jianla.shiro.remote;

import java.io.Serializable;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;

import com.jianla.shiro.service.AuthorizationService;

/**
 * 远程服务接口服务器实现类
 * 依赖 authorizationService 和 sessionDao 
 * 通过HttpInvoker给远端调用
 * 
 * @author dimhat
 * @date 2015年12月3日 下午3:05:48
 * @version 1.0
 */
public class RemoteService implements RemoteServiceInterface {

	@Autowired
	private SessionDAO sessionDAO;

	@Autowired
	private AuthorizationService authorizationService;

	/** 
	 * @see com.jianla.shiro.remote.RemoteServiceInterface#getSession(java.lang.String, java.io.Serializable)
	 */
	@Override
	public Session getSession(String appKey, Serializable sessionId) {
		return sessionDAO.readSession(sessionId);
	}

	/** 
	 * @see com.jianla.shiro.remote.RemoteServiceInterface#createSession(org.apache.shiro.session.Session)
	 */
	@Override
	public Serializable createSession(Session session) {
		return sessionDAO.create(session);
	}

	/** 
	 * @see com.jianla.shiro.remote.RemoteServiceInterface#updateSession(java.lang.String, org.apache.shiro.session.Session)
	 */
	@Override
	public void updateSession(String appKey, Session session) {
		sessionDAO.update(session);
	}

	/** 
	 * @see com.jianla.shiro.remote.RemoteServiceInterface#deleteSession(java.lang.String, org.apache.shiro.session.Session)
	 */
	@Override
	public void deleteSession(String appKey, Session session) {
		sessionDAO.delete(session);
	}

	/** 
	 * @see com.jianla.shiro.remote.RemoteServiceInterface#getPermissions(java.lang.String, java.lang.String)
	 */
	@Override
	public PermissionContext getPermissions(String appKey, String username) {
		PermissionContext permissionContext = new PermissionContext();
		permissionContext.setRoles(authorizationService.findRoles(appKey, username));
		permissionContext.setPermissions(authorizationService.findPermissions(appKey, username));
		return permissionContext;
	}

}
