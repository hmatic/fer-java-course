package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * Implementation of LocalizationProvider bridge which connects to 
 * frame when it opens and disconnects when it closes.
 * @author Hrvoje Matic
 * @version 1.0
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

	/**
	 * Constructor for FormLocalizationProvider.
	 * @param parent localization provider
	 * @param frame window frame
	 */
	public FormLocalizationProvider(ILocalizationProvider parent, JFrame frame) {
		super(parent);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
			}
		});
	}

}
