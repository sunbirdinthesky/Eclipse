<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>投票</title>
</head>
<script>
	function jump() {
		window.self.location = "Result"; 
	}
</script>
<body>
${message}
	<br>
	<form action="Vote" method="post">
		<p> 请投票 <br>
			<input type = "radio" name = "ques1" value = "1"> c <br>
			<input type = "radio" name = "ques1" value = "2"> c++ <br>
			<input type = "radio" name = "ques1" value = "3"> jsp <br>
			<input type = "radio" name = "ques1" value = "4"> c# <br>
		<p> <input type = "submit" value = "提交" >
			<input type = "button" value="查看结果" onClick="jump()">
	</form>
</body>
</html>