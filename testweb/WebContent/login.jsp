<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
</head>
<script>
	function jump() {
		window.self.location = "welcome.jsp"; 
	}
</script>
<body>
	${message}
	<br>
	<form action="Login" method="post">
		<table border="0">
			<tr>
				<Td colspan="2">UserLogin</Td>
			</tr>
			<tr>
				<td>UserName：</td>
				<td><input type="text" name="ID"></td>
			</tr>
			<tr>
				<td>Password：</td>
				<td><input type="password" name="PWD"></td>
			</tr>
			<tr>
				<td><input type="submit" value="Login"></td>
				<td><input type="reset" value="Reset"></td>
			</tr>
			<tr>
				<td><input type="button" value="welcomepage" onClick="jump()"></td>
			</tr>
		</table>
	</form>
</body>
</html>