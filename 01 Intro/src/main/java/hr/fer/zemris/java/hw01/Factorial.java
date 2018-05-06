package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program accepts integers in range of 1 to 20 and calculates their factorial.
 * Program ends when you input "kraj".
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class Factorial {
	
	/**
	 * Method called upon start of the program. Arguments explained below.
	 * @param args Arguments from command line as array of Strings
	 */
	public static void main(String[] args) {
		boolean end = false;
		
		try(Scanner scanner = new Scanner(System.in)) {
			while(!end) {
				System.out.print("Unesite broj > ");
				String input = scanner.nextLine();
				
				if(Validation.isValidInt(input)) {
					int value = Integer.parseInt(input);
					
					if(value>=1 && value<=20) {
						System.out.println(calculateFactorial(value));
					} else {
						System.out.println("'" + input + "' nije broj u dozvoljenom rasponu.");
					}	
				} else if(input.equals("kraj")) {
					end = true;
				} else {
					System.out.println("'" + input  + "' nije cijeli broj.");
				}
			}
		}
		
		System.out.println("Doviđenja.");
	}
	
	/**
	 * Recursive method that calculates the factorial of the number (n!) using given formula:
	 * n!=n*(n-1)*(n-2)*...*2*1.
	 * Note that value of 0! is 1.
	 * @param n input number
	 * @return factorial of n
	 */
	public static long calculateFactorial(int n) {	
		if(n<0) {
			throw new IllegalArgumentException("Argument n can't be negative.");
		}
		
		if(n==0) {
			return 1;
		}
		
		return n*calculateFactorial(n-1);
	}
}
