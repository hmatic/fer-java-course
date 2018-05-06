package hr.fer.zemris.java.hw07.shell.commands.massrename;

/**
 * Exception thrown upon errors or invalid inputs happening at NameBuilderParser.
 * 
 * @author Hrvoje Matić
 *
 */
public class NameBuilderParserException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Default constructor for NameBuilderParserException.
	 */
	public NameBuilderParserException() {
		super();
	}
	
	/**
	 * Constructor with error message for NameBuilderParserException.
	 * @param message error message
	 */
	public NameBuilderParserException(String message) {
		super(message);
	}
	
	

}
