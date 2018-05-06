package hr.fer.zemris.java.hw05.db;

/**
 * Exception class thrown upon exception occurring inside {@link QueryParser}.
 * @author Hrvoje Matić
 * @version 1.0
 * @see QueryParser
 */
public class QueryParserException extends RuntimeException {

	/**
	 * For serialization purposes.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Default constructor for QueryParserException using exception message.
	 * @param message exception message
	 */
	public QueryParserException(String message) {
		super(message);
	}
	
	/**
	 * Default constructor for QueryParserException without specified exception message.
	 */
	public QueryParserException() {
		super();
	}
}
