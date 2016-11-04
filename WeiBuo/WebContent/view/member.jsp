<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>欢迎登陆</title>
</head>
<script>
	void jump(val) {
// 		session.setAttribute("id", val);
		window.self.location = "/Deleteservlet"; 
	}
</script>
<body style="background-color: #303030">
		<table style="color: #c0c0c0">
			<tr>
				<td width=300 align="right"><image width=100 height=100
						src="/WeiBuo/res/1.JPG">
				<td align="center">140字，记录下您生活的点点滴滴
			</tr>
			<tr>
				<td width=300 align="right">欢迎您, ${username}
			</tr>
			<tr>
				<td>
					<div style="overflow: auto; height: 500px; width: 1000px;">
						<jsp:include page='<%="/Messageservlet"%>'></jsp:include>
					</div>
				</td>
			</tr>
			<tr>
				<td>
				<td align="center"> made by SunBird
			</tr>
		</table>
</body>
</html>