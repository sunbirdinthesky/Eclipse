<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<jsp:useBean id="book" type="com.yang.control.BookBean" scope="session"/>

 <html> 
  <body>
    <table border="0">
    <tr><td>书号：</td>
    <td><jsp:getProperty property="bookid" name="book"/></td></tr>
    <tr><td>书名：</td>
    <td><jsp:getProperty property="title" name="book"/></td></tr>
    <tr><td>作者：</td>
    <td><jsp:getProperty property="author" name="book"/></td></tr>
    <tr><td>出版社：</td>
    <td><jsp:getProperty property="publisher" name="book"/></td></tr>
    <tr><td>价格：</td>
    <td><jsp:getProperty property="price" name="book"/></td></tr>
    </table> <br>
  </body>
</html>
