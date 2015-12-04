/**
 * HangZhou Jianla Network Technology Co., Ltd.
 * Copyright (c) 2014-2015 All Rights Reserved.
 */
package com.jianla.shiro.service;

import java.util.List;
import java.util.Set;

import com.jianla.shiro.entity.Role;

/**
 * TODO
 * @author dimhat
 * @date 2015年12月4日 下午1:50:42
 * @version 1.0
 */
public interface RoleService {

	public Role createRole(Role role);

	public Role updateRole(Role role);

	public void deleteRole(Long roleId);

	public Role findOne(Long roleId);

	public List<Role> findAll();

	/**
	 * 根据角色编号得到角色标识符列表
	 * @param roleIds
	 * @return
	 */
	Set<String> findRoles(Long... roleIds);

	/**
	 * 根据角色编号得到权限字符串列表
	 * @param roleIds
	 * @return
	 */
	Set<String> findPermissions(Long[] roleIds);
}