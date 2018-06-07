<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%!
	private static String randomColor() {
		String[] colors = { "red", "blue", "green", "yellow" };
		return colors[(int)(System.currentTimeMillis() % colors.length)];
	}	
%>

<html>
	<head>
		<title>Funny story</title>
	</head>
	
	<body bgcolor="${pickedBgCol}">
		<a href="${pageContext.request.contextPath}/index.jsp">&lt;&lt; back to index</a>
		<p style="color:<%= randomColor() %>">
			A couple in their nineties are both having problems remembering things.
			They decide to go to the doctor for a checkup. The doctor tells them that they're physically okay, but they might want to start writing things down to help them remember.
			Later that night while watching TV, the old man gets up from his chair.
			His wife asks, "Where are you going?"
			"To the kitchen," he replies.
			"Will you get me a bowl of ice cream?"
			"Sure."
			"Don't you think you should write it down so you can remember it?" she asks.
			"No, I can remember it."
			"Well, I'd like some strawberries on top, too. You'd better write it down, because you know you'll forget it."
			He says, "I can remember that! You want a bowl of ice cream with strawberries."
			"I'd also like whipped cream. I'm certain you'll forget that, so you'd better write it down!" she retorts.
			Irritated, he says, "I don't need to write it down, I can remember it! Leave me alone! Ice cream with strawberries and whipped cream -- I got it, for goodness sake!" Then he grumbles into the kitchen.
			After about 20 minutes the old man returns from the kitchen and hands his wife a plate of bacon and eggs.
			She stares at the plate for a moment and says... "Where's my toast?
		</p>
	</body>
</html>