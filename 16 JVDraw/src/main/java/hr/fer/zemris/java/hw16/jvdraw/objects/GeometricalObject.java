package hr.fer.zemris.java.hw16.jvdraw.objects;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw16.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.listeners.GeometricalObjectListener;
import hr.fer.zemris.java.hw16.jvdraw.visitors.GeometricalObjectVisitor;

/**
 * Abstract class represents any geometrical object in JVDraw.
 * @author Hrvoje Matic
 * @version 1.0
 */
public abstract class GeometricalObject {
	/**
	 * Registered listeners.
	 */
	private List<GeometricalObjectListener> listeners = new ArrayList<>();
	
	/**
	 * Accept method for visitors.
	 * See Visitor design pattern.
	 * @param visitor geometrical object visitor
	 */
	public abstract void accept(GeometricalObjectVisitor visitor);

	/**
	 * Factory method for GeometricalObjectEditor.
	 * @return new instance of GeometricalObjectEditor.
	 */
	public abstract GeometricalObjectEditor createGeometricalObjectEditor();
	
	/**
	 * Register GeometricalObjectListener to this geometrical object.
	 * @param listener geometrical object listener
	 */
	public void addGeometricalObjectListener(GeometricalObjectListener listener) {
		listeners.add(listener);
	}
	
	/**
	 * De-register GeometricalObjectListener to this geometrical object.
	 * @param listener geometrical object listener
	 */
	public void removeGeometricalObjectListener(GeometricalObjectListener listener) {
		listeners.remove(listener);
	}
	
	/**
	 * Notify all listeners that geometrical object has changed.
	 * @param obj object which was changed
	 */
	public void notifyObjectChanged(GeometricalObject obj) {
		for(GeometricalObjectListener listener : new ArrayList<>(listeners)) {
			listener.geometricalObjectChanged(obj);
		}
	}
	
	
}
