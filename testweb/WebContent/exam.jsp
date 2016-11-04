<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>考试系统</title>
</head>
<body>
	<p> please answer these questions </p>
	<form action="Servlet" method = "post">
		<p> 1. 北京召开的是29届奥运会 <br>
			<input type = "radio" name = "ques1" value = "yes"> yes.
			<input type = "radio" name = "ques1" value = "no"> no. <br>
		<p> 2. 那些语言是面向对象的 <br>
			<input type = "checkbox" name = "ques2" value = "java"> java.
			<input type = "checkbox" name = "ques2" value = "c#"> c#. 
			<input type = "checkbox" name = "ques2" value = "c"> c. <br>
		<p> 3. 12345+54321=? <br>
			<input type = "text" name = "ques3" size = "30"> <br>
		<p> submit <input type = "submit" value = "submit" >
			reset <input type = "reset" value = "reset">
	</form> 
</body>
</html>