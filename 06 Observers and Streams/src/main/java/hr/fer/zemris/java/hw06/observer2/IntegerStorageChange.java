package hr.fer.zemris.java.hw06.observer2;

/**
 * Wrapper class for IntegerStorage which stores reference to IntegerStorage, 
 * but also stores old value and new value of updated IntegerStorage.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 * @see IntegerStorage
 */
public class IntegerStorageChange {
	/**
	 * Reference to wrapped IntegerStorage.
	 */
	private IntegerStorage storage;
	/**
	 * Old value of IntegerStorage.
	 */
	private int oldValue;
	/**
	 * New value of IntegerStorage.
	 */
	private int newValue;

	/**
	 * Default constructor for IntegerStorageChange.
	 * 
	 * @param storage reference to IntegerStorage
	 * @param oldValue old value
	 * @param newValue new value
	 */
	public IntegerStorageChange(IntegerStorage storage, int oldValue, int newValue) {
		this.storage = storage;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	/**
	 * Getter of old value of IntegerStorage.
	 * @return old value of IntegerStorage.
	 */
	public int getOldValue() {
		return oldValue;
	}

	/**
	 * Getter for new value of IntegerStorage.
	 * 
	 * @return new value of IntegerStorage
	 */
	public int getNewValue() {
		return newValue;
	}
	
	/**
	 * Getter for IntegerStorage.
	 * 
	 * @return reference to IntegerStorage
	 */
	public IntegerStorage getIntegerStorage() {
		return storage;
	}
	
}
