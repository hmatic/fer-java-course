package hr.fer.zemris.java.hw06.observer2;

/**
 * Demonstration of IntegerStorage and IntegerStorageObserver classes. 
 * In this package IntegerStorage is wrapped into IntegerStorageChange which
 * is better implementation and allows for some extra features.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class ObserverExample {
	/**
	 * Program entry point.
	 * 
	 * @param args array of String arguments
	 */
	public static void main(String[] args) {
		IntegerStorage istorage = new IntegerStorage(20);
		IntegerStorageObserver observer = new SquareValue();
		istorage.addObserver(observer);
		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);
		istorage.removeObserver(observer);
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue(1));
		istorage.addObserver(new DoubleValue(2));
		istorage.addObserver(new DoubleValue(2));
		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);
	}
}
