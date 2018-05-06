package hr.fer.zemris.java.hw06.demo2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Collection of prime numbers. 
 * This collection does not have any actually storing mechanism for multiple numbers. 
 * It simply produces new prime number when demanded, up to "size", which is maximum number of 
 * prime numbers produced, given in constructor.
 *
 * @author Hrvoje Matić
 * @version 1.0
 */
public class PrimesCollection implements Iterable<Integer> {
	/**
	 * Size of collection.
	 */
	private int size;
	
	/**
	 * Default constructor for PrimesCollection
	 * @param size
	 */
	public PrimesCollection(int size) {
		super();
		this.size = size;
	}

	/**
	 * Iterator factory method.
	 * @return reference to new Iterator
	 */
	@Override
	public Iterator<Integer> iterator() {
		return new IteratorImpl();
	}
	
	/**
	 * Iterator implementation for PrimesCollection.
	 * 
	 * @author Hrvoje Matić
	 */
	private class IteratorImpl implements Iterator<Integer> {
		/**
		 * Iteration counter.
		 */
		private int counter;
		/**
		 * Last prime number given by
		 */
		private int lastPrime;
		
		
		/**
		 * Default constructor for PrimesCollection iterator implementation. 
		 * Initializes counter value to 0 and lastPrime starting value to 1.
		 */
		public IteratorImpl() {
			super();
			this.counter = 0;
			this.lastPrime = 1;
		}

		/**
		 * Checks if there is next prime number in this collection.
		 * 
		 * @return true if there is next, false otherwise
		 */
		@Override
		public boolean hasNext() {
			return counter<size;
		}

		/**
		 * Returns next prime number in collection.
		 */
		@Override
		public Integer next() {	
			if(!hasNext()) throw new NoSuchElementException();
			
			do {				
				lastPrime++;								
			} while(!isPrime(lastPrime));
			
			counter++;
			return lastPrime;		
		}
		
	}
	
	
	/**
	 * Static method which checks if number is prime or not. 
	 * 
	 * @param n number to be checked
	 * @return true if number is prime number, false otherwise
	 */
	private static boolean isPrime(int n) {
		if(n<=1) return false;
		if(n==2) return true;
		
	    if (n%2==0) return false;
	    
	    for(int i=3, max=(int)Math.ceil(Math.sqrt(n));i<=max;i+=2) {
	        if(n%i==0)
	            return false;
	    }
	    
	    return true;
	}
	
}
