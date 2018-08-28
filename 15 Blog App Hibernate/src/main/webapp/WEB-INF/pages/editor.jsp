<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
		<title>${action} post</title>
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
				<li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/servleti/author/${user.nick}">${user.nick}</a></li>
				<c:if test="${action =='Edit'}">
				<li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/servleti/author/${user.nick}/${post.id}">${post.title}</a></li>
				</c:if>
				<li class="breadcrumb-item active" aria-current="page">${action} post</li>
			</ol>
		</nav>
		
		<h1>${action} post</h1>
		<form style="width: 500px" action="${pageContext.request.contextPath}/servleti/save" method="post">
			<div class="form-group">
				<label for="title">Title</label> 
				<input type="text" class="form-control" id="title" name="title" placeholder="Title" value="${post.title}" required>
			</div>
			
			<div class="form-group">
				<label for="title">Post</label> 
				<textarea class="form-control" id="text" name="text" placeholder="Text" required>${post.text}</textarea>
			</div>
			<input type="hidden" name="postID" value="${post.id}">
			<input type="hidden" name="user" value="${user.nick}">
			<button type="submit" class="btn btn-primary">${action} post</button>
		</form>
	</body>
</html>
