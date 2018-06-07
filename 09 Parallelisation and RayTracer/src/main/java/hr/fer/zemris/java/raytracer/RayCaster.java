package hr.fer.zemris.java.raytracer;

import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Simplification of ray-tracer for rendering 3D scenes using only one thread.
 * 
 * @author Hrvoje Matić
 */
public class RayCaster {
	/**
	 * Threshold for comparing doubles.
	 */
	private static final double THRESHOLD = 0.000000001;
	
	/**
	 * Program entry point.
	 * 
	 * @param args arguments
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), 
				new Point3D(10, 0, 0), 
				new Point3D(0, 0, 0),
				new Point3D(0, 0, 10), 
				20, 20);
	}

	/**
	 * Static factory method for IRayTracerProducer.
	 * 
	 * @return implementation of IRayTracerProducer
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, 
					double vertical, int width, int height, long requestNo, 
					IRayTracerResultObserver observer) {
				System.out.println("Započinjem izračune...");
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];

				Point3D yAxis = viewUp.normalize()
						.sub(view.sub(eye)
								.normalize()
								.scalarMultiply(view.sub(eye).normalize().scalarProduct(viewUp.normalize())))
						.normalize();
				
				Point3D xAxis = view.sub(eye).normalize().vectorProduct(yAxis).normalize();
				
				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal/2))
						.add(yAxis.scalarMultiply(vertical/2));

				Scene scene = RayTracerViewer.createPredefinedScene();
				short[] rgb = new short[3];
				int offset = 0;
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {						
						Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply(horizontal*x / (width-1.0)))
								.sub(yAxis.scalarMultiply(vertical*y / (height-1.0)));;
								
						Ray ray = Ray.fromPoints(eye, screenPoint);
						tracer(scene, ray, rgb);
						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
						offset++;
					}
				}
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}
		};
	}
	
	/**
	 * Determines color of closest intersection of traced ray.
	 * 
	 * @param scene given scene
	 * @param ray traced ray
	 * @param rgb color intensities
	 */
	protected static void tracer(Scene scene, Ray ray, short[] rgb) {
		Arrays.fill(rgb, (short) 15);
		
		RayIntersection intersection = findClosestIntersection(scene, ray);
		if (intersection == null) {
			rgb[0] = 0;
			rgb[1] = 0;
			rgb[2] = 0;
		} else {
			determineColorFor(intersection, ray, scene, rgb);
		}
	}
	
	/**
	 * Determine color of screen pixel where intersection is found. 
	 * 
	 * @param intersection ray and object intersection
	 * @param ray traced ray
	 * @param scene scene
	 * @param rgb color intensities
	 */
	private static void determineColorFor(RayIntersection intersection, Ray ray, Scene scene, short[] rgb) {
		for (LightSource lightSource : scene.getLights()) {
			RayIntersection i = findClosestIntersection(scene, 
					Ray.fromPoints(lightSource.getPoint(), intersection.getPoint()));
			if (i != null) {
				double distance1 = lightSource.getPoint().sub(intersection.getPoint()).norm();
				double distance2 = lightSource.getPoint().sub(i.getPoint()).norm();
				if (distance1-distance2<=THRESHOLD) {
					Point3D normal = i.getNormal();
					Point3D light = lightSource.getPoint().sub(i.getPoint()).normalize();
					
					Point3D r = normal.normalize()
							.scalarMultiply(2 * light.scalarProduct(normal) / normal.norm())
							.sub(light)
							.normalize();
					Point3D v = ray.start.sub(i.getPoint()).normalize();
					
					
					double diffuseCos = 0;
					double reflectiveCos = 0;
					if(normal.scalarProduct(light) > 0) {
						diffuseCos = normal.scalarProduct(light);
					}
					if(Math.pow(r.scalarProduct(v), i.getKrn()) > 0) {
						reflectiveCos = Math.pow(r.scalarProduct(v), i.getKrn());
					}
					
					rgb[0] += (short)(diffuseCos * lightSource.getR() * i.getKdr());
					rgb[1] += (short)(diffuseCos * lightSource.getG() * i.getKdg());
					rgb[2] += (short)(diffuseCos * lightSource.getB() * i.getKdb());
					
					if (r.scalarProduct(v) >= 0) {
						rgb[0] += (short) (reflectiveCos * lightSource.getR() * i.getKrr());
						rgb[1] += (short) (reflectiveCos * lightSource.getG() * i.getKrg());
						rgb[2] += (short) (reflectiveCos * lightSource.getB() * i.getKrb());
					}
				}
			}
		}
		
	}
	
	/**
	 * Finds a closest intersection between traced ray and objects in scene.
	 * 
	 * @param scene given scene
	 * @param ray traced ray
	 * @return closest intersection in scene for given ray
	 */
	private static RayIntersection findClosestIntersection(Scene scene, Ray ray) {
		RayIntersection closestIntersection = null;
		List<GraphicalObject> sceneObjects = scene.getObjects();
		
		for (GraphicalObject object : sceneObjects) {
			RayIntersection intersection = object.findClosestRayIntersection(ray);
			if (intersection != null && (closestIntersection == null || 
					 intersection.getDistance() < closestIntersection.getDistance())) {	
				closestIntersection = intersection;	
			}	
		}
		
		return closestIntersection;
	}

}
