package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LJLabel;

/**
 * Models status bar in JNotepad++
 * Status bar consists of three parts: document length, caret information and clock.
 * 
 * @author Hrvoje Matic
 * @version 1.0
 */
public class JStatusbar extends JPanel {
	/** Serialization ID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Length label.
	 */
	private LJLabel length;
	/**
	 * Length counter label.
	 */
	private JLabel lengthCount;
	
	/**
	 * Line label.
	 */
	private LJLabel line;
	/**
	 * Line counter label.
	 */
	private JLabel lineCount;
	/**
	 * Column label.
	 */
	private LJLabel column;
	/**
	 * Column counter label.
	 */
	private JLabel columnCount;
	/**
	 * Selection label.
	 */
	private LJLabel selection;
	/**
	 * Selection counter label.
	 */
	private JLabel selectionCount;
	
	/**
	 * Time label.
	 */
	private JLabel time;

	/**
	 * Default constructor for status bar.
	 * @param provider localization provider
	 */
	public JStatusbar(ILocalizationProvider provider) {
		this.length = new LJLabel("length", provider);
		this.lengthCount = new JLabel("0");
		this.line = new LJLabel("line", provider);
		this.lineCount = new JLabel("0");
		this.column = new LJLabel("column", provider);
		this.columnCount = new JLabel("0");
		this.selection = new LJLabel("selection", provider);
		this.selectionCount = new JLabel("0");
		
		this.time = new JLabel();
		
		setLayout(new GridLayout(1,3));
		setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
		time.setBorder(BorderFactory.createEmptyBorder(0,0,0,7));
		time.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JPanel lengthPart = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel status = new JPanel(new FlowLayout(FlowLayout.LEFT));
		lengthPart.add(length);
		lengthPart.add(lengthCount);
		status.add(line);
		status.add(lineCount);
		status.add(column);
		status.add(columnCount);
		status.add(selection);
		status.add(selectionCount);
		
		// Clock implementation using separate thread
		Thread timeThread = new Thread(() -> {
			DateTimeFormatter timeFormat = DateTimeFormatter
					.ofPattern("yyyy/MM/dd HH:mm:ss");
			while (true) {
				try {
					Thread.sleep(100);
				} catch (Exception ignorable) {
				}
				
				if(JNotepadPP.isClosed()) {
					break;
				}
				
				SwingUtilities.invokeLater(() -> {
					time.setText(timeFormat.format(LocalDateTime.now()));
				});
			}
		});
		timeThread.start();
		
		
		add(lengthPart);
		add(status);
		add(time);
	}

	/**
	 * Getter for length counter label.
	 * @return document length label
	 */
	public JLabel getLengthCount() {
		return lengthCount;
	}

	/**
	 * Getter for line counter label.
	 * @return line counter label
	 */
	public JLabel getLineCount() {
		return lineCount;
	}

	/**
	 * Getter for column counter label.
	 * @return column counter label
	 */
	public JLabel getColumnCount() {
		return columnCount;
	}

	/**
	 * Getter for selection counter label.
	 * @return selection counter label
	 */
	public JLabel getSelectionCount() {
		return selectionCount;
	}
}
