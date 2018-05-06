package hr.fer.zemris.java.hw05.db;

/**
 * ComparisonOperators holds all comparison operators and their implementations. 
 * Most of the implementations are done using lambda expressions.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class ComparisonOperators {
	/**
	 * Operator which lexicographically compares two Strings 
	 * and returns true if second String is LESS than first String, false otherwise.
	 */
	public static final IComparisonOperator LESS = (value1,value2)-> value1.compareTo(value2)<0;
	
	/**
	 * Operator which lexicographically compares two Strings 
	 * and returns true if second String is LESS or EQUAL to first String, false otherwise.
	 */
	public static final IComparisonOperator LESS_OR_EQUALS = (value1,value2)-> value1.compareTo(value2)<=0;
	
	/**
	 * Operator which lexicographically compares two Strings 
	 * and returns true if second String is GREATER than first String, false otherwise.
	 */
	public static final IComparisonOperator GREATER = (value1,value2)-> value1.compareTo(value2)>0;
	
	/**
	 * Operator which lexicographically compares two Strings 
	 * and returns true if second String is GREATER or EQUAL to first String, false otherwise.
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS = (value1,value2)-> value1.compareTo(value2)>=0;
	
	/**
	 * Operator which lexicographically compares two Strings 
	 * and returns true if second String is EQUAL to first String, false otherwise.
	 */
	public static final IComparisonOperator EQUALS = (value1, value2) -> value1.equals(value2);
	
	/**
	 * Operator which lexicographically compares two Strings 
	 * and returns true if second String is NOT EQUAL to first String, false otherwise.
	 */
	public static final IComparisonOperator NOT_EQUALS = (value1, value2) -> !(value1.equals(value2));
	
	/**
	 * Operator which lexicographically compares two Strings 
	 * and returns true if second String matches first String, false otherwise.
	 * Inside this operator, you can use wildcard(*) which represents regex for multiple character on
	 * start, middle or end of string. Only one wildcard can be used, otherwise exception will be thrown.
	 * @throws IllegalArgumentException when more than one wildcard is used
	 */
	public static final IComparisonOperator LIKE = new IComparisonOperator() {
		@Override
		public boolean satisfied(String value1, String value2) {
			if(value2.chars().filter(sign -> sign == '*').count()>1) {
				throw new IllegalArgumentException("There can be only zero or one wildcard(*) in string.");
			}
			return value1.matches(value2.replace("*", ".*"));
		}
	};

}
