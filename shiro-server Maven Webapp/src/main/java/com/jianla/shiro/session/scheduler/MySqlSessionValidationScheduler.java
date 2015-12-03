/**
 * HangZhou Jianla Network Technology Co., Ltd.
 * Copyright (c) 2014-2015 All Rights Reserved.
 */
package com.jianla.shiro.session.scheduler;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.AbstractValidatingSessionManager;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.session.mgt.SessionValidationScheduler;
import org.apache.shiro.session.mgt.ValidatingSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.ReflectionUtils;

import com.jianla.shiro.util.SerializableUtils;

/**
 * Mysql会话验证调度，用于验证会话是否过期。
 * 如果使用如Redis之类的有自动过期策略的DB，完全可以不用实现SessionValidationScheduler，直接借助于这些DB的过期策略即可。
 * 具体配置见《第十章》
 * 
 * @author dimhat
 * @date 2015年12月3日 下午3:28:28
 * @version 1.0
 */
public class MySqlSessionValidationScheduler implements SessionValidationScheduler, Runnable {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/** Private internal log instance. */
	private static final Logger logger = LoggerFactory.getLogger(MySqlSessionValidationScheduler.class);

	/** 验证会话管理器 */
	ValidatingSessionManager sessionManager;

	/** 调度执行者服务 */
	private ScheduledExecutorService service;

	/** 时间间隔 */
	private long interval = DefaultSessionManager.DEFAULT_SESSION_VALIDATION_INTERVAL;

	/** 可用 */
	private boolean enabled = false;

	/** 
	 * 多线程处理 
	 * 分页获取会话并进行验证会话是否过期
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		if (logger.isDebugEnabled()) {
			logger.debug("Executing session validation...");
		}
		long startTime = System.currentTimeMillis();

		//分页获取会话并验证
		String sql = "select session from sessions limit ?,?";
		int start = 0; //起始记录
		int size = 20; //每页大小
		List<String> sessionList = jdbcTemplate.queryForList(sql, String.class, start, size);
		while (sessionList.size() > 0) {
			for (String sessionStr : sessionList) {
				try {
					Session session = SerializableUtils.deserialize(sessionStr);
					Method validateMethod = ReflectionUtils.findMethod(AbstractValidatingSessionManager.class,
							"validate", Session.class, SessionKey.class);
					validateMethod.setAccessible(true);
					ReflectionUtils.invokeMethod(validateMethod, sessionManager, session,
							new DefaultSessionKey(session.getId()));
				} catch (Exception e) {
					//ignore
				}
			}
			start = start + size;
			sessionList = jdbcTemplate.queryForList(sql, String.class, start, size);
		}

		long stopTime = System.currentTimeMillis();
		if (logger.isDebugEnabled()) {
			logger.debug("Session validation completed successfully in " + (stopTime - startTime) + " milliseconds.");
		}
	}

	/** 
	 * 会话可用？
	 * @see org.apache.shiro.session.mgt.SessionValidationScheduler#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	/** 
	 * 开启会话验证
	 * @see org.apache.shiro.session.mgt.SessionValidationScheduler#enableSessionValidation()
	 */
	@Override
	public void enableSessionValidation() {
		if (this.interval > 0L) {
			this.service = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
				public Thread newThread(Runnable r) {
					Thread thread = new Thread(r);
					thread.setDaemon(true);
					return thread;
				}
			});
			this.service.scheduleAtFixedRate(this, interval, interval, TimeUnit.MILLISECONDS);
			this.enabled = true;
		}
	}

	/** 
	 * 关闭会话验证
	 * @see org.apache.shiro.session.mgt.SessionValidationScheduler#disableSessionValidation()
	 */
	@Override
	public void disableSessionValidation() {
		this.service.shutdownNow();
		this.enabled = false;
	}

	public MySqlSessionValidationScheduler() {
		super();
	}

	public ValidatingSessionManager getSessionManager() {
		return sessionManager;
	}

	public void setSessionManager(ValidatingSessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	public long getInterval() {
		return interval;
	}

	public void setInterval(long interval) {
		this.interval = interval;
	}

}
