<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<title>Glasanje</title>
	</head>
	<body>
		<a href="${pageContext.request.contextPath}/index.html">&lt;&lt; back to index</a>
		<h1>${pollInfo.name}:</h1>
		<p>${pollInfo.message}</p>
		<ol>
			<c:forEach var="option" items="${options}">
				<li><a href="glasanje-glasaj?id=${option.id}">${option.optionTitle}</a></li>
			</c:forEach>
		</ol>
	</body>
</html>