<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
		<title>Blog application</title>
	</head>
	<body style="padding: 20px; width:600px; margin: 0 auto;">
		<div style="text-align: right;">
			<c:choose>
				<c:when test="${empty sessionScope['current.user.id']}">Not logged in.</c:when>
				<c:otherwise>Logged in as <b>${sessionScope['current.user.nick']}</b>. (<a href="${pageContext.request.contextPath}/servleti/logout">Logout</a>)</c:otherwise>
			</c:choose>
		</div>
		<h1>Blog application</h1>
		<c:if test="${empty sessionScope['current.user.id']}">
			<form action="${pageContext.request.contextPath}/servleti/main/login" method="post">
				<div class="form-group">
					<label for="username">Username</label>
					<input type="text" class="form-control<c:if test="${loginForm.hasError('username')}"> is-invalid</c:if>" id="username" name="username" placeholder="Enter username" value="${loginForm.userName}"required>
					<div class="invalid-feedback">${loginForm.getError('username')}</div>
				</div>
				<div class="form-group">
					<label for="password">Password</label>
					<input type="password" class="form-control<c:if test="${loginForm.hasError('password')}"> is-invalid</c:if>" id="password" name="password" placeholder="Password" required>
					<div class="invalid-feedback">${loginForm.getError('password')}</div>
				</div>
				<button type="submit" class="btn btn-primary">Login</button>
			</form>
			<p>Dont have account yet? <a href="${pageContext.request.contextPath}/servleti/register">Register now.</a></p>
		</c:if>
		<h2 style="padding-top: 20px;">Available blogs:</h2>
		<div class="list-group">
			<c:choose>
				<c:when test="${empty users}">
	     		<span class="list-group-item">No blogs available.</span>
	    		</c:when>
				 <c:otherwise>
					<c:forEach var="user" items="${users}">
						<a href="author/${user.nick}" class="list-group-item list-group-item-action">${user.nick}</a>
					</c:forEach>
				 </c:otherwise>
			 </c:choose>
		</div>
	
	</body>
</html>
