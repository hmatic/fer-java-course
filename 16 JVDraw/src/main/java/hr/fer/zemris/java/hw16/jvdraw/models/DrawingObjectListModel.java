package hr.fer.zemris.java.hw16.jvdraw.models;

import javax.swing.AbstractListModel;

import hr.fer.zemris.java.hw16.jvdraw.listeners.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;

/**
 * Adapter class for DrawingModel used as model of JList.
 * See Adapter design pattern.
 * @author Hrvoje Matic
 * @version 1.0
 */
public class DrawingObjectListModel extends AbstractListModel<GeometricalObject> {
	/** Serialization ID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Reference to drawing model.
	 */
	private DrawingModel drawingModel;
	
	/**
	 * Default constructor for DrawingObjectListModel.
	 * @param drawingModel reference to drawing model
	 */
	public DrawingObjectListModel(DrawingModel drawingModel) {
		super();
		this.drawingModel = drawingModel;
		drawingModel.addDrawingModelListener(new DrawingModelListener() {
			
			@Override
			public void objectsRemoved(DrawingModel source, int index0, int index1) {
				fireIntervalRemoved(this, index0, index1);
				
			}
			
			@Override
			public void objectsChanged(DrawingModel source, int index0, int index1) {
				fireContentsChanged(this, index0, index1);
				
			}
			
			@Override
			public void objectsAdded(DrawingModel source, int index0, int index1) {
				fireIntervalAdded(this, index0, index1);
				
			}
		});
	}
	
	@Override
	public GeometricalObject getElementAt(int index) {
		return drawingModel.getObject(index);
	}

	@Override
	public int getSize() {
		return drawingModel.getSize();
	}
	
}
