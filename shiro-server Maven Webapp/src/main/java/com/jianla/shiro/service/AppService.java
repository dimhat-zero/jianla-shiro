/**
 * HangZhou Jianla Network Technology Co., Ltd.
 * Copyright (c) 2014-2015 All Rights Reserved.
 */
package com.jianla.shiro.service;

import java.util.List;

import com.jianla.shiro.entity.App;

/**
 * TODO
 * @author dimhat
 * @date 2015年12月4日 下午1:56:07
 * @version 1.0
 */
public interface AppService {

	public App createApp(App app);

	public App updateApp(App app);

	public void deleteApp(Long appId);

	public App findOne(Long appId);

	public List<App> findAll();

	/**
	 * 根据appKey查找AppId
	 * @param appKey
	 * @return
	 */
	public Long findAppIdByAppKey(String appKey);
}
