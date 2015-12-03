/**
 * HangZhou Jianla Network Technology Co., Ltd.
 * Copyright (c) 2014-2015 All Rights Reserved.
 */
package com.jianla.shiro.entity;

import java.io.Serializable;

/**
 * 组织机构表，总公司/分公司
 * 
 * @author dimhat
 * @date 2015年12月3日 下午5:36:05
 * @version 1.0
 */
public class Organization implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = -2075055813703462780L;

	private Long id; //编号
	private String name; //组织机构名称
	private Long parentId; //父编号
	private String parentIds; //父编号列表，如1/2/
	private Boolean available = Boolean.FALSE;

	/**
	 * 是根节点？  
	 */
	public boolean isRootNode() {
		return parentId == 0;
	}

	/**
	 * 已自己为父节点
	 */
	public String makeSelfAsParentIds() {
		return getParentIds() + getId() + "/";
	}

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

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Organization that = (Organization) o;

		if (id != null ? !id.equals(that.id) : that.id != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}

	@Override
	public String toString() {
		return "Organization{" + "id=" + id + ", name='" + name + '\'' + ", parentId=" + parentId + ", parentIds='"
				+ parentIds + '\'' + ", available=" + available + '}';
	}
}
