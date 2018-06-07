package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Interface which models Localization Provider.
 * @author Hrvoje Matic
 * @version 1.0
 */
public interface ILocalizationProvider {
	/**
	 * Register localization listener.
	 * @param l localization listener
	 */
	void addLocalizationListener(ILocalizationListener l);
	/**
	 * Deregister localization listener.
	 * @param l
	 */
	void removeLocalizationListener(ILocalizationListener l);
	/**
	 * Get translation result for given key.
	 * @param key localization key
	 * @return translation
	 */
	String getString(String key);
	
}
