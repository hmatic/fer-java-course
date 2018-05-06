package hr.fer.zemris.java.hw05.db;

/**
 * FieldValueGetters holds all field value getters and their implementations. 
 * Most of the implementations are done using lambda expressions.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class FieldValueGetters {
	/**
	 * Field value getter for first name of student.
	 */
	public static final IFieldValueGetter FIRST_NAME = record -> record.getFirstName();
	
	/**
	 * Field value getter for last name of student.
	 */
	public static final IFieldValueGetter LAST_NAME = record -> record.getLastName();
	
	/**
	 * Field value getter for JMBAG of student.
	 */
	public static final IFieldValueGetter JMBAG = record -> record.getJmbag();

}
