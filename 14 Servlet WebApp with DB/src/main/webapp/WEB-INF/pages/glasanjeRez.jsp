	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<head>
		<title>Rezultati glasanja</title>
		<style type="text/css">
			table.rez td {
				text-align: center;
			}
		</style>
	</head>
	<body>
		<a href="${pageContext.request.contextPath}/index.html">&lt;&lt; back to index</a>
		<h1>Rezultati glasanja</h1>
		<p>Ovo su rezultati glasanja.</p>
		<table border="1" class="rez">
			<thead>
				<tr>
					<th>Opcija</th>
					<th>Broj glasova</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="result" items="${results}">
					<tr><td>${result.optionTitle}</td><td>${result.votesCount}</td></tr>
				</c:forEach>
			</tbody>
		</table>
	
		<h2>Grafički prikaz rezultata</h2>
		<img alt="Pie-chart" src="glasanje-grafika?pollID=<%= request.getParameter("pollID") %>" width="500" height="270" />
	
		<h2>Rezultati u XLS formatu</h2>
		<p>
			Rezultati u XLS formatu dostupni su <a href="glasanje-xls?pollID=<%= request.getParameter("pollID") %>">ovdje</a>
		</p>
	
		<h2>Razno</h2>
		<p>Linkovi na pobjedničke opcije:</p>
		<ul>
			<c:forEach var="winner" items="${winners}">
				<li><a href="${winner.optionLink}" target="_blank">${winner.optionTitle}</a></li>
			</c:forEach>
		</ul>
	</body>
</html>