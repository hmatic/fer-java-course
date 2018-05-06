package hr.fer.zemris.java.custom.collections;

/**
 * Represents linked list-backed collection of Objects.
 * General contract of this collection is: duplicate elements are <b>allowed</b>; 
 * storage of null references is <b>not allowed</b>.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class LinkedListIndexedCollection extends Collection {
	/**
	 * Represents a node in double linked list. Node consists of value of type Object and 
	 * references to both previous and next node.
	 * ListNode does not accept null as value.
	 * 
	 * @author Hrvoje Matić
	 */
	static class ListNode {
		ListNode previous;
		ListNode next;
		Object value;
		
		/**
		 * Constructs new node of linked list.
		 * @param previous reference to previous node
		 * @param next reference to next node
		 * @param value value of current node
		 */
		public ListNode(ListNode previous, ListNode next, Object value) {
			if(value==null) {
				throw new NullPointerException("Value at ListNode is not valid.");
			} else {
				this.previous = previous;
				this.next = next;
				this.value = value;
			}
		}
	}
	
	/**
	 * Current number of elements in collection.
	 */
	private int size;
	
	/**
	 * Reference to first node in the collection.
	 */
	private ListNode first;
	
	/**
	 * Reference to last node in the collection.
	 */
	private ListNode last;
	
	/**
	 * Default constructor that initializes empty collection.
	 */
	public LinkedListIndexedCollection() {
		this.size = 0;
		this.first = null;
		this.last = null;
	}
	
	/**
	 * Constructs new LinkedListIndexedCollection and fills it with elements from another Collection.
	 * Throws exception for null as argument.
	 * 
	 * @param other reference to other collection
	 * @throws NullPointerException
	 */
	public LinkedListIndexedCollection(Collection other) {
		if(other == null) {
			throw new NullPointerException("Null is not acceptable argument.");
		} 
		addAll(other);
		this.size = other.size();
	}
	
	/**
	 * Adds the given object at the end of LinkedListIndexedCollection. 
	 * If argument passed to method is null, it will throw NullPointerException.
	 * 
	 * @param value object to be added into collection
	 * @throws NullPointerException
	 */
	@Override
	public void add(Object value) {
		if(value==null) {
			throw new NullPointerException("Argument value can't be null.");
		}
		if(last==null && first==null) {
			try {
				first = last = new ListNode(null, null, value);
			} catch(NullPointerException e) {
				System.out.println("You can't add null to this collection.");
			}
		} else {
			last.next = new ListNode(last, null, value);
			last = last.next;
		}
		size++;
	}
	
	/**
	 * Returns the object that is stored in linked list at the given index. 
	 * Valid index values are from 0 to size-1.
	 * @param index index of element to be returned
	 * @return element at given index
	 * @throws IndexOutOfBoundsException
	 */
	public Object get(int index) {
		if(index<0 || index>size-1) {
			throw new IndexOutOfBoundsException("Bounds are 0 - " + (size-1) + ". Index was " + index);
		}

		return findNode(index).value;
	}
	
	/**
	 * Removes all elements from collection.
	 */
	public void clear() {
		first = null;
		last = null;
		size = 0;
	}
	
	/**
	 * Inserts the object given in first argument into linked list at position given in second argument. 
	 * It does not overwrite element at given position. Valid positions are from 0 to (size of collection).
	 * 
	 * @param value element to be added
	 * @param position position at which element will be added
	 * @throws NullPointerException
	 * @throws IndexOutOfBoundsException
	 */
	public void insert(Object value, int position) {
		if(value==null) {
			throw new NullPointerException("Argument value can't be null.");
		} else if(position<0 || position>size-1) {
			throw new IndexOutOfBoundsException("Bounds are 0 - " + (size-1) + ". Position was " + position);
		}
		
		ListNode node = findNode(position);
		ListNode newNode;
		
		if(node!=first && node!=last) {
			newNode = new ListNode(node.previous,node,value);
			node.previous.next = newNode;
			node.previous = newNode;
		} else if(node==first) {
			newNode = new ListNode(null,node,value);
			node.previous = newNode;
			first = newNode;			
		} else if(node==last) {
			newNode = new ListNode(node,null,value);
			node.next = newNode;
			last = newNode;
		}
	}
	
	/**
	 * Searches for the first occurrence of value given in argument and returns index of found element.
	 * Returns -1 if element is not found.
	 * 
	 * @param value element to be found
	 * @return index of found element, -1 if not found
	 */
	public int indexOf(Object value) {
		ListNode node = first;
		for(int i=0; i<size; i++) {
			if(node.value.equals(value)) {
				return i;
			}
			node = node.next;
		}
		return -1;
	}
	
	/**
	 * Removes element in collection at given index.
	 * 
	 * @param index index of element that needs to be removed
	 * @throws IndexOutOfBoundsException
	 */
	public void remove(int index) {
		if(index<0 || index>size-1) {
			throw new IndexOutOfBoundsException("Bounds are 0 - " + (size-1) + ". Index was " + index);
		}
		
		ListNode node = findNode(index);
		
		if(node!=first && node!=last) {
			node.previous.next = node.next;
			node.next.previous = node.previous;
		} else if(node==first) {
			node.next.previous = null;
			first = node.next;
		} else if(node==last) {
			node.previous.next = null;
			last = node.previous;
		}
		
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
		ListNode node = first;
		for(int i=0; i<size; i++) {
			processor.process(node.value);
			node = node.next;
		}
	}
	
	/**
	 * Allocates new array with size equal to the size of this collections, fills it with collection
	 * content and returns the array. This method never returns null. 
	 * 
	 * @return array that represents collection
	 * @throws UnsupportedOperationException
	 */
	@Override
	public Object[] toArray() {
		if(size==0) {
			throw new UnsupportedOperationException("This method can't be perfomed on empty collection.");
		}
		
		Object[] result = new Object[size];
		
		ListNode node = first;
		for(int i=0; i<size; i++) {
			result[i] = node.value;
			node = node.next;
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
		ListNode node = first;
		for(int i=0; i<size; i++) {
			if(node.value.equals(value)) {
				return true;
			}
			node = node.next;
		}
		
		return false;
	}
	
	/**
	 * Searches for node at given index and returns reference to found node.
	 * 
	 * @param index index of node
	 * @return ListNode at given index
	 */
	private ListNode findNode(int index) {
		ListNode node;
		
		if(index>Math.floor(size/2)) {
			node = last;
			for(int i = 0; i<(size-index-1); i++) {
				node=node.previous;
			}
		} else {
			node = first;
			for(int i = 0; i<index; i++) {
				node=node.next;
			}			
		}
		
		return node;
	}
	
}
