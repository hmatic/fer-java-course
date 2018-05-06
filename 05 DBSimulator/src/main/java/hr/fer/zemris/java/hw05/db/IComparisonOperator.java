package hr.fer.zemris.java.hw05.db;

/**
 * This interface models objects which compare two strings and determine if they satisfy comparison according to operator implementation.
 * @author Hrvoje Matić
 */
public interface IComparisonOperator {
	/**
	 * Method determines if two given Strings satisfy implemented comparison or not.
	 * 
	 * @param value1 first value for comparison
	 * @param value2 second value for comparison
	 * @return true if comparison is satisfied, false otherwise
	 */
	public boolean satisfied(String value1, String value2);
}
