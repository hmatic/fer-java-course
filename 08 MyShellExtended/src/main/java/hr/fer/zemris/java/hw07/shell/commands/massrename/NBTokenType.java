package hr.fer.zemris.java.hw07.shell.commands.massrename;

/**
 * Enumeration defining NameBuilder token types.
 * Tokens can be string constant token, substitution command token or end of expression token.
 * @author Hrvoje Matić
 */
public enum NBTokenType {
	/** 
	 * String constant token type.
	 */
	CONSTANT,
	/**
	 * Substitution command token.
	 */
	SUBSTITUTION,
	/**
	 * End of expression.
	 */
	EOE
}
