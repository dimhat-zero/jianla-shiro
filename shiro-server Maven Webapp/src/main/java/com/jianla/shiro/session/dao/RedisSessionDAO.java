/**
 * HangZhou Jianla Network Technology Co., Ltd.
 * Copyright (c) 2014-2015 All Rights Reserved.
 */
package com.jianla.shiro.session.dao;

import java.io.Serializable;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.jianla.shiro.service.RedisService;
import com.jianla.shiro.util.SerializableUtils;

/**
 * 把session放在redis中
 * 
 * @author dimhat
 * @date 2015年12月3日 下午3:10:57
 * @version 1.0
 */
public class RedisSessionDAO extends CachingSessionDAO {

	@Autowired
	private RedisService redisService;

	private Long liveTime = 0L;

	/** 
	 * @see org.apache.shiro.session.mgt.eis.AbstractSessionDAO#doCreate(org.apache.shiro.session.Session)
	 */
	@Override
	protected Serializable doCreate(final Session session) {
		Serializable sessionId = generateSessionId(session);//需要配置生成策略
		assignSessionId(session, sessionId);//给session设置id
		redisService.set((String) sessionId, SerializableUtils.serialize(session), liveTime);
		return session.getId();
	}

	/** 
	 * @see org.apache.shiro.session.mgt.eis.CachingSessionDAO#doUpdate(org.apache.shiro.session.Session)
	 */
	@Override
	protected void doUpdate(Session session) {
		if (session instanceof ValidatingSession && !((ValidatingSession) session).isValid()) {
			return; //如果会话过期/停止 没必要再更新了
		}
		redisService.set((String) session.getId(), SerializableUtils.serialize(session), liveTime);
	}

	/** 
	 * @see org.apache.shiro.session.mgt.eis.CachingSessionDAO#doDelete(org.apache.shiro.session.Session)
	 */
	@Override
	protected void doDelete(Session session) {
		redisService.del((String) session.getId());
	}

	/** 
	 * @see org.apache.shiro.session.mgt.eis.AbstractSessionDAO#doReadSession(java.io.Serializable)
	 */
	@Override
	protected Session doReadSession(Serializable sessionId) {
		String sessionStr = redisService.get((String) sessionId);
		if (StringUtils.isEmpty(sessionStr))
			return null;
		return SerializableUtils.deserialize(sessionStr);
	}

	/**
	 * Setter method for property <tt>redisService</tt>.
	 * @param redisService value to be assigned to property redisService
	 */
	public void setRedisService(RedisService redisService) {
		this.redisService = redisService;
	}

	/**
	 * Setter method for property <tt>liveTime</tt>.
	 * @param liveTime value to be assigned to property liveTime
	 */
	public void setLiveTime(Long liveTime) {
		this.liveTime = liveTime;
	}

}
