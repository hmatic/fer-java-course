package hr.fer.zemris.java.custom.collections;

/**
 * Represents stack of Objects implemented with ArrayIndexedCollection.
 * Class works as Adaptor between client and Adaptee class (ArrayIndexedCollection), and provides 
 * appropriate interface for user to work with stack.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 * @see ArrayIndexedCollection
 */
public class ObjectStack {
	/**
	 * Stack storage.
	 */
	private ArrayIndexedCollection stack;
	
	/**
	 * Constructs new stack.
	 */
	public ObjectStack() {
		this.stack = new ArrayIndexedCollection();
	}
	
	/**
	 * Checks if stack is empty.
	 * 
	 * @return true if stack is empty, false otherwise
	 */
	public boolean isEmpty() {
		return stack.isEmpty();
	}
	
	/**
	 * Returns current number of elements on stack.
	 * 
	 * @return size of stack
	 */
	public int size() {
		return stack.size();
	}
	
	/**
	 * Pushes given element to the top of the stack.
	 * Null values aren't allowed on stack.
	 * 
	 * @param value element that will be pushed on stack
	 */
	public void push(Object value) {
		try {
			stack.add(value);
		} catch(NullPointerException e) {
			System.out.println("Null can't be added to stack");
		}
	}
	
	/**
	 * Removes element from the top of the stack and returns it.
	 * Throws exception if you are attempting to pop the element from the empty stack.
	 * 
	 * @return element
	 * @throws EmptyStackException
	 */
	public Object pop() throws EmptyStackException {
		if(stack.size()==0) {
			throw new EmptyStackException("You can't perform this method on empty stack.");
		}
		Object result = stack.get(size()-1);
		stack.remove(size()-1);
		return result;
	}
	
	/**
	 * Returns element at the top of the stack without actually removing it from stack.
	 * Throws exception if you are attempting to peek the element on the empty stack.
	 * 
	 * @return element on the top of the stack
	 * @throws EmptyStackException
	 */
	public Object peek() throws EmptyStackException {
		if(stack.size()==0) {
			throw new EmptyStackException("You can't perform this method on empty stack.");
		}
		return stack.get(size()-1);
	}
	
	/**
	 * Removes all elements from the stack.
	 */
	public void clear() {
		stack.clear();
	}
	
}

