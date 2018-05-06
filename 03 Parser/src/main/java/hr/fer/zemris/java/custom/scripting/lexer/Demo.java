package hr.fer.zemris.java.custom.scripting.lexer;

public class Demo {
	public static void main(String[] args) {
		SmartScriptLexer lexer = new SmartScriptLexer("{$ FOR i -1 10.0 @sin * \"string\" $}");
		lexer.setState(LexerState.TAG);
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());
		System.out.println(lexer.nextToken());

		

	}
}
