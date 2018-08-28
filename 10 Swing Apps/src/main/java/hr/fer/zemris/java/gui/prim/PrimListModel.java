package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Models list of prime numbers.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class PrimListModel implements ListModel<Integer>{
	/**
	 * List of subscribed listeners.
	 */
	private List<ListDataListener> listeners = new ArrayList<>();
	
	/**
	 * List of prime numbers.
	 */
	private List<Integer> primeNumbers = new ArrayList<>();
	
	/** 
	 * Last known prime number.
	 */
	private int lastPrime = 1;
	
	/**
	 * Default constructor for PrimListModel. List starts with 1.
	 */
	public PrimListModel() {
		primeNumbers.add(lastPrime);
	}
	
	@Override
	public void addListDataListener(ListDataListener listener) {
		listeners.add(listener);
		
	}

	@Override
	public Integer getElementAt(int index) {
		return primeNumbers.get(index);
	}

	@Override
	public int getSize() {
		return primeNumbers.size();
	}

	@Override
	public void removeListDataListener(ListDataListener listener) {
		listeners.remove(listener);
	}
	
	/**
	 * Helper method that notifies all subscribed listeners about event that occurred.
	 * @param e
	 */
	private void notifyListeners(ListDataEvent e) {
		for(ListDataListener listener : new ArrayList<>(listeners)) {
			listener.intervalAdded(e);
		}
	}
	
	/**
	 * Generates new prime number, adds it to list of primes and notifies all subscribed listeners.
	 */
	public void next() {
		int index = getSize();
		do {				
			lastPrime++;								
		} while(!isPrime(lastPrime));
		
		primeNumbers.add(lastPrime);
		notifyListeners(new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, index, index));
	}
	
	/**
	 * Validates if number given in argument is prime number or not.
	 * 
	 * @param n number to be validated
	 * @return true if number is prime, false otherwise
	 */
	static boolean isPrime(int n) {
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
