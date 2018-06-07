package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.FlavorEvent;
import java.awt.datatransfer.FlavorListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LJMenu;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LJToolBar;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.model.DefaultMultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.model.MultipleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadpp.model.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.model.SingleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadpp.model.SingleDocumentModel;

import static hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons.*;

/**
 * JNotepad++ GUI application.
 * @author Hrvoje Matić
 * @version 1.0
 */
public class JNotepadPP extends JFrame {
	/** Serialization ID. */
	private static final long serialVersionUID = 1L;
	/**
	 * Multiple document editor.
	 */
	private MultipleDocumentModel editor;
	/**
	 * Localization provider.
	 */
	private ILocalizationProvider flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
	/**
	 * Status bar.
	 */
	JStatusbar statusBar = new JStatusbar(flp);
	
	/**
	 * Determine if program closed.
	 */
	private static volatile boolean stopWorking = false;


	/**
	 * Default constructor for JNotepad++.
	 */
	public JNotepadPP() {
		this.editor = new DefaultMultipleDocumentModel(null);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocation(0,0);
		setSize(600,600);
		setTitle("JNotepad++");

		initGUI();
	}

	/**
	 * Initialize GUI.
	 */
	private void initGUI() {
		getContentPane().setLayout(new BorderLayout());
		
		JPanel contentPanel = new JPanel(new BorderLayout());
		//Editor
		contentPanel.add((JTabbedPane) editor, BorderLayout.CENTER);
		//Toolbar
		contentPanel.add(initToolbar(), BorderLayout.PAGE_START);
		add(contentPanel, BorderLayout.CENTER);
		//Status bar
		getContentPane().add(statusBar, BorderLayout.PAGE_END);
		
		addListeners();
		initActionStates();
		initMenus();
	}
	
	/**
	 * Initialize states of actions which are disabled on program start.
	 */
	private void initActionStates() {
		saveDocumentAction.setEnabled(false);
		saveAsAction.setEnabled(false);
		closeDocumentAction.setEnabled(false);
		closeAllDocumentsAction.setEnabled(false);
		cutAction.setEnabled(false);
		copyAction.setEnabled(false);
		pasteAction.setEnabled(false);
		statisticsAction.setEnabled(false);
		toUpperCaseAction.setEnabled(false);
		toLowerCaseAction.setEnabled(false);
		invertCaseAction.setEnabled(false);
		sortAscAction.setEnabled(false);
		sortDescAction.setEnabled(false);
		uniqueAction.setEnabled(false);
	}

	/**
	 * Initialize localizable ToolBar with all supported actions.
	 * @return reference to ToolBar
	 */
	private JToolBar initToolbar() {
		LJToolBar toolBar = new LJToolBar("toolbar", flp);
		toolBar.setFloatable(true);
		
		toolBar.add(createToolbarButton(createDocumentAction));
		toolBar.add(createToolbarButton(loadDocumentAction));
		toolBar.add(createToolbarButton(saveDocumentAction));
		toolBar.add(createToolbarButton(saveAsAction));
		toolBar.add(createToolbarButton(closeDocumentAction));
		toolBar.add(createToolbarButton(closeAllDocumentsAction));
		
		toolBar.addSeparator();
	
		toolBar.add(createToolbarButton(cutAction));
		toolBar.add(createToolbarButton(copyAction));
		toolBar.add(createToolbarButton(pasteAction));
		
		toolBar.addSeparator();
		
		toolBar.add(createToolbarButton(statisticsAction));
		
		toolBar.addSeparator();
		
		toolBar.add(createToolbarButton(toUpperCaseAction));
		toolBar.add(createToolbarButton(toLowerCaseAction));
		toolBar.add(createToolbarButton(invertCaseAction));
		
		toolBar.addSeparator();
		
		toolBar.add(createToolbarButton(sortAscAction));
		toolBar.add(createToolbarButton(sortDescAction));
		
		toolBar.addSeparator();
		
		toolBar.add(createToolbarButton(uniqueAction));
		
		return toolBar;	
	}
	
	/**
	 * Initialize all menus.
	 */
	private void initMenus() {
		JMenuBar menuBar = new JMenuBar();

		// File menu
		JMenu fileMenu = new LJMenu("file", flp);
		fileMenu.add(new JMenuItem(createDocumentAction));
		fileMenu.add(new JMenuItem(loadDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveAsAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(closeDocumentAction));
		fileMenu.add(new JMenuItem(closeAllDocumentsAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(exitApplicationAction));
		menuBar.add(fileMenu);
		
		// Edit menu
		JMenu editMenu = new LJMenu("edit", flp);
		editMenu.add(new JMenuItem(cutAction));
		editMenu.add(new JMenuItem(copyAction));
		editMenu.add(new JMenuItem(pasteAction));
		menuBar.add(editMenu);
		
		// Tools menu
		JMenu toolsMenu = new LJMenu("tools", flp);
		toolsMenu.add(new JMenuItem(statisticsAction));
		JMenu changeCaseMenu = new LJMenu("changecase", flp);
		changeCaseMenu.setIcon(CHANGECASE_ICON);
		changeCaseMenu.add(new JMenuItem(toUpperCaseAction));
		changeCaseMenu.add(new JMenuItem(toLowerCaseAction));
		changeCaseMenu.add(new JMenuItem(invertCaseAction));
		toolsMenu.add(changeCaseMenu);
		JMenu sortMenu = new LJMenu("sort", flp);
		sortMenu.setIcon(SORT_ICON);
		sortMenu.add(new JMenuItem(sortAscAction));
		sortMenu.add(new JMenuItem(sortDescAction));
		toolsMenu.add(sortMenu);
		toolsMenu.add(uniqueAction);
		menuBar.add(toolsMenu);
		
		// Language menu
		JMenu langMenu = new LJMenu("languages", flp);
		langMenu.add(new JMenuItem(setCroatian));
		langMenu.add(new JMenuItem(setEnglish));
		langMenu.add(new JMenuItem(setGerman));
		menuBar.add(langMenu);
		
		this.setJMenuBar(menuBar);
	}
	
	/* ======================================
	 *  ACTIONS
	 * ====================================== */

	/**
	 * Creates new blank document.
	 */
	private final Action createDocumentAction = 
			new LocalizableAction("new", NEW_ICON, "control N", KeyEvent.VK_N, flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			editor.createNewDocument();
			
		}
	};
	
	/**
	 * Loads new document from path selected in file chooser.
	 */
	private final Action loadDocumentAction = 
			new LocalizableAction("open", OPEN_ICON, "control O", KeyEvent.VK_O, flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Open file");
			if(fc.showOpenDialog(JNotepadPP.this)!=JFileChooser.APPROVE_OPTION) {
				return;
			}
			
			File fileName = fc.getSelectedFile();
			Path filePath = fileName.toPath();
			
			editor.loadDocument(filePath);
		}
	};
	
	/**
	 * Saves current document to its path. 
	 * If document has no path this action behaves same as "Save as" action.
	 */
	private final Action saveDocumentAction = 
			new LocalizableAction("save", SAVE_ICON, "control S", KeyEvent.VK_S, flp) {
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			SingleDocumentModel current = editor.getCurrentDocument();
			if(current.getFilePath()!=null) {
				editor.saveDocument(editor.getCurrentDocument(), current.getFilePath());
				saveDocumentAction.setEnabled(false);
			} else {
				saveFileAs();
			}	
		}
	};
	
	/**
	 * Save document to path selected in file chooser.
	 */
	private final Action saveAsAction = 
			new LocalizableAction("saveas", SAVE_AS_ICON, "control shift S", KeyEvent.VK_A, flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			saveFileAs();
		}
	};

	/**
	 * Close current tab.
	 */
	private final Action closeDocumentAction = 
			new LocalizableAction("close", CLOSE_ICON, "control T", KeyEvent.VK_C, flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			SingleDocumentModel closeDocument = editor.getCurrentDocument();
			closePrompt(closeDocument);
			
		}
	};
	
	/**
	 * Close all tabs.
	 */
	private final Action closeAllDocumentsAction = 
			new LocalizableAction("closeall", CLOSE_ALL_ICON, "control Z", KeyEvent.VK_L, flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			for(int i = editor.getNumberOfDocuments()-1; i>=0; i--) {
				closePrompt(editor.getDocument(i));			
			}
		}
	};
	
	/**
	 * Copy selected text to clipboard.	
	 */
	private final Action copyAction = 
			new LocalizableAction("copy", COPY_ICON, "control C", KeyEvent.VK_C, flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			editor.getCurrentDocument().getTextComponent().copy();	
		}
	};
	
	/**
	 * Delete selected text and copy it to clipboard.
	 */
	private final Action cutAction = 
			new LocalizableAction("cut", CUT_ICON, "control X", KeyEvent.VK_U, flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			editor.getCurrentDocument().getTextComponent().cut();	
		}
	};
	
	/**
	 * Paste text from clipboard to current caret location.
	 */
	private final Action pasteAction = 
			new LocalizableAction("paste", PASTE_ICON, "control V", KeyEvent.VK_P, flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			editor.getCurrentDocument().getTextComponent().paste();	
		}
	};
	
	/**
	 * Get statistics of current document.
	 */
	private final Action statisticsAction = 
			new LocalizableAction("statistics", STATISTICS_ICON, "control shift T", KeyEvent.VK_S, flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			SingleDocumentModel currentDocument = editor.getCurrentDocument();
			if(currentDocument==null) return;
			
			String text = currentDocument.getTextComponent().getText();
			
			JOptionPane.showMessageDialog(
					(JTabbedPane) editor,
					flp.getString("charstatistics") + ": " + 
					text.length() + "\n" +
					flp.getString("nscharstatistics") + ": " + 
					text.replaceAll("\\s+", "").length() + "\n" +
					flp.getString("linestatistics") + ": " + 
					(text.length() - text.replace("\n", "").length() + 1) + "\n",
					flp.getString("statistics"),
					JOptionPane.INFORMATION_MESSAGE, STATISTICS_LARGE_ICON);
		}
	};
	
	/**
	 * Change selected text to upper case.
	 */
	private final Action toUpperCaseAction = 
			new LocalizableAction("uppercase", UPPERCASE_ICON, "control U", KeyEvent.VK_U, flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			changeCase(Character::toUpperCase, null);
		}
	};
	
	/**
	 * Change selected text to lower case.
	 */
	private final Action toLowerCaseAction = 
			new LocalizableAction("lowercase", LOWERCASE_ICON, "control L", KeyEvent.VK_L, flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			changeCase(null, Character::toLowerCase);
		}
	};
	
	/**
	 * Invert case of selected text.
	 */
	private final Action invertCaseAction = 
			new LocalizableAction("invertcase", INVERTCASE_ICON, "control I", KeyEvent.VK_I, flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			changeCase(Character::toUpperCase, Character::toLowerCase);
		}
	};
	
	/**
	 * Sort selected text lines in ascending order. 
	 * Method uses current program Locale for String comparison.
	 */
	private final Action sortAscAction = 
			new LocalizableAction("sortasc", ASCENDING_ICON, "control alt A", KeyEvent.VK_A, flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			sort(new Comparator<String>() {
				@Override
				public int compare(String s1, String s2) {
					return LocalizationProvider.getInstance().getCollator().compare(s1, s2);
				}		
			});
		}
	};
	
	/**
	 * Sort selected text lines in descending order. 
	 * Method uses current program Locale for String comparison.
	 */
	private final Action sortDescAction = 
			new LocalizableAction("sortdesc", DESCENDING_ICON, "control alt D", KeyEvent.VK_D, flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			sort(new Comparator<String>() {
				@Override
				public int compare(String s1, String s2) {
					return LocalizationProvider.getInstance().getCollator().compare(s2, s1);
				}		
			});
		}
	};
	
	/**
	 * Delete all duplicated lines from current selection of text.
	 */
	private final Action uniqueAction = 
			new LocalizableAction("unique", UNIQUE_ICON, "control alt U", KeyEvent.VK_U, flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JTextArea area = editor.getCurrentDocument().getTextComponent();
			Document document = area.getDocument();
			
			int len = Math.abs(area.getCaret().getDot()-area.getCaret().getMark());
			if(len==0) return;
			int offset = Math.min(area.getCaret().getDot(), area.getCaret().getMark());
			
			int start;
			int end;
	
			try {
				start = area.getLineStartOffset(area.getLineOfOffset(offset));
				end = area.getLineEndOffset(area.getLineOfOffset(offset+len));
				
				String areaText = document.getText(start, end-start);
				Set<String> unique = new LinkedHashSet<>();
				Collections.addAll(unique, areaText.split("\n"));
				
				document.remove(start, end-start);
	
				int uniqueLines = unique.size();
				for(String line : unique) {
					document.insertString(start, line + (uniqueLines>1 ? "\n" : ""), null);
					start+=line.length()+1;
					uniqueLines--;
				}
			} catch (BadLocationException ex) {
				ex.printStackTrace();
			}
			
		}
	};
	
	/**
	 * Set language to Croatian.
	 */
	private final Action setCroatian = 
			new LocalizableAction("croatian", HR_ICON, "alt shift ctrl C", KeyEvent.VK_C, flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("hr");
		}
	};
	
	/**
	 * Set language to german.
	 */
	private final Action setGerman = 
			new LocalizableAction("german", DE_ICON, "alt shift ctrl G", KeyEvent.VK_G, flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("de");
		}
	};
	
	/**
	 * Set language to English.
	 */
	private final Action setEnglish = 
			new LocalizableAction("english", EN_ICON, "alt shift ctrl E", KeyEvent.VK_E, flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("en");
		}
	};
	
	/**
	 * Exit application.
	 */
	private final Action exitApplicationAction = 
			new LocalizableAction("exit", EXIT_ICON, "alt f4", KeyEvent.VK_X, flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			exitApplication();
		}
	};
	
	
	/* ======================================
	 *  LISTENERS
	 * ====================================== */
	
	/**
	 * Add all needed listeners.
	 */
	private void addListeners() {
		// Window listener triggered on closing window.
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exitApplication();	
			}
		});
		
		// Localization listener triggered when language changes.
		flp.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				if(editor.getCurrentDocument()!=null && 
						editor.getCurrentDocument().getFilePath()==null) {
					setTitle(flp.getString("untitled") + " - JNotepad++");
				}
			}
		});
		
		// Clipboard listener triggered when content in clipboard changes.
		Toolkit.getDefaultToolkit().getSystemClipboard().addFlavorListener(new FlavorListener() {
			@Override
			public void flavorsChanged(FlavorEvent e) {
				Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
				try {
					pasteAction.setEnabled(cb.isDataFlavorAvailable(DataFlavor.stringFlavor));
				} catch(NullPointerException | IllegalStateException exc) {
					pasteAction.setEnabled(true);
				}
			}
		});
		
		// Editor listener triggered when tab changes or document gets added/removed.
		editor.addMultipleDocumentListener(new MultipleDocumentListener() {	
			@Override
			public void documentRemoved(SingleDocumentModel model) {
				if(editor.getNumberOfDocuments()==0) {
					closeDocumentAction.setEnabled(false);
					closeAllDocumentsAction.setEnabled(false);
					saveDocumentAction.setEnabled(false);
					saveAsAction.setEnabled(false);
					cutAction.setEnabled(false);
					copyAction.setEnabled(false);
					pasteAction.setEnabled(false);
					statisticsAction.setEnabled(false);
				}
				
			}
			
			@Override
			public void documentAdded(SingleDocumentModel model) {
				saveAsAction.setEnabled(true);
				closeDocumentAction.setEnabled(true);
				closeAllDocumentsAction.setEnabled(true);
				statisticsAction.setEnabled(true);
			}
			
			@Override
			public void currentDocumentChanged(SingleDocumentModel previousDocument, 
					SingleDocumentModel currentDocument) {
				if(currentDocument!=null) {
					if(previousDocument!=null) {
						previousDocument.getTextComponent().removeCaretListener(caretListener);
						previousDocument.removeSingleDocumentListener(documentModifiedListener);
					}
					
					currentDocument.getTextComponent().addCaretListener(caretListener);
					currentDocument.addSingleDocumentListener(documentModifiedListener);
					
					JTextArea textArea = editor.getCurrentDocument().getTextComponent();
					updateStatusBar(textArea);
					
					Path filePath = currentDocument.getFilePath();
					setTitle(filePath==null ? 
							flp.getString("untitled") + " - JNotepad++" : filePath.toString()+" - JNotepad++");
					if(currentDocument.isModified()) {
						saveDocumentAction.setEnabled(true);
					} else {
						saveDocumentAction.setEnabled(false);
					}
				} else {
					setTitle("JNotepad++");
				}
				
			}
		});		
	}
	
	/**
	 * SingleDocumentListener triggered when current document gets modified or path changes.
	 */
	private SingleDocumentListener documentModifiedListener = new SingleDocumentListener() {
		@Override
		public void documentModifyStatusUpdated(SingleDocumentModel document) {
			saveDocumentAction.setEnabled(true);
		}

		@Override
		public void documentFilePathUpdated(SingleDocumentModel document) {
			Path filePath = document.getFilePath();
			setTitle(filePath.toString()+" - JNotepad++");
		}
		
	};
	
	/**
	 * Caret listener triggered when caret position changes.
	 */
	private CaretListener caretListener = new CaretListener() {
		@Override
		public void caretUpdate(CaretEvent e) {
			JTextArea textArea = editor.getCurrentDocument().getTextComponent();
			int selectionLength = textArea.getCaret().getDot()-textArea.getCaret().getMark();
			
			boolean isEnabled = selectionLength!=0;
			cutAction.setEnabled(isEnabled);
			copyAction.setEnabled(isEnabled);
			toUpperCaseAction.setEnabled(isEnabled);
			toLowerCaseAction.setEnabled(isEnabled);
			invertCaseAction.setEnabled(isEnabled);
			sortAscAction.setEnabled(isEnabled);
			sortDescAction.setEnabled(isEnabled);
			uniqueAction.setEnabled(isEnabled);
			
			updateStatusBar(textArea);
		}
	};
	
	/**
	 * Program entry point.
	 * Takes no arguments.
	 * @param args program arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JNotepadPP().setVisible(true);
		});
	}
	
	
	/* ======================================
	 *  HELPER METHODS
	 * ====================================== */
	
	/**
	 * ToolBar button factory method which removes text from button.
	 * @param action button action
	 * @return produced button
	 */
	private JButton createToolbarButton(Action action) {
		JButton button = new JButton(action);
		button.setHideActionText(true);
		return button;
	}
	
	/**
	 * Implementation for "Save file as" action.
	 */
	private void saveFileAs() {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle(flp.getString("save"));
		fc.setApproveButtonText(flp.getString("save"));
		if(fc.showOpenDialog(JNotepadPP.this)!=JFileChooser.APPROVE_OPTION) {
			return;
		}
		
		File fileName = fc.getSelectedFile();
		Path filePath = fileName.toPath();
		
		if(Files.exists(filePath)) {
			int result = JOptionPane.showOptionDialog(
					(JTabbedPane) editor,
					flp.getString("overwrite"),
					flp.getString("fileexists"),
					JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
			if (result!=JOptionPane.YES_OPTION) {
				return;
			}
		}
		
		editor.saveDocument(editor.getCurrentDocument(), filePath);
		saveDocumentAction.setEnabled(false);
	}
	
	/**
	 * Change case of current document based on arguments of method. 
	 * First argument handles lower-case letters while second handles upper-case letters.
	 * @param lowercase action on lower-case letters
	 * @param uppercase action on upper-case letters
	 */
	private void changeCase(Function<Character, Character> lowercase, Function<Character, Character> uppercase) {
		JTextArea area = editor.getCurrentDocument().getTextComponent();
		Document doc = area.getDocument();
		
		int len = Math.abs(area.getCaret().getDot()-area.getCaret().getMark());
		if(len==0) return;
		int offset = Math.min(area.getCaret().getDot(), area.getCaret().getMark());
			
		try {
			String text = doc.getText(offset, len);
			text = changeTextCase(text, lowercase, uppercase);
			doc.remove(offset, len);
			doc.insertString(offset,  text, null);
		} catch(BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Change case of input text.
	 * @param text input text
	 * @param lowercase function used on lower-case letters
	 * @param uppercase function used on upper-case letters
	 * @return resulting string
	 */
	private String changeTextCase(String text, 
			Function<Character, Character> lowercase,
			Function<Character, Character> uppercase) {
		char[] znakovi = text.toCharArray();
		for(int i=0; i<znakovi.length; i++) {
			char c = znakovi[i];
			if(lowercase!=null && Character.isLowerCase(c)) {
				znakovi[i] = lowercase.apply(c);
			} else if(uppercase!=null && Character.isUpperCase(c)) {
				znakovi[i] = uppercase.apply(c);
			}
		}
		return new String(znakovi);
	}
	
	/**
	 * Close document. If document was modified and not saved, prompt user.
	 * @param closeDocument document to close
	 * @return true if user cancels closing, false otherwise
	 */
	private boolean closePrompt(SingleDocumentModel closeDocument) {
		if(closeDocument==null) return false;
		if(closeDocument.isModified()) {
			String[] options = new String[] {
					flp.getString("savefile"), 
					flp.getString("dontsave"), 
					flp.getString("cancel")};
			int result = JOptionPane.showOptionDialog(
					closeDocument.getTextComponent(),
					flp.getString("filenotsaved.desc"),
					flp.getString("filenotsaved"),
					JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, 
					null, options, options[0]);
			switch(result) {
			case JOptionPane.YES_OPTION:
				if(closeDocument.getFilePath()!=null) {
					editor.saveDocument(closeDocument, closeDocument.getFilePath());
				} else {
					saveFileAs();
				}
				editor.closeDocument(closeDocument);
				break;
			case JOptionPane.NO_OPTION:
				editor.closeDocument(closeDocument);
				break;
			case JOptionPane.CANCEL_OPTION:
				return true;
			default:
				return true;
			}
		} else {
			editor.closeDocument(closeDocument);
		}
		return false;
	}
	
	/**
	 * Sort current document selected lines using comparator given in argument.
	 * @param comparator comparator
	 */
	private void sort(Comparator<String> comparator) {
		JTextArea area = editor.getCurrentDocument().getTextComponent();
		Document document = area.getDocument();
		
		int len = Math.abs(area.getCaret().getDot()-area.getCaret().getMark());
		if(len==0) return;
		int offset = Math.min(area.getCaret().getDot(), area.getCaret().getMark());
		
		int start;
		int end;

		try {
			start = area.getLineStartOffset(area.getLineOfOffset(offset));
			end = area.getLineEndOffset(area.getLineOfOffset(offset+len));
			
			String areaText = document.getText(start, end-start);
			List<String> lines = new ArrayList<>();
			Collections.addAll(lines, areaText.split("\n"));
			Collections.sort(lines, comparator);
			
			document.remove(start, end-start);

			int uniqueLines = lines.size();
			for(String line : lines) {
				document.insertString(start, line + (uniqueLines>1 ? "\n" : ""), null);
				start+=line.length()+1;
				uniqueLines--;
			}
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Updates information on status bar based on caret from text area given in argument.
	 * @param textArea text area
	 */
	private void updateStatusBar(JTextArea textArea) {
		int selectionLength = textArea.getCaret().getDot()-textArea.getCaret().getMark();
		
		int documentLength = textArea.getDocument().getLength();
		statusBar.getLengthCount().setText(String.valueOf(documentLength));
		
		int currentLine = 0;
		int currentColumn = 0;
		try {
			currentLine = textArea.getLineOfOffset(textArea.getCaret().getDot());
			currentColumn = Math.abs(textArea.getLineStartOffset(currentLine)-textArea.getCaret().getDot());
		} catch (BadLocationException ex) {
			ex.printStackTrace();
		}
		
		statusBar.getLineCount().setText(String.valueOf(currentLine+1));
		statusBar.getColumnCount().setText(String.valueOf(currentColumn+1));
		statusBar.getSelectionCount().setText(String.valueOf(selectionLength));
	}
	
	/**
	 * Determines if program is closed.
	 * @return true if application is closed, false otherwise
	 */
	public static boolean isClosed() {
		return stopWorking;
	}
	
	/**
	 * Exit application. Iterate over opened documents and prompt user for unsaved documents.
	 */
	private void exitApplication() {
		for(int i = editor.getNumberOfDocuments()-1; i>=0; i--) {
			SingleDocumentModel closeDocument = editor.getDocument(i);
			if(closePrompt(closeDocument)) {
				return;
			}
		}
		dispose();
		stopWorking=true;
	}
	
	
}
