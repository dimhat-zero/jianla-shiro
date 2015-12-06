package com.jianla.shiro.client;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.jianla.shiro.remote.PermissionContext;
import com.jianla.shiro.remote.RemoteServiceInterface;

/**
 * 提供给shiro做realm认证（设置角色和权限集合）
 * 
 * @author dimhat
 * @date 2015年12月6日 下午2:23:01
 * @version 1.0
 */
public class ClientRealm extends AuthorizingRealm {
    private RemoteServiceInterface remoteService;
    private String                 appKey;

    public void setRemoteService(RemoteServiceInterface remoteService) {
        this.remoteService = remoteService;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    /** 
     * 关键方法（授权），从服务器获取权限上下文，设置角色和权限集合
     * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        PermissionContext context = remoteService.getPermissions(appKey, username);
        authorizationInfo.setRoles(context.getRoles());
        authorizationInfo.setStringPermissions(context.getPermissions());
        return authorizationInfo;
    }

    /** 
     * 认证
     * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //never run
        throw new UnsupportedOperationException("never run");
    }

}