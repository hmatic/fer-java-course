package hr.fer.zemris.java.hw16.trazilica.utils;

/**
 * Class containing static vector utility methods.
 * @author Hrvoje Matic
 * @version 1.0
 */
public class VectorUtils {
	/**
	 * Multiply two vectors by multiplying their members on same indexes.
	 * Vectors must be same length or exception is thrown.
	 * @param vector1 first vector
	 * @param vector2 second vector
	 * @return resulting vector
	 */
	public static double[] multiplyVectors(double[] vector1, double[] vector2) {
		if(vector1.length!=vector2.length) {
			throw new IllegalArgumentException("Vectors have to be same size.");
		}
		double[] result = new double[vector1.length];
		for(int i=0; i<result.length; i++) {
			result[i] = vector1[i]*vector2[i];
		}
		return result;
	}
	
	/**
	 * Calculate similarity of two document vectors.
	 * Vectors must be same length or exception is thrown.
	 * @param vector1 first vector
	 * @param vector2 second vector
	 * @return similarity score
	 */
	public static double calculateSimilarity(double[] vector1, double[] vector2) {
		if(vector1.length!=vector2.length) {
			throw new IllegalArgumentException("Vectors have to be same size.");
		}
		
		double scalarProduct = 0.0;
		double firstNorm = 0.0;
		double secondNorm = 0.0;
		double similarity = 0.0;
		
		for (int i = 0, max = vector1.length; i < max; i++) {
		    scalarProduct += vector1[i] * vector2[i];
		    firstNorm += vector1[i] * vector1[i];
		    secondNorm += vector2[i] * vector2[i];
		}
		
		firstNorm = Math.sqrt(firstNorm);
		secondNorm = Math.sqrt(secondNorm);
		
		double normsMultiplied = firstNorm * secondNorm;
		
		if (normsMultiplied != 0) {
			similarity = scalarProduct / normsMultiplied;
		} else {
			return 0.0;
		}
		return similarity;
	}
}
