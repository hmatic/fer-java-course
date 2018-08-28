package hr.fer.zemris.java.tecaj_13.web.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Abstract class which models all Form objects. 
 * Each form must have some type of validation. 
 * Form's error messages are stored in map which 
 * has name of field which caused error as key 
 * and error message as value.
 * All form variables must be Strings for easier 
 * validation. You must be able to fill form directly 
 * from HttpRequest.
 * @author Hrvoje Matic
 * @version 1.0
 */
public abstract class Form {
	/**
	 * Map of validation error messages.
	 */
	private Map<String, String> errors = new HashMap<>();
	
	/**
	 * Check if form input has any validation errors.
	 * @return true if form input has errors, false otherwise
	 */
	public boolean hasErrors() {
		return !errors.isEmpty();
	}
	
	/**
	 * Get form error specified by name in argument.
	 * @param name name of form field that caused error
	 * @return form error
	 */
	public String getError(String name) {
		return errors.get(name);
	}
	
	/**
	 * Checks if form has specific error.
	 * @param name name of form field that caused error
	 * @return true if contains such error, false otherwise
	 */
	public boolean hasError(String name) {
		return errors.containsKey(name);
	}
	
	/**
	 * Adds new error message to form.
	 * @param name name of field that causes error
	 * @param message error message
	 */
	public void addError(String name, String message) {
		errors.put(name, message);
	}
	
	/**
	 * Helper method which prepares form's variables.
	 * It is much easier to handle forms if nulls are 
	 * converted to empty strings and trimmed down.
	 * @param param form parameter
	 * @return processed parameter
	 */
	public String prepareParam(String param) {
		if(param==null) return "";
		return param.trim();
	}
	
	/**
	 * Method used to validate form input. 
	 * If there are any errors found, add them 
	 * in errors map from this method.
	 */
	public abstract void validate();
	
	/**
	 * Fills form data directly from HttpServletRequest.
	 * @param req http request
	 */
	public abstract void fillFromRequest(HttpServletRequest req);
}
