<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<html>
	<head>
	<title>Glasanje</title>
	</head>
	<body>
		<h1>Ankete</h1>
		<p>Postoje sljedeće ankete:</p>
		<ul>
			<c:forEach var="poll" items="${polls}">
			<li><a href="glasanje?pollID=${poll.id}">${poll.name}</a></li>
			</c:forEach>
		</ul>
	
	</body>
</html>