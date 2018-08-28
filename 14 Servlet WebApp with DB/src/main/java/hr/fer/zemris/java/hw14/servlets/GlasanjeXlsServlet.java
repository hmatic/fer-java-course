package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.PollOption;

/**
 * Servlet which generates Excel file (.xls) containing voting results.
 * If poll with given ID does not exist or has no results, 
 * will dispatch request to appropriate error page.
 * 
 * @author Hrvoje Matic
 * @version 1.0
 */
@WebServlet("/servleti/glasanje-xls")
public class GlasanjeXlsServlet extends HttpServlet {
	/** Serialization ID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long pollID = 0;
		try {
			pollID = Long.parseLong(req.getParameter("pollID"));
		} catch (NumberFormatException e) {
			req.setAttribute("error", "Poll ID must be a number.");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		List<PollOption> results = DAOProvider.getDao().getPollResults(pollID);
		Collections.sort(results);
		Collections.reverse(results);
		
		if(results.isEmpty()) {
			req.setAttribute("error", "Poll with this ID does not exist or has no available results.");
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		
		HSSFWorkbook hwb=new HSSFWorkbook();
		HSSFSheet sheet =  hwb.createSheet("Rezultati glasanja");
		HSSFRow firstRow = sheet.createRow(0);
		firstRow.createCell((short) 0).setCellValue("Ime opcije:");
		firstRow.createCell((short) 1).setCellValue("Broj glasova:");
		int rowNum = 1;
		for(PollOption result : results) {
			HSSFRow row = sheet.createRow(rowNum);
			row.createCell((short) 0).setCellValue(result.getOptionTitle());
			row.createCell((short) 1).setCellValue(result.getVotesCount());
			rowNum++;
		}
		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=\"rezultati-glasanja.xls\"");
		hwb.write(resp.getOutputStream());
		hwb.close();
	}
	
	
}
