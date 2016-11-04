<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>欢迎登陆</title>
</head>
<script>
	function jump() {
		window.self.location = "login.jsp"; 
	}
</script>
<body>
	<%
		String id = (String) session.getAttribute("user");
		String loginTime = (String) session.getAttribute("loginTime");
	%>
	<form action="Login"  method="get">
		<table border="0">
			<tr>
				<Td colspan="2">welcome to login</Td>
			</tr>
			<tr>
				<td>UserName：<%=id%></td>
			</tr>
			<tr>
				<td>Login time：<%=loginTime%></td>
			</tr>
			<tr>
				<td><input type="submit" value="Log out"></td>
				<td><input type="button" value="homepage" onClick="jump()"></td>
			</tr>
		</table>
	</form>
</body>
</html>