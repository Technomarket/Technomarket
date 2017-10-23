<%@ page language="java"  contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Ð¢ÐµÑÐ½Ð¾Ð¼Ð°ÑÐºÐµÑ - ÑÐµÐ·ÑÐ»ÑÐ°ÑÐ¸ Ð¾Ñ ÑÑÑÑÐµÐ½Ðµ Ð½Ð° Ð¿ÑÐ¾Ð´ÑÐºÑÐ¸</title>
	</head>
	<body>
		<jsp:include page="header.jsp"></jsp:include>
		<c:if test="${ sessionScope.filtredProducts != null}">
			<c:forEach items="${ sessionScope.filtredProducts}" var="filtredProduct">
				<div>
				<c:if test="${ filtredProduct.percentPromo > 0}">
					<img alt="promo" src="D:\technomarket_images\stickers\promo.jpg">
				</c:if>
				<h3>${filtredProduct.name}</h3>
				<span>${filtredProduct.productNumber}</span>
				<h1>${filtredProduct.price}</h1>
				<input type="button" value="ÐÑÐ¿Ð¸ ÑÐµÐ³Ð°" name="${ filtredProduct.productId}">
				</div>			
			</c:forEach>
		</c:if>
		<jsp:include page="footer.jsp"></jsp:include>
	</body>
</html>

