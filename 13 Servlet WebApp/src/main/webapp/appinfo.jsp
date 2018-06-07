<%@ page import="java.time.Duration" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%!
private String appStartInfo(long appStart) throws java.io.IOException {
	long now = System.currentTimeMillis(); 
	Duration duration = Duration.ofMillis(now-appStart);
	StringBuilder sb = new StringBuilder();
			
	long days = duration.toDaysPart();
	long hours = duration.toHoursPart();
	long minutes = duration.toMinutesPart();
	long seconds = duration.toSecondsPart();
	long miliseconds = duration.toMillisPart();
	if(days>0) sb.append(days + " day" + (days==1 ? "" : "s") + ", ");
	if(hours>0) sb.append(hours + " hour" + (hours==1 ? "" : "s") + ", ");
	if(minutes>0) sb.append(minutes + " minute" + (minutes==1 ? "" : "s") + ", ");
	if(seconds>0) sb.append(seconds + " second" + (seconds==1 ? "" : "s") + " and ");
	if(miliseconds>0) sb.append(miliseconds + " milisecond" + (minutes==1 ? "" : "s"));
	
	return sb.toString();	     
}
%>
<html>
	<head>
		<title>App Startup Information</title>
	</head>
	<body bgcolor="${pickedBgCol}">
		<a href="${pageContext.request.contextPath}/index.jsp">&lt;&lt; back to index</a>
		<h1>Application has been running for
		<%
			long appStartTime = Long.parseLong(String.valueOf(application.getAttribute("appStart")));
			out.print(appStartInfo(appStartTime));
		%>.</h1>
	</body>
</html>