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
	<body bgcolor="${pickedBgCol}">
		<a href="${pageContext.request.contextPath}/index.jsp">&lt;&lt; back to index</a>
		<h1>Rezultati glasanja</h1>
		<p>Ovo su rezultati glasanja.</p>
		<table border="1" class="rez">
			<thead>
				<tr>
					<th>Bend</th>
					<th>Broj glasova</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="result" items="${results}">
					<tr><td>${bands[result.id].name}</td><td>${result.voteCount}</td></tr>
				</c:forEach>
			</tbody>
		</table>
	
		<h2>Grafički prikaz rezultata</h2>
		<img alt="Pie-chart" src="glasanje-grafika" width="500" height="270" />
	
		<h2>Rezultati u XLS formatu</h2>
		<p>
			Rezultati u XLS formatu dostupni su <a href="glasanje-xls">ovdje</a>
		</p>
	
		<h2>Razno</h2>
		<p>Primjeri pjesama pobjedničkih bendova:</p>
		<ul>
			<c:forEach var="winnerID" items="${winners}">
				<li><a href="${bands[winnerID].songLink }" target="_blank">${bands[winnerID].name}</a></li>
			</c:forEach>
		</ul>
	</body>
</html>