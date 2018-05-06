package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

/**
 * QueryParser provides methods for parsing given query. 
 * This parser is used in {@link StudentDB} for query parsing and interpretation. All query rules are explained there.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class QueryParser {
	/**
	 * Default exception message thrown by parser in case there is no other more specific message.
	 */
	private static final String EXCEPTION_MESSAGE = "Query is invalid and can not be parsed.";
	
	/**
	 * Query data as string.
	 */
	private String query;
	
	/**
	 * Default constructor for QueryParser.
	 * 
	 * @param query query text
	 */
	public QueryParser(String query) {
		this.query = query;
	}

	/**
	 * This method determines if query is direct or not.
	 * Direct query consists of JMBAG as attribute, EQUALS operator and string literal. Anything else is not direct query.
	 * 
	 * @return true if query is direct, false otherwise
	 */
	public boolean isDirectQuery() {
		QueryLexer lexer = new QueryLexer(query);
		
		// First token must be JMBAG.
		if(!(lexer.nextToken().getValue().toLowerCase().equals("jmbag"))) {
			return false;
		}
		// Second token must be = operator.
		if(!(lexer.nextToken().getValue().equals("=") && lexer.getToken().getType()==TokenType.OPERATOR)) {
			return false;
		}
		// Third token must be string.
		if(!(lexer.nextToken().getType()==TokenType.STRING)) {
			return false;
		}
		// Last token must be end of line.
		if(!(lexer.nextToken().getType()==TokenType.EOL)) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Returns value of direct query JMBAG attribute.
	 * 
	 * @return value of JMBAG attribute
	 * @throws IllegalStateException if called on non-direct query
	 */
	public String getQueriedJMBAG() {
		QueryLexer lexer = new QueryLexer(query);
		if(!isDirectQuery()) throw new IllegalStateException("Error with parser, can't call getQueriedJMBAG method on non-direct query.");
		lexer.nextToken();
		lexer.nextToken();
		lexer.nextToken();
		return lexer.getToken().getValue();
	}
	
	
	/**
	 * Method parses query and returns a list of conditional expressions from given query. 
	 * For direct queries this method will have only one element.
	 * 
	 * @return list of conditional expressions
	 * @throws QueryParserException if query is invalid
	 */
	public List<ConditionalExpression> getQuery() {
		List<ConditionalExpression> expressionList = new ArrayList<>();
		QueryLexer lexer = new QueryLexer(query);
		
		while(lexer.getToken()==null || lexer.getToken().getType()!=TokenType.EOL) {
			String stringLiteral;
			IFieldValueGetter getter = null;
			IComparisonOperator operator = null;
			
			if(lexer.nextToken().getType()==TokenType.KEYWORD) {
				String fieldGetter = lexer.getToken().getValue();
				switch(fieldGetter.toLowerCase()) {
					case "firstname":
						getter = FieldValueGetters.FIRST_NAME;
						break;
					case "lastname":
						getter = FieldValueGetters.LAST_NAME;
						break;
					case "jmbag":
						getter = FieldValueGetters.JMBAG;
						break;
				}
			} else {
				throw new QueryParserException(EXCEPTION_MESSAGE);
			}

			lexer.nextToken();
			if(lexer.getToken().getType()==TokenType.OPERATOR || lexer.getToken().getType()==TokenType.LIKE) {
				String comparisonOperator = lexer.getToken().getValue();
				switch(comparisonOperator.toLowerCase()) {
					case ">":
						operator = ComparisonOperators.GREATER;
						break;
					case "<":
						operator = ComparisonOperators.LESS;
						break;
					case "=":
						operator = ComparisonOperators.EQUALS;
						break;
					case ">=":
						operator = ComparisonOperators.GREATER_OR_EQUALS;
						break;
					case "<=":
						operator = ComparisonOperators.LESS_OR_EQUALS;
						break;
					case "!=":
						operator = ComparisonOperators.NOT_EQUALS;
						break;
					case "like":
						operator = ComparisonOperators.LIKE;
						break;
					default:
						throw new QueryParserException("Invalid operator inside query.");
				}
			} else {
				throw new QueryParserException(EXCEPTION_MESSAGE);
			}
			
			if(lexer.nextToken().getType()==TokenType.STRING) {
				stringLiteral = lexer.getToken().getValue();
			} else {
				throw new QueryParserException(EXCEPTION_MESSAGE);
			}
			
			expressionList.add(new ConditionalExpression(getter, stringLiteral, operator));
			
			lexer.nextToken();
			if(lexer.getToken().getType()==TokenType.AND) {
				continue;
			} else if(lexer.getToken().getType()==TokenType.EOL) {
				break;
			} else {
				throw new QueryParserException(EXCEPTION_MESSAGE);
			}
		}
		
		return expressionList;
	}

}
