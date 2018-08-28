package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.DoubleBinaryOperator;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import hr.fer.zemris.java.gui.layouts.CalcLayout;

/**
 * Calculator GUI application.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class Calculator extends JFrame {
	/** Serialization ID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Calculator model.
	 */
	private CalcModel model = new CalcModelImpl();
	/**
	 * Calculator display component.
	 */
	private JLabel display;
	/**
	 * Container for all calculator content.
	 */
	private JPanel p;
	/**
	 * inverter checkbox.
	 */
	private JCheckBox inverter;
	/**
	 * Calculator stack.
	 */
	private Double stack;
	
	/** Display color */
	private static final Color DISPLAY_COLOR = Color.decode("#ffd320");
	/** Default font */
	private static final Font DEFAULT_FONT = new Font("Arial Black", Font.BOLD, 20);
	/** Button color */
	private static final Color BUTTON_COLOR = Color.decode("#729FCF");
	/** Spacing between buttons */
	private static final int CALC_SPACING = 3;
	/** Element border color */
	private static final Color ELEMENT_BORDER = Color.BLUE;
	
	/**
	 * Default constructor for Calculator.
	 */
	public Calculator() {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Calculator");
		setLocation(20, 20);
		setSize(520, 370);
		initGUI();
	}
	
	/**
	 * Initialize GUI of Calculator.
	 */
	private void initGUI() {
		this.p = new JPanel(new CalcLayout(CALC_SPACING));

		this.display = new JLabel(model.toString());
		model.addCalcValueListener(new DisplayListener(display));
	
		initDisplay();
		initinverterCheckBox();
		initDigitButtons();
		initBinaryOperationButtons();
		initUnaryOperationButtons();
		initStackButtons();
		initMiscButtons();
	
		setMinimumSize(p.getMinimumSize());
		setMaximumSize(p.getMaximumSize());
		setPreferredSize(p.getPreferredSize());
		getContentPane().add(p);
	}
	
	/**
	 * Initializes miscellaneous buttons in calculator.
	 * Misc buttons include: EQUALS(=), CLEAR(CLR), RESET(RES), DOT(.), SWAP SIGN(+/-).
	 */
	private void initMiscButtons() {
		CalcButton equals = new CalcButton("=");
		equals.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(model.isActiveOperandSet()) {
					model.setValue(model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), model.getValue()));
					model.clearActiveOperand();
					((CalcModelImpl) model).clearPendingBinaryOperation();
				}
			}
		});
		p.add(equals, "1,6");
		
		CalcButton clear = new CalcButton("clr");
		clear.addActionListener(l -> model.setValue(0));
		p.add(clear, "1,7");
		
		CalcButton reset = new CalcButton("res");
		reset.addActionListener(l -> model.clearAll());
		p.add(reset, "2,7");
		
		CalcButton decimalDot = new CalcButton(".");
		decimalDot.addActionListener(l -> model.insertDecimalPoint());
		p.add(decimalDot, "5,5");
		
		CalcButton swapSign = new CalcButton("+/-");
		swapSign.addActionListener(l -> model.swapSign());
		p.add(swapSign, "5,4");	
	}

	/**
	 * Initializes stack buttons in calculator.
	 */
	private void initStackButtons() {
		CalcButton pop = new CalcButton("pop");
		pop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (stack == null) {
					JOptionPane.showMessageDialog(p, "Stack is empty");
					return;
				}
				model.setValue(stack);
				stack=null;
				
			}
		});
		p.add(pop, "4,7");
		
		CalcButton push = new CalcButton("push");
		push.addActionListener(l -> stack=model.getValue());
		p.add(push, "3,7");
	}

	/**
	 * Initializes inverter checkbox in calculator.
	 */
	private void initinverterCheckBox() {
		this.inverter = new JCheckBox("Inv");
		inverter.setBackground(BUTTON_COLOR);
		inverter.setFont(DEFAULT_FONT);
		inverter.setBorder(BorderFactory.createLineBorder(ELEMENT_BORDER));
		p.add(inverter, "5,7");
		
	}

	/**
	 * Initializes display in calculator.
	 */
	private void initDisplay() {
		display.setBackground(DISPLAY_COLOR);
		display.setHorizontalAlignment(JTextField.RIGHT);
		Border paddingBorder = BorderFactory.createEmptyBorder(0,0,0,15);
		Border border = BorderFactory.createLineBorder(ELEMENT_BORDER);
		display.setBorder(BorderFactory.createCompoundBorder(border,paddingBorder));
		display.setFont(DEFAULT_FONT);
		display.setForeground(Color.BLACK);
		display.setOpaque(true);
		p.add(display, "1,1");
	}

	/**
	 * Initializes unary operation buttons in calculator.
	 */
	private void initUnaryOperationButtons() {
		p.add(new UnaryOperationButton("sin", model, Math::sin, Math::asin, inverter), "2,2");
		p.add(new UnaryOperationButton("cos", model, Math::cos, Math::acos, inverter), "3,2");
		p.add(new UnaryOperationButton("tan", model, Math::tan, Math::atan, inverter), "4,2");
		p.add(new UnaryOperationButton("ctg", model, x -> Math.tan(1/x), x -> Math.atan(1/x), inverter), "5,2");
		p.add(new UnaryOperationButton("ln", model, Math::log, Math::exp, inverter), "4,1");
		p.add(new UnaryOperationButton("log", model, Math::log10, x -> Math.pow(10, x), inverter), "3,1");
		p.add(new UnaryOperationButton("1/n", model, x -> Math.pow(x,-1), x -> Math.pow(x,-1), inverter), "2,1");
	}

	/**
	 * Initializes binary operation buttons in calculator.
	 */
	private void initBinaryOperationButtons() {
		p.add(new BinaryOperationButton("+", model, (x,y)->x+y), "5,6");
		p.add(new BinaryOperationButton("-", model, (x,y)->x-y), "4,6");
		p.add(new BinaryOperationButton("*", model, (x,y)->x*y), "3,6");
		p.add(new BinaryOperationButton("/", model, (x,y)->x/y), "2,6");
		
		CalcButton powerOrRoot = new CalcButton("x^n");
		powerOrRoot.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!inverter.isSelected()) {
					powerOrRoot((x,y) -> Math.pow(x, y));
				} else {
					powerOrRoot((x,y) -> Math.pow(x, 1/y));
				}
			}
		});
		p.add(powerOrRoot, "5,1");
	}

	/**
	 * Initializes digit buttons in calculator.
	 */
	private void initDigitButtons() {
		p.add(new DigitButton(0, model), "5,3");
		p.add(new DigitButton(1, model), "4,3");
		p.add(new DigitButton(2, model), "4,4");
		p.add(new DigitButton(3, model), "4,5");
		p.add(new DigitButton(4, model), "3,3");
		p.add(new DigitButton(5, model), "3,4");
		p.add(new DigitButton(6, model), "3,5");
		p.add(new DigitButton(7, model), "2,3");
		p.add(new DigitButton(8, model), "2,4");
		p.add(new DigitButton(9, model), "2,5");
	}

	/**
	 * Models binary operation behavior. Used in button which creates inversive action for power or root.
	 * @param operation binary operation
	 */
	private void powerOrRoot(DoubleBinaryOperator operation) {
		if(model.isActiveOperandSet()) {
			double newValue = model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), model.getValue());
			model.setValue(newValue);
			model.setActiveOperand(newValue);
			model.setPendingBinaryOperation(operation);
		} else {
			model.setActiveOperand(model.getValue());
			model.setPendingBinaryOperation(operation);
		}
		model.clear();
	}
	

	/**
	 * Models CalcValueListener implementation for calculator display.
	 *
	 * @author Hrvoje Matić
	 *
	 */
	private class DisplayListener implements CalcValueListener {
		/**
		 * Reference to calculator display component.
		 */
		private JLabel display;
		
		/**
		 * Default constructor for DisplayListener.
		 * @param display reference to display
		 */
		public DisplayListener(JLabel display) {
			this.display = display;
		}

		@Override
		public void valueChanged(CalcModel model) {
			display.setText(model.toString());
		}
	}

	/**
	 * Program entry point.
	 * @param args program arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Calculator().setVisible(true));
	}
	
}