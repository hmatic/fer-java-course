package hr.fer.zemris.java.hw03.prob1;

/**
 * Lexer processes input text and generates tokens upon request.<br>
 * This lexer can generate tokens of type WORD, NUMBER and SYMBOL.<br>
 * In BASIC state:<br>
 * WORD is consisted of letters, NUMBER is consisted of digits while SYMBOL is everything else.<br>
 * Upon sign # lexer changes its state.<br>
 * In EXTENDED mode everything is generated into WORDs.
 * You can escape digits and \ by placing \ before them so they're counted as WORDs.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */

public class Lexer {
	private char[] data;
	private Token token;
	private int currentIndex;
	private LexerState state;
	
	/**
	 * Default constructor for Lexer. Default {@link LexerState state} of Lexer is BASIC.
	 * 
	 * @param text text that lexer will process
	 */
	public Lexer(String text) {
		if(text == null) throw new IllegalArgumentException("Lexer must obtain some text.");
		data = text.trim().toCharArray();
		token = null;
		currentIndex = 0;
		state = LexerState.BASIC;
	}
	
	/**
	 * Getter for token.
	 * 
	 * @return current token
	 */
	public Token getToken() {
		return token;
	}
	
	/** 
	 * Setter for lexer state.<br>
	 * Throws exception if lexer is already in given state or if state is null.
	 * 
	 * @param state state in which lexer will be
	 * @throws LexerException for null or already existing state
	 */
	public void setState(LexerState state) {
		if(state==null) throw new LexerException("Lexer state can't be null.");
		if(this.state==state)	throw new LexerException("Lexer is already in this state.");
		
		this.state = state;
	}
	
	/**
	 * Method which generates new token depending on state which lexer is in. <br>
	 * If there is no more input text, it returns EOF token.<br>
	 * Will throw exception if there are no more tokens.<br>
	 * @return newly generated token
	 * @throws LexerException if there are no more tokens or in case of invalid input text
	 */
	public Token nextToken() {
		if(!(token==null) && token.getType()==TokenType.EOF) {
			throw new LexerException("No more tokens.");
		}
				
		// Spaces are ignored, so skip all 
		skipSpaces();
		
		// No more input characters, returns EOF token.
		if(currentIndex>=data.length) {
			token = new Token(TokenType.EOF, null);
			return getToken();
		}
		
		if(state==LexerState.BASIC) {
			nextTokenBasic();
		} else if (state==LexerState.EXTENDED) {
			nextTokenExtended();
		}
		
		return getToken();
	}
	
	/**
	 * Method processes text and generates token while in BASIC state.
	 */
	public void nextTokenBasic() {
		//Determine type of first char
		TokenType tokenType;
		boolean escaped = false;
		
		if(data[currentIndex]=='\\') {
			currentIndex++;
			if(currentIndex<data.length && (determineChar(data[currentIndex]) == TokenType.NUMBER || data[currentIndex]=='\\')) {
				tokenType = TokenType.WORD;
				escaped = true;
			} else {
				throw new LexerException("Invalid escape usage.");
			}
		} else {
			tokenType = determineChar(data[currentIndex]);
		}
		
		
		// Instant token generation when symbol is found		
		if(tokenType == TokenType.SYMBOL) {
			token = new Token(TokenType.SYMBOL, data[currentIndex]);
			currentIndex++;
			return;
		}
		
		// Token can be either word or number, so we continue checking
		String tokenBuild = "";
		
		while(currentIndex<data.length) {
			char inputChar = data[currentIndex];
			if(escaped) {
				if(inputChar=='\\' || determineChar(inputChar)==TokenType.NUMBER) {
					tokenBuild+= data[currentIndex];
					currentIndex++;
					escaped = false;
				} else {
					throw new LexerException("Not valid escape usage.");
				}
			} else if (!escaped && inputChar=='\\') {
				currentIndex++;
				escaped = true;
			} else if(!isSpace(inputChar) && determineChar(inputChar)==tokenType) {
				tokenBuild += data[currentIndex];
				currentIndex++;	
			} else {
				break;
			}
		} 
		
		// If there is no new chars after escape, throws exception
		if(escaped) throw new LexerException("Wrong escape usage.");
		
		// if token is number, try to parse it
		if(tokenType == TokenType.NUMBER) {
			Long tokenParsedToLong;
			
			try {
				tokenParsedToLong = Long.parseLong(tokenBuild);
			} catch(NumberFormatException e) {
				throw new LexerException("no comprehendo");
			}
			
			token = new Token(tokenType, tokenParsedToLong);
		} else {
			token = new Token(tokenType, tokenBuild);
		}
	}
	
	/**
	 * Method processes text and generates token while in EXTENDED state.
	 */
	public void nextTokenExtended() {
		if(data[currentIndex]=='#') {
			token = new Token(TokenType.SYMBOL, '#');
			currentIndex++;
			return;
		}
		
		String tokenBuild = "";
		char inputChar;
		
		while(currentIndex<data.length) {
			inputChar=data[currentIndex];
			
			if(!isSpace(inputChar) && inputChar!='#') {
				tokenBuild += inputChar;
				currentIndex++;
			} else {
				break;
			}
		}
		
		token = new Token(TokenType.WORD, tokenBuild);
	}

	
	
	/* HELPER METHODS */
	
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
	
	/**
	 * Method evaluates type of token given char should generate.
	 * 
	 * @param inputChar char to be evaluated
	 * @return type of token of given char
	 */
	private TokenType determineChar(char inputChar) {
		TokenType tokenType;
		
		if(Character.isLetter(inputChar)) {
			tokenType = TokenType.WORD;
		} else if(Character.isDigit(inputChar)) {
			tokenType = TokenType.NUMBER;
		} else {
			tokenType = TokenType.SYMBOL;
		}
		
		return tokenType;
	}
	
	/**
	 * Method that skips all spaces in array of chars.
	 */
	private void skipSpaces() {
		while(currentIndex<data.length && isSpace(data[currentIndex])) {
			currentIndex++;
		}
	}
	
}
