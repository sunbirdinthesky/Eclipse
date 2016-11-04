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
	${message} <br>
	<form action = "FileUpload" enctype = "multipart/form-data" method = "post">
		<table>
			<tr> 
				<td colspan = "2" align = "center"> file upload </td>
			</tr>
			
			<tr> 
				<td> IDNumber </td>
				<td> 
					<input type = "text" name = "idnumber" size = "30" /> 
				</td>
			</tr>
			
			<tr> 
				<td> file name </td>
				<td> 
					<input type = "file" name = "filename" size = "30" /> 
				</td>
			</tr>
			
			<tr> 
				<td align = "right">
					<input type = "submit" value = "submit" />
				</td> 
				<td align = "left">
					<input type = "reset" value = "reset" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>