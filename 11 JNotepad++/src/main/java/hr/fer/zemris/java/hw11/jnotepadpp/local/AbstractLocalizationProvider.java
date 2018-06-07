package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class that models localization provider. Provides all methods except getString method.
 * @author Hrvoje Matic
 * @version 1.0
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

	/**
	 * List of registered localization listeners.
	 */
	private List<ILocalizationListener> listeners = new ArrayList<>();
	
	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		listeners.add(l);
		
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		listeners.remove(l);
		
	}

	/**
	 * Notify all registered localization listeners that localization changed.
	 */
	public void fire() {
		for(ILocalizationListener listener : new ArrayList<>(listeners)) {
			listener.localizationChanged();
		}
	}

}
