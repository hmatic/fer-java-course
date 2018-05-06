package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw05.collections.SimpleHashtable;

/**
 * StudentDatabase models simple database of student records. 
 * It has both list for iteration and indexed list (Hashtable) for fast retrieval of student records. 
 * 
 * @author Hrvoje Matić
 * @version 1.0
 * @see StudentRecord
 */
public class StudentDatabase {
	/**
	 * List of all student records.
	 */
	private List<StudentRecord> studentRecords = new ArrayList<>();
	/**
	 * List of all student records indexed by JMBAG. Used for fast retrieval of certain student record.
	 */
	private SimpleHashtable<String, StudentRecord> indexTable = new SimpleHashtable<>();
	
	/**
	 * Default constructor for StudentDatabase. Constructor gets array of database rows and creates lists of all student records.
	 * 
	 * @param dbRows array of database rows
	 */
	public StudentDatabase(String[] dbRows) {
		for(String row : dbRows) {
			String[] rowParts = row.trim().split("\\t+");
			String jmbag = rowParts[0];
			String lastName = rowParts[1];
			String firstName = rowParts[2];
			int finalGrade = Integer.parseInt(rowParts[3]);
			StudentRecord student = new StudentRecord(jmbag, lastName, firstName, finalGrade);
			studentRecords.add(student);
			indexTable.put(jmbag, student);			
		}
	}
	
	/**
	 * Method that uses index table of student records in order to retrieve student record with JMBAG given in argument. 
	 * This method does this in complexity of O(1). If there is no such record, method will return null.
	 * 
	 * @param jmbag JMBAG of needed student record
	 * @return student record with given JMBAG
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return indexTable.get(jmbag);
	}
	
	/**
	 * Method that filters StudentDatabase with filter given in argument and returns list of filtered student records.
	 * 
	 * @param filter reference to IFilter object used for filtering database
	 * @return filtered list of student records
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> filteredList = new ArrayList<>();
		for(StudentRecord student : studentRecords) {
			if(filter.accepts(student)) {
				filteredList.add(student);
			}
		}
		return filteredList;
	}
	
}
