package com.lishen.service.impl;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lishen.dao.SysUserDao;
import com.lishen.pojo.SysUser;
import com.lishen.service.SysUserService;
import com.lishen.utils.PasswordHelper;

@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService{

	@Resource

	private SysUserDao sysUserDao;

 

	public SysUserDao getSysUserDao() {

		return sysUserDao;

	}

 

	public void setSysUserDao(SysUserDao sysUserDao) {

		this.sysUserDao = sysUserDao;

	}

 

	@Override
	public SysUser getUserByUsername(String username) {

		return this.sysUserDao.getUserByUsername(username);

	}

 

	@Override
	public Set<String> listRoles(String username) {

		return this.sysUserDao.listRoles(username);

	}

 

	@Override

	public Set<String> listPermissions(String username) {

		return this.sysUserDao.listPermissions(username);

	}

 

	/**

	 * 添加用户

	 */

	@Override
	@Transactional
	public boolean saveSysUser(SysUser sysUser) {

		//service里主要的工作是，将随机salt和加密后的密码存进数据库

		SysUser sysUserToDB = new PasswordHelper().encryptPassword(sysUser);

		//保存最终进入数据库的sysUser

		int rows = this.sysUserDao.saveSysUser(sysUserToDB);

		if(rows==1) {

			return true;

		}else {

			throw new RuntimeException();

		}

	}
	

}
