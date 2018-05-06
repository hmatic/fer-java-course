package hr.fer.zemris.java.hw06.demo2;

/**
 * Demonstration program for PrimesCollection. This program demonstrates single for-loop.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class PrimesDemo1 {
	/**
	 * Program entry point.
	 * @param args array of String arguments
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(5); 
		for (Integer prime : primesCollection) {
			System.out.println("Got prime: " + prime);
		}

	}
}
