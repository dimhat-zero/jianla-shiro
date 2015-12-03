/**
 * HangZhou Jianla Network Technology Co., Ltd.
 * Copyright (c) 2014-2015 All Rights Reserved.
 */
package com.jianla.shiro.entity;

import java.io.Serializable;

/**
 * 应用对象，是多个应用的权限管理
 * 
 * @author dimhat
 * @date 2015年12月3日 下午5:34:25
 * @version 1.0
 */
public class App implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = -5550887791133787205L;

	private Long id;
	private String name;
	private String appKey;
	private String appSecret;
	private Boolean available = Boolean.FALSE;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

}
