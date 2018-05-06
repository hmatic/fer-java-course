package hr.fer.zemris.java.hw05.db;

/**
 * Exception class thrown upon exception occurring inside {@link QueryLexer}.
 * @author Hrvoje Matić
 * @version 1.0
 * @see QueryLexer
 */
public class QueryLexerException extends RuntimeException {

	/**
	 * For serialization purposes.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Default constructor for QueryLexerException using exception message.
	 * @param message exception message
	 */
	public QueryLexerException(String message) {
		super(message);
	}
	
	/**
	 * Default constructor for QueryLexerException without specified exception message.
	 */
	public QueryLexerException() {
		super();
	}

}
