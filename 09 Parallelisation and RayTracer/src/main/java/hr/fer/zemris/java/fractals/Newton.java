package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Newton-Raphson fractal viewer.
 * User will be prompted to input roots in complex format (a+ib)
 * where a is real part and b is imaginary part of complex number.
 * User must input at least 2 roots.
 * Input ends with keyword "done".
 * 
 * @author Hrvoje Matic
 * @version 1.0
 */
public class Newton {
	/**
	 * Maximum number of iterations.
	 */
	public static final int ITER_THRESHOLD = 16*16;
	/**
	 * Acceptable root distance.
	 */
	public static final double ROOT_THRESHOLD = 0.002;
	/**
	 * Convergence threshold.
	 */
	public static final double CONVERGENCE_THRESHOLD = 0.001;
	
	/**
	 * My implementation of fractal producer. Generates 8*numberOfAvailableProcessors jobs 
	 * and calculates results using parallelization and ExecutorService class.
	 * @author Hrvoje Matic
	 * @version 1.0
	 * @see ExecutorService
	 *
	 */
	public static class MyFractalProducer implements IFractalProducer {
		/**
		 * Coefficient for number of jobs. Given in instructions.
		 */
		private static final int NO_OF_JOBS_COEF = 8;
		/**
		 * Roots given by user.
		 */
		private ComplexRootedPolynomial roots;
		/**
		 * Polynomial generated from roots.
		 */
		private ComplexPolynomial poly;
		/**
		 * ExecutorService pool.
		 */
		private ExecutorService pool;
		
		/**
		 * Default constructor for MyFractalProducer.
		 * @param roots roots given by user
		 * @param poly polynomial from roots
		 */
		public MyFractalProducer(ComplexRootedPolynomial roots, ComplexPolynomial poly) {
			this.roots = roots;
			this.poly = poly;
			this.pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), 
					new ThreadFactory() {
						@Override
						public Thread newThread(Runnable r) {
							Thread newThread = new Thread(r);
							newThread.setDaemon(true);
							return newThread;
						}
					});	
		}
				
				
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height,
				long requestNo, IFractalResultObserver observer) {
			List<Future<Void>> results = new ArrayList<>();
			short[] data = new short[width*height];
			int noOfJobs = NO_OF_JOBS_COEF*Runtime.getRuntime().availableProcessors();
			
			for(int i=0; i<noOfJobs; i++) {
				int yMin = i*(height/noOfJobs);
				int yMax = i==(noOfJobs-1) ? height-1 : (i+1)*(height/noOfJobs) - 1;
				results.add(pool.submit(
						new CalculationJob(roots, poly, yMin, yMax, data, 
								reMin, reMax, imMin, imMax, width, height)
						));
			}
			
			for(Future<Void> result : results) {
				try {
					result.get();
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			}
			
			pool.shutdown();
			observer.acceptResult(data, (short)(poly.order()+1), requestNo);
		}
		
	}
	
	/**
	 * Class that models calculation job for every pixel.
	 * 
	 * @author Hrvoje Matic
	 * @version 1.0
	 */
	public static class CalculationJob implements Callable<Void> {
		/**
		 * Given polynomial roots.
		 */
		private ComplexRootedPolynomial roots; 
		/**
		 * Given polynomial roots as ComplexPolynomial.
		 */
		private ComplexPolynomial poly;
		/**
		 * Starting y of calculation
		 */
		private int yMin;
		/**
		 * Ending y of calculation
		 */
		private int yMax;
		/**
		 * Reference to array where color data is stored.
		 */
		private short[] data;
		/**
		 * Minimum real part of complex number.
		 */
		private double reMin; 
		/**
		 * Maximum real part of complex number.
		 */
		private double reMax; 
		/**
		 * Minimum imaginary part of complex number.
		 */
		private double imMin; 
		/**
		 * Maximum imaginary part of complex number.
		 */
		private double imMax;
		/**
		 * Window width.
		 */
		private int width;
		/**
		 * Window height.
		 */
		private int height;
		

		/**
		 * Default constructor for CalculationJob.
		 * @param roots given roots
		 * @param poly given roots as poly
		 * @param yMin min y in calculation
		 * @param yMax max y in calculation
		 * @param data colors data array
		 * @param reMin minimum real part of complex number
		 * @param reMax maximum real part of complex number
		 * @param imMin minimum imaginary part of complex number
		 * @param imMax maximum imaginary part of complex number
		 * @param width width of window
		 * @param height height of window
		 */
		public CalculationJob(ComplexRootedPolynomial roots, ComplexPolynomial poly, int yMin, int yMax, short[] data,
				double reMin, double reMax, double imMin, double imMax, int width, int height) {
			this.roots = roots;
			this.poly = poly;
			this.yMin = yMin;
			this.yMax = yMax;
			this.data = data;
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;

		}

		@Override
		public Void call() throws Exception {
			int offset = width*yMin;
			for (int y = yMin; y <= yMax; y++) {
				for (int x = 0; x < width; x++) {
					Complex zn = mapToComplexPlain(x, y);
					Complex zn1;
					double module;
					int iter = 0;
					do {
						Complex numerator = poly.apply(zn);
						Complex denominator = poly.derive().apply(zn);
						Complex fraction = numerator.divide(denominator);
						zn1 = zn.sub(fraction);
						module = zn1.sub(zn).module();
						zn = zn1;
						iter++;
					} while (module>CONVERGENCE_THRESHOLD && iter<ITER_THRESHOLD);
					int index = roots.indexOfClosestRootFor(zn1, ROOT_THRESHOLD);
					data[offset++] = index != -1 ? (short) index : 0;
					
				}
			}
			return null;
		}

		/**
		 * Helper method that maps given coordinates to complex plain.
		 * @param x x coordinate
		 * @param y y coordinate
		 * @return complex number in given coordinates
		 */
		private Complex mapToComplexPlain(int x, int y) {
			double re = x / (width-1.0) * (reMax-reMin) + reMin;
			double im = (height-y-1.0) / (height-1.0) * (imMax-imMin) + imMin;
			return new Complex(re, im);
		}
		
	}
	
	/**
	 * Program entry point.
	 * Program takes no arguments.
	 * @param args list of arguments
	 */
	public static void main(String[] args) {
		List<Complex> roots = new ArrayList<>();
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.\r\n" + 
				"Please enter at least two roots, one root per line. Enter 'done' when done.");
		try(Scanner sc = new Scanner(System.in)) {
			int i = 1;
			while(true) {
				System.out.print("Root " + i + " > ");
				String line = sc.nextLine().trim().toLowerCase();
				if(line.equals("done")) break;
				try {
					roots.add(Complex.parse(line));
				} catch(IllegalArgumentException e) {
					System.out.println("Can't parse given complex number. Please try again.");
					continue;
				}
				i++;
			}
		}
		if(roots.size()<2) {
			System.out.println("Expected at least 2 roots");
			System.exit(-1);
		}
		ComplexRootedPolynomial rootsPoly = new ComplexRootedPolynomial(roots.toArray(new Complex[0]));
		FractalViewer.show(new MyFractalProducer(rootsPoly, rootsPoly.toComplexPolynom()));
		
	}
	

}