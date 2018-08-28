package hr.fer.zemris.java.hw16.jvdraw.listeners;

import java.awt.Color;

import hr.fer.zemris.java.hw16.jvdraw.components.IColorProvider;

/**
 * Interface which models color change listener.
 * Color change listener notifies observers when 
 * color in IColorProvider has changed.
 * @author Hrvoje Matic
 *
 */
public interface ColorChangeListener {
	/**
	 * New color was selected.
	 * @param source listener source
	 * @param oldColor old color
	 * @param newColor new color
	 */
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}
