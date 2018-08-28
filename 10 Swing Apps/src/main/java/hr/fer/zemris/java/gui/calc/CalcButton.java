package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;

/**
 * Models styling for all buttons in calculator.
 * 
 * @author Hrvoje Matić
 */
public class CalcButton extends JButton {
	/** Serialization ID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Button color.
	 */
	private static final Color BUTTON_COLOR = Color.decode("#729fcf");
	/**
	 * Button border color.
	 */
	private static final Color BUTTON_BORDER_COLOR = Color.BLUE;
	/**
	 * Button font.
	 */
	private static final Font BUTTON_FONT = new Font("Arial Black", Font.BOLD, 20);
	
	/**
	 * Constructs new CalcButton, passes label to JButton constructor and sets button style.
	 * @param label
	 */
	public CalcButton(String label) {
		super(label);
		this.setBackground(BUTTON_COLOR);
		this.setBorder(BorderFactory.createLineBorder(BUTTON_BORDER_COLOR));
		this.setFont(BUTTON_FONT);
	}
}
