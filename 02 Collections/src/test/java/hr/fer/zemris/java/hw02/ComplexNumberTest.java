package hr.fer.zemris.java.hw02;

import org.junit.Assert;
import org.junit.Test;

public class ComplexNumberTest {
	
	@Test
	public void equalsTest() {
		ComplexNumber c1 = new ComplexNumber(2.3,3.56);
		ComplexNumber c2 = new ComplexNumber(2.3,3.56);
		Assert.assertTrue(c1.equals(c2));
	}
	@Test
	public void fromRealTest() {
		ComplexNumber c1 = new ComplexNumber(2, 0);
		ComplexNumber c2 = ComplexNumber.fromReal(2);

		Assert.assertTrue(c1.equals(c2));
	}
	
	@Test
	public void fromImaginaryTest() {
		ComplexNumber c1 = new ComplexNumber(0, 2);
		ComplexNumber c2 = ComplexNumber.fromImaginary(2);

		Assert.assertTrue(c1.equals(c2));
	}
	
	@Test
	public void fromMagnitudeAndAngleTest() {
		ComplexNumber c1 = new ComplexNumber(3, -4);
		ComplexNumber c2 = ComplexNumber.fromMagnitudeAndAngle(5, -0.927295);

		Assert.assertTrue(c1.equals(c2));
	}
	
	@Test
	public void parseTest() {
		Assert.assertTrue(ComplexNumber.parse("3.51").equals(new ComplexNumber(3.51, 0)));
		Assert.assertTrue(ComplexNumber.parse("-2.71i").equals(new ComplexNumber(0, -2.71)));
		Assert.assertTrue(ComplexNumber.parse("1 ").equals(new ComplexNumber(1, 0)));
		Assert.assertTrue(ComplexNumber.parse("-2.71- 3.15i").equals(new ComplexNumber(-2.71, -3.15)));	
	}
	
	@Test
	public void getMagnitudeTest() {
		ComplexNumber c1 = new ComplexNumber(3, -4);
		Assert.assertEquals(5, c1.getMagnitude(), 0.00001);
	}
	
	@Test
	public void getAngleTest() {
		ComplexNumber c1 = new ComplexNumber(3, -4);
		Assert.assertEquals(2.21429765, c1.getAngle(), 0.00001);
	}
	
	@Test
	public void addTest() {
		ComplexNumber c1 = new ComplexNumber(2,-3);
		ComplexNumber c2 = new ComplexNumber(4,2);
		ComplexNumber c3 = new ComplexNumber(6,-1);
		
		Assert.assertTrue(c3.equals(c1.add(c2)));		
	}
	
	@Test
	public void subTest() {
		ComplexNumber c1 = new ComplexNumber(2,-3);
		ComplexNumber c2 = new ComplexNumber(4,2);
		ComplexNumber c3 = new ComplexNumber(-2,-5);
		
		Assert.assertTrue(c3.equals(c1.sub(c2)));
	}
	
	@Test
	public void mulTest() {
		ComplexNumber c1 = new ComplexNumber(2,-3);
		ComplexNumber c2 = new ComplexNumber(4,2);
		ComplexNumber c3 = new ComplexNumber(14,-8);
		
		Assert.assertTrue(c3.equals(c1.mul(c2)));
	}
	
	@Test
	public void divTest() {
		ComplexNumber c1 = new ComplexNumber(2,-3);
		ComplexNumber c2 = new ComplexNumber(4,2);
		ComplexNumber c3 = new ComplexNumber(0.1,-0.8);
		
		Assert.assertTrue(c3.equals(c1.div(c2)));
	}
	
	@Test
	public void powerTest() {
		ComplexNumber c1 = new ComplexNumber(2,-3);
		ComplexNumber c2 = new ComplexNumber(-46,-9);
		
		Assert.assertTrue(c2.equals(c1.power(3)));
	}
	
	@Test
	public void rootTest() {
		ComplexNumber c1 = new ComplexNumber(2,-3);
		ComplexNumber c2 = new ComplexNumber(1.674149,-0.895977);
		Assert.assertTrue(c2.equals(c1.root(2)[0]));
	}
	
	@Test
	public void toStringTest() {
		ComplexNumber c1 = new ComplexNumber(2,-3);
		
		Assert.assertEquals("2.0-3.0i", c1.toString());
	}
}
