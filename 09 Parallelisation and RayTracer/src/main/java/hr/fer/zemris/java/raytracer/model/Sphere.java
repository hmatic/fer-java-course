package hr.fer.zemris.java.raytracer.model;

/**
 * Class models sphere as 3D graphical object.
 * 
 * @author Hrvoje Matić
 *
 */
public class Sphere extends GraphicalObject {

	/**
	 * Sphere center.
	 */
	private Point3D center;
	/**
	 * Sphere radius.
	 */
	private double radius;
	/**
	 * Diffuse coef. for red.
	 */
	private double kdr;
	/**
	 * Diffuse coef. for green.
	 */
	private double kdg;
	/**
	 * Diffuse coef. for blue.
	 */
	private double kdb;
	/**
	 * Reflexive coef. for red.
	 */
	private double krr;
	/**
	 * Reflexive coef. for green.
	 */
	private double krg;
	/**
	 * Reflexive coef for blue.
	 */
	private double krb;
	/**
	 * Reflexive intensity coef.
	 */
	private double krn;

	/**
	 * Default constructor for sphere
	 * 
	 * @param center sphere center
	 * @param radius sphere radius
	 * @param kdr diffuse coef for red
	 * @param kdg diffuse coef for green
	 * @param kdb diffuse coef for blue
	 * @param krr reflexive coef for red
	 * @param krg reflexive coef for green
	 * @param krb reflexive coef for blue
	 * @param krn reflexive coef for itensity
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, 
			double krr, double krg, double krb,	double krn) {
		super();
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {
		double a = ray.direction.scalarProduct(ray.direction); 
		double b = ray.direction.scalarMultiply(2).scalarProduct(ray.start.sub(center));
		double c = (ray.start.sub(center)).scalarProduct(ray.start.sub(center)) - radius * radius;

		double discriminant = b * b - 4 * a * c;
		if (discriminant < 0) {
			return null;
		}
		
		double denominator = 2*a;
		double discriminantRooted = Math.sqrt(discriminant);
		double solution1 = (-b + discriminantRooted) / denominator;
		double solution2 = (-b - discriminantRooted) / denominator;
		if (solution1 <= 0 && solution2 <= 0) {
			return null;
		}

		double distance = Math.min(solution1, solution2);
		Point3D intersection = ray.start.add(ray.direction.scalarMultiply(distance));
		boolean isOuter = intersection.sub(ray.start).norm() > radius;

		return new RayIntersectionImpl(intersection, distance, isOuter);
	}
	
	/**
	 * Implementation of RayIntersection for sphere.
	 * 
	 * @author Hrvoje Matić
	 *
	 */
	private class RayIntersectionImpl extends RayIntersection {

		/**
		 * Default constructor for RayIntersectionImpl.
		 * 
		 * @param point point of intersection between ray and object
		 * @param distance distance between start of ray and intersection
		 * @param outer outer specifies if intersection is outer
		 */
		protected RayIntersectionImpl(Point3D point, double distance, boolean outer) {
			super(point, distance, outer);
		}
		
		@Override
		public Point3D getNormal() {
			return getPoint().sub(center).normalize();
		}

		@Override
		public double getKrr() {
			return krr;
		}

		@Override
		public double getKrn() {
			return krn;
		}

		@Override
		public double getKrg() {
			return krg;
		}

		@Override
		public double getKrb() {
			return krb;
		}

		@Override
		public double getKdr() {
			return kdr;
		}

		@Override
		public double getKdg() {
			return kdg;
		}

		@Override
		public double getKdb() {
			return kdb;
		}
		
	}

}