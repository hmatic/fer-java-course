package hr.fer.zemris.java.hw01;

import java.text.NumberFormat;
import java.text.ParseException;

/**
 * Helper methods used for input validation.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class Validation {
	
	/**
	 * Helper method that validates if string can be converted into integer.
	 * @param input possible integer
	 * @return true if input is integer, false otherwise
	 */
	public static boolean isValidInt(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}
	
	/**
	 * Helper method that validates if given String can be parsed into positive number of type double.
	 * Prints appropriate error message if input is invalid.
	 * It also handles localization problems through NumberFormat parsing.
	 * @param input possible positive double
	 * @param numberFormat instance of NumberFormat
	 * @return true if input is positive double, false otherwise
	 */
	public static boolean isPositiveDouble(String input, NumberFormat numberFormat) {
		try {	
			double value = numberFormat.parse(input).doubleValue();
			if(value<=0) {
				System.out.println("Unijeli ste negativnu vrijednost.");
				return false;
			} else {
				return true;
			}
		} catch(ParseException e) {
			System.out.println("'" + input + "' se ne može prepoznati kao broj.");
			return false;
		}
	}
	
}
