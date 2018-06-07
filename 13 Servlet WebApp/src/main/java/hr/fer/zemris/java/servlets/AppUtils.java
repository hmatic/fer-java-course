package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Contains all static helper methods used in this application.
 * @author Hrvoje Matic
 * @version 1.0
 */
public class AppUtils {
	/**
	 * Helper method used to load lines of text file given by relative path in second argument.
	 * Path will be resolved according to servlet context real path.
	 * @param req http request
	 * @param filePath path relative to app root
	 * @return list of lines from input file
	 * @throws IOException if error occurs during IO operation
	 */
	public static List<String> loadLines(HttpServletRequest req, String filePath) throws IOException {
		String fileName = req.getServletContext().getRealPath(filePath);
		return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
	}
}
