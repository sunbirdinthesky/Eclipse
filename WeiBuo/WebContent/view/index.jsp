<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登陆</title>
</head>
<body>
	${message} <br>
	<form action="/WeiBuo/Loginservlet" method="post">
		<table>
			<tr>
				<Td colspan="2">UserLogin</Td>
			</tr>
			<tr>
				<td>UserName：</td>
				<td><input type="text" name="username"></td>
			</tr>
			<tr>
				<td>Password：</td>
				<td><input type="password" name="passwd"></td>
			</tr>
			<tr>
				<td><input type="submit" value="Login"></td>
				<td><input type="reset" value="Reset"></td>
			</tr>
			<tr>
				<td><a href="/WeiBuo/view/register.jsp"> 点击注册 </a></td>
			</tr>
		</table>
	</form>
</body>
</html>