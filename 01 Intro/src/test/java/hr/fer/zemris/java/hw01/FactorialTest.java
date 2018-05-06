package hr.fer.zemris.java.hw01;

import org.junit.Assert;
import org.junit.Test;

public class FactorialTest {

	@Test
	public void forPositiveNumber() {
		Assert.assertEquals(120, Factorial.calculateFactorial(5));
	}
	
	@Test
	public void forZero() {
		Assert.assertEquals(1, Factorial.calculateFactorial(0));
	}
	
	@Test
	public void forNegativeNumber() {
		boolean exceptionThrown = false;
		
		try {
			Factorial.calculateFactorial(-5);
		} catch(IllegalArgumentException e) {
			exceptionThrown = true;
		}
		
		Assert.assertTrue(exceptionThrown);
	}
	
}
