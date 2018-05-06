package hr.fer.zemris.java.hw05.db;

/**
 * QueryLexer generates {@link Token Tokens} based on following set of rules:<br>
 * KEYWORD tokens are names of attributes: jmbag, lastName, firstName. <br>
 * OPERATOR tokens are all one or two part operators which contain one or two of following: <, >, =, ! <br>
 * STRING tokens are strings which start with quotation marks and end with quotation marks("string example").
 * AND token represents "AND" keyword used to connect conditional expressions.<br>
 * LIKE token is generated for LIKE operator.<br>
 * EOL token represents end of query line. <br><br>
 * 
 * This Lexer is used by {@link QueryParser}.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 * @see QueryParser
 */
public class QueryLexer {
	/**
	 * Array of input chars.
	 */
	public char[] data;
	/**
	 * Current token.
	 */
	private Token<TokenType, String> token;
	/**
	 * Current index in array of data.
	 */
	private int currentIndex;
	
	/**
	 * Default constructor for QueryLexer.
	 * 
	 * @param text text of query that QueryLexer will process
	 * @throws IllegalArgumentException if QueryLexer does not obtain any query
	 */
	public QueryLexer(String text) {
		if(text == null) throw new IllegalArgumentException("Lexer must obtain some query.");
		data = text.trim().toCharArray();
		token = null;
		currentIndex = 0;
	}
	
	/**
	 * Getter for token.
	 * 
	 * @return current token
	 */
	public Token<TokenType, String> getToken() {
		return token;
	}
	
	/**
	 * Method which generates new token and returns it. <br>
	 * If there is no more input text, it returns EOL(end of line) token.<br>
	 * Will throw exception if there are no more tokens upon call of method.<br>
	 * @return newly generated token
	 * @throws QueryLexerException if there are no more tokens or in case of invalid query
	 */
	public Token<TokenType, String> nextToken() {
		if(!(token==null) && token.getType()==TokenType.EOL) {
			throw new QueryLexerException("No more tokens. Error with lexer.");
		}
		
		if(currentIndex>=data.length) {
			token = new Token<TokenType, String>(TokenType.EOL, null);
			return getToken();
		}
		
		skipSpaces();
		 
		if(isOperator(data[currentIndex])) { //Create token of type OPERATOR.
			String operatorToken = "";
			if(isOperator(data[currentIndex+1])) {
				if(!isOperator(data[currentIndex+2])) {
					operatorToken += data[currentIndex++];
					operatorToken += data[currentIndex++];
					token = new Token<TokenType, String>(TokenType.OPERATOR, operatorToken);					
				} else {
					throw new QueryLexerException("Not valid operator");
				}
			} else {
				operatorToken += data[currentIndex++];
				token = new Token<TokenType, String>(TokenType.OPERATOR, operatorToken);
			}
			return getToken();
			
		} else if(data[currentIndex]=='\"') { // Create token of type STRING.
			String stringToken = "";
			currentIndex++;
			while(data[currentIndex]!='\"') {
				stringToken += data[currentIndex];
				currentIndex++;
			} 
			currentIndex++;
			
			token = new Token<TokenType, String>(TokenType.STRING, stringToken);
			return getToken();
			
		} else if(Character.isLetter(data[currentIndex])) { // Create token of type KEYWORD or LIKE/AND.
			String keywordToken = "";
			
			while(Character.isLetter(data[currentIndex]) && currentIndex<data.length-1) {
				keywordToken += data[currentIndex];
				currentIndex++;
			}
			
			if(keywordToken.toLowerCase().equals("like")) {
				token = new Token<TokenType, String>(TokenType.LIKE, keywordToken);
			} else if(keywordToken.toLowerCase().equals("and")) {
				token = new Token<TokenType, String>(TokenType.AND, keywordToken);
			} else {
				token = new Token<TokenType, String>(TokenType.KEYWORD, keywordToken);
			}	
			return getToken();
		
		} else { // No token can be generated, throw exception.
			throw new QueryLexerException("Input can not be processed by lexer.");
		}
	}
	
	
	// HELPER METHODS:
	
	/**
	 * Method evaluates if char is one of query operators.
	 * 
	 * @param inputChar char to be evaluated
	 * @return true if char is query operator, false otherwise
	 */
	public static boolean isOperator(char inputChar) {
		if (inputChar == '>' || inputChar == '=' || inputChar == '<' || inputChar == '!') {
		    return true;
		}
		return false;
	}
	
	/**
	 * Method that skips all spaces in array of chars.
	 */
	private void skipSpaces() {
		while(currentIndex<data.length && isSpace(data[currentIndex])) {
			currentIndex++;
		}
	}
	
	/**
	 * Method evaluates if char is any kind of space.
	 * 
	 * @param inputChar char to be evaluated
	 * @return true if char is space, false otherwise
	 */
	public static boolean isSpace(char inputChar) {
		if (inputChar == ' ' || inputChar == '\t' || inputChar == '\r' || inputChar == '\n') {
		    return true;
		}
		return false;
	}
	
}
