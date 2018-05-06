package Demo;

import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;
/**
 * Testing program where you can load LSystem configurations.
 * 
 * @author Hrvoje Matić
 */
public class Glavni3 {
	/**
	 * Program entry point.
	 * @param args array of arguments
	 */
	public static void main(String[] args) {
		LSystemViewer.showLSystem(LSystemBuilderImpl::new);
	}
}
