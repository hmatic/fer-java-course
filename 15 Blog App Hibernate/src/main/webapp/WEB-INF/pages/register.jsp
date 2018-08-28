<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
	<head>
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
		<title>Register</title>
	</head>
	<body style="padding: 20px; width:600px; margin: 0 auto;">
		<nav aria-label="breadcrumb">
			<ol class="breadcrumb" style="background-color:white; padding-left: 0px;">
				<li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/servleti/main">Home</a></li>
				<li class="breadcrumb-item active" aria-current="page">Register</li>
			</ol>
		</nav>
		<h1>Register</h1>
		<form style="width: 500px" action="register" method="post">
		
			<div class="form-row">
				<div class="form-group col-md-6">
					<label for="firstName">First name</label> <input type="text"
						class="form-control<c:if test="${form.hasError('firstName')}"> is-invalid</c:if>" id="firstName" name="firstName" placeholder="First name" value="${form.firstName}" required>
					<div class="invalid-feedback">${form.getError('firstName')}</div>
				</div>
				<div class="form-group col-md-6">
					<label for="lastName">Last name</label> <input type="text"
						class="form-control<c:if test="${form.hasError('lastName')}"> is-invalid</c:if>" id="lastName" name="lastName" placeholder="Last name" value="${form.lastName}" required>
					<div class="invalid-feedback">${form.getError('lastName')}</div>
				</div>
			</div>
			
			<div class="form-group">
				<label for="userName">Username</label>
				<div class="input-group">
					<div class="input-group-prepend">
						<span class="input-group-text" id="usernamePrepend">@</span>
					</div>
					<input type="text" 
						class="form-control<c:if test="${form.hasError('userName')}"> is-invalid</c:if>"
						id="userName" name="userName"
						placeholder="Username" value="${form.userName}"
						aria-describedby="usernamePrepend" 
						required>
					<div class="invalid-feedback">${form.getError('userName')}</div>
	
				</div>
			</div>
	
			<div class="form-group">
				<label for="email">Email</label> <input type="email"
					class="form-control<c:if test="${form.hasError('email')}"> is-invalid</c:if>" id="email" name="email" placeholder="Email" value="${form.email}" required>
				<div class="invalid-feedback">${form.getError('email')}</div>
			</div>
			
			<div class="form-row">
				<div class="form-group col-md-6">
					<label for="password">Password</label> <input type="password"
						class="form-control<c:if test="${form.hasError('password')}"> is-invalid</c:if>" id="password" name="password" placeholder="Password" required>
					<div class="invalid-feedback">${form.getError('password')}</div>
				</div>
				<div class="form-group col-md-6">
					<label for="repeat-password">Repeat password</label> <input
						type="password" class="form-control" id="repeat-password" oninput="checkPassword(this)"
						placeholder="Repeat password" required>
				</div>
			</div>
	
			<button type="submit" class="btn btn-primary">Register</button>
		</form>
		
		
		
		<script language='javascript' type='text/javascript'>
			function checkPassword(input) {
				if (input.value != document.getElementById('password').value) {
					input.setCustomValidity('Passwords must be matching.');
				} else {
					input.setCustomValidity('');
				}
			}
		</script>
</body>
</html>
