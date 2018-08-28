package hr.fer.zemris.java.hw16.jvdraw.listeners;

import hr.fer.zemris.java.hw16.jvdraw.models.DrawingModel;

/**
 * Interface which models Drawing Model Listener.
 * DrawingModelListener notifies observers when drawing model changed,
 * either by adding, removing or changing its objects.
 * @author Hrvoje Matic
 * @version 1.0
 */
public interface DrawingModelListener {
	/**
	 * Objects were added into model.
	 * @param source listener source
	 * @param index0 starting index of change
	 * @param index1 ending index of change
	 */
	public void objectsAdded(DrawingModel source, int index0, int index1);
	/**
	 * Objects were removed from model.
	 * @param source listener source
	 * @param index0 starting index of change
	 * @param index1 ending index of change
	 */
	public void objectsRemoved(DrawingModel source, int index0, int index1);
	/**
	 * Objects in model changed.
	 * @param source listener source
	 * @param index0 starting index of change
	 * @param index1 ending index of change
	 */
	public void objectsChanged(DrawingModel source, int index0, int index1);
}
