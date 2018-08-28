package hr.fer.zemris.java.hw16.trazilica;

/**
 * Exception rethrown when IOException occurs in console.
 * @author Hrvoje Matic
 *
 */
public class ConsoleIOException extends RuntimeException {
	/**
	 * Serialization ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public ConsoleIOException() {
		super();
	}
	
	/**
	 * Constructor with error message.
	 * @param message
	 */
	public ConsoleIOException(String message) {
		super(message);
	}

}
