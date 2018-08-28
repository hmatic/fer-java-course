<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
		<title>${post.title} | ${user.firstName} ${user.lastName} blog</title>
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
				<li class="breadcrumb-item active" aria-current="page">${post.title}</li>
			</ol>
		</nav>
		
		
		<h1>${post.title}</h1>
		<span class="text-muted">Written by ${user.nick} on ${post.creationDateFormatted()}</span>
		<div class="card" style="margin-top:20px; margin-bottom:20px;">
    		<div class="card-body">${post.text}</div>
    	</div>
    	
    	<c:if test="${sessionScope['current.user.nick'].equals(user.nick)}">
			<a href="edit/${post.id}" class="btn btn-primary">Edit post</a>
		</c:if>
		
		<h2 style="padding-top: 20px; padding-bottom: 20px;">Comments</h2>
		
		<c:forEach var="comment" items="${post.comments}">
			<div class="card" style="margin-top: 10px;">
				<div class="card-header">
					<span class="card-title">${comment.usersEMail }</span>
					<span style="float:right;" class="text-muted">${comment.postedOnFormatted()}</span>
				</div>
				<div class="card-body">
					<p class="card-text">${comment.message}</p>
				</div>
			</div>
		</c:forEach>
		
		
		<div class="card" style="margin-top: 10px;">
			<div class="card-header">
				<h3>Leave a comment</h3>
			</div>
			<div class="card-body">
				<form action="${pageContext.request.contextPath}/servleti/comment" method="POST">
					
				<div class="col-md-6 form-group">
					<label class="sr-only" for="email">Email</label>
					<input type="email" class="form-control<c:if test="${commentForm.hasError('email')}"> is-invalid</c:if>" id="email" name="email" placeholder="Email" value="${commentForm.email}">
					<div class="invalid-feedback">${commentForm.getError('email')}</div>
				</div>
				
				<div class="col-md-12 form-group">
					<label class="sr-only" for="email">Comment</label>
					<textarea class="form-control<c:if test="${commentForm.hasError('message')}"> is-invalid</c:if>" id="comment" name="message" placeholder="Comment">${commentForm.message}</textarea>
					<div class="invalid-feedback">${commentForm.getError('message')}</div>
				</div>
				
				<div class="col-md-12 form-group text-right">
					<input type="hidden" name="postID" value="${post.id}">
					<input type="hidden" name="user" value="${user.nick}">
					<button type="submit" class="btn btn-primary">Add comment</button>
				</div>
				
				</form>
			</div>
		</div>
	</body>
</html>
