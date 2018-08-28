package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Enumeration to determine current state of {@link SmartScriptLexer}.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 *
 */
public enum LexerState {
	/**
	 * State in which Lexer processes text.
	 */
	TEXT,
	/**
	 * State in which Lexer processes tags.
	 */
	TAG
}
