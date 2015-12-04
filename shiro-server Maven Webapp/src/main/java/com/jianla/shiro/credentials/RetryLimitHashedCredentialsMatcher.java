/**
 * HangZhou Jianla Network Technology Co., Ltd.
 * Copyright (c) 2014-2015 All Rights Reserved.
 */
package com.jianla.shiro.credentials;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

/**
 * 用于编码和加密
 * 如在1个小时内密码最多重试5次，如果尝试次数超过5次就锁定1小时，1小时后可再次重试，
 * 如果还是重试失败，可以锁定如1天，以此类推，防止密码被暴力破解。
 * 我们通过继承HashedCredentialsMatcher，且使用Ehcache记录重试次数和超时时间。
 * 
 * 如上代码逻辑比较简单，即如果密码输入正确清除cache中的记录；
 * 否则cache中的重试次数+1，如果超出5次那么抛出异常表示超出重试次数了。
 * 
 * 在spring-config-shiro中被userRealm引用
 * 
 * @author dimhat
 * @date 2015年12月4日 下午1:15:31
 * @version 1.0
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

	private Cache<String, AtomicInteger> passwordRetryCache;

	public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager) {
		passwordRetryCache = cacheManager.getCache("passwordRetryCache");
	}

	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		String username = (String) token.getPrincipal();
		//retry count + 1
		AtomicInteger retryCount = passwordRetryCache.get(username);
		if (retryCount == null) {
			retryCount = new AtomicInteger(0);
			passwordRetryCache.put(username, retryCount);
		}
		if (retryCount.incrementAndGet() > 5) {
			//if retry count > 5 throw
			throw new ExcessiveAttemptsException();
		}

		boolean matches = super.doCredentialsMatch(token, info);
		if (matches) {
			//clear retry count
			passwordRetryCache.remove(username);
		}
		return matches;
	}

}
