package hr.fer.zemris.java.gui.calc;

import java.util.function.DoubleBinaryOperator;

/**
 * Model interface for calculator GUI application.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public interface CalcModel {
	/**
	 * Adds new CalcValueListener to list of subscribed listeners.
	 * @param l CalcValueListener to be added
	 */
	void addCalcValueListener(CalcValueListener l);
	
	/**
	 * Removes CalcValueListener from list of subscribed listeners.
	 * @param l CalcValueListener to be removed
	 */
	void removeCalcValueListener(CalcValueListener l);
	
	/**
	 * Returns current value as double.
	 * @return current value
	 */
	double getValue();
	
	/**
	 * Sets value given in argument as current value.
	 * @param value value to be set
	 */
	void setValue(double value);
	
	/**
	 * Clears current value.
	 */
	void clear();
	
	/**
	 * Clears current value, active operand and pending operation.
	 */
	void clearAll();
	
	/**
	 * Swaps sign of current value.
	 */
	void swapSign();
	
	/**
	 * Inserts decimal point into current value if it doesnt already exist.
	 */
	void insertDecimalPoint();
	
	/**
	 * Inserts digit into current value.
	 * @param digit digit to be inserted
	 */
	void insertDigit(int digit);
	
	/**
	 * Checks if active operand is set.
	 * @return true if operand is set, false otherwise
	 */
	boolean isActiveOperandSet();
	
	/**
	 * Getter for active operand
	 * @return active operand
	 */
	double getActiveOperand();
	
	/**
	 * Setter for active operand.
	 * @param activeOperand new active operand
	 */
	void setActiveOperand(double activeOperand);
	
	/**
	 * Clears active operand.
	 */
	void clearActiveOperand();
	
	/**
	 * Getter for pending binary operation.
	 * @return pending operation
	 */
	DoubleBinaryOperator getPendingBinaryOperation();
	
	/**
	 * Setter for pending binary operation.
	 * @param op operation to be set
	 */
	void setPendingBinaryOperation(DoubleBinaryOperator op);

}