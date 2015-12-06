package com.jianla.shiro.client;

import java.io.Serializable;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;

import com.jianla.shiro.remote.RemoteServiceInterface;

/**
 * 提供给shiro对session进行操作
 * 
 * @author dimhat
 * @date 2015年12月6日 下午2:35:36
 * @version 1.0
 */
public class ClientSessionDAO extends CachingSessionDAO {

    private RemoteServiceInterface remoteService;
    private String                 appKey;

    public void setRemoteService(RemoteServiceInterface remoteService) {
        this.remoteService = remoteService;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    @Override
    protected void doDelete(Session session) {
        remoteService.deleteSession(appKey, session);
    }

    @Override
    protected void doUpdate(Session session) {
        remoteService.updateSession(appKey, session);
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = remoteService.createSession(session);
        assignSessionId(session, sessionId);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        return remoteService.getSession(appKey, sessionId);
    }
}
