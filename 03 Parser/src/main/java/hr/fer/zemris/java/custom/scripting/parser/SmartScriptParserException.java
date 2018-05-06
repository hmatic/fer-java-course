package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Exception thrown upon any problems occurring with {@link SmartScriptParser}.
 * 
 * @author Hrvoje Matić
 */
public class SmartScriptParserException extends RuntimeException {

	private static final long serialVersionUID = -7146405271722626823L;
	
	/**
	 * Default constructor for SmartScriptParserException.
	 */
	public SmartScriptParserException() {
		super();
	}
	
	/**
	 * Constructor for SmartScriptParserException with appropriate error message.
	 */
	public SmartScriptParserException(String message) {
		super(message);
	}
}
