package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.JMenu;

/**
 * Models localizable menu.
 * @author Hrvoje Matic
 * @version 1.0
 */
public class LJMenu extends JMenu {
	/** Serialization ID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for localizable menu.
	 * @param key localization key
	 * @param provider localization provider
	 */
	public LJMenu(String key, ILocalizationProvider provider) {
		super(provider.getString(key));
		
		provider.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				setText(provider.getString(key));
			}
		});
	}
}
