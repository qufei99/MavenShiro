package com.lishen.service;

import java.util.Set;

import com.lishen.pojo.SysUser;

public interface SysUserService {

	SysUser getUserByUsername(String username);

	Set<String> listRoles(String username);

	Set<String> listPermissions(String username);

	boolean saveSysUser(SysUser sysUser);

}
