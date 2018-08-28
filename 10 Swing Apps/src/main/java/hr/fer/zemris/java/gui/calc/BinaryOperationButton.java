package hr.fer.zemris.java.gui.calc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.DoubleBinaryOperator;

/**
 * Models button which handles binary operation.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class BinaryOperationButton extends CalcButton {
	/** Serialization ID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Calculator model.
	 */
	private CalcModel model;
	/**
	 * Binary operator
	 */
	private DoubleBinaryOperator operator;
	
	/**
	 * Default constructor for BinaryOperationButton.
	 * 
	 * @param label button label
	 * @param model calculator model
	 * @param operator operator
	 */
	public BinaryOperationButton(String label, CalcModel model, DoubleBinaryOperator operator) {
		super(label);
		this.model = model;
		this.operator = operator;
		this.addActionListener(new BinaryOperationListener());
	}
	
	/**
	 * Models action listener for BinaryOperationButton. 
	 * Upon button activation binary operation will be scheduled, and previous operation will be executed.
	 * 
	 * @author Hrvoje Matić
	 */
	private class BinaryOperationListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(model.isActiveOperandSet()) {
				double newValue = model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), model.getValue());
				model.setValue(newValue);
				model.setActiveOperand(newValue);
				model.setPendingBinaryOperation(operator);
			} else {
				model.setActiveOperand(model.getValue());
				model.setPendingBinaryOperation(operator);
			}
			model.clear();	
		}
	}
	

}
