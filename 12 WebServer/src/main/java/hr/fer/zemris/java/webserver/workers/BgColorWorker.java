package hr.fer.zemris.java.webserver.workers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Models worker which changes background color in persistent parameters if given parameter is valid
 * HEX code for color. Will output status message and link to /index2.html.
 * @author Hrvoje Matic
 */
public class BgColorWorker implements IWebWorker{
	/**
	 * Color change success message.
	 */
	private static final String COLOR_CHANGED_MSG = "Color has been changed!";
	/**
	 * Color change failed message.
	 */
	private static final String COLOR_NOT_CHANGED_MSG = "Color has not been changed!";

	@Override
	public void processRequest(RequestContext context) throws Exception {
		String hexColor = context.getParameter("bgcolor");
		if(hexColor!=null) {
			Pattern colorPattern = Pattern.compile("^([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$");
			Matcher m = colorPattern.matcher(hexColor.toLowerCase());
			if(m.matches()) {
				context.setPersistentParameter("bgcolor", hexColor);
				context.write("<html><head><title>Color changed</title></head>"
						+ "<body> "+ COLOR_CHANGED_MSG );
			} else {
				context.write("<html><head><title>Color not changed</title></head>"
						+ "<body> " + COLOR_NOT_CHANGED_MSG);
			}
			context.write("<br><a href=\"/index2.html\">index2.html<a/></body></html>");
		}
	}

}
