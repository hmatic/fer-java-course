package hr.fer.zemris.java.gui.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

/**
 * Implementation of CalcModel. Stores current calculator value, operand value and pending operation.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class CalcModelImpl implements CalcModel {
	/**
	 * Current calculator value.
	 */
	private String value = null;
	/**
	 * Current pending operation.
	 */
	private DoubleBinaryOperator pendingOperation = null;
	/**
	 * Current operand.
	 */
	private Double activeOperand = null;
	
	/**
	 * List of observers watching this model.
	 */
	private List<CalcValueListener> observers = new ArrayList<>();
	
	@Override
	public void addCalcValueListener(CalcValueListener l) {
		observers.add(l);
		
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		observers.remove(l);
		
	}

	@Override
	public double getValue() {
		return Double.parseDouble(toString());
	}

	@Override
	public void setValue(double value) {
		if(!Double.isNaN(value) && !Double.isInfinite(value)) {
			this.value = formatDouble(value);
		}
		notifyObservers();
	}

	@Override
	public void clear() {
		value = null;
//		notifyObservers();
		
	}

	@Override
	public void clearAll() {
		clear();
		clearActiveOperand();
		pendingOperation = null;
		notifyObservers();
		
	}

	@Override
	public void swapSign() {
		if(value==null) return;
		if(value.startsWith("-")) {
			value = value.substring(1);
		} else {
			value = "-" + value;
		}
		notifyObservers();
	}

	@Override
	public void insertDecimalPoint() {
		if(value==null) insertDigit(0);
		if(!value.contains(".")) {
			value+=".";
		}
		notifyObservers();
	}

	@Override
	public void insertDigit(int digit) {
		String digitAsString = String.valueOf(digit);
		if(value!=null) {
			if (Double.isInfinite(getValue() * 10 + (double) digit)) return;
			if(digit==0 && value.equals("0")) return;
			if(value.equals("0") && digit!=0) {
				value = "";
			}
			value+=digitAsString;
			
		} else {
			value=digitAsString;
		}
		
		notifyObservers();
	}

	@Override
	public boolean isActiveOperandSet() {
		return activeOperand!=null;
	}

	@Override
	public double getActiveOperand() {
		if(!isActiveOperandSet()) {
			throw new IllegalStateException();
		}
		return activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand; 
		
	}

	@Override
	public void clearActiveOperand() {
		activeOperand = null;
		
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		pendingOperation = op;
		
	}
	
	/**
	 * Clears current pending operation. Sets it to null.
	 */
	public void clearPendingBinaryOperation() {
		pendingOperation = null;
	}
	
	@Override
	public String toString() {
		return value==null ? "0" : value;
	}

	/**
	 * Notifies all observers stored in observer list.
	 */
	private void notifyObservers() {
		for(CalcValueListener observer : new ArrayList<>(observers)) {
			observer.valueChanged(this);
		}
	}
	
	/**
	 * Helper method that formats double into String. 
	 * If number can be parsed into integer, final string will be without decimal dot.
	 * 
	 * @param number number to be formatted
	 * @return formatted string
	 */
	public static String formatDouble(double number)
	{
	    if(number == (long) number)
	        return String.format("%d", (long)number);
	    else
	        return String.format("%s", number);
	}
	
	
}
