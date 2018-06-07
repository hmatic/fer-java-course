<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<title>HW13 Index</title>
		<style>
			h2 {
				padding-top:20px;
				margin:0
			}
		</style>
	</head>
	<body bgcolor="${pickedBgCol}">
		<h1>Welcome to HW13 web application</h1>
		<a href="colors.jsp">Background color chooser</a>
		
		<h2>TRIGONOMETRIC:</h2>
		<a href="trigonometric?a=0&b=90">Trigonometric for a=0, b=90</a>
		<form action="trigonometric" method="GET">
 			Početni kut:<br><input type="number" name="a" min="0" max="360" step="1" value="0"><br>
			Završni kut:<br><input type="number" name="b" min="0" max="360" step="1" value="360"><br>
			<input type="submit" value="Tabeliraj"><input type="reset" value="Reset">
		</form>
		
		<h2>LINKS:</h2>
		<ul style="list-style-type: none; margin:0; padding:0;">
		<li><a href="stories/funny.jsp">Funny story</a></li>
		<li><a href="report.jsp">OS usage report</a></li>
		<li><a href="powers?a=1&b=100&n=3">Powers XLS for a=1, b=100, n=3</a></li>
		<li><a href="appinfo.jsp">App Startup Information</a></li>
		<li><a href="glasanje">Glasanje za omiljeni bend</a></li>
		</ul>
		
	</body>
</html>