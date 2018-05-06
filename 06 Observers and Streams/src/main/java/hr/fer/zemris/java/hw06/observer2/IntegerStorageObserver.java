package hr.fer.zemris.java.hw06.observer2;
/**
 * Interface that describes Observers for IntegerStorage class.
 * @author Hrvoje Matić
 * @see IntegerStorage
 */
public interface IntegerStorageObserver {
	/**
	 * When IntegerStorage changes its value, IntegerStorageObserver is notified and reference to IntegerStorageChange is passed to observer.
	 * After observer is notified, it performs actions implemented inside this method.
	 * @param istorage reference to IntegerStorage
	 */
	void valueChanged(IntegerStorageChange istorage);
}