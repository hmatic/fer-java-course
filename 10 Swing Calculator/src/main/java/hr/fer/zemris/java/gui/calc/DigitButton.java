package hr.fer.zemris.java.gui.calc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Models button which handles digit insertion.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class DigitButton extends CalcButton {
	/** Serialization ID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Calculator digit.
	 */
	private int digit;
	/**
	 * Calculator model.
	 */
	private CalcModel model;
	
	/**
	 * Default constructor for DigitButton.
	 * 
	 * @param digit digit
	 * @param model calculator model
	 */
	public DigitButton(int digit, CalcModel model) {
		super(String.valueOf(digit));
		
		this.digit = digit;
		this.model = model;
		this.addActionListener(new NumberActionListener());

	}

	/**
	 * Models action listener for DigitButton. 
	 * Upon button activation new digit will be inserted into model.
	 * 
	 * @author Hrvoje Matić
	 */
	private class NumberActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			model.insertDigit(digit);
		}
	}
	
}
