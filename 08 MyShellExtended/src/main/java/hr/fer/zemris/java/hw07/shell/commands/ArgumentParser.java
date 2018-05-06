package hr.fer.zemris.java.hw07.shell.commands;

import java.util.ArrayList;
import java.util.List;

/**
 * Argument parser parses arguments string into list of arguments.
 * It provides feature to use quotes in order to have spaces in arguments. 
 * Escaping is possible inside quotes by using backslash before another backslash or quote.
 * @author Hrvoje Matić
 * @version 1.0
 * @throws ArgumentParserException if input arguments can not be parsed
 */
public class ArgumentParser {
	/**
	 * Arguments as array of chars.
	 */
	private char[] arguments;
	/**
	 * Current position of parser.
	 */
	private int currentPosition;
	
	/**
	 * Default constructor for ArgumentParser.
	 * @param arguments
	 */
	public ArgumentParser(String arguments) {
		this.arguments = arguments.toCharArray();
		this.currentPosition = 0;
	}
	/**
	 * Parses given arguments into list of arguments.
	 * Will not use escape mechanism if "NO_ESCAPE" option is given in argument.
	 * 
	 * @param options parsing options
	 * 
	 * @return list of String arguments
	 */
	public List<String> parse(String... options) {
		List<String> argList = new ArrayList<>();
		String argBuild = "";
		while(currentPosition<arguments.length) {
			skipSpaces();
			if(currentPosition>=arguments.length) {
				return argList;
			}
			// Entrance to quote part of parsing
			if(arguments[currentPosition]=='"') {
				currentPosition++;
				while(currentPosition<arguments.length && arguments[currentPosition]!='"') {
					if((options[0]!=null && !options[0].equals("NO_ESCAPE")) && 
							arguments[currentPosition]=='\\') {
						// Escape mechanism
						if(arguments[currentPosition+1]=='"') {
							currentPosition++;
							argBuild+=arguments[currentPosition];
							currentPosition++;
						} else if(arguments[currentPosition+1]=='\\') {
							currentPosition++;
							argBuild+=arguments[currentPosition];
							currentPosition++;		
						} else {
							argBuild+=arguments[currentPosition];
							currentPosition++;
						}
					} else {
						argBuild+=arguments[currentPosition];
						currentPosition++;
					}
				}
				if(currentPosition>=arguments.length || arguments[currentPosition]!='"') {
					throw new ArgumentParserException("Invalid argument. "
							+ "Opened quotes must be closed.");
				}
				currentPosition++;
				if(currentPosition<arguments.length && arguments[currentPosition]!=' ') {
					throw new ArgumentParserException("Invalid argument. "
							+ "There must be space after closed quotes.");
				}
				argList.add(argBuild);
				argBuild="";
			// Parsing outside quotes
			} else {
				while(currentPosition<arguments.length && 
						arguments[currentPosition]!=' ' && 
						arguments[currentPosition]!='"') {
					
					argBuild+=arguments[currentPosition];
					currentPosition++;
				}
				
				argList.add(argBuild);
				argBuild="";
			}
		}
		
		
		return argList;
	}


	/**
	 * Helper method which skips all spaces.
	 */
	private void skipSpaces() {
		while(currentPosition<arguments.length && arguments[currentPosition]==' ') {
			currentPosition++;
		}
	}
	
}
