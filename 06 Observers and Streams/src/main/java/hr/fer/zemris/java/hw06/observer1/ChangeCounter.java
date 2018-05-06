package hr.fer.zemris.java.hw06.observer1;

/**
 * Observer that counts number of changes made on Subject and prints it to standard output.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class ChangeCounter implements IntegerStorageObserver {
	/**
	 * Counter that keeps track of Subject changes.
	 */
	private int counter;
	
	/**
	 * Default constructor that initializes counter to 0.
	 */
	public ChangeCounter() {
		this.counter=0;
	}
	
	/**
	 * When value of Subject changes, prints counted number of changes to standard output.
	 * @param istorage reference to Subject
	 */
	@Override
	public void valueChanged(IntegerStorage istorage) {
		counter++;
		System.out.println("Number of value changes since tracking: " + counter);
		
	}

}
