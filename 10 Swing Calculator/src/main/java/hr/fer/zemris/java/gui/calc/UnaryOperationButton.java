package hr.fer.zemris.java.gui.calc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.DoubleUnaryOperator;

import javax.swing.JCheckBox;

/**
 * Models button which handles unary operation.
 * These operation can be inverted using inverter button on calculator.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class UnaryOperationButton extends CalcButton {
	/** Serialization ID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Calculator model.
	 */
	private CalcModel model;
	/**
	 * Normal operation.
	 */
	private DoubleUnaryOperator operation;
	/**
	 * Inverted operation.
	 */
	private DoubleUnaryOperator invertedOperation;
	/**
	 * Reference to inverter checkbox.
	 */
	private JCheckBox inverter;
	
	/**
	 * Default constructor for UnaryOperationButton.
	 * 
	 * @param label button label
	 * @param model calculator model
	 * @param operation normal operation
	 * @param invertedOperation inverted operation
	 * @param inverter inverter
	 */
	public UnaryOperationButton(String label, CalcModel model, DoubleUnaryOperator operation,
			DoubleUnaryOperator invertedOperation, JCheckBox inverter) {
		super(label);
		this.model = model;
		this.operation = operation;
		this.invertedOperation = invertedOperation;
		this.inverter = inverter;
		this.addActionListener(new UnaryOperationListener());
	}


	/**
	 * Models action listener for BinaryOperationButton. 
	 * Upon button activation listener checks if inverter is selected and then performs correct unary operation.
	 * 
	 * @author Hrvoje Matić
	 */
	private class UnaryOperationListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(inverter.isSelected()) {
				model.setValue(invertedOperation.applyAsDouble(model.getValue()));
			} else {
				model.setValue(operation.applyAsDouble(model.getValue()));
			}
			
		}
		
	}
}
