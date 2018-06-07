package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Models localizable action.
 * @author Hrvoje Matic
 * @version 1.0
 */
public abstract class LocalizableAction extends AbstractAction {
	/** Serialization ID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor for localizable action.
	 * @param key localization key
	 * @param icon action icon
	 * @param accelerator action accelerator key
	 * @param mn action mnemonic
	 * @param provider localization provider
	 */
	public LocalizableAction(String key, ImageIcon icon, String accelerator, int mn, ILocalizationProvider provider) {
		super();
		putValue(Action.NAME, provider.getString(key));
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(accelerator));
		putValue(Action.MNEMONIC_KEY, mn);
		putValue(Action.SHORT_DESCRIPTION, provider.getString(key + ".desc"));
		putValue(Action.SMALL_ICON, icon);

		
		provider.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				putValue(Action.NAME, provider.getString(key));
				putValue(Action.SHORT_DESCRIPTION, provider.getString(key + ".desc"));
			}
		});
	}
	
}

