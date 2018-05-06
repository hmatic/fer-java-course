package hr.fer.zemris.java.hw06.observer1;

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
	public void valueChanged(IntegerStorage istorage) {
		System.out.format("Provided new value: %d, square is: %.0f \n", 
				istorage.getValue(), Math.pow(istorage.getValue(), 2));
	}
}
