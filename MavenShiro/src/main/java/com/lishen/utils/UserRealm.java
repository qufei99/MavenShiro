package com.lishen.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;

import com.lishen.pojo.SysUser;
import com.lishen.service.SysUserService;

public class UserRealm extends AuthorizingRealm {

 

    private SysUserService sysUserService;

	

    public SysUserService getSysUserService() {

		return sysUserService;

	}

 

	public void setSysUserService(SysUserService sysUserService) {

		this.sysUserService = sysUserService;

	}

 

	/**

	 * 获取权限信息，只有在身份验证成功后才调用此方法获取权限信息

	 */

	@Override

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

		//获取用户名

        String username = (String)principals.getPrimaryPrincipal();

        //new一个授权信息

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        //给授权信息设置角色集合,只能放角色名

        authorizationInfo.setRoles(sysUserService.listRoles(username));

        //给授权信息设置权限集合

        authorizationInfo.setStringPermissions(sysUserService.listPermissions(username));

        //返回用户授权信息

        return authorizationInfo;

    }

 

	/**

	 * 获取身份验证信息

	 */

    @Override

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

    	

    	//获取用户名

        String username = (String)token.getPrincipal();

        //根据用户名获取User对象

        SysUser user = sysUserService.getUserByUsername(username);

 

        if(user == null) {

        	//找不到账号

            throw new UnknownAccountException();

        }

 

        if(user.getLocked()==1) {

        	//帐号锁定

            throw new LockedAccountException();

        }

 

        /*

         * new一个身份验证信息

         */

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(

        		//用户名

                user.getUsername(),

                //从数据库中查出的密文密码

                user.getPassword(),

                //credentialsSalt=username+salt

                ByteSource.Util.bytes(user.getCredentialsSalt()),

                //realm名称

                getName()

        );

        Session session = getSession();

        //将当前用户放进session

        session.setAttribute("username", username);

        /*

         * 返回身份验证信息，将交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配

	     * CredentialsMatcher使用盐加密传入的明文密码和此处的密文密码进行匹配

         */

        return authenticationInfo;

    }

    /**

     * 获取shiro封装的session

     */

    private Session getSession(){  

        try{  

            Subject subject = SecurityUtils.getSubject();  

            Session session = subject.getSession(false);  

            if (session == null){  

                session = subject.getSession();  

            }  

            if (session != null){  

                return session;  

            }  

        }catch (InvalidSessionException e){  

              

        }  

        return null;  

    }  

    //以下是一些清空realm缓存的方法

    @Override

    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {

        super.clearCachedAuthorizationInfo(principals);

    }

 

    @Override

    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {

        super.clearCachedAuthenticationInfo(principals);

    }

 

    @Override

    public void clearCache(PrincipalCollection principals) {

        super.clearCache(principals);

    }

 

    public void clearAllCachedAuthorizationInfo() {

        getAuthorizationCache().clear();

    }

 

    public void clearAllCachedAuthenticationInfo() {

        getAuthenticationCache().clear();

    }

 

    public void clearAllCache() {

        clearAllCachedAuthenticationInfo();

        clearAllCachedAuthorizationInfo();

    }

 

}
