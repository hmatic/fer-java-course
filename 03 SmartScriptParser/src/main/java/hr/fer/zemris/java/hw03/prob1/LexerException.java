package hr.fer.zemris.java.hw03.prob1;

/**
 * Exception thrown upon any problems occurring with {@link Lexer}.
 * 
 * @author Hrvoje Matić
 */
public class LexerException extends RuntimeException {

	private static final long serialVersionUID = -1173741942168406063L;

	/**
	 * Default constructor for LexerException.
	 */
	public LexerException(String message) {
		super(message);
	}
	
	/**
	 * Constructor for LexerException with appropriate error message.
	 */
	public LexerException() {
		super();
	}
}
