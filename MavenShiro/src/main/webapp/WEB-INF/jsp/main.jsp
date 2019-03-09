<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<!-- 相当于Subject.getPrincipals().oneByType(String.class) -->

	当前用户：<shiro:principal type="java.lang.String"/><br>

	有无超级管理员权限:

	<shiro:hasRole name="super_admin">  

    	有

	</shiro:hasRole> 

	<br>

	有无普通管理员权限:

	<shiro:hasRole name="admin">  

    	有

	</shiro:hasRole> 

	<br>

	有哪些权限：

	<shiro:hasPermission name="user:create">  

    	<a href="${pageContext.request.contextPath }/sysuser_add.jsp">添加用户</a>

	</shiro:hasPermission>

	<shiro:hasPermission name="user:read">  

    	<a href="#">查询用户</a>

	</shiro:hasPermission>

	<shiro:hasPermission name="user:update">  

    	<a href="#">修改用户</a>

	</shiro:hasPermission>

	<shiro:hasPermission name="user:delete">  

    	<a href="#">删除用户</a>

	</shiro:hasPermission>

	<br>

	<a href="${pageContext.request.contextPath }/sysUser/doLogout">退出登录</a>


</body>
</html>

