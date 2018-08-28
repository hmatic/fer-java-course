package hr.fer.zemris.java.hw16.jvdraw.editors;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw16.jvdraw.components.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.objects.Circle;

/**
 * Editor implementation for Circle geometrical objects.
 * @author Hrvoje Matic
 * @version 1.0
 */
public class CircleEditor extends GeometricalObjectEditor {
	/** Serialization ID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Reference to Circle which is being edited.
	 */
	private Circle circle;
	
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
	 * Default constructor for CircleEditor.
	 * @param circle reference to edited circle
	 */
	public CircleEditor(Circle circle) {
		this.circle = circle;
		
		setLayout(new GridLayout(4,2));
		add(new JLabel("x"));
		x.setText(String.valueOf(circle.getX()));
		add(x);
		add(new JLabel("y"));
		y.setText(String.valueOf(circle.getY()));
		add(y);
		add(new JLabel("Radius"));
		radius.setText(String.valueOf(circle.getRadius()));
		add(radius);
		
		add(new JLabel("Color"));
		fg = new JColorArea(circle.getColor());
		add(fg);
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
		Color editedColor = fg.getCurrentColor();
		
		if(circle.getX() != editedX) {
			circle.setX(editedX);
		}
		if(circle.getY() != editedY) {
			circle.setY(editedY);
		}
		if(circle.getRadius() != editedRadius) {
			circle.setRadius(editedRadius);
		}
		
		if(circle.getColor() != editedColor) {
			circle.setColor(editedColor);
		}
		
	}

}
