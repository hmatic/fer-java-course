package hr.fer.zemris.java.hw16.jvdraw.models;

import hr.fer.zemris.java.hw16.jvdraw.listeners.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;

/**
 * Drawing model interface.
 * @author Hrvoje Matic
 * @version 1.0
 */
public interface DrawingModel {
	/**
	 * Get number of geometrical objects in drawing model.
	 * @return model size
	 */
	public int getSize();
	/**
	 * Get geometrical object on given index.
	 * @param index index of object
	 * @return geometrical object
	 */
	public GeometricalObject getObject(int index);
	/**
	 * Remove geometrical object given in argument.
	 * @param object object to be removed
	 */
	void remove(GeometricalObject object);
	/**
	 * Change ordering of objects list by shifting object given 
	 * in first argument for offset given in second argument.
	 * @param object object to be reordered
	 * @param offset reorder offset
	 */
	void changeOrder(GeometricalObject object, int offset);
	/**
	 * Add geometrical object to model.
	 * @param object object to be added
	 */
	public void add(GeometricalObject object);
	/**
	 * Register drawing model listener.
	 * @param listener drawing model listener
	 */
	public void addDrawingModelListener(DrawingModelListener listener);
	/**
	 * De-register drawing model listener.
	 * @param listener drawing model listener
	 */
	public void removeDrawingModelListener(DrawingModelListener listener);
}
