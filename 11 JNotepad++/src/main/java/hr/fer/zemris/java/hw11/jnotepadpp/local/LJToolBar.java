package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.JToolBar;

/**
 * Models localizable toolbar.
 * @author Hrvoje Matic
 * @version 1.0
 */
public class LJToolBar extends JToolBar {
	/** Serialization ID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for localizable toolbar.
	 * @param key localization key
	 * @param provider localization provider
	 */
	public LJToolBar(String key, ILocalizationProvider provider) {
		super(provider.getString(key));
		
		provider.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				setName(provider.getString(key));
			}
		});
	}
}
