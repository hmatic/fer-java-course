package hr.fer.zemris.java.hw05.db;

/**
 * Enumeration of Token types used in {@link QueryLexer}.
 * 
 * @author Hrvoje Matić
 * @see QueryLexer
 */
public enum TokenType {
	/**
	 * Represents end of line.
	 */
	EOL,
	/**
	 * Represents string token. String starts with quotation and ends with quotation.
	 */
	STRING,
	/**
	 * Represents keyword token. Keywords are names of attributes.
	 */
	KEYWORD,
	/**
	 * Represents operators which can be <, >, =, <=, >=, !=.
	 */
	OPERATOR,
	/**
	 * Represents AND keyword used to connect expressions.
	 */
	AND, 
	/**
	 * Represents LIKE operator.
	 */
	LIKE
}
