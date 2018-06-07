package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Exception re/thrown upon errors happening in SmartScriptEngine.
 * @author Hrvoje Matic
 */
public class SmartScriptEngineException extends RuntimeException {
	/**
	 * Serialization ID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor with error message for SmartScriptEngineException. 
	 * @param message error message
	 */
	public SmartScriptEngineException(String message) {
		super(message);
	}

}
