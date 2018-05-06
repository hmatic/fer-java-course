package hr.fer.zemris.java.hw07.shell.commands;

/**
 * Exception thrown upon error while parsing arguments.
 * @author Hrvoje Matić
 * @version 1.0
 */
public class ArgumentParserException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor with exception message.
	 * @param message exception message
	 */
	public ArgumentParserException(String message) {
		super(message);
	}
	
	/**
	 * Default constructor.
	 */
	public ArgumentParserException() {
		super();
	}
}
