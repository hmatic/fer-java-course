package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Models polynom with complex numbers given in form:
 * f(z)=zn*z^n+z(n-1)*z^(n-1)+...+z1*z+z0
 * @author Hrvoje Matić
 */
public class ComplexPolynomial {
	/**
	 * List of factors.
	 */
	List<Complex> factors = new ArrayList<>();
	
	/**
	 * Default constructor for ComplexPolynomial.
	 * @param factors array of factors
	 */
	public ComplexPolynomial(Complex ...factors) {
		for(Complex factor : factors) {
			this.factors.add(factor);
		}
	}

	/**
	 * Returns order of polynomial(number of highest power).
	 * @return order of polynomial
	 */
	public short order() {
		return (short) (factors.size()-1);
	}
	
	/**
	 * Multiplies current polynomial with other one and returns new resulting polynomial.
	 * @param p other polynomial
	 * @return new resulting polynomial
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] result = new Complex[factors.size() - 1 + p.factors.size()];
		Arrays.fill(result, Complex.ZERO);
		
		for (int i = 0; i < p.factors.size(); i++) {
			for (int j = 0; j < factors.size(); j++) {
				result[i+j] = result[i+j].add(p.factors.get(i).multiply(factors.get(j)));
			}
		}
		return new ComplexPolynomial(result);
		
		
	}
	
	/**
	 * Computes first derivation of this complex polynomial.
	 * @return first derivation of current polynomial
	 */
	public ComplexPolynomial derive() {
		if (factors.size() == 1) {
			return new ComplexPolynomial(Complex.ZERO);
		}
		List<Complex> result = new ArrayList<>();
		for (int i = 0; i < factors.size()-1; i++) {
			result.add(factors.get(i).multiply(new Complex(factors.size()-1-i,0)));
		}
	
		return new ComplexPolynomial(result.toArray(new Complex[1]));
	}
	
	/**
	 * Computes polynomial value at point given in argument.
	 * @param z point of computation
	 * @return polynomial value at given point
	 */
	public Complex apply(Complex z) {
		Complex result = Complex.ZERO;
		int power = factors.size()-1;
		for(Complex factor : factors) {
			result = result.add(factor.multiply(z.power(power)));
			power--;
		}
		return result;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int power = order();
		for(Complex factor : factors) {
			sb.append("(" + factor +")");
			if(power>0) {
				sb.append("*z");
				if(power>1) {
					sb.append("^" + power);
				}
				sb.append("+");
			}
			power--;
		}
		return sb.toString();
	}

}
