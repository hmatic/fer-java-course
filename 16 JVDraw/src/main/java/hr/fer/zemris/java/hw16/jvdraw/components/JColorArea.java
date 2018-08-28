package hr.fer.zemris.java.hw16.jvdraw.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

import hr.fer.zemris.java.hw16.jvdraw.listeners.ColorChangeListener;

/**
 * Swing Component which models area of selected color.
 * Shows JColor Chooser upon click on this area.
 * Component has collection of ColorChangeListeners which it
 * notifies upon changes occuring in selected color.
 * @author Hrvoje Matic
 * @version 1.0
 */
public class JColorArea extends JComponent implements IColorProvider {
	/** Serialization ID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Default component preferred dimensions.
	 */
	private static final Dimension DEFAULT_DIMENSION = new Dimension(15,15);
	
	/**
	 * Currently selected color.
	 */
	private Color selectedColor;
	
	/**
	 * Registered listeners.
	 */
	private List<ColorChangeListener> listeners = new ArrayList<>();

	/**
	 * Default constructor for JColorArea.
	 * @param selectedColor initial color
	 */
	public JColorArea(Color selectedColor) {
		this.selectedColor = selectedColor;
		
		this.addMouseListener(new ColorChooserMouseListener());
	}
	
	/**
	 * Mouse listener added on this component upon initialization. 
	 * Used to open JColorChooser.
	 * @author Hrvoje Matic
	 * @version 1.0
	 */
	private class ColorChooserMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			Color newColor = JColorChooser.showDialog(
                    JColorArea.this,
                    "Choose Background Color",
                    getCurrentColor());
			if (newColor != null) {
			    setSelectedColor(newColor);
			}
		}
	}
	
	/**
	 * Setter for selected color. Notifies registered listeners about change.
	 * @param newColor new selected color
	 */
	private void setSelectedColor(Color newColor) {
		Color oldColor = this.selectedColor;
		this.selectedColor = newColor;
		repaint();
		for(ColorChangeListener listener : new ArrayList<>(listeners)) {
			listener.newColorSelected(this, oldColor, newColor);
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(selectedColor);
		Dimension size = getPreferredSize();
		g2d.fillRect(0, 0, (int)size.getWidth(), (int)size.getHeight());
	}
	
	@Override
	public Dimension getPreferredSize() {
		return DEFAULT_DIMENSION;
	}
	
	@Override
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}
	
	@Override
	public Dimension getMaximumSize() {
		return getPreferredSize();
	}

	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}

	@Override
	public void addColorChangeListener(ColorChangeListener listener) {
		listeners.add(listener);
		
	}

	@Override
	public void removeColorChangeListener(ColorChangeListener listener) {
		listeners.remove(listener);
		
	}
}
