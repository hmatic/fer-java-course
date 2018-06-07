package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Servlet which generates Excel file (.xls) containing voting results.
 * If no voting results storage file exists, servlet will return xls file 
 * with 0 as default vote count for every band from voting definition.
 * @author Hrvoje Matic
 * @version 1.0
 */
@WebServlet("/glasanje-xls")
public class GlasanjeXlsServlet extends HttpServlet {
	/** Serialization ID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, Integer> results = new HashMap<>();
		Map<String, String> bands = new HashMap<>();
		for(String line : AppUtils.loadLines(req, "/WEB-INF/glasanje-definicija.txt")) {
			String[] lineParts = line.split("\t");
			bands.put(lineParts[0], lineParts[1]);
		}
		if(Files.exists(Paths.get(req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt")))) {
			for(String line : AppUtils.loadLines(req, "/WEB-INF/glasanje-rezultati.txt")) {
				String[] lineParts = line.split("\t");
				String resultID = lineParts[0];
				if(bands.containsKey(resultID)) {
					results.put(lineParts[0], Integer.parseInt(lineParts[1]));
				}
			}
		} else {
			for(String id : bands.keySet()) {
				results.put(id, Integer.valueOf(0));
			}
		}
		
		List<XlsResult> voteResults = new ArrayList<>();
		for(Map.Entry<String, Integer> entry : results.entrySet()) {
			voteResults.add(new XlsResult(bands.get(entry.getKey()), entry.getValue()));
		}
		Collections.sort(voteResults);
		Collections.reverse(voteResults);
		
		HSSFWorkbook hwb=new HSSFWorkbook();
		HSSFSheet sheet =  hwb.createSheet("Rezultati glasanja");
		HSSFRow firstRow = sheet.createRow(0);
		firstRow.createCell((short) 0).setCellValue("Ime benda:");
		firstRow.createCell((short) 1).setCellValue("Broj glasova:");
		int rowNum = 1;
		for(XlsResult result : voteResults) {
			HSSFRow row = sheet.createRow(rowNum);
			row.createCell((short) 0).setCellValue(result.getBandName());
			row.createCell((short) 1).setCellValue(result.getVoteCount());
			rowNum++;
		}
		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=\"rezultati-glasanja.xls\"");
		hwb.write(resp.getOutputStream());
		hwb.close();
	}
	
	/**
	 * Models single row of XLS file result.
	 * @author Hrvoje Matic
	 * @version 1.0
	 */
	private static class XlsResult implements Comparable<XlsResult>{
		/**
		 * Band name.
		 */
		private String bandName;
		/**
		 * Vote count.
		 */
		private int voteCount;
		/**
		 * Default constructor
		 * @param bandName band name
		 * @param voteCount vote count
		 */
		public XlsResult(String bandName, int voteCount) {
			super();
			this.bandName = bandName;
			this.voteCount = voteCount;
		}
		/**
		 * Getter for band name.
		 * @return band name
		 */
		public String getBandName() {
			return bandName;
		}
		/**
		 * Getter for vote count.
		 * @return vote count
		 */
		public int getVoteCount() {
			return voteCount;
		}
		@Override
		public int compareTo(XlsResult other) {
			return ((Integer)voteCount).compareTo(other.getVoteCount());
		}
		
		
	}
}
