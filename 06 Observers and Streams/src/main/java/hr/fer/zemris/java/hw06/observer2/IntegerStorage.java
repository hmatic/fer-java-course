package hr.fer.zemris.java.hw06.observer2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * IntegerStorage models storage for single value of type int. 
 * It has feature to notify all registered {@link IntegerStorageObserver IntegerStorageObservers} about any changes occurring to value.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class IntegerStorage {
	/** 
	 * Value stored in IntegerStorage.
	 */
	private int value;
	/**
	 * List of registered observers.
	 */
	private List<IntegerStorageObserver> observers;

	/**
	 * Default constructor for IntegerStorage.
	 * 
	 * @param initialValue initial value when constructing IntegerStorage
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
		this.observers = new ArrayList<>();
	}

	/**
	 * Adds new observer to list of registered observers.
	 * Reference to observer can not be null.
	 * 
	 * @param observer reference to observer
	 * @throws NullPointerException if argument is null
	 */
	public void addObserver(IntegerStorageObserver observer) {
		Objects.requireNonNull(observer);
		observers.add(observer);
	}

	/**
	 * Removes observer from the list of registered observers.
	 * Reference to observer can not be null.
	 * 
	 * @param observer reference to observer
	 * @throws NullPointerException if argument is null
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		Objects.requireNonNull(observer);
		observers.remove(observer);
	}

	/**
	 * Clears all observers from list of registered observers.
	 */
	public void clearObservers() {
		observers.clear();
	}

	/**
	 * Getter for IntegerStorage value.
	 * 
	 * @return value of IntegerStorage
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Setter for IntegerStorage. If value is updated, all registered observers are notified about change.
	 * Before IntegerStorage is sent to observers, it is wrapped inside {@link IntegerStorageChange}.
	 * 
	 * @param value new value of IntegerStorage
	 */
	public void setValue(int value) {
		// Only if new value is different than the current value:
		if (this.value != value) {
			IntegerStorageChange changeWrapper = new IntegerStorageChange(this, this.value, value);
			// Update current value
			this.value = value;
			// Notify all registered observers
			if (observers != null) {
				for (IntegerStorageObserver observer : new ArrayList<>(observers)) {
					observer.valueChanged(changeWrapper);
				}
			}
		}
	}
}
