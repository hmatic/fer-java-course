<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
		<title>Error</title>
	</head>
	<body style="padding: 20px; width:600px; margin: 0 auto;">
		<div style="text-align: right;">
			<c:choose>
				<c:when test="${empty sessionScope['current.user.id']}">Not logged in</c:when>
				<c:otherwise>Logged in as <b>${sessionScope['current.user.nick']}</b>. (<a href="${pageContext.request.contextPath}/servleti/logout">Logout</a>)</c:otherwise>
			</c:choose>
		</div>
		
		<h1 style="padding-top: 100px;">Sorry</h1>
		<p>${error}</p>
		
		<a href="${pageContext.request.contextPath}/servleti/main" class="btn btn-primary">Back to home</a>
	</body>
</html>
