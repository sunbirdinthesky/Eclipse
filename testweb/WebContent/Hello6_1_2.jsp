<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Hello</title>
</head>
<body bgcolor="white">
	<img src ="images/duke.gif">
	my name is SunBird, what`s your name?
	<form action = "" method = "get">
		<input type = "text" name = "username" size = "25">
		<input type = "submit" value = "submit">
		<input type = "reset" value = "reset">
	</form>
	<%! String username = "Duke"; %>
	<%@ include file = "response.jsp"%>
</body>
</html>