package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * SmartScriptLexer generates tokens based on following set of rules:<br>
 * Given text consists of TEXT and TAGS. Tags are placed inside {$ and $}. Spaces inside tags are ignored.<br>
 * Lexer processes text in TEXT mode until it finds tag open bracket. <br>
 * Then it switches its state to TAG mode and processes tag.<br>
 * Available tags are FOR (ForLoopTag), = (EchoTag), END (EndTag).<br><br>
 * While in TAG mode lexer can generate following tokens:<br>
 * VARIABLE, DOUBLE, INTEGER, FUNCTION, OPERATOR, STRING<br>
 * Variable starts with letter and consists of letters, digits or underscores.<br>
 * Double consists of digits and one dot. Can be negative with minus before.<br>
 * Integer consists of digits. Can be negative with minus before.<br>
 * Function starts with @ and consists of letters, digits and underscores.<br>
 * Operators can be + (plus), - (minus), * (multiplication), / (division), ^ (power).<br>
 * String are all chars placed inside quote marks.<br>
 * Inside strings you can escape \ and " by placing \ before them.<br>
 * Inside text you can escape \ and { by placing \ before them.<br><br>
 * 
 * Example of SmartScript:<br>
 * This is sample text.<br>
 * {$ FOR i 1 10 1 $}<br>
 * This is {$= i $}-th time this message is generated.<br>
 * {$END$}<br>
 * {$FOR i 0 10 2 $}<br>
 * sin({$=i$}^2) = {$= i i * @sin "0.000" @decfmt $}<br>
 * {$END$}
 * 
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class SmartScriptLexer {
	/**
	 * Document data.
	 */
	public char[] data;
	/**
	 * Current token.
	 */
	private Token token;
	/**
	 * Current index.
	 */
	private int currentIndex;
	/**
	 * Current lexer state.
	 */
	private LexerState state;
	
	/**
	 * Word tags length.
	 */
	private static int TAGLENGTH=3;
	
	/**
	 * Default constructor for Lexer. Default {@link LexerState state} of Lexer is TEXT.
	 * 
	 * @param text text that lexer will process
	 */
	public SmartScriptLexer(String text) {
		if(text == null) throw new IllegalArgumentException("Lexer must obtain some text.");
		data = text.toCharArray();
		token = null;
		currentIndex = 0;
		state = LexerState.TEXT;
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
	 * @throws SmartScriptLexerException for null or already existing state
	 */
	public void setState(LexerState state) {
		if(state==null) throw new SmartScriptLexerException("Lexer state can't be null.");
		if(this.state==state) throw new SmartScriptLexerException("Lexer is already in this state.");
		
		this.state = state;
	}
	
	/**
	 * Method which generates new token depending on state which lexer is in. <br>
	 * If there is no more input text, it returns EOF token.<br>
	 * Will throw exception if there are no more tokens.<br>
	 * @return newly generated token
	 * @throws SmartScriptLexerException if there are no more tokens or in case of invalid input text
	 */
	public Token nextToken() {
		if(!(token==null) && token.getType()==TokenType.EOF) {
			throw new SmartScriptLexerException("No more tokens.");
		}
		
		if(currentIndex>=data.length) {
			token = new Token(TokenType.EOF, null);
			return getToken();
		}
		
		if(state==LexerState.TEXT) {
			nextTokenTextState();
		} else if(state==LexerState.TAG) {
			nextTokenTagState();
		}
		
		return getToken();
	}
	
	/**
	 * Method processes text and generates token while in TAG state.
	 */
	private void nextTokenTagState() {
		skipSpaces();
		
		String tokenBuild = "";
		if(token==null || token.getType()==TokenType.TAG) {
			skipSpaces();
			if(data[currentIndex]=='=') {
				token = new Token(TokenType.TAGNAME, data[currentIndex]);
				currentIndex++;
				return;
			} else {
				for(int i=0; i<TAGLENGTH; i++) {
					tokenBuild+=data[currentIndex];	
					currentIndex++;
				}
				if(tokenBuild.toUpperCase().equals("FOR") || tokenBuild.toUpperCase().equals("END")) {
					token = new Token(TokenType.TAGNAME, tokenBuild);
					return;
				} else {
					throw new SmartScriptLexerException("Must be tag name after tag opening");
				}
			}
		} else if(data[currentIndex] == '$') {
			tokenBuild += data[currentIndex++];
			if(data[currentIndex] == '}') {
				tokenBuild += data[currentIndex++];
				token = new Token(TokenType.TAG, tokenBuild);
				return;
			} else {
				throw new SmartScriptLexerException("Must be sign $ after you open { parentheses.");
			}
		} else if(data[currentIndex] == '@') {
			currentIndex++;
			if(Character.isLetter(data[currentIndex])) {
				while(!isSpace(data[currentIndex]) && data[currentIndex]!='$') {
					if(Character.isLetter(data[currentIndex]) || Character.isDigit(data[currentIndex]) || data[currentIndex]=='_') {
						tokenBuild += data[currentIndex];
						currentIndex++;
					} else {
						throw new SmartScriptLexerException("Invalid function name");
					}	
				}		
			}
			
			token = new Token(TokenType.FUNCTION, tokenBuild);
		} else if(Character.isLetter(data[currentIndex])) {
			while(!isSpace(data[currentIndex]) && data[currentIndex]!='$') {
				if(Character.isLetter(data[currentIndex]) || Character.isDigit(data[currentIndex]) || data[currentIndex]=='_') {
					tokenBuild += data[currentIndex];
					currentIndex++;
				} else {
					throw new SmartScriptLexerException("Invalid variable name");
				}	
			}	
			
			token = new Token(TokenType.VARIABLE, tokenBuild);
			return;
		} else if(Character.isDigit(data[currentIndex])) {
			String numberBuild="";
			while(Character.isDigit(data[currentIndex])) {
				numberBuild+=data[currentIndex];
				currentIndex++;
			}
			if(data[currentIndex]=='.') {
				numberBuild+=data[currentIndex++];
				while(Character.isDigit(data[currentIndex])) {
					numberBuild+=data[currentIndex];
					currentIndex++;
				}
				try {
					token = new Token(TokenType.DOUBLE, Double.parseDouble(numberBuild));
				} catch(NumberFormatException e) {
					throw new SmartScriptLexerException("Can't parse number into double.");
				}
				
			} else {
				try {
					token = new Token(TokenType.INTEGER, Integer.parseInt(numberBuild));
				} catch(NumberFormatException e) {
					throw new SmartScriptLexerException("Can't parse number into integer.");
				}
			}
		} else if(isOperator(data[currentIndex])) {
			if(data[currentIndex]=='-' && Character.isDigit(data[currentIndex+1])) {
				String numberBuild="";
				numberBuild+=data[currentIndex++];
				while(Character.isDigit(data[currentIndex])) {
					numberBuild+=data[currentIndex];
					currentIndex++;
				}
				if(data[currentIndex]=='.') {
					numberBuild+=data[currentIndex++];
					while(Character.isDigit(data[currentIndex])) {
						numberBuild+=data[currentIndex];
						currentIndex++;
					}
					try {
						token = new Token(TokenType.DOUBLE, Double.parseDouble(numberBuild));
					} catch(NumberFormatException e) {
						throw new SmartScriptLexerException("Can't parse number into double.");
					}
					
				} else {
					try {
						token = new Token(TokenType.INTEGER, Integer.parseInt(numberBuild));
					} catch(NumberFormatException e) {
						throw new SmartScriptLexerException("Can't parse number into integer.");
					}
				}
				
			} else {
				token = new Token(TokenType.OPERATOR, data[currentIndex]);
				currentIndex++;
				return;
			}
		} else if(data[currentIndex]=='\"') {
			currentIndex++;
			while(data[currentIndex]!='\"') {
				if(data[currentIndex]=='\\') {
					currentIndex++;
					if(data[currentIndex]=='\\' || (data[currentIndex]=='\"')) {
						tokenBuild += data[currentIndex];
						currentIndex++;
					} else if(data[currentIndex]=='n' || (data[currentIndex]=='r') || (data[currentIndex]=='t')) {
						tokenBuild += "\\" + data[currentIndex];
						currentIndex++;
					} else {
						throw new SmartScriptLexerException("Invalid escape usage in strings.");
					}
				} else {
					tokenBuild += data[currentIndex];
					currentIndex++;
				}
			} 
			currentIndex++;
			tokenBuild = tokenBuild.replace("\\n", "\n");
			tokenBuild = tokenBuild.replace("\\r", "\r");
			tokenBuild = tokenBuild.replace("\\t", "\t");
			token = new Token(TokenType.STRING, tokenBuild);
			return;
		} else {
			throw new SmartScriptParserException("Lexer can't process this document.");
		}
	}
	
	/**
	 * Method processes text and generates token while in TEXT state.
	 */
	public void nextTokenTextState() {
		String tokenBuild = "";
		
		//instant tag token generation
		if(data[currentIndex] == '{') {
			tokenBuild += data[currentIndex++];
			if(data[currentIndex] == '$') {
				tokenBuild += data[currentIndex++];
				token = new Token(TokenType.TAG, tokenBuild);
				return;
			} else {
				throw new SmartScriptLexerException("Must be sign $ after you open { parentheses.");
			}
		} else {
			//text token generation
			while(currentIndex<data.length && data[currentIndex] != '{') {
				if(data[currentIndex]=='\\') {
					currentIndex++;
					if(data[currentIndex]=='\\' || (data[currentIndex]=='{')) {
						tokenBuild += data[currentIndex];
						currentIndex++;
					} else {
						throw new SmartScriptLexerException("Invalid escape usage in text.");
					}
				} else {
					tokenBuild+= data[currentIndex++];
					token = new Token(TokenType.TEXT, tokenBuild);
				}
			}
		}
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
	 * Method evaluates if char is mathematical operator.
	 * 
	 * @param inputChar char to be evaluated
	 * @return true if char is operator, false otherwise
	 */
	public static boolean isOperator(char inputChar) {
		if (inputChar == '+' || inputChar == '-' || inputChar == '*' || inputChar == '/' || inputChar == '^') {
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
}
