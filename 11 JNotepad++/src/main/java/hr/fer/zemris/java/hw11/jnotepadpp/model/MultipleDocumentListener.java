package hr.fer.zemris.java.hw11.jnotepadpp.model;

/**
 * Models listener for MultipleDocumentModel.
 * @author Hrvoje Matic
 * @version 1.0
 */
public interface MultipleDocumentListener {
	/**
	 * Method triggered when current document changes.
	 * @param previousModel previous document
	 * @param currentModel new current document
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, 
			SingleDocumentModel currentModel);
	/**
	 * Method triggered when new document is added.
	 * @param addedDocument added document
	 */
	void documentAdded(SingleDocumentModel addedDocument);
	/**
	 * Method triggered when document gets removed.
	 * @param removedDocument removed document
	 */
	void documentRemoved(SingleDocumentModel removedDocument);
}
