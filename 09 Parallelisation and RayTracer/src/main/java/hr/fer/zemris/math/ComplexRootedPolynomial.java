package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Models polynom with complex numbers given in form:
 * f(z)=(z-z1)(z-z2)(z-z3)...(z-zn)
 * @author Hrvoje Matić
 */
public class ComplexRootedPolynomial {
	/**
	 * List of roots.
	 */
	private List<Complex> roots = new ArrayList<>();
	
	/**
	 * Default constructor for ComplexRootedPolynomial.
	 * @param roots array of roots
	 */
	public ComplexRootedPolynomial(Complex...roots) {
		for(Complex root : roots) {
			this.roots.add(root);
		}
	}
	
	/**
	 * Computes rooted polynomial value at point given in argument as complex number.
	 * @param z point of computation
	 * @return polynomial value at given point
	 */
	public Complex apply(Complex z) {
		Complex result = Complex.ONE;
		for(Complex root : roots) {
			result = result.multiply(z.sub(root));
		}
		return result;
	}
	
	/**
	 * Converts ComplexRootedPolynomial to ComplexPolynomial representation.
	 * 
	 * @return ComplexPolynomial representation
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial complexPolynom = new ComplexPolynomial(Complex.ONE, roots.get(0).negate());
		for (int i = 1; i < roots.size(); i++) {
			complexPolynom = complexPolynom.multiply(
					new ComplexPolynomial(Complex.ONE, roots.get(i).negate())
					);
		}
		return complexPolynom;
	}
	
	/**
	 * Finds index of closest root for complex number given in first argument, 
	 * that is within threshold given in second argument.
	 * Returns -1 if no such root exists.
	 * @param z complex number
	 * @param treshold threshold
	 * @return index of the closest root
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		double distance = z.sub(roots.get(0)).module();
		int index = 0;
		int i = 0;
		for (Complex root : roots) {
			double magnitude = root.sub(z).module();
			if (magnitude < distance) {
				distance = magnitude;
				index = i;
			}
			i++;
		}
		if(distance>treshold) {
			return -1;
		}
		return index+1;
	}
	
	@Override
	public String toString() {
		StringJoiner sj = new StringJoiner("))*(z-(", "(z-(", "))");
		for(Complex root : roots) {
			sj.add(root.toString());
		}
		return sj.toString();
	}
	
}
