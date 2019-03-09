package com.lishen.controller;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lishen.pojo.SysUser;
import com.lishen.service.SysUserService;

@Controller

@RequestMapping("/sysUser")

public class SysUserController {

	@Resource

	private SysUserService sysUserService;

 

	public SysUserService getSysUserService() {

		return sysUserService;

	}

 

	public void setSysUserService(SysUserService sysUserService) {

		this.sysUserService = sysUserService;

	}

	

	@RequestMapping("/doLogin")

	public String doLogin(SysUser sysUser,String rememberMe) {

		System.out.println(rememberMe);

		

		//得到Subject,通过SecurityUtils得到Subject，其会自动绑定到当前线程；如果在web环境在请求结束时需要解除绑定

	    Subject subject = SecurityUtils.getSubject();  

	    //创建用户名/密码身份验证Token（即用户身份/凭证）  

	    UsernamePasswordToken token=new UsernamePasswordToken(sysUser.getUsername(),sysUser.getPassword());  

	    if(rememberMe!=null) {

	    	//当checkbox勾选之后，rememberMe就不为null，此时启用rememberMe

	    	token.setRememberMe(true);

	    }else {

	    	//关闭rememberMe

	    	token.setRememberMe(false);

	    }

	    try {  

	        /*

	         * 身份验证，调用subject.login方法进行登录，其会自动委托给SecurityManager.login方法进行登录

	         * 通过login登录，如果登录失败将抛出相应的AuthenticationException，

	         * 如果登录成功调用subject.isAuthenticated就会返回true，即已经通过身份验证

	         * 如果isRemembered返回true，表示是通过记住我功能登录的而不是调用login方法登录的

	         * isAuthenticated/isRemembered是互斥的，即如果其中一个返回true，另一个返回false

	         */

	        subject.login(token);  

	        

	    } catch (AuthenticationException e) { 

	    	/*

	    	 *  如果身份验证失败请捕获AuthenticationException或其子类，常见的如：

	    	 *  DisabledAccountException（禁用的帐号）、

	    	 *  LockedAccountException（锁定的帐号）、

	    	 *  UnknownAccountException（错误的帐号）、

	    	 *  ExcessiveAttemptsException（登录失败次数过多）、

	    	 *  IncorrectCredentialsException （错误的凭证）、

	    	 *  ExpiredCredentialsException（过期的凭证）等，具体请查看其继承关系

	    	 *  对于页面的错误消息展示，最好使用如“用户名/密码错误”而不是“用户名错误”/“密码错误”，防止一些恶意用户非法扫描帐号库

	    	 */

	    	

	        return "login_failed";

	    }  

	    

	    

		return "main";

	}

	

	@ResponseBody

	@RequestMapping("/saveSysUser")

	public String saveSysUser(SysUser sysUser) {

		try {

			//如果b为true，说明添加成功

			boolean b = this.sysUserService.saveSysUser(sysUser);

			return "添加成功";

		} catch (Exception e) {

			//如果有异常，说明添加失败

			return "添加失败";

		}

		

	}

	

	@RequestMapping("/doLogout")

	public String doLogout() {

		//退出操作后一定要重定向页面

		Subject subject = SecurityUtils.getSubject(); 

	    subject.logout();

	    return "login";

	}

	

 

}
