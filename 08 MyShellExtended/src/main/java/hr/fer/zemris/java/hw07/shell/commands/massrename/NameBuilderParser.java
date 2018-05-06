package hr.fer.zemris.java.hw07.shell.commands.massrename;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser for MassrenameShellCommand naming expressions. <br>
 * Parser builds NameBuilder object based on given expression.<br>
 * Given expression can be consisted of string constants or substitution commands. <br>
 * Substitution commands start with '${' and end with '}'.<br>
 * NameBuilderParser creates several types of NameBuilder objects:<br>
 * {@link NameBuilderString} for string constant.<br>
 * {@link NameBuilderSubstitution} for substitution commands.<br>
 * {@link NameBuilderContainer} that contains references to all other generated NameBuilder objects.<br>
 * @author Hrvoje Matić
 * @version 1.0
 */
public class NameBuilderParser {
	/**
	 * Naming expression.
	 */
	private String expression;
	
	/**
	 * Default constructor for NameBuilderParser.
	 * @param expression naming expression
	 */
	public NameBuilderParser(String expression) {
		this.expression = expression;
	}
	
	/**
	 * Method that parses given expression and creates all needed NameBuilder objects for naming files.
	 * NameBuilderSubstitution and NameBuilderString objects are wrapped into NameBuilderContainer.
	 * 
	 * @return NameBuilderContainer which contains references to all other NameBuilders
	 */
	public NameBuilder getNameBuilder() {
		List<NameBuilder> nbList = new ArrayList<>();
		NameBuilderLexer nbLexer = new NameBuilderLexer(expression);
		
		NameBuilderToken token;
		while((token=nbLexer.nextToken()).getType()!=NBTokenType.EOE) {
			if(token.getType()==NBTokenType.CONSTANT) {
				nbList.add(new NameBuilderString(token.getData()));
			} else if(token.getType()==NBTokenType.SUBSTITUTION) {
				String substitutionData = token.getData();
				String[] substitutionParts = substitutionData.split(",");
				if(substitutionParts.length==1) {
					int groupIndex = 0;
					try {
						groupIndex = Integer.parseInt(substitutionData.trim());
					} catch(NumberFormatException e) {
						throw new NameBuilderParserException();
					}
					nbList.add(new NameBuilderSubstitution(groupIndex));
				} else if(substitutionParts.length==2) {
					String firstArg = substitutionParts[0].trim();
					String secondArg = substitutionParts[1].trim();
					
					int groupIndex = 0;		
					try {
						groupIndex = Integer.parseInt(firstArg);
					} catch(NumberFormatException e) {
						throw new NameBuilderParserException();
					}
					char padChar = ' ';
					if(substitutionParts[1].trim().startsWith("0")) {
						padChar = '0';
						secondArg = secondArg.substring(1);
					}
					
					int length = 0;
					try {
						length = Integer.parseInt(secondArg);
					} catch(NumberFormatException e) {
						throw new NameBuilderParserException();
					}
					
					nbList.add(new NameBuilderSubstitution(groupIndex, length, padChar));
					
				} else {
					throw new NameBuilderParserException();
				}
			}
		}
		
		NameBuilder container = new NameBuilderContainer(nbList);
		return container;
	}
	
	/**
	 * Models substitution command NameBuilder object.
	 * This object can be in 2 states: basic and extended.
	 * In basic state it only appends matched group, 
	 * while in extended it formats group to specified length and pads it with spaces or zeroes.
	 * @author Hrvoje Matić
	 */
	private static class NameBuilderSubstitution implements NameBuilder {
		/**
		 * Index of matched group.
		 */
		private int groupIndex;
		/**
		 * Extended substitution mode.
		 */
		private boolean extended;
		/**
		 * Length of substitute. Used only in extended mode.
		 */
		private int length;
		/**
		 * Padding character for substitute. Used only in extended mode.
		 */
		private char padChar;

		/**
		 * Constructor for extended mode for NameBuilderSubstitution.
		 * 
		 * @param groupIndex group index
		 * @param length substitute length
		 * @param padChar padding char
		 */
		public NameBuilderSubstitution(int groupIndex, int length, char padChar) {
			super();
			this.groupIndex = groupIndex;
			this.extended = true;
			this.length = length;
			this.padChar = padChar;
		}

		/**
		 * Constructor for basic mode for NameBuilderSubstitution.
		 * 
		 * @param groupIndex group index
		 */
		public NameBuilderSubstitution(int groupIndex) {
			super();
			this.groupIndex = groupIndex;
			this.extended = false;
		}

		/**
		 * Appends matched group to StringBuilder acquired from argument.
		 * @param info reference to NameBuilderInfo 
		 */
		@Override
		public void execute(NameBuilderInfo info) {
			if(extended) {
				info.getStringBuilder().append(paddedStringWithLength(info.getGroup(groupIndex), length, padChar));
			} else {
				info.getStringBuilder().append(info.getGroup(groupIndex));	
			}
		}
	}
	
	/**
	 * Models string constant NameBuilder object.
	 * @author Hrvoje Matić
	 */
	private static class NameBuilderString implements NameBuilder {
		/**
		 * String constant.
		 */
		private String stringConstant;
		
		/**
		 * Default constructor for NameBuilderString.
		 * @param stringConstant string constant
		 */
		public NameBuilderString(String stringConstant) {
			super();
			this.stringConstant = stringConstant;
		}
		
		/**
		 * Appends constant given in constructor to StringBuilder acquired from argument.
		 * @param info reference to NameBuilderInfo
		 */
		@Override
		public void execute(NameBuilderInfo info) {
			info.getStringBuilder().append(stringConstant);	
		}
		
	}
	
	/**
	 * Storage class for all NameBuilder objects generated by parser.
	 * @author Hrvoje Matić
	 */
	private static class NameBuilderContainer implements NameBuilder {
		/**
		 * List of references to all NameBuilder objects generated by parser.
		 */
		private List<NameBuilder> nameBuilderList;
		
		/**
		 * Default constructor for NameBuilderContainer.
		 * @param nameBuilderList list of NameBuilders
		 */
		public NameBuilderContainer(List<NameBuilder> nameBuilderList) {
			this.nameBuilderList = nameBuilderList;
		}

		/**
		 * Executes all NameBuilders stored in this object.
		 * @param info reference to NameBuilderInfo
		 */
		@Override
		public void execute(NameBuilderInfo info) {
			for(NameBuilder nb : nameBuilderList) {
				nb.execute(info);
			}
		}	
	}
	
	/**
	 * Converts input string into string with specific length. 
	 * If length of input string is less than specified length, 
	 * result will be padded from left side with character defined in arguments.
	 * 
	 * @param input input string
	 * @param length final length
	 * @param padChar character used for padding
	 * @return specified length string with left padding
	 */
	private static String paddedStringWithLength(String input, int length, char padChar) {
		if(input.length()<length) {
			return new String(new char[length - input.length()]).replace('\0', padChar) + input;
		} else {
			return input;
		}
	}
	
}
