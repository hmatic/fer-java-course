package hr.fer.zemris.java.hw14.model;

/**
 * Model class describing option inside poll. Each option 
 * has its identification number(ID), option title, option 
 * link and number of votes option received.
 * @author Hrvoje Matic
 * @version 1.0
 */
public class PollOption implements Comparable<PollOption> {
	/**
	 * Option identification number.
	 */
	private int id;
	/**
	 * Option title.
	 */
	private String optionTitle;
	/**
	 * Option link.
	 */
	private String optionLink;
	/**
	 * Number of votes option received.
	 */
	private int votesCount;
	
	/**
	 * Default constructor for PollOption.
	 * @param id option id
	 * @param optionTitle option title
	 * @param optionLink option link
	 * @param votesCount option votes count
	 */
	public PollOption(int id, String optionTitle, String optionLink, int votesCount) {
		super();
		this.id = id;
		this.optionTitle = optionTitle;
		this.optionLink = optionLink;
		this.votesCount = votesCount;
	}
	
	/**
	 * Getter for optionID.
	 * @return option ID
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Getter for option title.
	 * @return option title
	 */
	public String getOptionTitle() {
		return optionTitle;
	}
	
	/**
	 * Getter for option link.
	 * @return option link
	 */
	public String getOptionLink() {
		return optionLink;
	}
	
	/**
	 * Getter for option votes counter.
	 * @return option votes counter
	 */
	public int getVotesCount() {
		return votesCount;
	}
	
	@Override
	public int compareTo(PollOption other) {
		return ((Integer)votesCount).compareTo(other.getVotesCount());
	}
	
	
	
	
}
