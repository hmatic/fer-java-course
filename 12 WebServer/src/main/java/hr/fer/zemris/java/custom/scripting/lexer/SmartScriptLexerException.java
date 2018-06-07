package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Exception thrown upon any problems occurring with {@link SmartScriptLexer}.
 * 
 * @author Hrvoje Matić
 */
public class SmartScriptLexerException extends RuntimeException {
	/**
	 * Serialization ID.
	 */
	private static final long serialVersionUID = 5936656131853783560L;
	
	/**
	 * Default constructor for SmartScriptLexerException.
	 */
	public SmartScriptLexerException() {
		super();
	}
	
	/**
	 * Constructor for SmartScriptLexerException with appropriate error message.
	 * @param message exception message
	 */
	public SmartScriptLexerException(String message) {
		super(message);
	}
}
