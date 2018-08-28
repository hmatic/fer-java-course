package hr.fer.zemris.java.hw16.jvdraw.components;

import java.awt.Color;

import hr.fer.zemris.java.hw16.jvdraw.listeners.ColorChangeListener;

/**
 * Interface modeling color provider.
 * @author Hrvoje Matic
 * @version 1.0
 */
public interface IColorProvider {
	/**
	 * Get currently selected color.
	 * @return selected color
	 */
	public Color getCurrentColor();
	/**
	 * Registers color change listener to color provider.
	 * @param listener color change listener
	 */
	public void addColorChangeListener(ColorChangeListener listener);
	/**
	 * Deregisters color change listener from color provider.
	 * @param listener color change listener
	 */
	public void removeColorChangeListener(ColorChangeListener listener);
}

