package hr.fer.zemris.java.hw07.shell;

/**
 * Exception thrown upon IOException occuring at MyShell.
 * @author Hrvoje Matić
 * @version 1.0
 */
public class ShellIOException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor with exception message.
	 * @param message exception message
	 */
	public ShellIOException(String message) {
		super(message);
	}

	/**
	 * Default constructor.
	 */
	public ShellIOException() {
		super();
	}

}
