package hr.fer.zemris.java.hw14.model;

/**
 * Model class describing poll. Poll consists of its 
 * identification number(ID), name and message.
 * @author Hrvoje Matic
 * @version 1.0
 */
public class Poll {
	/**
	 * Poll identification number.
	 */
	private int id;
	/**
	 * Poll name.
	 */
	private String name;
	/**
	 * Poll message.
	 */
	private String message;
	
	/**
	 * Default constructor for Poll.
	 * @param id poll id
	 * @param name poll name
	 * @param message poll message
	 */
	public Poll(int id, String name, String message) {
		super();
		this.id = id;
		this.name = name;
		this.message = message;
	}
	
	/**
	 * Getter for poll ID.
	 * @return poll ID
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Getter for poll name.
	 * @return poll name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Getter for poll message.
	 * @return poll message
	 */
	public String getMessage() {
		return message;
	}
	
	
}
