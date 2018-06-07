package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.text.Collator;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Models localization provider which handles localization of JNotepad++.
 * @author Hrvoje Matic
 * @version 1.0
 */
public class LocalizationProvider extends AbstractLocalizationProvider {
	/**
	 * Current language.
	 */
	private String language;
	/**
	 * Current resource bundle.
	 */
	private ResourceBundle bundle;
	/**
	 * Current collator.
	 */
	private Collator collator;
	
	/**
	 * Single instance of LocalizationProvider.
	 */
	private final static LocalizationProvider INSTANCE = new LocalizationProvider();
	/**
	 * Default language.
	 */
	private static final String DEFAULT_LANGUAGE = "en";

	/**
	 * Private constructor for LocalizationProvider prevents 
	 * construction from outside of this class.
	 */
	private LocalizationProvider() {
		setLanguage(DEFAULT_LANGUAGE);
	}
	
	/**
	 * Get instance of LocalizationProvider
	 * @return LocalizationProvider instance
	 */
	public static LocalizationProvider getInstance() {
		return INSTANCE;
	}
	
	/**
	 * Change current localization language.
	 * @param language
	 */
	public void setLanguage(String language) {
		if(!language.equals(this.language)) {
			this.language = language;
			Locale locale = Locale.forLanguageTag(this.language);
			this.bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.translations", locale);
			this.collator = Collator.getInstance(locale);
			fire();
		}
	}

	/**
	 * Getter for current collator.
	 * @return current collator
	 */
	public Collator getCollator() {
		return collator;
	}

	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}

}
