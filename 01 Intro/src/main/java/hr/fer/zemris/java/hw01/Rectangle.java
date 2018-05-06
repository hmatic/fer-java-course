package hr.fer.zemris.java.hw01;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Scanner;

/**
 * Program calculates area and perimeter of rectangle. 
 * If program is started without arguments it will ask user to enter width and height of rectangle.
 * Width and height can only be positive decimal values.
 * Once user enters both valid width and height, it prints area and perimeter of given rectangle.
 * Program can be called with 2 arguments(width and height). In such case, it does not ask user for input and
 * calculates results immediately.
 * Any other number of arguments except 0 and 2 will result in error.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class Rectangle {
	
	/**
	 * Method called upon start of the program. Arguments explained below.
	 * @param args Arguments from command line as array of Strings
	 */
	public static void main(String[] args) {
		NumberFormat numberFormat = NumberFormat.getInstance();
		
		if(args.length==2) {
			// & used instead of && so both arguments are checked and both error messages are printed:
			if(Validation.isPositiveDouble(args[0], numberFormat) & Validation.isPositiveDouble(args[1], numberFormat)) { 
				try {
					double width = numberFormat.parse(args[0]).doubleValue();
					double height = numberFormat.parse(args[1]).doubleValue();
					
					rectangleOutput(width,height);
				} catch(ParseException e) {
					e.printStackTrace();
				}
			} else {
				System.exit(1);
			}			
		} else if(args.length==0) {
			try(Scanner scanner = new Scanner(System.in)) {
				double width = userInput(scanner, numberFormat, "Unesite širinu > ");
				double height = userInput(scanner, numberFormat, "Unesite visinu > ");
			
				rectangleOutput(width, height);
			} catch(ParseException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Krivi broj argumenata.");
		}		
	}
	
	/**
	 * Method that manages and validates user input using Scanner. It keeps prompting user to input value
	 * until value is positive number of type double, then it returns that input as double.
	 * @param scanner reference to Scanner used for input
	 * @param numberFormat instance of NumberFormat
	 * @param message message printed before every input
	 * @return input value as double if input is valid
	 * @throws ParseException 
	 */
	public static double userInput(Scanner scanner, NumberFormat numberFormat, String message) throws ParseException {
		String input;
		
		do {
			System.out.print(message);
			input = scanner.nextLine();
		} while(!Validation.isPositiveDouble(input, numberFormat));
		
		return numberFormat.parse(input).doubleValue();
	}
		
	/**
	 * Prints area and perimeter values for given rectangle.
	 * @param width rectangle width
	 * @param height rectangle height
	 */
	public static void rectangleOutput(double width, double height) {
		double area = calculateRectangleArea(width,height);
		double perimeter = calculateRectanglePerimeter(width,height);
		
		System.out.println("Pravokutnik širine " + width + " i visine " + height + 
				" ima površinu " + area + " te opseg " + perimeter + ".");
	}
	
	/**
	 * Calculates area of rectangle using following formula:
	 * a*b
	 * @param d rectangle width
	 * @param e rectangle height
	 * @return area value of rectangle.
	 */
	public static double calculateRectangleArea(double d, double e) {
		return d * e;
	}
	
	/**
	 * Calculates perimeter of rectangle using following formula:
	 * 2(a+b)
	 * @param a rectangle width
	 * @param b rectangle height
	 * @return perimeter value of rectangle.
	 */
	public static double calculateRectanglePerimeter(double a, double b) {
		return 2*(a+b);
	}
}



