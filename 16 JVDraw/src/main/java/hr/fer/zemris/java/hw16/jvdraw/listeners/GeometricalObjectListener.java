package hr.fer.zemris.java.hw16.jvdraw.listeners;

import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;

/**
 * Interface which models Geometrical Object Listeners.
 * GeometricalObjectListener notifies observers 
 * when geometrical object properties have changed.
 * @author Hrvoje Matic
 * @version 1.0
 */
public interface GeometricalObjectListener {
	/**
	 * Geometrical object has changed.
	 * @param object geometrical object which was changed
	 */
	public void geometricalObjectChanged(GeometricalObject object);
}
