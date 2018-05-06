package hr.fer.zemris.java.custom.collections;

/**
 * Exception thrown when you try to perform some operations on stack which is empty
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class EmptyStackException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/** 
	 * Constructs a new runtime exception with the specified detail message.
	 * 
	 * @param message the detail message
	 */
	public EmptyStackException(String message) {
		super(message);
	}
	
	/**
	 * Constructs a new runtime exception with {@code null} as its
     * detail message. 
	 */
	public EmptyStackException() {
		super();
	}
}
