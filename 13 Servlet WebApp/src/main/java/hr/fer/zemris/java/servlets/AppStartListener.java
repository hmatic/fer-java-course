package hr.fer.zemris.java.servlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Listener triggered when application starts. 
 * It marks down current time in milliseconds in "appStart" attribute
 * which is later used to calculate application life span.
 * @author Hrvoje Matic
 * @version 1.0
 */
@WebListener
public class AppStartListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent e) {		
	}

	@Override
	public void contextInitialized(ServletContextEvent e) {
		e.getServletContext().setAttribute("appStart", System.currentTimeMillis());
	}

}
