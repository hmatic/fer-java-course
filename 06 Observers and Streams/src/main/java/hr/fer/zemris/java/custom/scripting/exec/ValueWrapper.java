package hr.fer.zemris.java.custom.scripting.exec;

import java.util.function.BinaryOperator;
/**
 * Wrapper for objects of type Object. Also can be null. 
 * Provides additional methods such as arithmetic operations or comparison method
 * for objects of type String, Integer, Double or for nulls.
 * @author Hrvoje Matić
 * @version 1.0
 */
public class ValueWrapper {
	/**
	 * Stored value.
	 */
	private Object value;

	/**
	 * Default constructor for ValueWrapper.
	 * 
	 * @param value initial value
	 */
	public ValueWrapper(Object value) {
		super();
		this.value = value;
	}

	/**
	 * Getter for ValueWrapper value.
	 * @return value of ValueWrapper
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Setter for VallueWrapper value.
	 * @param value new value of ValueWrapper
	 */
	public void setValue(Object value) {
		this.value = value;
	}
	
	/**
	 * Changes current value of ValueWrapper by adding value given in argument. 
	 * 
	 * @param incValue term
	 */
	public void add(Object incValue) {
		this.value = calculate(this.value, incValue, (x,y)->x+y);
	}
	
	/**
	 * Changes current value of ValueWrapper by subtracting value given in argument. 
	 * @param decValue subtrahend
	 */
	public void subtract(Object decValue) {
		this.value = calculate(this.value, decValue, (x,y)->x-y);
	}
	
	/**
	 * Changes current value of ValueWrapper by multiplying it with value given in argument. 
	 * @param mulValue multiplier
	 */
	public void multiply(Object mulValue) {
		this.value = calculate(this.value, mulValue, (x,y)->x*y);
	}
	
	/**
	 * Changes current value of ValueWrapper by dividing it by value given in argument. 
	 * @param divValue divider
	 */
	public void divide(Object divValue) {
		this.value = calculate(this.value, divValue, (x,y)->x/y);
	}
	
	/**
	 * Compares two ValueWrappers value with other Object given in argument. <br>
	 * Can only be performed on classes that extend Number class.<br>
	 * If they are equals, returns 0. <br>
	 * If first is bigger than second, returns positive value 1. <br>
	 * If first is smaller than second, returns negative value -1.<br>
	 * @param withValue second value in comparison
	 * @return 0 if equal, 1 if bigger, -1 if smaller
	 */
	public int numCompare(Object withValue) {
		Number first = determineObjectType(this.value);
		Number second = determineObjectType(withValue);
		
		return Double.compare(first.doubleValue(), second.doubleValue());
	}
	
	/**
	 * Determines type of argument object and returns Integer or Double if argument can be parsed.<br>
	 * Allowed argument types are String, Integer, Double or null.<br>
	 * Null value is considered to be Integer of value 0.<br>
	 * 
	 * @param arg object to be determined
	 * @return given argument as Number object if possible
	 * @throws IllegalArgumentException if argument is not allowed type or can not be parsed into Integer or Double
	 */
	static Number determineObjectType(Object arg) {
		if(arg==null) return Integer.valueOf(0);
		if(arg.getClass()==String.class) {
			String argument = (String) arg;
			try {
				if(argument.contains(".") || argument.contains("E") || argument.contains("e")) {
					return Double.parseDouble(argument);
				} else {
					return Integer.parseInt(argument);
				}
			} catch(NumberFormatException e) {
				throw new IllegalArgumentException("Argument can only be String, Integer, Double or null.");
			}
		} else if(arg.getClass()==Integer.class) {
			return (Integer)arg;
		} else if(arg.getClass()==Double.class) {
			return (Double)arg;
		} else {
			throw new IllegalArgumentException("Argument can only be String, Integer, Double or null.");
		}
	}
	
	/**
	 * Performs calculation on two objects given in argument. Calculation is defined by third argument.<br>
	 * Can only perform calculations on objects of type Number.<br>
	 * Returns Integer value if both arguments are Integers. Returns Double value if one or both arguments are Doubles.<br>
	 * 
	 * @param arg1 first argument in calculation
	 * @param arg2 second argument in calculation
	 * @param operator defined operation
	 * @return calculated value
	 */
	private Object calculate(Object arg1, Object arg2, BinaryOperator<Double> operator) {
		Number first = determineObjectType(arg1);
		Number second = determineObjectType(arg2);
		
		double result = operator.apply(first.doubleValue(), second.doubleValue());
		
		if(first.getClass()==Integer.class && second.getClass()==Integer.class) {
			return Integer.valueOf((int) result);
		} else {
			return Double.valueOf(result);
		}
	}
	
}
