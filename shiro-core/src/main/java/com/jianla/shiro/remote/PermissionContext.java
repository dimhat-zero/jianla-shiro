/**
 * HangZhou Jianla Network Technology Co., Ltd.
 * Copyright (c) 2014-2015 All Rights Reserved.
 */
package com.jianla.shiro.remote;

import java.io.Serializable;
import java.util.Set;

/**
 * 权限上下文对象，存储角色集合和权限集合
 * 
 * @author dimhat
 * @date 2015年12月3日 下午2:53:19
 * @version 1.0
 */
public class PermissionContext implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = -2756204179906186538L;

	private Set<String> roles;
	private Set<String> permissions;

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	public Set<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<String> permissions) {
		this.permissions = permissions;
	}

	@Override
	public String toString() {
		return "PermissionContext{" + ", roles=" + roles + ", permissions=" + permissions + '}';
	}
}
