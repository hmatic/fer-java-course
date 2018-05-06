package hr.fer.zemris.java.hw05.db;

/**
 * ConditionalExpression models every expression in query. 
 * Each conditional expression is made of attribute (IFieldValueGetter), operator (IComparisonOperator) and String literal.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class ConditionalExpression {
	/**
	 * Represents attribute name in expression.
	 */
	private IFieldValueGetter fieldGetter;
	/**
	 * Represents String literal in expression.
	 */
	private String stringLiteral;
	/**
	 * Represent comparison operator in expression.
	 */
	private IComparisonOperator comparisonOperator;
	
	/**
	 * Default constructor for ConditionalExpression.
	 * 
	 * @param fieldGetter instance of IFieldValueGetter used to get attribute value
	 * @param stringLiteral String literal 
	 * @param comparisonOperator instance of IComparisonOperator used to process expression evaluation
	 */
	public ConditionalExpression(IFieldValueGetter fieldGetter, String stringLiteral, IComparisonOperator comparisonOperator) {
		this.fieldGetter = fieldGetter;
		this.stringLiteral = stringLiteral;
		this.comparisonOperator = comparisonOperator;
	}
	
	/**
	 * Getter for comparison operator.
	 * 
	 * @return comparison operator
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}
	
	/**
	 * Getter for String literal.
	 * 
	 * @return String literal
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}
	
	/**
	 * Getter for field value getter.
	 * 
	 * @return field value getter
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}
}
