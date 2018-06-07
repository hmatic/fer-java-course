package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Worker that sets background color parameter from persistent parameters map to 
 * temporary parameters map and dispatches request to home.smscr script to generate index2.html
 * @author Hrvoje Matic
 */
public class Home implements IWebWorker {
	@Override
	public void processRequest(RequestContext context) throws Exception {
		String bgcolor = context.getPersistentParameter("bgcolor");
		if(bgcolor!=null) {
			context.setTemporaryParameter("background", bgcolor);
		} else {
			context.setTemporaryParameter("background", "7F7F7F");
		}
		context.getDispatcher().dispatchRequest("/private/home.smscr");
		
	}

}
