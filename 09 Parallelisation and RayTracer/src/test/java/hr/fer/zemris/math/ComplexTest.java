package hr.fer.zemris.math;

import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("javadoc")
public class ComplexTest {
	@Test
	public void addTest() {
		Complex c1 = new Complex(2,-3);
		Complex c2 = new Complex(4,2);
		Complex c3 = new Complex(6,-1);
		
		Assert.assertTrue(c3.equals(c1.add(c2)));		
	}
	
	@Test
	public void subTest() {
		Complex c1 = new Complex(2,-3);
		Complex c2 = new Complex(4,2);
		Complex c3 = new Complex(-2,-5);
		
		Assert.assertTrue(c3.equals(c1.sub(c2)));
	}
	
	@Test
	public void mulTest() {
		Complex c1 = new Complex(2,-3);
		Complex c2 = new Complex(4,2);
		Complex c3 = new Complex(14,-8);
		
		Assert.assertTrue(c3.equals(c1.multiply(c2)));
	}
	
	@Test
	public void divTest() {
		Complex c1 = new Complex(2,-3);
		Complex c2 = new Complex(4,2);
		Complex c3 = new Complex(0.1,-0.8);
		
		Assert.assertTrue(c3.equals(c1.divide(c2)));
	}
	
	@Test
	public void powerTest() {
		Complex c1 = new Complex(2,-3);
		Complex c2 = new Complex(-46,-9);
		Assert.assertTrue(c2.equals(c1.power(3)));
		Assert.assertEquals(c2, c1.power(3));
		
	}
	
	@Test
	public void rootTest() {
		Complex c1 = new Complex(2,-3);
		Complex c2 = new Complex(1.674149,-0.895977);
		Assert.assertTrue(c2.equals(c1.root(2).get(0)));
	}
}
