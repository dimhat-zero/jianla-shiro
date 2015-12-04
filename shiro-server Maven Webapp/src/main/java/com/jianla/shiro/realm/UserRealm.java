/**
 * HangZhou Jianla Network Technology Co., Ltd.
 * Copyright (c) 2014-2015 All Rights Reserved.
 */
package com.jianla.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.jianla.shiro.Constants;
import com.jianla.shiro.entity.User;
import com.jianla.shiro.service.AuthorizationService;
import com.jianla.shiro.service.UserService;

/**
 * 对接我们的用户，角色和权限
 * 
 * @author dimhat
 * @date 2015年12月3日 下午6:02:37
 * @version 1.0
 */
public class UserRealm extends AuthorizingRealm {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthorizationService authorizationService;

	/** 
	 * 找到角色、权限的字符串集合
	 * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String username = (String) principals.getPrimaryPrincipal();

		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.setRoles(authorizationService.findRoles(Constants.SERVER_APP_KEY, username));
		authorizationInfo
				.setStringPermissions(authorizationService.findPermissions(Constants.SERVER_APP_KEY, username));
		return authorizationInfo;
	}

	/** 
	 * 提供认证信息
	 * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal();

		User user = userService.findByUsername(username);

		if (user == null) {
			throw new UnknownAccountException();//没找到帐号
		}

		if (Boolean.TRUE.equals(user.getLocked())) {
			throw new LockedAccountException(); //帐号锁定
		}

		//交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getUsername(), //用户名
				user.getPassword(), //密码
				ByteSource.Util.bytes(user.getCredentialsSalt()),//salt=username+salt
				getName() //realm name
		);
		return authenticationInfo;
	}

}
