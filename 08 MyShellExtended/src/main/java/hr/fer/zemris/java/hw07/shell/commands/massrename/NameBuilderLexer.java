package hr.fer.zemris.java.hw07.shell.commands.massrename;

/**
 * Simple lexer for NameBuilderParser. Generates 3 type of tokens: CONSTANT, SUBSTITUTION and EOE
 * 
 * @author Hrvoje Matić
 *
 */
public class NameBuilderLexer {
	/**
	 * Data to be processed.
	 */
	private char[] data;
	/**
	 * Current position.
	 */
	private int pos;
	/**
	 * Current token.
	 */
	private NameBuilderToken token;
	
	/**
	 * Default constructor for NameBuilderLexer.
	 * @param expression
	 */
	public NameBuilderLexer(String expression) {
		this.data = expression.toCharArray();
		this.pos = 0;
		this.token = null;
	}
	
	/**
	 * Getter for current token.
	 * @return current token
	 */
	public NameBuilderToken getToken() {
		return token;
	}
	
	/**
	 * Generates new token and returns it.
	 * 
	 * @return new token
	 */
	public NameBuilderToken nextToken() {
		if(pos>=data.length) {
			token = new NameBuilderToken(null, NBTokenType.EOE);
			return token;
		}
		
		String tokenBuild = "";
		if(data[pos]=='$' && data[pos+1]=='{') {
			pos+=2;
			while(data[pos]!='}') {
				tokenBuild+=data[pos];
				pos++;
				if(pos>=data.length) {
					throw new NameBuilderParserException();
				}
			}
			pos++;
			token = new NameBuilderToken(tokenBuild, NBTokenType.SUBSTITUTION);
		} else {
			while(pos<data.length) {
				if(data[pos]=='$' && pos+1<data.length && data[pos+1]=='{') {
					break;
				}
				tokenBuild+=data[pos];
				pos++;
			}
			token = new NameBuilderToken(tokenBuild, NBTokenType.CONSTANT);
		}
		return token;
	}

}
