package hr.fer.zemris.java.hw02;

import static java.lang.Math.pow;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Representation of a Complex number which has both real and imaginary part.
 * Complex numbers created are not modifiable, so every operation that changes complex number
 * actually returns new ComplexNumber.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class ComplexNumber {
	
	private final double real;
	private final double imaginary;
	
	/**
	 * Static variable used to determine precision of comparing double numbers.
	 */
	private static double THRESHOLD = 0.00001;
	
	/**
	 * Create a complex number given the real and imaginary parts.
	 * 
	 * @param real real part of complex number
	 * @param imaginary imaginary part of complex number
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}
	
	/**
	 * Factory method that creates a complex number given the real part.
	 * 
	 * @param real real part of complex number
	 * @return
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}
	
	/**
	 * Factory method that creates a complex number given the imaginary part.
	 * 
	 * @param imaginary imaginary part of complex number
	 * @return
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}
	
	/**
	 * Factory method that creates a complex number given the magnitude and angle.
	 * 
	 * @param magnitude magnitude of complex number
	 * @param angle angle of complex number
	 * @return
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		double re = magnitude * cos(angle);
		double im = magnitude * sin(angle);
		return new ComplexNumber(re, im); 
	}
	
	/**
	 * Parses given string into complex number. It ignores spaces in given string. <br>
	 * Examples of strings that will be accept and parsed into complex number: <br>
	 * "3.51", "-3.17", "-2.71i", "i", "1", "-2.71-3.15i"
	 * @param complex
	 * @return
	 */
	public static ComplexNumber parse(String complex) {		
		String noWhiteSpaceComplex = complex.replaceAll("\\s","");
			
		Matcher fullComplex = Pattern.compile("([-]?[0-9]+\\.?[0-9]*)([-|+]+[0-9]+\\.?[0-9]*)i$").matcher(noWhiteSpaceComplex);
		Matcher onlyReal = Pattern.compile("([-]?[0-9]+\\.?[0-9]*)$").matcher(noWhiteSpaceComplex);
		Matcher onlyImaginary = Pattern.compile("([-]?[0-9]+\\.?[0-9]*)i$").matcher(noWhiteSpaceComplex);
		
		double re;
		double im;
		
		if(fullComplex.matches()) {
			re = Double.parseDouble(fullComplex.group(1));
			im = Double.parseDouble(fullComplex.group(2));
		} else if(onlyReal.matches()) {
			re = Double.parseDouble(onlyReal.group(1));
			im = 0;
		} else if(onlyImaginary.matches()) {
			re = 0;
			im = Double.parseDouble(onlyImaginary.group(1));
		} else {
			throw new IllegalArgumentException("Argument:\"" + complex + "can't be parsed into complex number.");
		}
		
		return new ComplexNumber(re, im);
	}
	
	/**
	 * Returns real part of complex number as double.
	 * 
	 * @return real part of complex number
	 */
	public double getReal() {
		return real;
	}
	
	/**
	 * Returns imaginary part of complex number as double.
	 * 
	 * @return imaginary part of complex number
	 */
	public double getImaginary() {
		return imaginary;
	}
	
	/**
	 * Calculates and returns magnitude of complex number.
	 * 
	 * @return magnitude of complex number
	 */
	public double getMagnitude() {
		return sqrt(pow(this.real, 2.0)+pow(this.imaginary, 2.0));
	}
	
	/**
	 * Calculates and returns angle of complex number.
	 * 
	 * @return angle of complex number
	 */
	public double getAngle() {
		return StrictMath.atan2(this.imaginary, this.real) + Math.PI;
	}
	
	/**
	 * Adds given complex number to current complex number. 
	 * Method doesn't change current ComplexNumber object, as it returns new ComplexNumber object.
	 * 
	 * @param other given complex number
	 * @return sum of two complex numbers as new ComplexNumber
	 */
	public ComplexNumber add(ComplexNumber other) {
		double re = this.real + other.real;
		double im = this.imaginary + other.imaginary;
		return new ComplexNumber(re, im);
	}

	/**
	 * Subtracts given complex number from current complex number.
	 * Method doesn't change current ComplexNumber object, as it returns new ComplexNumber object.
	 * 
	 * @param other given complex number
	 * @return subtraction of two complex numbers as new ComplexNumber
	 */
	public ComplexNumber sub(ComplexNumber other) {
		double re = this.real - other.real;
		double im = this.imaginary - other.imaginary;
		return new ComplexNumber(re, im);
	}
	
	/**
	 * Multiplies current complex number with given complex number. 
	 * Method doesn't change current ComplexNumber object, as it returns new ComplexNumber object.
	 * 
	 * @param other given complex number
	 * @return
	 */
	public ComplexNumber mul(ComplexNumber other) {
		double re = (this.real*other.real) - (this.imaginary*other.imaginary);
		double im = (this.real*other.imaginary) + (this.imaginary*other.real);
		return new ComplexNumber(re, im);
	}
	
	/**
	 * Divides current complex number with . Method doesn't change current ComplexNumber object,
	 * as it returns new ComplexNumber object.
	 * 
	 * @param other
	 * @return
	 */
	public ComplexNumber div(ComplexNumber other) {
		double denominator = pow(other.real, 2.0) + pow(other.imaginary, 2.0);
		double re = ((this.real*other.real) + (this.imaginary*other.imaginary)) / denominator;
		double im = ((this.imaginary*other.real) - (this.real*other.imaginary)) / denominator;
		return new ComplexNumber(re, im);
	}
	
	/**
	 * Calculates the value of the current complex number raised to the power of the argument n.
	 * @param n exponent
	 * @return new ComplexNumber which is result of raising current complex number to the power of n
	 */
	public ComplexNumber power(int n) {
		double magnitudeSquared = pow(getMagnitude(),n);
		double re = magnitudeSquared*cos((getAngle()-Math.PI)*n);
		double im = magnitudeSquared*sin((getAngle()-Math.PI)*n);
		
		return new ComplexNumber(re, im);
	}
	
	/**
	 * Calculates nth root of the given complex number.
	 * 
	 * @param n determines which root will be calculated
	 * @return nth root of complex number
	 */
	public ComplexNumber[] root(int n) {
		ComplexNumber[] result = new ComplexNumber[n];
		
		double nThRootOfMagnitude = pow(getMagnitude(), 1.0/n); 
		
		for(int i=0; i<n; i++) {
			double argument = ((getAngle()-Math.PI) + i * 2 * Math.PI) / n;
			double re = nThRootOfMagnitude * cos(argument);
			double im = nThRootOfMagnitude * sin(argument);
			
			result[i]= new ComplexNumber(re, im);
		}
		
		return result;
	}
			
	/**
	 * Returns string representation of complex number. 
	 * Example of string format: "5.0+4.5i"
	 * @return 
	 */
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		
		if(real==0 && imaginary==0) return "0.0";
		
		if(real!=0) {
			str.append(real);
		}
		
		if(imaginary!=0) {
			if(imaginary<0) {
				str.append(imaginary + "i");
			} else if(real==0) {
				str.append(imaginary + "i");
			} else {
				str.append("+" + imaginary + "i");
			}
		}
		
		return str.toString();
	}
	
	/**
	 * Indicates if one complex number is equal to another by comparing their real and imaginary part.
	 * 
	 * @param arg other complex number
	 * @return true if complex numbers are same, false otherwise
	 */
	@Override
	public boolean equals(Object arg) {
		if(!(arg instanceof ComplexNumber)) {
			return false;
		}
		
		ComplexNumber other = (ComplexNumber)arg;
		
		if(Math.abs(other.real-real)<THRESHOLD && Math.abs(other.real-this.real)<THRESHOLD) {
			return true;
		} else {
			return false;
		}		
	}
	
	
	

	
}
