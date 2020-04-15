<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <%@ taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	这是首页 ， 
	<c:if test="${not empty  userBean}">
		欢迎您, ${userBean.username }!
	</c:if>
	
	<c:if test="${ empty  userBean}">
		您好，请<a href="login.jsp">登录</a>!
	</c:if>
	
</body>
</html>