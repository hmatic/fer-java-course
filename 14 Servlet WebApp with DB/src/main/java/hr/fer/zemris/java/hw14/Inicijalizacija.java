package hr.fer.zemris.java.hw14;

import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import hr.fer.zemris.java.hw14.dao.DAOProvider;

/**
 * Listener triggered when application is started. It initializes database 
 * connection pool and creates/populates if they are missing/empty.
 * Destroys connection pool when application exits.
 * @author Hrvoje Matic
 * @version 1.0
 */
@WebListener
public class Inicijalizacija implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Properties dbconfig = new Properties();
		try {
			dbconfig.load(new FileInputStream(sce.getServletContext().getRealPath("/WEB-INF/dbsettings.properties")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String dbName = dbconfig.getProperty("name");
		String host = dbconfig.getProperty("host");
		String port = dbconfig.getProperty("port");
		String user = dbconfig.getProperty("user");
		String password = dbconfig.getProperty("password");
		String connectionURL = "jdbc:derby://" + host + ":" + port + "/" + dbName + ";user=" + user + ";password=" + password;

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {
			throw new RuntimeException("Pogreška prilikom inicijalizacije poola.", e1);
		}
		cpds.setJdbcUrl(connectionURL);
		DAOProvider.getDao().createTables(cpds);
		
		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
		
		DAOProvider.getDao().fillTables(cpds);
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource)sce.getServletContext().getAttribute("hr.fer.zemris.dbpool");
		if(cpds!=null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}