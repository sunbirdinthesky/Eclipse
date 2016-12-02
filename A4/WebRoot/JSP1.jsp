<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.yang.control.*" %>

<%
	AddressBean address=new AddressBean("jinzhou","Science and Technology Road","121000");
	CustomerBean  customer=new CustomerBean("Hacker","hacker@163.com","8899123",address);
	session.setAttribute("customer",customer);
	Cookie cookie=new Cookie("userName","Hacker");
	response.addCookie(cookie);
	%>

<html>
 
  <body>
  	<h4>隐含变量的使用</h4>
  	<table border="1">
  	<tr><td>EL表达式</td></tr>
  	<tr><td>\${pageContext.request.method}</td>
  	<td>${pageContext.request.method}</td></tr>
  	<tr><td>\${param.userName}</td><td>${param.userName}</td></tr>
  	
  	<tr><td>\${cookie.userName.value}</td><td>${cookie.userName.value}</td></tr>
  	<tr><td>\${initparam.email}</td><td>${initparam.email}</td></tr>
  	<tr><td>\${sessionScope.customer.custName}</td><td>${sessionScope.customer.custName}</td></tr>
  	</table>
     <br>
  </body>
</html>
