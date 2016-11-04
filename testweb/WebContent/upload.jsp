<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>file upload</title>
</head>
<body>
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