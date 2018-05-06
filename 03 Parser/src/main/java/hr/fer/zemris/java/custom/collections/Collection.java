package hr.fer.zemris.java.custom.collections;

/**
 * Collection is abstract representation of group of Objects, known as elements. 
 * It has no storage capabilities, so it has no direct implementations on this level. 
 * Other classes that extend this class will have more appropriate implementations.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class Collection {
	
	/**
	 * Protected constructor that does nothing.
	 */
	protected Collection() {	
	}
	
	/**
	 * Method checks if collection is empty.
	 * 
	 * @return true if collection is empty, false otherwise
	 */
	public boolean isEmpty() {
		if(size()==0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Returns number of currently stored elements in collection.
	 * Here it is implemented to always return 0. 
	 * 
	 * @return size of collection
	 */
	public int size() {
		return 0;
	}
	
	/**
	 * Adds given object into collection. Here it is implemented to do nothing.
	 * 
	 * @param value reference to Object that will be added to list
	 */
	public void add(Object value) {
	}
	
	/**
	 * Checks if collection contains given Object, determined by equals method. 
	 * Here it is implemented to always return false.
	 * 
	 * @param value given object to be checked
	 * @return true if collection contains given object, false otherwise
	 */
	public boolean contains(Object value) {
		return false;
	}

	/**
	 * Removes first occurrence of given object, if it exists. 
	 * Here it is implemented to always return false.
	 * 
	 * @param value given object to be deleted
	 * @return true if value is found and removed, false otherwise
	 */
	public boolean remove(Object value) {
		return false;
	}

	/**
	 * Allocates new array with size equal to the size of this collections, fills it with collection
	 * content and returns the array. This method never returns null. 
	 * Here it is implemented to always throw UnsupportedOperationException.
	 * 
	 * @return array that contains all elements of collection
	 * @throws UnsupportedOperationException
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Iterates over collection and performs action described in Processor on every element of collection.
	 * In order to use this method, create new Processor and override process() method in class Processor, 
	 * then pass that reference to this method.
	 * Here it is implemented to do nothing.
	 * 
	 * @param processor reference to Processor which performs action on elements
	 * @see Processor
	 */
	public void forEach(Processor processor) {			
	}
	
	/**
	 * Adds all elements from given collection to current collection without changing other collection.
	 * 
	 * @param other reference to other collection which contains elements that need to be added to current collection
	 */
	public void addAll(Collection other) {

		class CollectionProcessor extends Processor {
			@Override
			public void process(Object value) {
				add(value);
			}
		}

		other.forEach(new CollectionProcessor());
	}

	/**
	 * Removes all elements from collection. Here it is implemented to do nothing.
	 */
	public void clear() {		
	}	
}
