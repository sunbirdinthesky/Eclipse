<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册</title>
</head>
<body style="background-color: #303030; color:#c0c0c0;">
	${message} <br>
	<form action="/WeiBuo/Registerservlet" method="post">
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
				<td>Confirm Password：</td>
				<td><input type="password" name="confirmpasswd"></td>
			</tr>
			<tr>
				<td><input type="submit" value="Confirm"></td>
				<td><input type="reset" value="Reset"></td>
			</tr>
		</table>
	</form>
</body>
</html>