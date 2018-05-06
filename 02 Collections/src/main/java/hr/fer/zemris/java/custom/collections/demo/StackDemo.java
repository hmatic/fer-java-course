package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Command-line application which accepts single command-line argument,
 * which is expression which needs to be evaluated. Expression is in postfix representation.<br>
 * 
 * Example 1: “8 2 /” means apply / on 8 and 2, so 8/2=4.<br>
 * Example 2: “-1 8 2 / +” means apply / on 8 and 2, so 8/2=4, then apply + on -1 and 4, so the result is 3.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class StackDemo {
	/**
	 * Method called upon start of the program. Arguments explained below.
	 * @param args Arguments from command line as array of Strings
	 */
	public static void main(String[] args) {
		if(args.length!=1) {
			System.out.println("Program prihvaća isključivo 1 argument.");
			System.exit(1);
		}
		
		ObjectStack stack = new ObjectStack();
		String expression = args[0].trim().replaceAll("\\s+", " ");
		
		for(String element : expression.split(" ")) {
			if(isOperand(element)) {
				try {
					switch(element) {
					case "+": 
						stack.push((int)stack.pop() + (int)stack.pop());
						break;
						
					case "-":
						int subtrahend = (int)stack.pop();
						int minuend = (int)stack.pop();
						stack.push(minuend - subtrahend);
						break;
						
					case "/":
						int divider = (int)stack.pop();
						int dividend = (int)stack.pop();						
						stack.push((int) (dividend / divider));
						break;
						
					case "*":
						stack.push((int)stack.pop() * (int)stack.pop());
						break;
					
					case "%":
						int moduloDivider = (int)stack.pop();
						int moduloDividend = (int)stack.pop();
						stack.push(moduloDividend % moduloDivider);
						break;
					
					default:
						throw new IllegalArgumentException("Illegal expression");
					}
				} catch(EmptyStackException e) {
					System.out.println("Current expression can't be evaluated. It is not valid as it is "
							+ "trying to pop value from empty stack. Please try to use another expression.");
					System.exit(1);
				}
			} else {
				try {
					stack.push(Integer.parseInt(element));
				} catch(NumberFormatException e) {
					System.out.println("Not valid expression, can't parse numbers as Integers");
				}
			}
				
		}
		
		if(stack.size()!=1) {
			System.out.println("Current expression can't be evaluated. Please try to use another expression.");
		} else {
			System.out.println("Expression evaluates to " + stack.pop());
		}		
	}

	/**
	 * Checks if given string represents any of following operands: + - / * %
	 * 
	 * @param expression String expression that will be checked
	 * @return true if String is any of operands
	 */
	private static boolean isOperand(String s) {
		if (s.equals("+") || s.equals("-") || s.equals("/") || s.equals("*") || s.equals("%")) {
			return true;
		} else {
			return false;
		}
	}
	
}
