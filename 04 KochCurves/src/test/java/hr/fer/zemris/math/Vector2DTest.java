package hr.fer.zemris.math;

import org.junit.Assert;
import org.junit.Test;

public class Vector2DTest {
	@Test
	public void testEqualsTrue() {
		Vector2D vector1 = new Vector2D(1, 1);
		Vector2D vector2 = new Vector2D(1, 1);
		
		Assert.assertTrue(vector1.equals(vector2));
	}
	
	@Test
	public void testEqualsFalse() {
		Vector2D vector1 = new Vector2D(1, 1);
		Vector2D vector2 = new Vector2D(3, 1);
		
		Assert.assertFalse(vector1.equals(vector2));
	}
	
	@Test
	public void testTranslate() {
		Vector2D vector = new Vector2D(2,2);
		Vector2D offset = new Vector2D(1,0);
		vector.translate(offset);
		
		Assert.assertEquals(new Vector2D(3,2), vector);		
	}
	
	@Test
	public void testTranslated() {
		Vector2D vector = new Vector2D(2,2);
		Vector2D offset = new Vector2D(1,1);
		Vector2D newVector = vector.translated(offset);
		
		Assert.assertEquals(new Vector2D(3, 3), newVector);
	}
	
	@Test
	public void testRotate() {
		Vector2D vector = new Vector2D(1,0);
		vector.rotate(60);
		
		Assert.assertEquals(new Vector2D(0.5, 0.8660254), vector);
		vector.rotate(-60);
		Assert.assertEquals(new Vector2D(1, 0), vector);
	}
	
	@Test
	public void testRotated() {
		Vector2D vector = new Vector2D(1,0);
		Vector2D newVector = vector.rotated(60);
		
		Assert.assertEquals(new Vector2D(0.5, 0.8660254), newVector);
		Vector2D newVectorAgain = newVector.rotated(-60);
		Assert.assertEquals(new Vector2D(1, 0), newVectorAgain);
	}
	
	@Test
	public void testScale() {
		Vector2D vector = new Vector2D(2,3);
		vector.scale(3);
		
		Assert.assertEquals(new Vector2D(6, 9), vector);
	}
	
	@Test
	public void testScaled() {
		Vector2D vector = new Vector2D(2,3);
		Vector2D newVector = vector.scaled(3);
		
		Assert.assertEquals(new Vector2D(6, 9), newVector);
	}
	
	@Test
	public void testCopy() {
		Vector2D vector1 = new Vector2D(1, 1);
		Vector2D vector2 = vector1.copy();
		
		Assert.assertTrue(vector1.equals(vector2));
	}
	
	
	
	
}
