package hr.fer.zemris.java.hw07.shell.commands.massrename;

/**
 * Class representing token in NameBuilder lexer and parser.
 * Token is consisted of data and type.
 * For token types see {@link NBTokenType}.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class NameBuilderToken {
	/**
	 * Token data.
	 */
	private String data;
	/**
	 * Token type.
	 */
	private NBTokenType type;
	
	/**
	 * Default constructor for NameBuilderToken.
	 * 
	 * @param data token data
	 * @param type token type
	 */
	public NameBuilderToken(String data, NBTokenType type) {
		super();
		this.data = data;
		this.type = type;
	}
	
	/**
	 * Getter for token data.
	 * @return token data
	 */
	public String getData() {
		return data;
	}
	
	/**
	 * Getter for token type.
	 * @return token type
	 */
	public NBTokenType getType() {
		return type;
	}
	
}
