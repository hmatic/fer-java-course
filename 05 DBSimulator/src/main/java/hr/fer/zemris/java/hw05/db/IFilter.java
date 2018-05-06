package hr.fer.zemris.java.hw05.db;

/**
 * This interface models objects which filter database according to given query.
 * @author Hrvoje Matić
 */
public interface IFilter {
	/**
	 * Method accepts or declines student records according to implemented filter.
	 * @param record student record
	 * @return true if record is accepted, false otherwise
	 */
	public boolean accepts(StudentRecord record);
}
