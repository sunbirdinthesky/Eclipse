<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>欢迎登陆</title>
</head>
<body style="background-color: #303030">
		<% 
			if (session.getAttribute("username") == null)
				response.sendRedirect("/WeiBuo/view/index.jsp");
		%>
		<table style="color: #c0c0c0" border=0>
			<tr>
				<td width=300 align="right"><image width=100 height=100
						src="/WeiBuo/res/1.JPG">
				<td align="center">140字，记录下您生活的点点滴滴
			</tr>
			<tr>
				<td width=300 align="right">欢迎您, <%=session.getAttribute("username") %> <br>
					<a href="/WeiBuo/Loginservlet"> 退出登陆 </a> 
				</td>
			</tr>
			<tr>
				<td>
				<td>
					<div style="border-style:solid;overflow: auto; height: 400px; width: 1000px;" id="main">
						<jsp:include page='<%="/Messageservlet"%>'></jsp:include>
						<script type="text/javascript">
							var div = document.getElementById('main');
							div.scrollTop = div.scrollHeight;
						</script>
					</div>
				</td>
			</tr>
			<tr>
				<td>
				<td align="center">
					<form action="/WeiBuo/Publicservlet" method="post">
						<textarea style="resize:none;background-color: #707070;color: #e0e0e0" 
							rows="5" cols="40" maxlength="140" name="contain"></textarea> <br>
						<input align="middle" type="submit" value="新建微博" 
							style="background-color: #707070;color: #e0e0e0">
					</form>
				</td>
			</tr>
			<tr>
				<td>
				<td align="center"> made by SunBird
			</tr>
		</table>
</body>
</html>