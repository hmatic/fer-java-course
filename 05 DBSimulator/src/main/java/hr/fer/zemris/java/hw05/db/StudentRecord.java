package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

/**
 * StudentRecord is class which holds data of each student from database.
 * Each student has unique JMBAG number, first name, last name and final grade.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class StudentRecord {
	/**
	 * JMBAG number of student. JMBAG is unique for each student.
	 */
	private final String jmbag;
	/**
	 * Last name of student.
	 */
	private final String lastName;
	/**
	 * First name of student.
	 */
	private final String firstName;
	/**
	 * Student's final grade.
	 */
	private final int finalGrade;
	
	/**
	 * Default constructor for StudentRecord. All values must be initialized. No value can be null.
	 * @param jmbag JMBAG of student
	 * @param lastName last name of student
	 * @param firstName first name of student
	 * @param finalGrade final grade of student
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		Objects.requireNonNull(jmbag);
		Objects.requireNonNull(lastName);
		Objects.requireNonNull(firstName);
		Objects.requireNonNull(finalGrade);
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}

	/**
	 * Getter for JMBAG.
	 * 
	 * @return JMBAG of student
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Getter for last name.
	 * 
	 * @return last name of student
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Getter for first name.
	 * 
	 * @return first name of student
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Getter for final grade.
	 * 
	 * @return final grade of student
	 */
	public int getFinalGrade() {
		return finalGrade;
	}

	/**
	 * Returns a hash code value for the StudentRecord. Hash code is calculated using student's JMBAG.
	 * This method is supported for the benefit of hash tables such as those provided by java.util.HashMap. 
	 * @return hash code of StudentRecord
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	/**
	 * Method which takes Object as argument and determines if argument is equal to current StudentRecord.
	 * Students are compared according to their JMBAG.
	 * 
	 * @param obj Object to be compared with StudentRecord
	 * @return true if argument is equal to StudentRecord, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}

	/**
	 * Returns String representation of StudentRecord.
	 * @return StudentRecord fields as String
	 */
	@Override
	public String toString() {
		return "JMBAG: " + jmbag + ", Name: " + firstName + " " + lastName + ", Grade: " + finalGrade;
	}
}
