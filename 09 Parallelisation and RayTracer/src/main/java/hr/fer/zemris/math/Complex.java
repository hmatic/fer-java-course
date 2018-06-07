package hr.fer.zemris.math;

import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

import java.util.ArrayList;
import java.util.List;


/**
 * Class models complex numbers and operations between them. 
 * Complex numbers have real and imaginary part.
 * Each operation creates new complex number.
 * 
 * @author Hrvoje Matić
 *
 */
public class Complex {
	/**
	 * Real part of complex number.
	 */
	private final double re;
	/**
	 * Imaginary part of imaginary number.
	 */
	private final double im;
	
	/**
	 * Complex constant for zero.
	 */
	public static final Complex ZERO = new Complex(0,0);
	/**
	 * Complex constant for one.
	 */
	public static final Complex ONE = new Complex(1,0);
	/**
	 * Complex constant for negative one.
	 */
	public static final Complex ONE_NEG = new Complex(-1,0);
	/**
	 * Complex constant for imaginary one.
	 */
	public static final Complex IM = new Complex(0,1);
	/**
	 * Complex constant for negative imaginary one.
	 */
	public static final Complex IM_NEG = new Complex(0,-1);
	
	/**
	 * Threshold for comparing doubles.
	 */
	private static double THRESHOLD = 0.000001;
	
	
	/**
	 * Default constructor for Complex.
	 * @param re real part
	 * @param im imaginary part
	 */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}
	
	/**
	 * Constructor without arguments. Throws exception.
	 */
	public Complex() {
		throw new IllegalArgumentException("Must initialize complex number "
				+ "with real and imaginary values.");
	}

	/**
	 * Getter for real part
	 * @return real part
	 */
	public double getRe() {
		return re;
	}

	/**
	 * Getter for imaginary part.
	 * @return imaginary part
	 */
	public double getIm() {
		return im;
	}
	
	/**
	 * Calculates module of complex number.
	 * @return module of complex number
	 */
	public double module() {
		return sqrt((re*re)+(im*im));
	}
	
	
	/**
	 * Multiplies this complex number with another complex number. 
	 * Returns new complex number without changing current ones.
	 * @param c other complex number
	 * @return resulting complex number
	 */
	public Complex multiply(Complex c) {
		return new Complex(this.re * c.re - this.im * c.im, 
				 this.re * c.im + this.im * c.re);
	}
	
	/**
	 * Divides this complex number with another complex number. 
	 * Returns new complex number without changing current ones.
	 * @param c other complex number
	 * @return resulting complex number
	 */
	public Complex divide(Complex c) {
		double denominator = pow(c.getRe(), 2.0) + pow(c.getIm(), 2.0);
		return new Complex((this.re*c.getRe() + this.im*c.getIm()) / denominator,
				(this.im*c.getRe() - this.re*c.getIm()) / denominator);
	}
	
	/**
	 * Adds this complex number to another complex number. 
	 * Returns new complex number without changing current ones.
	 * @param c other complex number
	 * @return resulting complex number
	 */
	public Complex add(Complex c) {
		return new Complex(re+c.getRe(), im+c.getIm());
	}
	
	/**
	 * Subtracts other complex number from this complex number. 
	 * Returns new complex number without changing current ones.
	 * @param c other complex number
	 * @return resulting complex number
	 */
	public Complex sub(Complex c) {
		return new Complex(re-c.getRe(), im-c.getIm());
	}
	
	/**
	 * Returns new complex number which is negated current number.
	 * @return negated current number as new Complex object
	 */
	public Complex negate() {
		return new Complex(-re, -im);
	}
	
	/**
	 * Calculates nth power of current complex number. 
	 * Returns new complex number without changing current one.
	 * @param n level of power
	 * @return number to power of n
	 */
	public Complex power(int n) {
		double magnitudeSquared = pow(module(),n);
		double re = magnitudeSquared*cos(Math.atan2(this.im, this.re)*n);
		double im = magnitudeSquared*sin(Math.atan2(this.im, this.re)*n);
		
		return new Complex(re, im);
	}
	
	/**
	 * Calculates nth roots of current complex number.
	 * Returns list of new complex numbers without changing current one.
	 * @param n level of root
	 * @return all nth roots as list
	 */
	public List<Complex> root(int n) {
		List<Complex> result = new ArrayList<>();

		double nThRootOfMagnitude = pow(module(), 1.0 / n);

		for (int i = 0; i < n; i++) {
			double argument = (Math.atan2(this.im, this.re) + i * 2 * Math.PI) / n;

			result.add(new Complex(nThRootOfMagnitude * cos(argument), 
					nThRootOfMagnitude * sin(argument)));
		}

		return result;
	}
	
	/**
	 * Parses given input string into complex number. 
	 * Given string must be in format: "a+ib" or "a-ib" where a is real part and b is imaginary part.
	 * Either a or b can be dropped from input string.
	 * @param input input string to be parsed
	 * @return parsed complex number
	 */
	public static Complex parse(String input) {
		input = input.replaceAll("\\s","");
		double re=0;
		double im=0;
		
		String[] parsedParts = input.split("-i|\\+i");
		if(input.startsWith("i")) {
			im=1;
		}else if(input.startsWith("-i") || input.startsWith("+i")) {
			String imPart = input.substring(2);
			if(imPart.isEmpty()) {
				if(input.startsWith("-i")) {
					im=-1;
				} else if(input.startsWith("+i")) {
					im=1;
				}
			} else {
				im=Double.parseDouble(imPart);
			}
		} else {
			if(parsedParts.length==2) {
				re = Double.parseDouble(parsedParts[0]);
				im = input.contains("-i") ? -Double.parseDouble(parsedParts[1]) : Double.parseDouble(parsedParts[1]);
			} else if(parsedParts.length==1) {
				re = Double.parseDouble(parsedParts[0]);
				if(input.contains("-i")) {
					im=-1;
				} else if(input.contains("+i") || input.contains("i")) {
					im=1;
				}
			} else {
				throw new IllegalArgumentException("Cant given complex number. Please try again.");
			}
		}
		
		
		return new Complex(re, im);
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		
		if(re==0 && im==0) return "0.0";
		
		if(re!=0) {
			str.append(re);
		}
		
		if(im!=0) {
			if(im<0) {
				str.append(im + "i");
			} else if(re==0) {
				str.append(im + "i");
			} else {
				str.append("+" + im + "i");
			}
		}
		
		return str.toString();
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(im);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(re);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object arg) {
		if(!(arg instanceof Complex)) {
			return false;
		}
		
		Complex other = (Complex)arg;
		
		if(Math.abs(other.re-re)<THRESHOLD && Math.abs(other.re-this.re)<THRESHOLD) {
			return true;
		} else {
			return false;
		}		
	}
	
	
}
