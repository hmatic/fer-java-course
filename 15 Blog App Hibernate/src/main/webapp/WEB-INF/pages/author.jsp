<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
		<title>${user.firstName} ${user.lastName} blog</title>
	</head>
	<body style="padding: 20px; width:600px; margin: 0 auto;">
		<div style="text-align: right;">
			<c:choose>
				<c:when test="${empty sessionScope['current.user.id']}">Not logged in</c:when>
				<c:otherwise>Logged in as <b>${sessionScope['current.user.nick']}</b>. (<a href="${pageContext.request.contextPath}/servleti/logout">Logout</a>)</c:otherwise>
			</c:choose>
		</div>
		
		<nav aria-label="breadcrumb">
			<ol class="breadcrumb" style="background-color:white; padding-left: 0px;">
				<li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/servleti/main">Home</a></li>
				<li class="breadcrumb-item active" aria-current="page">${user.nick}</li>
			</ol>
		</nav>
		<h1>${user.firstName} ${user.lastName} blog</h1>
		<c:choose>
			<c:when test="${empty entries}">
	     		<p>This blog has no posts.</p>
	    	</c:when>
			<c:otherwise>
				<p>List of ${user.nick} blog post entries:</p>
				<div class="list-group" style="margin-top:20px; margin-bottom:20px;">
					<c:forEach var="entry" items="${entries}">
						<a href="${user.nick}/${entry.id}" class="list-group-item list-group-item-action">${entry.title}</a>
					</c:forEach>
				</div>
			</c:otherwise>
		</c:choose>
		<c:if test="${sessionScope['current.user.nick'].equals(user.nick)}">
			<a href="${user.nick}/new" class="btn btn-success">Add new post</a>
		</c:if>
		
	
	</body>
</html>
