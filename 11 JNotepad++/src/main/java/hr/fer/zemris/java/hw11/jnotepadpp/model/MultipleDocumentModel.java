package hr.fer.zemris.java.hw11.jnotepadpp.model;

import java.nio.file.Path;

/**
 * Interface which represents multiple document model.
 * @author Hrvoje Matic
 * @version 1.0
 */
public interface MultipleDocumentModel {
	/**
	 * Create new document and set it as current document.
	 * @return created document
	 */
	SingleDocumentModel createNewDocument();
	/**
	 * Getter for current document.
	 * @return current document
	 */
	SingleDocumentModel getCurrentDocument();
	/**
	 * Load document from path given in argument and set it was current document.
	 * @param path document path
	 * @return loaded document
	 */
	SingleDocumentModel loadDocument(Path path);
	/**
	 * Save document given in first argument to path given in second argument.
	 * @param document document to be saved
	 * @param newPath saving destination path
	 */
	void saveDocument(SingleDocumentModel document, Path newPath);
	/**
	 * Close document given in argument and remove it from model.
	 * @param document document to be closed
	 */
	void closeDocument(SingleDocumentModel document);
	/**
	 * Add MultipleDocumentListener to registered listeners.
	 * @param l listener to be added
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);
	/**
	 * Remove MultipleDocumentListener from registered listeners.
	 * @param l listener to be removed
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);
	/**
	 * Get number of documents in model
	 * @return number of documents
	 */
	int getNumberOfDocuments();
	/**
	 * Get document at index given in argument.
	 * Return null if no document found at given index.
	 * @param index document index
	 * @return requested document
	 */
	SingleDocumentModel getDocument(int index);
}
