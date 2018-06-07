package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Worker that takes a and b parameters and sets them as temporary parameters. 
 * If parameter is not given, default value will be used.
 * Default values: a = 1, b = 2.
 * It calculates their summation and sets it as "zbroj" in temporary parameters.
 * Dispatches request to calc.smscr to generate output table.
 * @author Hrvoje Matic
 *
 */
public class SumWorker implements IWebWorker {
	
	@Override
	public void processRequest(RequestContext context) throws Exception {
		int a=1;
		int b=2;
		if(context.getParameter("a")!=null) {
			a=Integer.parseInt(context.getParameter("a"));
		}
		if(context.getParameter("b")!=null) {
			b=Integer.parseInt(context.getParameter("b"));
		}
		int zbroj = a+b;
		context.setTemporaryParameter("a", String.valueOf(a));
		context.setTemporaryParameter("b", String.valueOf(b));
		context.setTemporaryParameter("zbroj", String.valueOf(zbroj));
		context.getDispatcher().dispatchRequest("/private/calc.smscr");
		
	}
	
}
