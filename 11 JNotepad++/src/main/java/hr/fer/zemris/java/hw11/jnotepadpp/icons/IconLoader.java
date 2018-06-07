package hr.fer.zemris.java.hw11.jnotepadpp.icons;

import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;

/**
 * Models icon loader for JNotepad++.
 * This class can not be instanced as it is used only for loading icons using static icon.
 * 
 * @author Hrvoje Matic
 * @version 1.0
 */
public class IconLoader {
	/**
	 * Instance of IconLoader.
	 */
	private static IconLoader instance = new IconLoader();
	/**
	 * Private constructor.
	 */
	private IconLoader() {};
	
	/**
	 * Loads icon from given path and creates ImageIcon object.
	 * @param path path to icon
	 * @return ImageIcon object
	 */
	public static ImageIcon loadIcon(String path) {
		byte[] bytes = null;
		try(InputStream is = instance.getClass().getResourceAsStream(path)) {
			if(is==null) throw new IllegalArgumentException("Icon does not exist.");
			bytes = is.readAllBytes();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return new ImageIcon(bytes); 
	}

}
