package hr.fer.zemris.java.hw06.observer1;

/**
 * Observer that prints doubled value to standard output. 
 * DoubleValue observer will perform this action only for a number of times initially declared in a constructor.
 * After limit is surpassed, it will de-register itself from current subject.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class DoubleValue implements IntegerStorageObserver {
	/**
	 * Maximum number of Subject changes DoubleValue tracks.
	 */
	private final int limit;
	/**
	 * Counter that keeps track of Subject changes.
	 */
	private int counter;

	/**
	 * Default constructor for DoubleValue. 
	 * Limit for maximum number of changes can not be less than one, otherwise exception will be thrown.
	 * @param limit maximum number of changes tracked
	 * @throws IllegalArgumentException if limit is less than 1
	 */
	public DoubleValue(int limit) {
		if(limit<1) throw new IllegalArgumentException("Limit can't be less than 1. Was " + limit);
		this.limit = limit;
		this.counter = 0;
	}
	
	/**
	 * When value of Subject changes, prints doubled value to standard output. If limit is reached, removes Observer from Subject.
	 * @param istorage reference to Subject
	 */
	@Override
	public void valueChanged(IntegerStorage istorage) {
		if (counter<limit) {
			System.out.println("Double value: " + istorage.getValue()*2);
			counter++;
		} else {
			istorage.removeObserver(this);
		}
	}
	
	
}
