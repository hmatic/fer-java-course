package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

/**
 * Layout Manager for calculator. This layout manager is fixed, made of 5 rows and 7 columns. 
 * Each component except first one has same width and same height.
 * First component takes width of first 5 elements.
 * 
 * @author Hrvoje Matić
 *
 */
public class CalcLayout implements LayoutManager2 {
	/**
	 * Spacing between buttons.
	 */
	private int spacing;
	/**
	 * Map of layout components.
	 */
	private Map<RCPosition, Component> components = new HashMap<>();
	
	/**
	 * Default spacing. Used if no other spacing defined.
	 */
	private static final int DEFAULT_SPACING = 0;
	/**
	 * Number of columns.
	 */
	private static final int COLS = 7;
	/**
	 * Number of rows.
	 */
	private static final int ROWS = 5;
	/**
	 * Second coordinate of first component.
	 */
	private static final int FIRST_COMPONENT_START = 1;
	/**
	 * Second coordinate of second component.
	 */
	private static final int SECOND_COMPONENT_START = 6;
	/**
	 * Position of first component.
	 */
	private static final RCPosition FIRST_COMPONENT = new RCPosition(1, 1);

	/**
	 * Constructs new CalcLayout with defined spacing.
	 * @param spacing spacing between components
	 */
	public CalcLayout(int spacing) {
		if(spacing < 0) {
			throw new IllegalArgumentException("Spacing can not be less than 0. Was " + spacing + ".");
		}
		this.spacing = spacing;
	}
	
	/**
	 * Default constructor for CalcLayout. Uses default spacing.
	 */
	public CalcLayout() {
		this(DEFAULT_SPACING);
	}

	@Override
	public void addLayoutComponent(String arg0, Component arg1) {}

	@Override
	public void addLayoutComponent(Component component, Object constraints) {
		RCPosition position;
		if(constraints.getClass()==String.class) {
			position = Util.parseCoordinates((String)constraints, RCPosition::new);
		} else if(constraints.getClass()==RCPosition.class) {
			position = (RCPosition)constraints;
		} else {
			throw new IllegalArgumentException("Constraints have to be String or RCPosition.");
		}
		if(validatePosition(position) && !components.containsKey(position)) {
			components.put(position, component);
		} else {
			throw new CalcLayoutException();
		}
		
	}
	
	/**
	 * Validates position inside CalcLayout.
	 * Positions taken by first component are invalid.
	 * Positions with coordinates bigger than number of rows or columns are invalid.
	 * 
	 * @param pos position
	 * @return true if position is valid, false otherwise
	 */
	private boolean validatePosition(RCPosition pos) {
		// First component takes these positions so they are not valid
		if(pos.getRow()==1 && (pos.getColumn()>FIRST_COMPONENT_START && 
				pos.getColumn()<SECOND_COMPONENT_START)) return false;
		
		if(pos.getRow()<=0 || pos.getRow()>ROWS) return false;
		if(pos.getColumn()<=0 || pos.getColumn()>COLS) return false;
		
		return true;
	}
	
	@Override
	public void removeLayoutComponent(Component component) {
		while(components.values().remove(component));
	}
	
	@Override
	public void layoutContainer(Container parent) {
		int width = (parent.getWidth()-(COLS-1)*spacing)/COLS;
		int height = (parent.getHeight()-(ROWS-1)*spacing)/ROWS;
		
		for(Entry<RCPosition, Component> entry : components.entrySet()) {
			if(entry.getKey().equals(FIRST_COMPONENT)) {
				entry.getValue().setBounds(0, 0, 5 * width + 4 * spacing , height);
			} else {
				int row = entry.getKey().getRow();
				int column = entry.getKey().getColumn();
				entry.getValue().setBounds((column-1)*(width + spacing), (row-1)*(height + spacing), width, height);
			}
		}
		
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return calculateLayoutSize(parent, Component::getMinimumSize);
	}
	
	@Override
	public Dimension maximumLayoutSize(Container parent) {
		return calculateLayoutSize(parent, Component::getMaximumSize);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return calculateLayoutSize(parent, Component::getPreferredSize);
	}



	@Override
	public float getLayoutAlignmentX(Container container) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container container) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container container) {}
	
	/**
	 * Generic method for calculating desired size of layout. 
	 * Method handle to size getter is given in second argument.
	 * @param parent container
	 * @param size method handle to size getter
	 * @return layout size as Dimension
	 */
	private Dimension calculateLayoutSize(Container parent, Function<Component, Dimension> size ) {
		Insets insets = parent.getInsets();
		
		int maxWidth = 0;
        int maxHeight = 0;
		for(Entry<RCPosition, Component> entry : components.entrySet()) {
            Dimension d = size.apply(entry.getValue());
            if(d != null) {
            	if(!entry.getKey().equals(FIRST_COMPONENT)) {
            		maxWidth = Math.max(maxWidth, d.width);
            	}
            	maxHeight = Math.max(maxHeight, d.height);
            }
        }
		
		int width;
		int firstComponentSize;
		if(components.containsKey(FIRST_COMPONENT)) {
			firstComponentSize=(int) size.apply(components.get(FIRST_COMPONENT)).getWidth();
		} else {
			firstComponentSize=0;
		}
		if(firstComponentSize > 5*maxWidth) {
			width = firstComponentSize + 2*maxWidth + (COLS-1) * spacing + insets.left + insets.right;
		} else {
			width = COLS * maxWidth + (COLS-1) * spacing + insets.left + insets.right;
		}
        return new Dimension(width, ROWS * maxHeight + (ROWS-1) * spacing + insets.top + insets.bottom);
	}
	
	
}
