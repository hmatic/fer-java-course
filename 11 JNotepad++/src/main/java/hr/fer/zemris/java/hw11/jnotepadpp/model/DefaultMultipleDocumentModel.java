package hr.fer.zemris.java.hw11.jnotepadpp.model;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

import static hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons.SAVED_ICON;
import static hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons.UNSAVED_ICON;

/**
 * Implementation of MultipleDocumentModel. 
 * It models JTabbedPane with currently opened tab stored as single document 
 * and rest of tabs stored in internal list.
 * @author Hrvoje Matic
 * @version 1.0
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {
	/** Serialization ID. */
	private static final long serialVersionUID = 1L;
	/**
	 * Current document
	 */
	private SingleDocumentModel currentDocument;
	/**
	 * List of all opened documents.
	 */
	private List<SingleDocumentModel> documents;
	/**
	 * List of registered listeners.
	 */
	private List<MultipleDocumentListener> listeners;
		
	/**
	 * Default constructor for DefaultMultipleDocumentModel.
	 * @param currentDocument current document
	 */
	public DefaultMultipleDocumentModel(SingleDocumentModel currentDocument) {
		this.currentDocument = currentDocument;
		this.documents = new ArrayList<>();
		this.listeners = new ArrayList<>();
		
		this.addChangeListener(new TabChangedListener());
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel newDocument = new DefaultSingleDocumentModel("", null);
		documents.add(newDocument);
		currentDocument = newDocument;
		currentDocument.addSingleDocumentListener(documentModifiedListener);
		this.addTab(LocalizationProvider.getInstance().getString("newtab"), SAVED_ICON, new JScrollPane(newDocument.getTextComponent()));
		
		this.setSelectedIndex(this.getTabCount()-1);

		notifyListenersDocumentAdded(currentDocument);
		return currentDocument;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return currentDocument;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		for(SingleDocumentModel document : documents) {
			if(path.equals(document.getFilePath())) {
				JOptionPane.showMessageDialog(
						this,
						LocalizationProvider.getInstance().getString("file") +
						" " + path.toAbsolutePath() + 
						" " + LocalizationProvider.getInstance().getString("alreadyopened") + ".",
						LocalizationProvider.getInstance().getString("error"),
						JOptionPane.ERROR_MESSAGE);
				return null;
			}
		}
		if(!Files.isReadable(path)) {
			JOptionPane.showMessageDialog(
					this,
					LocalizationProvider.getInstance().getString("file") +
					" " + path.toAbsolutePath() + 
					" " + LocalizationProvider.getInstance().getString("doesntexist") + "!",
					LocalizationProvider.getInstance().getString("error"),
					JOptionPane.ERROR_MESSAGE);
			return null;
		}
		
		byte[] okteti;
		try {
			okteti = Files.readAllBytes(path);
		} catch(IOException ex) {
			JOptionPane.showMessageDialog(
					this,
					LocalizationProvider.getInstance().getString("readingerror") + " " + path.toAbsolutePath(),
					LocalizationProvider.getInstance().getString("error"),
					JOptionPane.ERROR_MESSAGE);
			return null;
		}
		
		String text = new String(okteti, StandardCharsets.UTF_8);
		currentDocument = new DefaultSingleDocumentModel(text, path);
		
		documents.add(currentDocument);
		currentDocument.addSingleDocumentListener(documentModifiedListener);
	
		this.addTab(path.getFileName().toString(), SAVED_ICON, new JScrollPane(currentDocument.getTextComponent()));
		this.setSelectedIndex(this.getTabCount()-1);
		this.setToolTipTextAt(this.getTabCount()-1, path.toString());
		
		notifyListenersDocumentAdded(currentDocument);
		return currentDocument;
	}

	@Override
	public void saveDocument(SingleDocumentModel document, Path newPath) {
		Path path = newPath!=null ? newPath : document.getFilePath(); 
		
		for(SingleDocumentModel doc : documents) {
			if(path.equals(doc.getFilePath())) {
				JOptionPane.showMessageDialog(
						this,
						LocalizationProvider.getInstance().getString("file") +
						" " + path.toAbsolutePath() + 
						" " + LocalizationProvider.getInstance().getString("savingonopened") + ".",
						LocalizationProvider.getInstance().getString("error"),
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
		
		byte[] data = document.getTextComponent().getText().getBytes(StandardCharsets.UTF_8);
		
		try {
			Files.write(path, data);
		} catch(IOException ex) {
			JOptionPane.showMessageDialog(
					this,
					LocalizationProvider.getInstance().getString("writingerror") + " " + newPath.toString(),
					LocalizationProvider.getInstance().getString("error"),
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		document.setFilePath(newPath);
		document.setModified(false);
		setIconAt(getSelectedIndex(), SAVED_ICON);
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		int index = documents.indexOf(model);
		SingleDocumentModel closeDocument = documents.get(index);
		documents.remove(index);
		removeTabAt(index);
		currentDocument = getNumberOfDocuments()>0 ? documents.get(index>0 ? index-1 : index) : null;
		notifyListenersDocumentChanged(closeDocument, currentDocument);
		notifyListenersDocumentRemoved(closeDocument);	
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(l);
		
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);
		
	}

	@Override
	public int getNumberOfDocuments() {
		return documents.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return index>=0 ? documents.get(index) : null;
	}

	/**
	 * Notify all listeners that document was added into model.
	 * @param newDocument added document
	 */
	private void notifyListenersDocumentAdded(SingleDocumentModel newDocument) {
		for(MultipleDocumentListener listener : new ArrayList<>(listeners)) {
			listener.documentAdded(newDocument);
		}
	}

	/**
	 * Notify all listeners that current document was changed.
	 * @param previousDocument previous document
	 * @param currentDocument new current document
	 */
	private void notifyListenersDocumentChanged(SingleDocumentModel previousDocument, SingleDocumentModel currentDocument) {
		for(MultipleDocumentListener listener : new ArrayList<>(listeners)) {
			listener.currentDocumentChanged(previousDocument, currentDocument);
		}
	}
	
	/**
	 * Notify all listeners that document was removed from model.
	 * @param removedDocument removed document
	 */
	private void notifyListenersDocumentRemoved(SingleDocumentModel removedDocument) {
		for(MultipleDocumentListener listener : new ArrayList<>(listeners)) {
			listener.documentRemoved(removedDocument);
		}
	}
	
	/**
	 * Change listener triggered when current tab changes.
	 * @author Hrvoje Matic
	 * @version 1.0
	 */
	private class TabChangedListener implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent e) {
			SingleDocumentModel previousDocument = currentDocument;
			currentDocument = getDocument(getSelectedIndex());
			
//			if(currentDocument!=null) {
//				currentDocument.addSingleDocumentListener(documentModifiedListener);
//			}
//			if(previousDocument!=null) {
//				previousDocument.removeSingleDocumentListener(documentModifiedListener);
//			}
			
			notifyListenersDocumentChanged(previousDocument, currentDocument);
		}
	}
	
	/**
	 * SingleDocumentListener triggered when document gets modified.
	 */
	private SingleDocumentListener documentModifiedListener = new SingleDocumentListener() {

		@Override
		public void documentModifyStatusUpdated(SingleDocumentModel document) {
			setIconAt(documents.indexOf(document), UNSAVED_ICON);
		}

		@Override
		public void documentFilePathUpdated(SingleDocumentModel document) {
			setTitleAt(documents.indexOf(document), document.getFilePath().getFileName().toString());
			setToolTipTextAt(documents.indexOf(document), document.getFilePath().toString());
		}
		
	};
}
