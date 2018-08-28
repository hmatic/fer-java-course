package hr.fer.zemris.java.hw16.jvdraw.editors;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw16.jvdraw.components.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.objects.FilledCircle;

/**
 * Editor implementation for FilledCircle geometrical objects.
 * @author Hrvoje Matic
 * @version 1.0
 */
public class FilledCircleEditor extends GeometricalObjectEditor {
	/** Serialization ID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Reference to FilledCircle which is being edited.
	 */
	private FilledCircle filledCircle;
	
	/**
	 * Text field for x coordinate of circle center.
	 */
	private JTextField x = new JTextField();
	/**
	 * Text field for y coordinate of circle center.
	 */
	private JTextField y = new JTextField();
	/**
	 * Text field for circle radius.
	 */
	private JTextField radius = new JTextField();
	/**
	 * Color chooser for circle stroke color.
	 */
	private JColorArea fg;
	/**
	 * Color chooser for circle fill color.
	 */
	private JColorArea bg;
	
	
	/**
	 * Default constructor for FilledCircleEditor.
	 * @param filledCircle reference to edited filled circle
	 */
	public FilledCircleEditor(FilledCircle filledCircle) {
		this.filledCircle = filledCircle;
		
		setLayout(new GridLayout(5,2));
		add(new JLabel("x"));
		x.setText(String.valueOf(filledCircle.getX()));
		add(x);
		add(new JLabel("y"));
		y.setText(String.valueOf(filledCircle.getY()));
		add(y);
		add(new JLabel("Radius"));
		radius.setText(String.valueOf(filledCircle.getRadius()));
		add(radius);
		
		add(new JLabel("Border Color"));
		fg = new JColorArea(filledCircle.getFgColor());
		add(fg);
		add(new JLabel("Fill Color"));
		bg = new JColorArea(filledCircle.getBgColor());
		add(bg);
	}

	@Override
	public void checkEditing() {
		try {
			Integer.parseInt(x.getText());
			Integer.parseInt(y.getText());
			Integer.parseInt(radius.getText());
		} catch(NumberFormatException e) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public void acceptEditing() {
		int editedX = Integer.parseInt(x.getText());
		int editedY = Integer.parseInt(y.getText());
		int editedRadius = Integer.parseInt(radius.getText());
		Color editedFgColor = fg.getCurrentColor();
		Color editedBgColor = bg.getCurrentColor();
		
		if(filledCircle.getX() != editedX) {
			filledCircle.setX(editedX);
		}
		if(filledCircle.getY() != editedY) {
			filledCircle.setY(editedY);
		}
		if(filledCircle.getRadius() != editedRadius) {
			filledCircle.setRadius(editedRadius);
		}
		
		if(filledCircle.getFgColor() != editedFgColor) {
			filledCircle.setFgColor(editedFgColor);
		}
		
		if(filledCircle.getBgColor() != editedBgColor) {
			filledCircle.setBgColor(editedBgColor);
		}
		
	}
}
