package hr.fer.zemris.java.hw16.trazilica.environment;

/**
 * Models single search result.
 * @author Hrvoje Matic
 * @version 1.0
 */
public class SearchResult implements Comparable<SearchResult> {
	/**
	 * Reference to resulting document.
	 */
	private Document document;
	/**
	 * Documents similarity with query.
	 */
	private double similarity;
	
	/**
	 * Default constructor.
	 * @param document document
	 * @param similarity similarity score
	 */
	public SearchResult(Document document, double similarity) {
		super();
		this.document = document;
		this.similarity = similarity;
	}
	
	/**
	 * Getter for document.
	 * @return document
	 */
	public Document getDocument() {
		return document;
	}
	/**
	 * Getter for similarity score.
	 * @return similarity score
	 */
	public double getSimilarity() {
		return similarity;
	}
	
	@Override
	public int compareTo(SearchResult other) {
		return Double.compare(other.similarity, this.similarity);
	}
	
	
	
}
