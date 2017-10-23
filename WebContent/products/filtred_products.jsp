<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Техномаркет - резултати от търсене на продукти</title>
	</head>
	<body>
		<jsp:include page="../footear_and_header/header.jsp"></jsp:include>
		<c:if test="${sessionScope.filtredProducts != null}">
			<c:forEach items="${sessionScope.filtredProducts}" var="filtredProduct">
				<div>
				<c:if test="${filtredProduct.percentPromo > 0}">
					<img alt="promo" src="D:\technomarket_images\stickers\promo.jpg">
				</c:if>
				<h3>${filtredProduct.name}</h3>
				<span>${filtredProduct.productNumber}</span>
				<h1>${filtredProduct.price}</h1>
				<input type="button" value="Купи сега" name="${filtredProduct.productId}">
				</div>			
			</c:forEach>
		</c:if>
		<c:if test="${sessionScope.filtredProducts != null}">
			<p>Невалидно търсене</p>
		</c:if>
		<jsp:include page="../footear_and_header/footer.jsp"></jsp:include>
	</body>
</html>

