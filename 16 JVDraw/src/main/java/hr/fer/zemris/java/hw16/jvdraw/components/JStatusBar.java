package hr.fer.zemris.java.hw16.jvdraw.components;

import java.awt.Color;

import javax.swing.JLabel;

import hr.fer.zemris.java.hw16.jvdraw.listeners.ColorChangeListener;

/**
 * Simple status bar using JLabel.
 * Stores foreground and background color and displays their RGB values.
 * Registers color change listeners to IColorProviders so it can update its data.
 * @author Hrvoje Matic
 * @version 1.0
 */
public class JStatusBar extends JLabel {
	/** Serialization ID */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Current foreground color.
	 */
	private Color foregroundColor;
	/**
	 * Current background color.
	 */
	private Color backgroundColor;
	
	/**
	 * Default constructor for JStatusBar.
	 * @param fgColorProvider provider of foreground color
	 * @param bgColorProvider provider of background color
	 */
	public JStatusBar(IColorProvider fgColorProvider, IColorProvider bgColorProvider) {
		this.foregroundColor = fgColorProvider.getCurrentColor();
		this.backgroundColor = bgColorProvider.getCurrentColor();
		updateColorStatus();
		
		fgColorProvider.addColorChangeListener(new ColorChangeListener() {
			@Override
			public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
				setForegroundColor(newColor);
			}
		});
		bgColorProvider.addColorChangeListener(new ColorChangeListener() {
			@Override
			public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
				setBackgroundColor(newColor);
			}
		});
	}

	/**
	 * Updates JLabelText status.
	 */
	private void updateColorStatus() {
		StringBuilder colorStatusText = new StringBuilder();
		
		colorStatusText.append("Foreground color: (");
		colorStatusText.append(foregroundColor.getRed()).append(", ");
		colorStatusText.append(foregroundColor.getGreen()).append(", ");
		colorStatusText.append(foregroundColor.getBlue());
		colorStatusText.append("), background color: (");
		colorStatusText.append(backgroundColor.getRed()).append(", ");
		colorStatusText.append(backgroundColor.getGreen()).append(", ");
		colorStatusText.append(backgroundColor.getBlue());
		colorStatusText.append(").");
		
		setText(colorStatusText.toString());
	}

	/**
	 * Setter for foreground color.
	 * @param foreground foreground color
	 */
	public void setForegroundColor(Color foreground) {
		this.foregroundColor = foreground;
		updateColorStatus();
	}

	/**
	 * Setter for background color.
	 * @param background background color
	 */
	public void setBackgroundColor(Color background) {
		this.backgroundColor = background;
		updateColorStatus();
	}
	
	
}
