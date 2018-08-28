package hr.fer.zemris.hw17.gallery.servlets;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used for serving thumbnails of gallery pictures.
 * Will create thumbnail directory in WEB-INF if it does not exist.
 * Thumbnails will be created and saved to thumbnail directory only 
 * when needed (lazy-loading).
 * 
 * @author Hrvoje Matic
 * @version 1.0
 */
@WebServlet("/thumbnail/*")
public class ThumbnailServlet extends HttpServlet {
	/** Serialization ID */
	private static final long serialVersionUID = 1L;

	/**
	 * Relative path to images directory.
	 */
	private static final String IMG_DIR = "/WEB-INF/slike";
	/**
	 * Relative path to thumbnails directory.
	 */
	private static final String THUMB_DIR = "/WEB-INF/thumbnail";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Path thumbDir = Paths.get(req.getServletContext().getRealPath(THUMB_DIR));
		Path imgDir = Paths.get(req.getServletContext().getRealPath(IMG_DIR));
		
		if(!Files.exists(thumbDir)) {
			Files.createDirectory(thumbDir);
		}
		
		String img = req.getPathInfo().substring(1);
		Path imgPath = imgDir.resolve(img);
		if(img.contains("/") || !Files.exists(imgPath)) {
			resp.sendError(404, imgPath.toString() + " " + img);
			return;
		}
		
		Path thumbPath = thumbDir.resolve(img);
		
		if(!Files.exists(thumbPath)) {
			try {
				resize(imgPath.toString(), thumbPath.toString(), 150, 150);
			} catch (IOException e) {
				resp.sendError(500);
				return;
			}
			
		}
		
		ServletContext cntx= req.getServletContext();
	    String mime = cntx.getMimeType(imgPath.toString());
		if (mime == null) {
			resp.sendError(500);
			return;
		}
		resp.setContentType(mime);
	    
	    try(FileInputStream in = new FileInputStream(thumbPath.toFile());
	    		OutputStream out = resp.getOutputStream()) {
			byte[] buf = new byte[1024];
			int count = 0;
			while ((count = in.read(buf)) >= 0) {
				out.write(buf, 0, count);
			}
			out.flush();
	    }
	}
	
	/**
	 * Helper method which creates thumbnail from original image.
	 * Method taken from StackOverFlow and modified.
	 * https://stackoverflow.com/questions/1069095/how-do-you-create-a-thumbnail-image-out-of-a-jpeg-in-java
	 * 
	 * @param input path to original image
	 * @param output path to thumbnail file
	 * @param width thumbnail width
	 * @param height thumbnail height
	 * @throws IOException
	 */
	public static void resize(String input, String output, int width, int height) throws IOException {
		InputStream in = new BufferedInputStream(new FileInputStream(input));
		BufferedImage originalImage = ImageIO.read(in);
		int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
		BufferedImage resizedImage = new BufferedImage(width, height, type);
		{
			Graphics2D g = resizedImage.createGraphics();
			g.drawImage(originalImage, 0, 0, width, height, null);
			g.dispose();
			g.setComposite(AlphaComposite.Src);

			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}
		OutputStream out = new FileOutputStream(output);
		String extension = input.substring(input.lastIndexOf(".") + 1, input.length());
		ImageIO.write(resizedImage, extension, out);
		in.close();
		out.close();
	}
	
}
