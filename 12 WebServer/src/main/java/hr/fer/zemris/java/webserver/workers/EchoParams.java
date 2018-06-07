package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.Logger;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Models worker which prints table containing parameters given to worker.
 * @author Hrvoje Matic
 */
public class EchoParams implements IWebWorker {
	@Override
	public void processRequest(RequestContext context) {
		try {
			if (context.getParameterNames().isEmpty()) {
				context.write("Expected some parameters.");
				return;
			}
			context.write("<table border=\"2\">");
			for (String paramName : context.getParameterNames()) {
				context.write("<tr><td>" + paramName + "</td><td>" + 
					context.getParameter(paramName) + "</td></tr>");
			}
			context.write("</table>");
		} catch (IOException e) {
			Logger.getLogger().log("IOException in EchoParams", e);
		}
	}
}