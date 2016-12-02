<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>使用EL访问javaBeans的属性</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <ul>
    <li>客户名：${customer.custName}
    <li>Email地址：${customer.email }
    <li>电话：${customer.phone }
    </ul> 
    <ul>
    <li>城市：${customer.address.city }
    <li>街道：${customer.address.street }
    <li>邮编：${customer.address.zipCode}
    </ul><br>
  
    <a href="JSP1.jsp" >进入JSP1</a>
  </body>
</html>
