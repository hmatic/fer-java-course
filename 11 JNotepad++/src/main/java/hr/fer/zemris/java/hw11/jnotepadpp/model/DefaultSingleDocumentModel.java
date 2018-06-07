package hr.fer.zemris.java.hw11.jnotepadpp.model;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Implementation of SingleDocumentModel.
 * @author Hrvoje Matic
 * @version 1.0
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {
	/**
	 * Text area component.
	 */
	private JTextArea textArea;
	/**
	 * File path of document.
	 */
	private Path filePath;
	/**
	 * Document modified flag.
	 */
	private boolean isModified;
	
	/**
	 * List of registered listeners.
	 */
	private List<SingleDocumentListener> listeners; 
	
	/**
	 * Default constructor for DefaultSingleDocumentModel.	
	 * @param text initial text
	 * @param filePath file path
	 */
	public DefaultSingleDocumentModel(String text, Path filePath) {
		this.textArea = new JTextArea(text);
		this.filePath = filePath;
		this.isModified = false;
		
		listeners = new ArrayList<>();
		this.textArea.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent arg0) {}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				setModified(true);
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				setModified(true);
			}
		});
	}

	@Override
	public JTextArea getTextComponent() {
		return textArea;
	}

	@Override
	public Path getFilePath() {
		if(filePath!=null) {
			return filePath;
		}
		return null;
	}

	@Override
	public void setFilePath(Path path) {
		Objects.requireNonNull(path);
		this.filePath = path;
		notifyListenersPathChanged();
		
	}

	@Override
	public boolean isModified() {
		return isModified;
	}

	@Override
	public void setModified(boolean modified) {
		this.isModified = modified;
		notifyListenersDocumentModified();
	}


	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners.add(l);
		
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);
		
	}

	/**
	 * Notify all registered listeners when document gets modified.
	 */
	private void notifyListenersDocumentModified() {
		for(SingleDocumentListener listener : new ArrayList<>(listeners)) {
			listener.documentModifyStatusUpdated(this);
		}
	}
	
	/**
	 * Notify all registered listeners when document's path gets modified.
	 */
	private void notifyListenersPathChanged() {
		for(SingleDocumentListener listener : new ArrayList<>(listeners)) {
			listener.documentFilePathUpdated(this);
		}
	}
}
