package hr.fer.zemris.java.hw01;

import java.text.NumberFormat;

import org.junit.Assert;
import org.junit.Test;

public class RectangleTest {

	@Test
	public void forAreaCalculation() {
		Assert.assertEquals(16.0, Rectangle.calculateRectangleArea(2, 8), 0.00001);
		Assert.assertEquals(13.2038, Rectangle.calculateRectangleArea(2.14, 6.17), 0.00001);
	}
	
	@Test
	public void forPerimeterCalculation() {
		Assert.assertEquals(20.0, Rectangle.calculateRectanglePerimeter(2, 8), 0.00001);
		Assert.assertEquals(16.62, Rectangle.calculateRectanglePerimeter(2.14, 6.17), 0.00001);
	}
	
	@Test
	public void forIsPositiveDouble() {
		NumberFormat numberFormat = NumberFormat.getIntegerInstance();
		
		Assert.assertTrue(Validation.isPositiveDouble("4.34", numberFormat));
		Assert.assertTrue(Validation.isPositiveDouble("3,242", numberFormat));
		Assert.assertTrue(Validation.isPositiveDouble("342,323.242", numberFormat));
		Assert.assertFalse(Validation.isPositiveDouble("štefica", numberFormat));
	}
	
}
