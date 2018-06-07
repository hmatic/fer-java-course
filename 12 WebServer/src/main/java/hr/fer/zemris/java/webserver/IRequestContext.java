package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.util.Set;

import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Interface models request context.
 * @author Hrvoje Matic
 *
 */
public interface IRequestContext {
	/**
	 * Get parameter from parameters map.
	 * @param name parameter key
	 * @return parameter value
	 */
	String getParameter(String name);
	/**
	 * Get unmodifiable set of parameter keys.
	 * @return unmodifiable set of parameter keys
	 */
	Set<String> getParameterNames();
	/**
	 * Get parameter from persistent parameters map
	 * @param name parameter key
	 * @return persistent parameter value
	 */
	String getPersistentParameter(String name);
	/**
	 * Get unmodifiable set of persistent parameter keys.
	 * @return unmodifiable set of persistent parameter keys
	 */
	Set<String> getPersistentParameterNames();
	/**
	 * Set parameter in map of persistent parameters.
	 * @param name parameter key
	 * @param value parameter value
	 */
	void setPersistentParameter(String name, String value);
	/**
	 * Remove parameter from map of persistent parameters.
	 * @param name parameter key
	 */
	void removePersistentParameter(String name);
	/**
	 * Get parameter from map of temporary parameters.
	 * @param name parameter key
	 * @return temporary parameter value
	 */
	String getTemporaryParameter(String name);
	/**
	 * Get unmodifiable set of temporary parameter keys.
	 * @return unmodifiable set of temporary parameter keys
	 */
	Set<String> getTemporaryParameterNames();
	/**
	 * Set temporary parameter in map of temporary parameters.
	 * @param name parameter key
	 * @param value parameter value
	 */
	void setTemporaryParameter(String name, String value);
	/**
	 * Remove parameter from map of temporary parameters.
	 * @param name parameter key
	 */
	void removeTemporaryParameter(String name);
	/**
	 * Write array of bytes to context output stream. 
	 * Generate header before writing if header is not created already. 
	 * @param data array of data
	 * @return request context
	 * @throws IOException if error occurs on IO operations
	 */
	RequestContext write(byte[] data) throws IOException;
	/**
	 * Write from array of bytes to context output stream.
	 * Starting position is given in second argument and 
	 * number of bytes is given in third argument.
	 * @param data array of data
	 * @param offset starting offset
	 * @param len data length in bytes
	 * @return request context
	 * @throws IOException if error occurs on IO operations
	 */
	public RequestContext write(byte[] data, int offset, int len) throws IOException;
	/**
	 * Write String to context output stream. 
	 * Generate header before writing if header is not created already. 
	 * @param text string to be written
	 * @return request context
	 * @throws IOException if error occurs on IO operations
	 */
	RequestContext write(String text) throws IOException;
	/**
	 * Add RCCookie to list of cookies.
	 * @param rcCookie cookie
	 */
	void addRCCookie(RCCookie rcCookie);
}
