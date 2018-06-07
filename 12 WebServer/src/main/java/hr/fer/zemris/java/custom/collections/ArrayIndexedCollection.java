package hr.fer.zemris.java.custom.collections;

/**
 * Represents resizeable array-backed collection of Objects.
 * General contract of this collection is: duplicate elements are <b>allowed</b>; 
 * storage of null references is <b>not allowed</b>.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class ArrayIndexedCollection extends Collection {
	/**
	 * Current number of elements stored in this collection.
	 */
	private int size;
	
	/**
	 * Total capacity of collection determined by size of currently allocated array.
	 */
	private int capacity;
	
	/**
	 * Array where all elements are stored as Objects.
	 */
	private Object[] elements;
	
	/**
	 * Constructor that creates new ArrayIndexedCollection with capacity and array allocated
	 * to initial capacity argument value.
	 * Initial capacity can't be less than 1, otherwise IllegalArgumentException is thrown.
	 * 
	 * @param initialCapacity initial capacity of current collection
	 * @throws IllegalArgumentException
	 * @see Collection
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if(initialCapacity<1) {
			throw new IllegalArgumentException("Capacity can not be 0 or negative. Was " + capacity + ".");
		} else {
			this.size = 0;
			this.capacity = initialCapacity;
			this.elements = new Object[initialCapacity];
		}
	}
	
	/**
	 * Default constructor which sets capacity of collection to 16.
	 */
	public ArrayIndexedCollection() {
		this(16);	
	}
	
	/**
	 * Constructs new ArrayIndexedCollection with initial capacity given in second argument
	 * and fills collection with all elements from other collection given in first argument.
	 * If initial capacity is lower than the size of given collection, capacity will be set to size of given collection.
	 * 
	 * @param other reference to other collection
	 * @param initialCapacity initial capacity collection will be set to
	 * @throws NullPointerException
	 */
	public ArrayIndexedCollection(Collection other, int initialCapacity) {		
		if(other == null) {
			throw new NullPointerException("Null is not acceptable argument.");
		} 
		if(initialCapacity<other.size()) {
			this.capacity = other.size();
			this.elements = new Object[other.size()];
			addAll(other);
		} else {
			this.capacity = initialCapacity;
			this.elements = new Object[initialCapacity];
			addAll(other);
		}
	}
	
	/**
	 * Constructs new ArrayIndexedCollection with initial capacity set to 16 and 
	 * fills collection with all elements from another collection.
	 * 
	 * @param other reference to other collection
	 * @throws NullPointerException
	 */
	public ArrayIndexedCollection(Collection other) {
		this(other, 16);
	}
	
	
	/**
	 * Adds the given object into ArrayIndexedCollection. Object is added into the first empty place at the
	 * end of ArrayIndexedCollection. If collection is full, array will be reallocated by doubling its capacity.
	 * If argument passed to method is null, it will throw NullPointerException.
	 * 
	 * @param value object that will be added into collection
	 * @throws NullPointerException
	 */
	@Override
	public void add(Object value) {
		if(value==null) {
			throw new NullPointerException("Argument value can't be null.");
		} else if(size>=capacity) {
		    elements = reallocateArray(elements,capacity*2);
		    capacity*=2;
		}
		
		elements[size] = value;
		size++;		    		
	}
	
	/**
	 * Reallocates array to capacity of second argument and returns reference to new array.
	 * 
	 * @param array array to be reallocated
	 * @param newCapacity current capacity of array
	 * @return reference to new reallocated array
	 */
	private Object[] reallocateArray(Object[] array, int newCapacity) {
		Object[] newArray = new Object[newCapacity];
	    System.arraycopy(array, 0, newArray, 0, array.length); 
	    return newArray;
	}

	/**
	 * Returns element from collection at given index.
	 * Valid index values are from 0 to size-1.
	 * 
	 * @param index index value of desired element
	 * @return element at given index
	 */
	public Object get(int index) {
		if(index<0 || index>size-1) {
			throw new IndexOutOfBoundsException("Bounds are 0 - " + (size-1) + ". Index was " + index);
		}
		return elements[index];
	}
	
	/**
	 * Removes all elements from current collection.
	 */
	@Override
	public void clear() {
		for(int i=0; i<size; i++) {
			elements[i] = null;
		}
		
		size = 0;
	}
	
	/**
	 * Inserts the first argument object at second argument position inside collection. 
	 * It does not overwrite current element. Other elements are shifted to the right.
	 * 
	 * @param value value to be added
	 * @param position position inside collection where value will be added
	 */
	public void insert(Object value, int position) {
		if(size==capacity) {
			reallocateArray(elements,capacity*2);
			capacity*=2;
		} 
		
		System.arraycopy(elements, position, elements, position+1, size-position);
		
		elements[position] = value;
		size++;
	}
	
	/**
	 * Returns index value of given object.
	 * Returns -1 if element is not found inside collection.
	 * 
	 * @param value reference to object which is searched for
	 * @return index value of given object
	 */
	public int indexOf(Object value) {
		for(int i=0; i<size; i++) {
			if(elements[i].equals(value)) return i;
		}
		
		return -1;
	}
	
	/**
	 * Removes element at given index from collection.
	 * 
	 * @param index index of element that needs to be removed
	 */
	public void remove(int index) {
		System.arraycopy(elements, index+1, elements, index, elements.length-index-1);
		size--;
	}
	
	/**
	 * Returns number of currently stored elements in collection.
	 * 
	 * @return size of collection
	 */
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * Iterates over collection and performs action from Processor on every element.
	 * 
	 * @param processor reference to Processor
	 */
	@Override
	public void forEach(Processor processor) {
		for(int i=0; i<size; i++) {
			processor.process(elements[i]);
		}
	}
	
	/**
	 * Allocates new array with size equal to the size of this collections, fills it with collection
	 * content and returns the array. This method never returns null. 
	 * 
	 * @return array of Objects made from current collection
	 */
	@Override
	public Object[] toArray() {
		Object[] result = new Object[size];
		
		for(int i=0; i<size; i++) {
			result[i]=elements[i];
		}
		
		return result;
	}
	
	/**
	 * Checks if collection contains given Object.
	 * 
	 * @param value reference to object that needs to be checked
	 * @return true if collection contains given element, false otherwise
	 */
	@Override
	public boolean contains(Object value) {
		for(int i=0; i<size; i++) {
			if(elements[i].equals(value)) {
				return true;
			}
		}
		return false;
	}
}
