<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript" src="css_and_js/scripts/common_scripts.js"></script>
		<title>Insert title here</title>
	</head>
	<body>
	  <h1>Вход в сайта</h1>	
	  
		<c:if test="${ requestScope.error != null }">
			<div>
			<p>Невалидно удостоверение за автентикация.</p>
			</div>
		</c:if>
		<form action="LoginServlet" method="post">
			Е-мейл <input type="text" name="username"><br>
			Парола <input type="password" name="password"><br>
			Запомни ме <input type = "checkbox" name = "remember"> 
			<a href="forgotten_password.jsp">Забравена парола?</a>
			<input type="submit" value="Вход"><br>
		</form>
		<h1>Нямаш профил?</h1>
		<button onclick="visitRegister();">Регистрирай се</button>
	</body>
</html>