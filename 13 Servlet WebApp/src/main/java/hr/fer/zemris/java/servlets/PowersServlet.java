package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Servlet which generates Excel file (.xls) containing powers of 
 * numbers in range from a to b. Variables "a" and "b" are given as 
 * parameters. Third parameter "n" defines how many pages document 
 * will have. Each pages provides value powered to number of current page.
 * Bounds for variables are: a[-100,100], b[-100,100], n[1,5]
 * @author Hrvoje Matic
 * @version 1.0
 */
@WebServlet("/powers")
public class PowersServlet extends HttpServlet {
	/** Serialization ID. */
	private static final long serialVersionUID = 1L;
	/**
	 * Relative path to error page.
	 */
	private static final String ERROR_PAGE_PATH = "/WEB-INF/pages/errorPage.jsp";
	/** Maximum variable "a" value. */
	private static final int MAX_A = 100;
	/** Minimum variable "a" value. */
	private static final int MIN_A = -100;
	/** Maximum variable "b" value. */
	private static final int MAX_B = 100;
	/** Minimum variable "b" value. */
	private static final int MIN_B = -100;
	/** Maximum variable "n" value. */
	private static final int MAX_N = 5;
	/** Minimum variable "n" value. */
	private static final int MIN_N = 1;

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String paramA = req.getParameter("a");
		String paramB = req.getParameter("b");
		String paramN = req.getParameter("n");
		if(paramA==null || paramB==null || paramN==null) {
			req.setAttribute("errorMsg", "Error: All parameters must be asigned(a, b and n)");
			req.getRequestDispatcher(ERROR_PAGE_PATH).forward(req, resp);
			return;
		}
		int a=0;
		int b=0;
		int n=0;
		try {
			a = Integer.parseInt(paramA);
			b = Integer.parseInt(paramB);
			n = Integer.parseInt(paramN);
		} catch(NumberFormatException e) {
			req.setAttribute("errorMsg", "Error: Can not parse parameter into Integer");
			req.getRequestDispatcher(ERROR_PAGE_PATH).forward(req, resp);
			return;
		}
		if(a<MIN_A || a>MAX_A || b<MIN_B || b>MAX_B || n<MIN_N || n>MAX_N) {
			req.setAttribute("errorMsg", "Error: Parameter out of its bounds."
					+ "\nBounds are: a[-100,100], b[-100,100], n[1,5]");
			req.getRequestDispatcher(ERROR_PAGE_PATH).forward(req, resp);
			return;
		}
			
		HSSFWorkbook hwb=new HSSFWorkbook();
			
		for(int i=1; i<=n; i++) {
			HSSFSheet sheet =  hwb.createSheet(String.valueOf(i));
			int rowNum = 1;
			HSSFRow firstRow = sheet.createRow(0);
			firstRow.createCell((short) 0).setCellValue("value");
			firstRow.createCell((short) 1).setCellValue("value^" + i);
			for(int j=a; j<=b; j++) {
				HSSFRow row = sheet.createRow(rowNum);
				row.createCell((short) 0).setCellValue(j);
				row.createCell((short) 1).setCellValue(Math.pow(j, i));
				rowNum++;
			}
		}
		resp.setContentType("application/vnd.ms-excel");
		resp.setHeader("Content-Disposition", "attachment; filename=\"powers-table.xls\"");
		hwb.write(resp.getOutputStream());
		hwb.close();
	}
}
