package hr.fer.zemris.java.hw11.jnotepadpp.model;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * Interface which represents single document model.
 * @author Hrvoje Matic
 * @version 1.0
 */
public interface SingleDocumentModel {
	/**
	 * Getter for JTextArea component.
	 * @return
	 */
	JTextArea getTextComponent();
	/**
	 * Getter for document file path.
	 * @return document file path
	 */
	Path getFilePath();
	/**
	 * Setter for document file path.
	 * New path can not be null.
	 * @param path new document file path
	 */
	void setFilePath(Path path);
	/**
	 * Determine if document is modified.
	 * @return true if modified, false otherwise
	 */
	boolean isModified();
	/**
	 * Set modification flag.
	 * @param modified flag value
	 */
	void setModified(boolean modified);
	/**
	 * Add SingleDocumentListener to registered listeners.
	 * @param l listener to be added
	 */
	void addSingleDocumentListener(SingleDocumentListener l);
	/**
	 * Remove SingleDocumentListener from registered listeners.
	 * @param l listener to be added
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);
}
