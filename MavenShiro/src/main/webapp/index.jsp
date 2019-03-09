<html>
<body>
<h2>Hello World!</h2>

<form action="${pageContext.request.contextPath }/sysUser/doLogin" method="post">

		用户名：<input type="text" name="username"><br>

		密码：<input type="password" name="password"><br>

		记住我：<input type="checkbox" value="true" name="rememberMe"><br>

		<input type="submit" value="登录">

		

	</form>

</body>
</html>
