package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

/**
 * Tool represents current State of JVDraw. 
 * Each tool reacts to mouse events and paints something on canvas.
 * See State design pattern.
 * @author Hrvoje Matic
 * @version 1.0
 */
public interface Tool {
	/**
	 * Action performed when mouse is pressed.
	 * @param e mouse event
	 */
	void mousePressed(MouseEvent e);
	/**
	 * Action performed when mouse is released.
	 * @param e mouse event
	 */
	void mouseReleased(MouseEvent e);
	/**
	 * Action performed when mouse is clicked.
	 * @param e mouse event
	 */
	void mouseClicked(MouseEvent e);
	/**
	 * Action performed when mouse is moved.
	 * @param e mouse event
	 */
	void mouseMoved(MouseEvent e);
	/**
	 * Action performed when mouse is dragged.
	 * @param e mouse event
	 */
	void mouseDragged(MouseEvent e);
	/**
	 * Paints to canvas.
	 * @param g2d graphics object
	 */
	void paint(Graphics2D g2d);
}
