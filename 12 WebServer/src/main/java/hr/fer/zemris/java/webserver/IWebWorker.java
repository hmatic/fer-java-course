package hr.fer.zemris.java.webserver;

/**
 * Interface which models WebWorkers. Workers are processing parts of server.
 * @author Hrvoje Matic
 */
public interface IWebWorker {
	/**
	 * Process request given in request context.
	 * @param context request context
	 * @throws Exception throws exception upon errors
	 */
	void processRequest(RequestContext context) throws Exception;
}
