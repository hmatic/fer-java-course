package hr.fer.zemris.java.hw03.prob1;

/**
 * The Token class represents tokens returned by the lexer.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class Token {
	/**
	 * Type of token from {@link TokenType} enumeration.
	 */
	private TokenType type;
	/**
	 * Value of token.
	 */
	private Object value;
	
	/**
	 * Default constructor for Token.
	 * 
	 * @param type token type
	 * @param value token value
	 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Getter for token type.
	 * 
	 * @return type of token
	 */
	public TokenType getType() {
		return type;
	}
	
	/**
	 * Getter for token value.
	 * 
	 * @return token value
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * Returns String representation of Token.
	 * @return token fields as String
	 */
	@Override
	public String toString() {
		return "(" + type + "," + value +")";
	}
}