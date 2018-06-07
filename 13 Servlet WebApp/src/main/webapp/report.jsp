<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
	<head>
		<title>OS usage report</title>
	</head>
	<body bgcolor="${pickedBgCol}">
		<a href="${pageContext.request.contextPath}/index.jsp">&lt;&lt; back to index</a>
		<h1>OS usage</h1>
		<p>Here are the results of OS usage in survey that we completed.</p>
		<img src="reportImage" alt="OS usage pie chart">
	</body>
</html>