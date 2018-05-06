package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * This class stores turtle context. It works as stack where you can store {@link TurtleState TurtleStates}.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class Context {
	/**
	 * Stack used for storage of turtle states.
	 */
	private ObjectStack stack;
	
	/**
	 * Default constructor. Initializes new ObjectStack.
	 */
	public Context() {
		this.stack = new ObjectStack();
	}
	
	/**
	 * Returns current turtle state without removing it from top of the stack.
	 * @return current turtle state
	 */
	public TurtleState getCurrentState() {
		return (TurtleState) stack.peek();
	} 
	
	/**
	 * Pushes new turtle state to the top of the stack.
	 * @param state new turtle state
	 */
	public void pushState(TurtleState state) {
		stack.push(state);
	} 
	
	/**
	 * Removes turtle state from top of the stack.
	 */
	public void popState() {
		stack.pop();
	} 
}
