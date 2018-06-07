package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Decorator class for LocalizationProvider.
 * @author Hrvoje Matic
 * @version 1.0
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {
	/**
	 * ILocalizationProvider parent
	 */
	private ILocalizationProvider parent;
	/**
	 * Localization listener.
	 */
	private ILocalizationListener listener;
	/**
	 * Connection established flag.
	 */
	private boolean connected;
	
	/**
	 * Constructor for LocalizationProvider bridge.
	 * @param parent decorator parent localization provider
	 */
	public LocalizationProviderBridge(ILocalizationProvider parent) {
		this.parent = parent;
		this.listener = () -> fire();
	}
	
	/**
	 * Disconnect from ILocalizationProvider.
	 */
	public void disconnect() {
		parent.removeLocalizationListener(listener);
		connected = false;
	}
	
	/**
	 * Connect to ILocalizationProvider.
	 * Can not connect again if already connected.
	 */
	public void connect() {
		if(connected) {
			return;
		}
		parent.addLocalizationListener(listener);
		connected = true;
	}


	@Override
	public String getString(String key) {
		return parent.getString(key);
	}

}
