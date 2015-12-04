/**
 * HangZhou Jianla Network Technology Co., Ltd.
 * Copyright (c) 2014-2015 All Rights Reserved.
 */
package com.jianla.shiro.service;

import java.util.List;

import com.jianla.shiro.entity.Organization;

/**
 * TODO
 * @author dimhat
 * @date 2015年12月4日 下午1:49:32
 * @version 1.0
 */
public interface OrganizationService {

	public Organization createOrganization(Organization organization);

	public Organization updateOrganization(Organization organization);

	public void deleteOrganization(Long organizationId);

	Organization findOne(Long organizationId);

	List<Organization> findAll();

	Object findAllWithExclude(Organization excludeOraganization);

	void move(Organization source, Organization target);
}
