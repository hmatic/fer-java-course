package hr.fer.zemris.java.hw05.db;

import java.util.List;

/**
 * QueryFilter is used for filtering student records which do not 
 * satisfy ALL conditional expressions from the list of the expressions.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class QueryFilter implements IFilter {
	/**
	 * List of conditional expressions.
	 */
	private List<ConditionalExpression> expressions;
	
	/**
	 * Default constructor for QueryFilter.
	 * @param expressions list of conditional expressions
	 */
	public QueryFilter(List<ConditionalExpression> expressions) {;
		this.expressions = expressions;
	}

	/**
	 * Accepts student record if it satisfies ALL conditional expressions from list of expressions.
	 * 
	 * @param record student record to be evaluated
	 * @return true if record is accepted, false otherwise
	 */
	@Override
	public boolean accepts(StudentRecord record) {
		for(ConditionalExpression expr : expressions) {
			boolean satisfied = expr.getComparisonOperator().satisfied(expr.getFieldGetter().get(record), expr.getStringLiteral());
			if(!satisfied) return false;
		}		
		return true;
	}

}
