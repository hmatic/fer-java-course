package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Demonstration of PrimListModel in simple GUI application.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class PrimDemo extends JFrame {
	/**
	 * Serialization ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor for PrimDemo.
	 */
	public PrimDemo() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(100, 100);
		setSize(400, 400);
		initGUI();
	}

	/**
	 * Initialize GUI.
	 */
	private void initGUI() {
		getContentPane().setLayout(new BorderLayout());
		PrimListModel model = new PrimListModel();
		
		JList<Integer> leftList = new JList<>(model);
		JList<Integer> rightList = new JList<>(model);
		JButton next = new JButton("Next");
		next.addActionListener(l -> model.next());
		JPanel listPanel = new JPanel(new GridLayout(1,2));
		listPanel.add(new JScrollPane(leftList));
		listPanel.add(new JScrollPane(rightList));
		
		getContentPane().add(listPanel, BorderLayout.CENTER);
		getContentPane().add(next, BorderLayout.PAGE_END);
	}
	
	/**
	 * Program entry point.
	 * 
	 * @param args array of arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new PrimDemo().setVisible(true));
	}
}
