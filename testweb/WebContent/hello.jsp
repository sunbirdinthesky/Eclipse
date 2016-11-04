<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage="errorHander.jsp"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<% 
    if(request.getParameter("name")==null){
    	throw new RuntimeException("没有指定name请求参数。");
    }
%>
    hello,<%=request.getParameter("name") %>
    	
</body>
</html>