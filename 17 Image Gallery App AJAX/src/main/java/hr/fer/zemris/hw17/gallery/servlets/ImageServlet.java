package hr.fer.zemris.hw17.gallery.servlets;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used for serving images from WEB-INF folder.
 * Sends 404 if path is not valid or image does not exist.
 * 
 * @author Hrvoje Matic
 * @version 1.0
 */
@WebServlet("/image/*")
public class ImageServlet extends HttpServlet {
	/** Serialization ID */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Relative path to images directory.
	 */
	private static final String IMG_DIR = "/WEB-INF/slike";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Path imgDir = Paths.get(req.getServletContext().getRealPath(IMG_DIR));
		
		String img = req.getPathInfo().substring(1);
		
		Path imgPath = imgDir.resolve(img);
		if(img.contains("/") || !Files.exists(imgPath)) {
			resp.sendError(404, imgPath.toString() + " " + img);
			return;
		}
		
		try(FileInputStream in = new FileInputStream(imgPath.toFile());
	    		OutputStream out = resp.getOutputStream()) {
			byte[] buf = new byte[1024];
			int count = 0;
			while ((count = in.read(buf)) >= 0) {
				out.write(buf, 0, count);
			}
			out.flush();
	    }
	}
}
