package com.jianla.shiro.web.taglib;

import java.util.Collection;

import org.springframework.util.CollectionUtils;

import com.jianla.shiro.entity.App;
import com.jianla.shiro.entity.Organization;
import com.jianla.shiro.entity.Resource;
import com.jianla.shiro.entity.Role;
import com.jianla.shiro.entity.User;
import com.jianla.shiro.service.AppService;
import com.jianla.shiro.service.OrganizationService;
import com.jianla.shiro.service.ResourceService;
import com.jianla.shiro.service.RoleService;
import com.jianla.shiro.service.UserService;
import com.jianla.shiro.util.SpringUtils;

/**
 * name获取工具类
 * 通过SpringUtils.getBean(clazz)获取service
 * 完成根据xxId获取xxName的功能
 * 
 * @author dimhat
 * @date 2015年12月4日 下午2:05:34
 * @version 1.0
 */
public class Functions {

	public static boolean in(Iterable iterable, Object element) {
		if (iterable == null) {
			return false;
		}
		return CollectionUtils.contains(iterable.iterator(), element);
	}

	public static String username(Long userId) {
		User user = getUserService().findOne(userId);
		if (user == null) {
			return "";
		}
		return user.getUsername();
	}

	public static String appName(Long appId) {
		App app = getAppService().findOne(appId);
		if (app == null) {
			return "";
		}
		return app.getName();
	}

	public static String organizationName(Long organizationId) {
		Organization organization = getOrganizationService().findOne(organizationId);
		if (organization == null) {
			return "";
		}
		return organization.getName();
	}

	public static String organizationNames(Collection<Long> organizationIds) {
		if (CollectionUtils.isEmpty(organizationIds)) {
			return "";
		}

		StringBuilder s = new StringBuilder();
		for (Long organizationId : organizationIds) {
			Organization organization = getOrganizationService().findOne(organizationId);
			if (organization == null) {
				return "";
			}
			s.append(organization.getName());
			s.append(",");
		}

		if (s.length() > 0) {
			s.deleteCharAt(s.length() - 1);
		}

		return s.toString();
	}

	public static String roleName(Long roleId) {
		Role role = getRoleService().findOne(roleId);
		if (role == null) {
			return "";
		}
		return role.getDescription();
	}

	public static String roleNames(Collection<Long> roleIds) {
		if (CollectionUtils.isEmpty(roleIds)) {
			return "";
		}

		StringBuilder s = new StringBuilder();
		for (Long roleId : roleIds) {
			Role role = getRoleService().findOne(roleId);
			if (role == null) {
				return "";
			}
			s.append(role.getDescription());
			s.append(",");
		}

		if (s.length() > 0) {
			s.deleteCharAt(s.length() - 1);
		}

		return s.toString();
	}

	public static String resourceName(Long resourceId) {
		Resource resource = getResourceService().findOne(resourceId);
		if (resource == null) {
			return "";
		}
		return resource.getName();
	}

	public static String resourceNames(Collection<Long> resourceIds) {
		if (CollectionUtils.isEmpty(resourceIds)) {
			return "";
		}

		StringBuilder s = new StringBuilder();
		for (Long resourceId : resourceIds) {
			Resource resource = getResourceService().findOne(resourceId);
			if (resource == null) {
				return "";
			}
			s.append(resource.getName());
			s.append(",");
		}

		if (s.length() > 0) {
			s.deleteCharAt(s.length() - 1);
		}

		return s.toString();
	}

	private static OrganizationService organizationService;
	private static RoleService roleService;
	private static ResourceService resourceService;
	private static UserService userService;
	private static AppService appService;

	public static UserService getUserService() {
		if (userService == null) {
			userService = SpringUtils.getBean(UserService.class);
		}
		return userService;
	}

	public static AppService getAppService() {
		if (appService == null) {
			appService = SpringUtils.getBean(AppService.class);
		}
		return appService;
	}

	public static OrganizationService getOrganizationService() {
		if (organizationService == null) {
			organizationService = SpringUtils.getBean(OrganizationService.class);
		}
		return organizationService;
	}

	public static RoleService getRoleService() {
		if (roleService == null) {
			roleService = SpringUtils.getBean(RoleService.class);
		}
		return roleService;
	}

	public static ResourceService getResourceService() {
		if (resourceService == null) {
			resourceService = SpringUtils.getBean(ResourceService.class);
		}
		return resourceService;
	}
}
