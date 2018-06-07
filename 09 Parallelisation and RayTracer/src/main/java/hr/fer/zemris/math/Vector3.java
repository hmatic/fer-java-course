package hr.fer.zemris.math;

/**
 * Models vector in 3D space.
 * 
 * @author Hrvoje Matić
 */
public class Vector3 {
	/**
	 * X component of vector.
	 */
	private double x;
	/**
	 * Y component of vector.
	 */
	private double y;
	/**
	 * Z component of vector.
	 */
	private double z;
	/**
	 * Threshold for comparing vector components.
	 */
	private static double THRESHOLD = 0.000001;
	
	/**
	 * Default constructor for Vector3.
	 * @param x x component
	 * @param y y component
	 * @param z z component
	 */
	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Getter for X component
	 * @return x component
	 */
	public double getX() {
		return x;
	}

	/**
	 * Getter for Y component
	 * @return y component
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Getter for Z component
	 * @return z component
	 */
	public double getZ() {
		return z;
	}

	/**
	 * Returns length of vector.
	 * @return length of vector.
	 */
	public double norm() {
		return Math.sqrt(dot(this));
	}
	
	/**
	 * Returns new vector with same direction as current one but with length of 1.
	 * @return normalized vector
	 */
	public Vector3 normalized() {
		return new Vector3(x/norm(), y/norm(), z/norm());
	} 
	
	/**
	 * Adds current vector to other one.
	 * Returns new vector without changing current vectors.
	 * 
	 * @param other other vector
	 * @return resulting vector
	 */
	public Vector3 add(Vector3 other) {
		return new Vector3(this.x+other.getX(), this.y+other.getY(), this.z+other.getZ());
	}
	
	/**
	 * Subtracts other vector from current one.
	 * Returns new vector without changing current vectors.
	 * 
	 * @param other other vector
	 * @return resulting vector
	 */
	public Vector3 sub(Vector3 other) {
		return new Vector3(this.x-other.getX(), this.y-other.getY(), this.z-other.getZ());
	} 
	
	/**
	 * Scalar product of current vector with another vector
	 * @param other other vector
	 * @return scalar product
	 */
	public double dot(Vector3 other) {
		return (this.x * other.getX()) + (this.y * other.getY()) + (this.z * other.getZ());	
	} 
	
	/**
	 * Cross product of current vector with another vector.
	 * @param other other vector
	 * @return cross product
	 */
	public Vector3 cross(Vector3 other) {
		return new Vector3((this.y*other.getZ())-other.getY()*this.z,
				(other.getX()*this.z)-this.x*other.getZ(),
				(this.x*other.getY())-other.getX()*this.y);
	} 
	
	/**
	 * Scale current vector with scaler given in argument, but return it as new vector.
	 * @param s scale coefficient
	 * @return new vector scaled for coefficient
	 */
	public Vector3 scale(double s) {
		return new Vector3(x*s, y*s, z*s);
	} 
	
	/**
	 * Cosinus of angle between two vectors.
	 * 
	 * @param other other vector
	 * @return cosinus of angle between current vector and other vector
	 */
	public double cosAngle(Vector3 other) {
		return dot(other)/(norm()*other.norm());
	} 
	
	/**
	 * Vector converted to array of 3 variables.
	 * @return vector as array
	 */
	public double[] toArray() {		
		return new double[] {x, y, z};
	}
	
	@Override
	public String toString() {
		return String.format("(%8f, %8f, %8f)", x, y, z);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(z);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object arg) {
		if(!(arg instanceof Vector3)) {
			return false;
		}
		
		Vector3 other = (Vector3)arg;
		
		if(Math.abs(other.x-x)<THRESHOLD && 
				Math.abs(other.y-this.y)<THRESHOLD && 
				Math.abs(other.y-this.y)<THRESHOLD) {
			return true;
		} else {
			return false;
		}
	}
	
	
	
	
}
