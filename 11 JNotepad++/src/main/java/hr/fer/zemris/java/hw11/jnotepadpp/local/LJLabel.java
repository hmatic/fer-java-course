package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.JLabel;

/**
 * Models localizable label.
 * @author Hrvoje Matic
 * @version 1.0
 */
public class LJLabel extends JLabel {
	/** Serialization ID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for localizable label.
	 * @param key localization key
	 * @param provider localization provider
	 */
	public LJLabel(String key, ILocalizationProvider provider) {
		super(provider.getString(key));
		
		provider.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				setText(provider.getString(key));
			}
		});
	}
	
}

