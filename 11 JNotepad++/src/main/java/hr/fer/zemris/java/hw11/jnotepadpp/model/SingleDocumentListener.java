package hr.fer.zemris.java.hw11.jnotepadpp.model;

/**
 * Models listener for SingleDocumentModel.
 * @author Hrvoje Matic
 * @version 1.0
 */
public interface SingleDocumentListener {
	/**
	 * Method triggered when document gets modified.
	 * @param document modified document
	 */
	void documentModifyStatusUpdated(SingleDocumentModel document);
	/**
	 * Method triggered when documents path gets modified.
	 * @param document modified document
	 */
	void documentFilePathUpdated(SingleDocumentModel document);

}
