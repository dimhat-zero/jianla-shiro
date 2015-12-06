/**
 * HangZhou Jianla Network Technology Co., Ltd.
 * Copyright (c) 2014-2015 All Rights Reserved.
 */
package com.jianla.shiro.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * 授权对象
 * 
 * @author dimhat
 * @date 2015年12月3日 下午4:10:36
 * @version 1.0
 */
public class Authorization implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = -4856072427740010672L;

    private Long              id;
    private Long              userId;
    private Long              appId;
    private List<Long>        roleIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public List<Long> getRoleIds() {
        if (roleIds == null) {
            roleIds = new ArrayList<Long>();
        }
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }

    public String getRoleIdsStr() {
        if (CollectionUtils.isEmpty(roleIds)) {
            return "";
        }
        StringBuilder s = new StringBuilder();
        for (Long roleId : roleIds) {
            s.append(roleId);
            s.append(",");
        }
        return s.toString();
    }

    public void setRoleIdsStr(String roleIdsStr) {
        if (StringUtils.isEmpty(roleIdsStr)) {
            return;
        }
        String[] roleIdStrs = roleIdsStr.split(",");
        for (String roleIdStr : roleIdStrs) {
            if (StringUtils.isEmpty(roleIdStr)) {
                continue;
            }
            getRoleIds().add(Long.valueOf(roleIdStr));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Authorization that = (Authorization) o;

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
        return "Authorization{" + "id=" + id + ", userId=" + userId + ", appId=" + appId + ", roleIds=" + roleIds + '}';
    }
}
