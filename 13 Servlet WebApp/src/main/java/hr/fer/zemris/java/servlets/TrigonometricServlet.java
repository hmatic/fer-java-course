package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet which generates table with trigonometric values 
 * (sinus and cosinus) for angles in degrees in given interval.
 * Interval values a and b are given in parameters and 
 * by default they are a=0 and b=360.
 * If a is greater than b, they will swap.
 * If b is greater than a+720, b will be a+720.
 * 
 * @author Hrvoje Matic
 * @version 1.0
 */
@WebServlet("/trigonometric")
public class TrigonometricServlet extends HttpServlet {
	/** Serialization ID. */
	private static final long serialVersionUID = 1L;
	/**
	 * Default variable "a" value.
	 */
	private static final int DEFAULT_A = 0;
	/**
	 * Default variable "b" value.
	 */
	private static final int DEFAULT_B = 360;
	/**
	 * B-A threshold.
	 * Variable b can be larger than variable a only for amount of this threshold.
	 */
	private static final int B_THRESHOLD = 720;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int a = DEFAULT_A;
		int b = DEFAULT_B;
		if(req.getParameterMap().containsKey("a")) {
			a = Integer.parseInt(req.getParameter("a"));
		}
		if(req.getParameterMap().containsKey("b")) {
			b = Integer.parseInt(req.getParameter("b"));
		}
		
		if(a>b) {
			int temp = b;
			b = a;
			a = temp;
		}
		
		if(b>(a+B_THRESHOLD)) {
			b=a+B_THRESHOLD;
		}
		
		List<TrigonometricValues> trigonometricTable = new ArrayList<>();
		
		for(int i = a; i <= b; i++) {
			trigonometricTable.add(
					new TrigonometricValues(i, 
							Math.sin(Math.toRadians(i)), 
							Math.cos(Math.toRadians(i))
							)
					);
		}
		
		req.setAttribute("trigonometricTable", trigonometricTable);
		req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);
	}
	
	/**
	 * Models trigonometric values(sin,cos) for given angle.
	 * @author Hrvoje Matic
	 * @version 1.0
	 */
	public static class TrigonometricValues {
		/**
		 * Angle in degrees.
		 */
		int angle;
		/**
		 * Sinus value of angle.
		 */
		double sin;
		/**
		 * Cosinus value of angle.
		 */
		double cos;
		/**
		 * Default constructor.
		 * @param angle angle
		 * @param sin sinus
		 * @param cos cosinus
		 */
		public TrigonometricValues(int angle, double sin, double cos) {
			super();
			this.angle = angle;
			this.sin = sin;
			this.cos = cos;
		}
		/**
		 * Getter for angle.
		 * @return angle
		 */
		public int getAngle() {
			return angle;
		}
		/**
		 * Getter for sinus.
		 * @return sinus
		 */
		public double getSin() {
			return sin;
		}
		/**
		 * Getter for cosinus.
		 * @return cosinus
		 */
		public double getCos() {
			return cos;
		}
	}
}
