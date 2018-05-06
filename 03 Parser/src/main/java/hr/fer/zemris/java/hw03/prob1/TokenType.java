package hr.fer.zemris.java.hw03.prob1;

/**
 * Enumeration of token types.
 * 
 * @author Hrvoje
 * @version 1.0
 */
public enum TokenType {
	/**
	 * Represents end of input string.
	 */
	EOF, 
	/**
	 * Represents word which is consisted of letters.
	 */
	WORD, 
	/**
	 * Represents number that can be stored in Long type.
	 */
	NUMBER,
	/**
	 * Represents every other single char when words and numbers are removed from input.
	 */
	SYMBOL;
}
