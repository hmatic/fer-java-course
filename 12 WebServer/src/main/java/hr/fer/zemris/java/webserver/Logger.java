package hr.fer.zemris.java.webserver;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Handles logging of exceptions and errors occuring on SmartHttpServer.
 * Please see "log" method.
 * @author Hrvoje Matic
 * @version 1.0
 */
public class Logger {
	/**
	 * Path to log file.
	 */
	private static final String LOGS_PATH = "logs.txt";
	/**
	 * Single and only logger instance generated when class is loaded.
	 */
	private static Logger instance = new Logger();
	
	/**
	 * Private Logger constructor to prevent construction from outside.
	 */
	private Logger() { }
	
	/**
	 * Get reference to logger instance.
	 * @return logger instance
	 */
	public static Logger getLogger() {
		return instance;
	}
	
	/**
	 * Write log to logs file.<br>
	 * Takes 2 arguments: log message and reference to exception.<br>
	 * Stack trace will be only printed if given exception is not null.<br>
	 * Output will be formatted as:<br>
	 * "HH:mm:ss - dd.MM.yyyy:<br>
	 * log message<br>
	 * Stack trace:<br>
	 * exception stack trace"<br>
	 * @param log log message
	 * @param e reference to exception
	 */
	public void log(String log, Exception e) {
		try(FileWriter fw = new FileWriter(LOGS_PATH, true);
			    BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter out = new PrintWriter(bw)) {
			String timeStamp = new SimpleDateFormat("HH:mm:ss - dd.MM.yyyy:").format(Calendar.getInstance().getTime());
		    out.println(timeStamp);
			out.println(log);
			if(e!=null) {
				out.println("Stack trace:\n");
				for(StackTraceElement element : e.getStackTrace()) {
					out.println(element.toString());
				}
			}
			out.println("=============================");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
}
