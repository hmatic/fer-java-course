package hr.fer.zemris.java.hw05.db;

/**
 * This interface models objects used for retrieval of certain attributes of student record.
 * @author Hrvoje Matić
 */
public interface IFieldValueGetter {
	/**
	 * Getter for certain attribute of student record.
	 * @param record student record
	 * @return String value of attribute
	 */
	public String get(StudentRecord record);
}
