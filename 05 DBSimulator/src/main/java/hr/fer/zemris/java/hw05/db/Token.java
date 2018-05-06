package hr.fer.zemris.java.hw05.db;

/**
 * The Token class represents tokens returned by the lexer.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 * @param <Type> token type
 * @param <Value> token value
 * @see TokenType
 */
public class Token<Type, Value> {
	/**
	 * Type of token from {@link TokenType} enumeration.
	 */
	private Type type;
	/**
	 * Value of token.
	 */
	private Value value;
	
	/**
	 * Default constructor for Token.
	 * 
	 * @param type token type
	 * @param value token value
	 */
	public Token(Type type, Value value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Getter for token type.
	 * 
	 * @return type of token
	 */
	public Type getType() {
		return type;
	}
	
	/**
	 * Getter for token value.
	 * 
	 * @return token value
	 */
	public Value getValue() {
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
