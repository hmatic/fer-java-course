package hr.fer.zemris.java.webserver;

/**
 * Models Dispatcher for SmartHttpServer. Dispatcher dispatches requests to appropriate place.
 * @author Hrvoje Matic
 */
public interface IDispatcher {
	/**
	 * Dispatch request given by URL path in argument.
	 * @param urlPath path of request
	 * @throws Exception if error occurs
	 */
	void dispatchRequest(String urlPath) throws Exception;
}
