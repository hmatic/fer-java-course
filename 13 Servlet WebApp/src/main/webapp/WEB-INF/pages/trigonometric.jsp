<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<title>Trigonometric values</title>
	</head>
	<body bgcolor="${pickedBgCol}">
		<a href="${pageContext.request.contextPath}/index.jsp">&lt;&lt; back to index</a>
		<h1>Trigonometric table</h1>
		<table border="1">
			<thead>
				<tr bgcolor="lightGray"><td>Angle</td><td>Sinus</td><td>Cosinus</td></tr>
			</thead>
			<tbody>
				<c:forEach var="trigonometricValues" items="${trigonometricTable}" >
					<tr>
					<td>${trigonometricValues.angle}</td>
					<td>${trigonometricValues.sin}</td>
					<td>${trigonometricValues.cos}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</body>
</html>