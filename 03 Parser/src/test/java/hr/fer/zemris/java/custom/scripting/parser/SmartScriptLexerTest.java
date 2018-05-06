package hr.fer.zemris.java.custom.scripting.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import hr.fer.zemris.java.custom.scripting.lexer.*;

public class SmartScriptLexerTest {
	
	@Test
	public void testNotNull() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		assertNotNull("Token was expected but null was returned.", lexer.nextToken());
	}
	
	@Test
	public void testTextMode() {
		// Lets check for several symbols...
		SmartScriptLexer lexer = new SmartScriptLexer("This is test text {$");

		Token correctData[] = {
			new Token(TokenType.TEXT, "This is test text "),
			new Token(TokenType.TAG, "{$"),
			new Token(TokenType.EOF, null)
		};
		checkTokenStream(lexer, correctData);
	}
	
	@Test
	public void testTagMode() {
		// Lets check for several symbols...
		SmartScriptLexer lexer = new SmartScriptLexer(" FOR i -1 10.0 @sin * \"string\" $}");

		Token correctData[] = {
			new Token(TokenType.TAGNAME, "FOR"),
			new Token(TokenType.VARIABLE, "i"),
			new Token(TokenType.INTEGER, Integer.valueOf(-1)),
			new Token(TokenType.DOUBLE, Double.valueOf(10.0)),
			new Token(TokenType.FUNCTION, "sin"),
			new Token(TokenType.OPERATOR, '*'),
			new Token(TokenType.STRING, "string"),
			new Token(TokenType.TAG, "$}"),
			new Token(TokenType.EOF, null)
		};
		lexer.setState(LexerState.TAG);
		checkTokenStream(lexer, correctData);
	}
	
	
	
	private void checkTokenStream(SmartScriptLexer lexer, Token[] correctData) {
		int counter = 0;
		for(Token expected : correctData) {
			Token actual = lexer.nextToken();
			String msg = "Checking token "+counter + ":";
			assertEquals(msg, expected.getType(), actual.getType());
			assertEquals(msg, expected.getValue(), actual.getValue());
			counter++;
		}
	}
}
