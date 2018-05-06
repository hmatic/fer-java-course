package hr.fer.zemris.java.custom.scripting.lexer;

public enum TokenType {
	/**
	 * Represents text outside tags.
	 */
	TEXT,
	/**
	 * Represents tag open and close brackets.
	 */
	TAG,
	/**
	 * Represents name of tag.
	 */
	TAGNAME,
	/**
	 * Represents number in Double format.
	 */
	DOUBLE,
	/**
	 * Represents number in Integer format.
	 */
	INTEGER,
	/**
	 * Represents function which starts with @.
	 */
	FUNCTION,
	/**
	 * Represents operator.
	 */
	OPERATOR,
	/**
	 * Represents string inside quotes.
	 */
	STRING,
	/** 
	 * Represents variable.
	 */
	VARIABLE, 
	/**
	 * Represents end of document.
	 */
	EOF
}
