package hr.fer.zemris.java.hw16.jvdraw.editors;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw16.jvdraw.components.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.objects.Line;

/**
 * Editor implementation for Line geometrical objects.
 * @author Hrvoje Matic
 * @version 1.0
 */
public class LineEditor extends GeometricalObjectEditor {
	/** Serialization ID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Reference to Line which is being edited.
	 */
	private Line line;
	
	/**
	 * Text field for x coordinate of starting point.
	 */
	private JTextField x1 = new JTextField();
	/**
	 * Text field for y coordinate of starting point.
	 */
	private JTextField y1 = new JTextField();
	/**
	 * Text field for x coordinate of ending point.
	 */
	private JTextField x2 = new JTextField();
	/**
	 * Text field for y coordinate of ending point.
	 */
	private JTextField y2 = new JTextField();
	/**
	 * Color chooser for line color.
	 */
	private JColorArea fg;
	
	/**
	 * Default constructor for LineEditor.
	 * @param line reference to edited line
	 */
	public LineEditor(Line line) {
		this.line = line;
		setLayout(new GridLayout(5,2));
		add(new JLabel("x1"));
		x1.setText(String.valueOf(line.getX1()));
		add(x1);
		add(new JLabel("y1"));
		y1.setText(String.valueOf(line.getY1()));
		add(y1);
		add(new JLabel("x2"));
		x2.setText(String.valueOf(line.getX2()));
		add(x2);
		add(new JLabel("y2"));
		y2.setText(String.valueOf(line.getY2()));
		add(y2);
		add(new JLabel("Color"));
		fg = new JColorArea(line.getColor());
		add(fg);
	}

	@Override
	public void checkEditing() {
		try {
			Integer.parseInt(x1.getText());
			Integer.parseInt(y1.getText());
			Integer.parseInt(x2.getText());
			Integer.parseInt(y2.getText());
		} catch(NumberFormatException e) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public void acceptEditing() {
		int editedX1 = Integer.parseInt(x1.getText());
		int editedY1 = Integer.parseInt(y1.getText());
		int editedX2 = Integer.parseInt(x2.getText());
		int editedY2 = Integer.parseInt(y2.getText());
		Color editedColor = fg.getCurrentColor();
		
		if(line.getX1() != editedX1) {
			line.setX1(editedX1);
		}
		if(line.getY1() != editedY1) {
			line.setY1(editedY1);
		}
		if(line.getX2() != editedX2) {
			line.setX2(editedX2);
		}
		if(line.getY2() != editedY2) {
			line.setY2(editedY2);
		}
		if(line.getColor() != editedColor) {
			line.setColor(editedColor);
		}
		
	}
}
