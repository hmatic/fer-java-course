<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
	<head>
		<title>Pick background color</title>
	</head>
	<body bgcolor="${pickedBgCol}">
		<a href="${pageContext.request.contextPath}/index.jsp">&lt;&lt; back to index</a>
	
		<ul style="list-style-type: none; margin-top:20px; padding:0;">
			<li><a href="setcolor?bgcolor=white">WHITE</a></li>
			<li><a href="setcolor?bgcolor=red">RED</a></li>
			<li><a href="setcolor?bgcolor=green">GREEN</a></li>
			<li><a href="setcolor?bgcolor=cyan">CYAN</a></li>
		</ul>
	</body>
</html>