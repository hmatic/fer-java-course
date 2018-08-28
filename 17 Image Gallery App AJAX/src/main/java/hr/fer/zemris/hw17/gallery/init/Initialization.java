package hr.fer.zemris.hw17.gallery.init;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import hr.fer.zemris.hw17.gallery.model.GalleryImageDB;

/**
 * ServletContextListener triggered on application startup.
 * Fills our basic database with data from configuration file.
 * 
 * @author Hrvoje Matic
 * @version 1.0
 */
@WebListener
public class Initialization implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent e) {
	}

	@Override
	public void contextInitialized(ServletContextEvent e) {
		Path data = Paths.get(e.getServletContext().getRealPath("/WEB-INF/opisnik.txt"));
		GalleryImageDB.getInstance().build(data);
		
	}

}
