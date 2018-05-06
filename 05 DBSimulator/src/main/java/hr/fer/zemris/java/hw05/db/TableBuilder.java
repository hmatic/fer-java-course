package hr.fer.zemris.java.hw05.db;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Class used to build resizeable tables. Table adapts its column width to the biggest column member width.
 * Use it by adding rows to TableBuilder and calling toString() upon TableBuilder.
 * @author Hrvoje Matić
 * @version 1.0
 */
public class TableBuilder {
	/**
	 * List of table rows.
	 */
    List<String[]> rows = new LinkedList<String[]>();
 
    /**
     * Method adds single row with indefinite number of columns.
     * @param cols column value
     */
    public void addRow(String... cols) {
        rows.add(cols);
    }
 
    /**
     * Method calculates width for each column. Width is calculated by finding biggest member of column.
     * 
     * @return array of column widths
     */
    private int[] colWidths() {
        int cols = 0;
 
        for(String[] row : rows) {
            cols = Math.max(cols, row.length);
        }
        
        int[] widths = new int[cols];
 
		for (String[] row : rows) {
			for (int colNum = 0; colNum < row.length; colNum++) {
				widths[colNum] = Math.max(widths[colNum], row[colNum].length());
			}
		}
 
        return widths;
    }
 
    /**
     * Returns String representation of TableBuilder.
     * This method must be called in order to have functional table.
     * 
     * @return table
     */
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        StringJoiner sj = new StringJoiner(" | ", "| ", " |");
 
        int[] colWidths = colWidths();
        
        sb.append(getHeaderOrFooter(colWidths)).append("\n");
        
        for(String[] row : rows) {
			for (int colNum = 0; colNum < row.length; colNum++) {
				sj.add(padRight(row[colNum], colWidths[colNum]));
				
			}
			
            sb.append(sj.toString()).append("\n");
            sj = new StringJoiner(" | ", "| ", " |");
        }
       
        sb.append(getHeaderOrFooter(colWidths));
        
        return sb.toString();
    }
    
    /**
     * Creates header or footer for table.
     * 
     * @param colWidths array with column widths
     * @return string that represents header or footer
     */
    private static String getHeaderOrFooter(int[] colWidths) {
        String repeatChar = "=";
        StringJoiner sj = new StringJoiner("+", "+", "+");
        for (int colNum = 0; colNum < colWidths.length; colNum++) {
        	sj.add(String.join("", Collections.nCopies(colWidths[colNum]+2, repeatChar)));
        }
        return sj.toString();
    }
    
    /**
     * Helper method that formats string to be certain width. String is moved to the left, and empty space is moved to the right.
     * Method idea copied from Apache Commons.
     * 
     * @param s string to be adjusted
     * @param n final width of string
     * @return adjusted string
     */
    private static String padRight(String s, int n) {
        return String.format("%1$-" + n + "s", s);  
   }
}
