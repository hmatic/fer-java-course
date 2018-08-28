package hr.fer.zemris.java.hw16.jvdraw.editors;

import javax.swing.JPanel;

/**
 * Abstract class which models GeometricalObjectEditor.
 * Editors are used as dialogs which take input data 
 * and edit geometrical objects.
 * @author Hrvoje Matic
 * @version 1.0
 */
public abstract class GeometricalObjectEditor extends JPanel {
	/** Serialization ID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Check if editing input data is valid.
	 * Throw exception if it is not.
	 */
	public abstract void checkEditing();
	/**
	 * Update geometrical object with new data.
	 */
	public abstract void acceptEditing();
}
