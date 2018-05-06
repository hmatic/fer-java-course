package hr.fer.zemris.java.hw06.demo4;

import java.util.Objects;
import java.util.StringJoiner;

/** 
 * Models storage for a single student record. 
 * Each student has JMBAG, first name, last name, grade and points from MI, ZI and LAB.
 * Neither of these values are allowed to be null.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class StudentRecord {
	/**
	 * JMBAG number of student.
	 */
	private String jmbag;
	/**
	 * Student last name.
	 */
	private String prezime;
	/**
	 * Student first name.
	 */
	private String ime;
	/**
	 * Points scored on midterm exam called MI.
	 */
	private double bodoviMI;
	/**
	 * Points scored on final exam called ZI.
	 */
	private double bodoviZI;
	/**
	 * Points scored on lab exercises.
	 */
	private double bodoviLAB;
	/**
	 * Final grade.
	 */
	private int ocjena;
	
	
	/**
	 * Default constructor for StudentRecord. 
	 * 
	 * @param jmbag JMBAG
	 * @param prezime last name
	 * @param ime first name
	 * @param bodoviMI midterm exam points
	 * @param bodoviZI final exam points
	 * @param bodoviLAB lab points
	 * @param ocjena final grade
	 * @throws NullPointerException if jmbag, first or last name are null
	 */
	public StudentRecord(String jmbag, String prezime, String ime, double bodoviMI, double bodoviZI, double bodoviLAB,
			int ocjena) {
		super();
		Objects.requireNonNull(jmbag);
		Objects.requireNonNull(ime);
		Objects.requireNonNull(prezime);
		this.jmbag = jmbag;
		this.prezime = prezime;
		this.ime = ime;
		this.bodoviMI = bodoviMI;
		this.bodoviZI = bodoviZI;
		this.bodoviLAB = bodoviLAB;
		this.ocjena = ocjena;
	}
	
	/**
	 * Getter for JMBAG.
	 * @return JMBAG of student
	 */
	public String getJmbag() {
		return jmbag;
	}
	
	/**
	 * Getter for last name.
	 * @return last name of student
	 */
	public String getPrezime() {
		return prezime;
	}
	
	/**
	 * Getter for first name.
	 * @return first name of student
	 */
	public String getIme() {
		return ime;
	}
	
	/**
	 * Getter for points from midterm exam.
	 * @return midterm exam points
	 */
	public double getBodoviMI() {
		return bodoviMI;
	}
	
	/**
	 * Getter for points from final exam.
	 * @return final exam points
	 */
	public double getBodoviZI() {
		return bodoviZI;
	}
	
	/**
	 * Getter for points from lab exercises.
	 * @return lab points
	 */
	public double getBodoviLAB() {
		return bodoviLAB;
	}
	
	/**
	 * Getter for final grade.
	 * @return final grade
	 */
	public int getOcjena() {
		return ocjena;
	}
	
	/**
	 * Returns String representation of StudentRecord.
	 * @return StudentRecord as String
	 */
	@Override
	public String toString() {
		StringJoiner sj = new StringJoiner(", ", "( ", " )");
		sj.add(jmbag).add(ime).add(prezime).add(Double.toString(bodoviMI+bodoviZI+bodoviLAB)).add(Integer.toString(ocjena));
		return sj.toString();
	}
}
