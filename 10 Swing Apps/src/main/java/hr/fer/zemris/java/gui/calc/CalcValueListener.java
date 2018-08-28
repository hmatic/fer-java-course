package hr.fer.zemris.java.gui.calc;

/**
 * Interface which models value listener for calculator.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public interface CalcValueListener {
	/**
	 * Method called when calculator value is changed.
	 * @param model reference to calculator model
	 */
	void valueChanged(CalcModel model);
}