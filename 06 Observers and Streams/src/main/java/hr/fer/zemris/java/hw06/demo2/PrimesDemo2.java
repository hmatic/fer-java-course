package hr.fer.zemris.java.hw06.demo2;

/**
 * Demonstration program for PrimesCollection. This program demonstrates two nested for-loops.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class PrimesDemo2 {
	/**
	 * Program entry point.
	 * @param args array of String arguments
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(3);
		for (Integer prime : primesCollection) {
			for (Integer prime2 : primesCollection) {
				System.out.println("Got prime pair: " + prime + ", " + prime2);
			}
		}

	}
}
