package hr.fer.zemris.java.hw06.observer2;

/**
 * Observer class that prints squared value to standard output. 
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class SquareValue implements IntegerStorageObserver {
	/**
	 * When value of Subject changes, prints squared value to standard output.
	 * @param istorage reference to Subject
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		int newValue = istorage.getNewValue();
		System.out.format("Provided new value: %d, square is: %.0f \n", 
				newValue, Math.pow(newValue, 2));
	}
}
