package hr.fer.zemris.java.custom.scripting.exec;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Collection which models map that allows you to store multiple values for same key. <br>
 * Each map entry represents one stack and is abstracted in such way. Keys are instances of class String.<br>
 * Values associated to keys are instance of class {@link ValueWrapper}.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class ObjectMultistack {
	/**
	 * 
	 */
	private Map<String, MultistackEntry> multistack;
	
	/**
	 * Default constructor for ObjectMultistack. Initializes new HashMap.
	 */
	public ObjectMultistack() {
		super();
		this.multistack = new HashMap<>();
		
	}

	/**
	 * Pushes ValueWrapper to the top of the stack declared by first argument.
	 * Name of the stack can not be null as such stack can not exist.
	 * 
	 * @param name name of stack
	 * @param valueWrapper pushed value
	 * @throws NullPointerException if called on nonexistent stack
	 */
	public void push(String name, ValueWrapper valueWrapper) {
		Objects.requireNonNull(name);
		if(multistack.containsKey(name)) {
			multistack.put(name, new MultistackEntry(valueWrapper, multistack.get(name)));
		} else {
			multistack.put(name, new MultistackEntry(valueWrapper, null));
		}
	}
	
	/**
	 * Pops ValueWrapper from the top of the stack given in argument.
	 * Name of the stack can not be null as such stack can not exist.
	 * Will throw exception if called on empty stack.
	 * 
	 * @param name name of stack
	 * @return popped value
	 * @throws NullPointerException if called on nonexistent stack
	 * @throws EmptyStackException if called on empty stack
	 */
	public ValueWrapper pop(String name) {
		Objects.requireNonNull(name);
		
		MultistackEntry entry;
		if(multistack.containsKey(name)) {
			entry = multistack.get(name);
			if(entry.next!=null) {
				multistack.put(name, entry.next);
			} else {
				multistack.remove(name);
			}	
		} else {
			throw new EmptyStackException();
		}
		return entry.getValue();
	}
	
	/**
	 * Peeks ValueWrapper on the top of the stack given in argument.
	 * Name of the stack can not be null as such stack can not exist.
	 * Will throw exception if called on empty stack.
	 * 
	 * @param name name of stack
	 * @return peeked value
	 * @throws NullPointerException if called on nonexistent stack
	 * @throws EmptyStackException if called on empty stack
	 */
	public ValueWrapper peek(String name) {
		Objects.requireNonNull(name);
		
		MultistackEntry entry;
		if(multistack.containsKey(name)) {
			entry = multistack.get(name);
		} else {
			throw new EmptyStackException();
		}
		return entry.getValue();
	}
	
	/**
	 * Checks if stack given in argument is empty or not.
	 * 
	 * @param name name of the stack
	 * @return true if stack is empty, false otherwise
	 */
	public boolean isEmpty(String name) {
		if(multistack.containsKey(name)) return false;
		return true;	
	}
	
	/**
	 * Storage class that represents each stack entry in ObjectMultistack.
	 * @author Hrvoje Matić
	 */
	private static class MultistackEntry {
		/**
		 * Value of current entry.
		 */
		private ValueWrapper value;
		/**
		 * Reference to next entry.
		 */
		private MultistackEntry next;
		
		/**
		 * Default constructor for MultistackEntry.
		 * @param value entry value
		 * @param next reference to next entry
		 */
		public MultistackEntry(ValueWrapper value, MultistackEntry next) {
			super();
			this.value = value;
			this.next = next;
		}
		
		/**
		 * Getter for entry value.
		 * @return entry value
		 */
		public ValueWrapper getValue() {
			return value;
		}		
		
	}

}
