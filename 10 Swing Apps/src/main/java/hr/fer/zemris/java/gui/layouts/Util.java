package hr.fer.zemris.java.gui.layouts;

import java.util.function.BiFunction;

/**
 * Class with utility static methods.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class Util {
	/**
	 * Parses coordinate given as "x,y" where x and y are Integer. Generates new object with parsed value. 
	 * Object typing is determined by method handle to object constructor in second argument. 
	 * 
	 * @param input input string
	 * @param factory method handle to desired object constructor
	 * @return new object of desired type containing parsed values
	 */
	public static <T> T parseCoordinates(String input, BiFunction<Integer, Integer, T> factory) {
		String[] inputParts = input.split(",");
		if(inputParts.length!=2) {
			throw new IllegalArgumentException("Coordinates must have two parts.");
		}
		int x = 0;
		int y = 0;
		try {
			x = Integer.parseInt(inputParts[0]);
			y = Integer.parseInt(inputParts[1]);
		} catch(NumberFormatException e) {
			throw new IllegalArgumentException("Coordinate numbers must fit into integer values.");
		}
		return factory.apply(x, y);
	}
}
