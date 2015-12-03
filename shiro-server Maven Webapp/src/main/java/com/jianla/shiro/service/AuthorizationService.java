/**
 * HangZhou Jianla Network Technology Co., Ltd.
 * Copyright (c) 2014-2015 All Rights Reserved.
 */
package com.jianla.shiro.service;

import java.util.List;
import java.util.Set;

import com.jianla.shiro.entity.Authorization;

/**
 * 授权接口
 * 
 * @author dimhat
 * @date 2015年12月3日 下午4:00:46
 * @version 1.0
 */
public interface AuthorizationService {

	public Authorization createAuthorization(Authorization authorization);

	public Authorization updateAuthorization(Authorization authorization);

	public void deleteAuthorization(Long authorizationId);

	public Authorization findOne(Long authorizationId);

	public List<Authorization> findAll();

	/**
	 * 根据AppKey和用户名查找其角色
	 * @param username
	 * @return
	 */
	public Set<String> findRoles(String appKey, String username);

	/**
	 * 根据AppKey和用户名查找权限字符串
	 * @param username
	 * @return
	 */
	public Set<String> findPermissions(String appKey, String username);
}
