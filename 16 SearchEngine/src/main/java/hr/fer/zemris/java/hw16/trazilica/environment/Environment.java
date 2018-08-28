package hr.fer.zemris.java.hw16.trazilica.environment;

import java.util.List;

/**
 * Interface which models query console environment.
 * Environment is set up on start of program and used while program is running.
 * @author Hrvoje Matic
 * @version 1.0
 */
public interface Environment {
	
	/**
	 * Write string to environment output.
	 * @param text output text
	 */
	void write(String text);

	/**
	 * Write string ending with new line to environment output.
	 * @param text output text
	 */
	void writeln(String text);
	
	/**
	 * Read line from environment input.
	 * @return input string
	 */
	String readLine();
	
	/**
	 * Getter for search results.
	 * @return search results list
	 */
	List<SearchResult> getResults();
	
	/**
	 * Getter for environment's documents.
	 * @return documents of environment
	 */
	List<Document> getDocuments();
		
	/**
	 * Getter for environment's vocabulary.
	 * @return vocabulary of environment
	 */
	Vocabulary getVocabulary();

	/**
	 * Getter for IDF vector.
	 * @return IDF vector
	 */
	double[] getIdfVector();

}
