<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
  <form method="post" action="LoginServlet">
	账号：<input type="text" name="username"><br>
	密码：<input type="password" name="password"><br>
		<input type="checkbox" name="auto_login"> 自动登录<br>
		<input type="submit" value="登录">
  </form>
</body>
</html>