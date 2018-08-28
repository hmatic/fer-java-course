package hr.fer.zemris.java.hw16.jvdraw.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw16.jvdraw.listeners.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.listeners.GeometricalObjectListener;
import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;

/**
 * Implementation of drawing model.
 * @author Hrvoje Matic
 * @version 1.0
 */
public class DrawingModelImpl implements DrawingModel {
	/**
	 * List of geometrical objects.
	 */
	private List<GeometricalObject> objects = new ArrayList<>();
	
	/**
	 * Registered listeners.
	 */
	private List<DrawingModelListener> listeners = new ArrayList<>();
	
	@Override
	public int getSize() {
		return objects.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		return objects.get(index);
	}

	@Override
	public void remove(GeometricalObject object) {
		int index = objects.indexOf(object);
		if(index >= 0) {
			objects.remove(object);
			notifyObjectRemoved(index, index);
		}
	}


	@Override
	public void changeOrder(GeometricalObject object, int offset) {
		int index = objects.indexOf(object);
		int newPosition = index+offset;
		if(newPosition>=0 && newPosition<objects.size()) {
			Collections.swap(objects, index, newPosition);
			notifyObjectChanged(index, newPosition);
		}
	}

	@Override
	public void add(GeometricalObject object) {
		object.addGeometricalObjectListener(new GeometricalObjectListener() {
			@Override
			public void geometricalObjectChanged(GeometricalObject o) {
				int index = objects.indexOf(o);
				notifyObjectChanged(index, index);
			}
		});
		objects.add(object);
		notifyObjectAdded(objects.indexOf(object));	
	}

	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		listeners.add(l);	
	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		listeners.remove(l);		
	}

	/**
	 * Notification method when objects in model changed.
	 * @param index0 starting index of change
	 * @param index1 ending index of change
	 */
	private void notifyObjectChanged(int index0, int index1) {
		for(DrawingModelListener listener : new ArrayList<>(listeners)) {
			listener.objectsChanged(this, index0, index1);
		}
	}

	/**
	 * Notification method when objects in model were added.
	 * @param index of added element (usually end of the list)
	 */
	private void notifyObjectAdded(int index) {	
		for(DrawingModelListener listener : new ArrayList<>(listeners)) {
			listener.objectsAdded(this, index, index);
		}	
	}
	
	/**
	 * Notification method when objects in model were removed
	 * @param index0 starting index of change
	 * @param index1 ending index of change
	 */
	private void notifyObjectRemoved(int index0, int index1) {
		for(DrawingModelListener listener : new ArrayList<>(listeners)) {
			listener.objectsRemoved(this, index0, index1);
		}
	}
}
