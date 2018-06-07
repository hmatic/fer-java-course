package hr.fer.zemris.math;

import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("javadoc")
public class Vector3Test {
	@Test
	public void testNorm() {
		Vector3 i = new Vector3(0,5,5);
		Assert.assertEquals(7.0710678118654755, i.norm(), 1e-9);
	}

	@Test
	public void testNormalized() {
		Vector3 i = new Vector3(0,5,5);
		Assert.assertEquals(new Vector3(0.000000, 0.707107, 0.707107), i.normalized());
	}
	
	@Test
	public void testAdd() {
		Vector3 i = new Vector3(1,0,0);
		Vector3 j = new Vector3(0,1,0);
		Vector3 actual = i.add(j);
		Assert.assertEquals(new Vector3(1,1,0), actual);
	}
	
	@Test
	public void testSub() {
		Vector3 i = new Vector3(1,0,0);
		Vector3 j = new Vector3(0,1,0);
		Vector3 actual = i.sub(j);
		Assert.assertEquals(new Vector3(1,-1,0), actual);
	}
	
	@Test
	public void testDot() {
		Vector3 i = new Vector3(0,5,5);
		Vector3 j = new Vector3(0,1,0);
		Assert.assertEquals(5.0, i.dot(j), 1e-9);
	}
	
	@Test
	public void testCross() {
		Vector3 i = new Vector3(1,0,0);
		Vector3 j = new Vector3(0,1,0);
		Vector3 actual = i.cross(j);
		Assert.assertEquals(new Vector3(0,0,1), actual);
	}
	
	@Test
	public void testCosAngle() {
		Vector3 i = new Vector3(1,1,0);
		Vector3 j = new Vector3(0,5,5);
		Assert.assertEquals(0.4999999999999, i.cosAngle(j), 1e-9);
	}
}
