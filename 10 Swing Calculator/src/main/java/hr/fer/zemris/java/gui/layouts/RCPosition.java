package hr.fer.zemris.java.gui.layouts;

/**
 * Models position of component in CalcLayout.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class RCPosition {
	/**
	 * Components row.
	 */
	private final int row;
	/**
	 * Components column.
	 */
	private final int column;
	
	/**
	 * Default constructor for RCPosition.
	 * 
	 * @param row row
	 * @param column column
	 * @throws IllegalArgumentException if any given coordinate is less than 1
	 */
	public RCPosition(int row, int column) {
		if(row<=0 || column<=0) {
			throw new IllegalArgumentException("Position cords cant be less than 1.");
		}
		this.row = row;
		this.column = column;
	}
	
	/**
	 * Getter for row.
	 * @return row number
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Getter for column.
	 * @return column number
	 */
	public int getColumn() {
		return column;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RCPosition other = (RCPosition) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}
	

	@Override
	public String toString() {
		return row + "," + column;
	}
	
	
}
