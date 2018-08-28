package hr.fer.zemris.java.hw16.trazilica.environment;

import java.nio.file.Path;

/**
 * Models single document from document database.
 * @author Hrvoje Matic
 *
 */
public class Document {
	/**
	 * Document's file path.
	 */
	private Path docPath;
	/**
	 * Documents tf-idf vector.
	 */
	private double[] vector;
	
	/**
	 * Default constructor for document.
	 * @param docPath document path
	 * @param vector document vector
	 */
	public Document(Path docPath, double[] vector) {
		this.docPath = docPath;
		this.vector = vector;
	}

	/**
	 * Getter for document path.
	 * @return document path
	 */
	public Path getDocPath() {
		return docPath;
	}

	/**
	 * Getter for document vector.
	 * @return document vector
	 */
	public double[] getVector() {
		return vector;
	}

	/**
	 * Setter for document vector.
	 * @param vector new document vector
	 */
	public void setVector(double[] vector) {
		this.vector = vector;
	}
}
