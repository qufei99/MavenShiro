package com.lishen.dao;

import java.util.Set;

import  com.lishen.pojo.SysUser;


public interface SysUserDao {

	SysUser getUserByUsername(String username);

	Set<String> listRoles(String username);

	Set<String> listPermissions(String username);

	int saveSysUser(SysUser sysUser);

}
