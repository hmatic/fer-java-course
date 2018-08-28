package hr.fer.zemris.java.tecaj_13.dao;

/**
 * Wrapper exception for all exceptions thrown from DAO methods.
 * @author Hrvoje Matic
 * @version 1.0
 */
public class DAOException extends RuntimeException {
	/**
	 * Serialization ID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Default constructor.
	 */
	public DAOException() {
	}
	
	/**
	 * Constructor with error message and wrapped exception.
	 * @param message error message
	 * @param cause wrapped exception
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor with error message.
	 * @param message error message
	 */
	public DAOException(String message) {
		super(message);
	}
	
	/**
	 * Constructor with wrapped exception.
	 * @param cause wrapped exception
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}
}