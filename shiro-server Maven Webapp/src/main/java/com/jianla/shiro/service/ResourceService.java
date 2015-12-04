/**
 * HangZhou Jianla Network Technology Co., Ltd.
 * Copyright (c) 2014-2015 All Rights Reserved.
 */
package com.jianla.shiro.service;

import java.util.List;
import java.util.Set;

import com.jianla.shiro.entity.Resource;

/**
 * TODO
 * @author dimhat
 * @date 2015年12月4日 下午1:50:08
 * @version 1.0
 */
public interface ResourceService {

	public Resource createResource(Resource resource);

	public Resource updateResource(Resource resource);

	public void deleteResource(Long resourceId);

	Resource findOne(Long resourceId);

	List<Resource> findAll();

	/**
	 * 得到资源对应的权限字符串
	 * @param resourceIds
	 * @return
	 */
	Set<String> findPermissions(Set<Long> resourceIds);

	/**
	 * 根据用户权限得到菜单
	 * @param permissions
	 * @return
	 */
	List<Resource> findMenus(Set<String> permissions);

}
