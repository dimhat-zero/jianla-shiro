/**
 * HangZhou Jianla Network Technology Co., Ltd.
 * Copyright (c) 2014-2015 All Rights Reserved.
 */
package com.jianla.shiro.remote;

import java.io.Serializable;

import org.apache.shiro.session.Session;

/**
 * 远程服务接口，HttpInvoker用于spring应用间的远程调用
 * 
 * @author dimhat
 * @date 2015年12月3日 下午2:55:33
 * @version 1.0
 */
public interface RemoteServiceInterface {

	/**
	 * 获取session
	 */
	public Session getSession(String appKey, Serializable sessionId);

	/**
	 * 创建session
	 */
	Serializable createSession(Session session);

	/**
	 * 更新session
	 */
	public void updateSession(String appKey, Session session);

	/**
	 * 删除session
	 */
	public void deleteSession(String appKey, Session session);

	/**
	 * 获取权限上下文
	 */
	public PermissionContext getPermissions(String appKey, String username);

}
