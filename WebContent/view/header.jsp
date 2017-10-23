<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="../css_and_js/styles/header_styles.css">
		<script type="text/javascript" src="../css_and_js/scripts/header_script.js"></script>
	</head>
	<body>
		<nav>
			<ul>
				<li>
					<a href="contacts.jsp">
						<img src="D:\technomarket_images\buttons\contacts.jpg" alt="contacts">
					</a>
				</li>
				<li>
					<a href="../ProductFIlterServlet?type=home">
						<img src="D:\technomarket_images\buttons\home.jpg" alt="home">
					</a>
				</li>
				<li>
					<a href="../ProductFIlterServlet?type=apple">
						<img src="D:\technomarket_images\buttons\apple.jpg" alt="apple">
					</a>
				</li>
				<li>
					<a href="../ProductFIlterServlet?type=promo">
						<img src="D:\technomarket_images\buttons\prom.jpg" alt="promo">
					</a>
				</li>
				<li>
					<a href="services.jsp">
						<img src="D:\technomarket_images\buttons\services.jpg" alt="services">
					</a>
				</li>
				<li>
					<a href="stores.jsp">
						<img src="D:\technomarket_images\buttons\stores.jpg" alt="stores">
					</a>
				</li>
			</ul>
		</nav>
		<a href="index.jsp">
			<img alt="technomarket_logo" src="D:\technomarket_images\logo\tm-logo.png">
		</a>
		<form action="../ProductFIlterServlet" method="get">
			<input type="text" name="searched_text" placeholder="Търси...">
			<input type="submit" id="search_button"><br>
		</form>
		<div class="user_dropdown">
  			<button class="drop_head_button"><c:out value = "${sessionScope.user != null ? user.firstName : 'Вход'}"/></button>
  			<div class="dropdown_content">
   				<c:if test="${sessionScope.user == null}">
					<a href="login.jsp">Вход</a>
   			 		<a href="register.jsp">Регистрация</a>
				</c:if>
   				<c:if test="${sessionScope.user != null}">
					<a href="profile.jsp">Профил</a>
    				<a href="orders.jsp">Поръчки</a>
    				<a href="favourites.jsp">Любими</a>
    				<c:if test="${sessionScope.user.isAdmin == true}">
						<a href="admin_panel.jsp">Админ панел</a>
					</c:if>
					<form action="../LogoutServlet" method="post">
						<a id="logout_a">Изход</a>
						<input type="submit" id="logout_submit"/>
					</form>
				</c:if>
 			 </div>
		</div>
		<button type="button"><c:out value = "${sessionScope.user.basket != null ? sessionScope.user.getBasketPrice() : '0'}"/></button>
	</body>
</html>