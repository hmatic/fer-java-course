package hr.fer.zemris.java.custom.scripting.exec;

import org.junit.Test;
import org.junit.Assert;

public class ValueWrapperTest {
	
	@Test
	public void testAddNulls() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getValue());
		
		Assert.assertEquals(Integer.valueOf(0), v1.getValue());
		Assert.assertEquals(null, v2.getValue());
	}
	
	@Test
	public void testSubtractNulls() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.subtract(v2.getValue()); 
		
		Assert.assertEquals(Integer.valueOf(0), v1.getValue());
		Assert.assertEquals(null, v2.getValue());
	}
	
	@Test
	public void testMultiplyNulls() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.multiply(v2.getValue());
		
		Assert.assertEquals(Integer.valueOf(0), v1.getValue());
		Assert.assertEquals(null, v2.getValue());
	}
	
	@Test
	public void testDivideNulls() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.divide(v2.getValue());
		
		Assert.assertEquals(Integer.valueOf(0), v1.getValue());
		Assert.assertEquals(null, v2.getValue());
	}
	
	@Test
	public void testArithmeticForTwoIntegers() {
		ValueWrapper v1 = new ValueWrapper(Integer.valueOf(5));
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(2));
		
		v1.add(v2.getValue());
		Assert.assertEquals(Integer.valueOf(7), v1.getValue());
		Assert.assertEquals(Integer.valueOf(2), v2.getValue());
		
		v1.subtract(v2.getValue());
		Assert.assertEquals(Integer.valueOf(5), v1.getValue());
		Assert.assertEquals(Integer.valueOf(2), v2.getValue());
		
		v1.divide(v2.getValue());
		Assert.assertEquals(Integer.valueOf(2), v1.getValue());
		Assert.assertEquals(Integer.valueOf(2), v2.getValue());
		
		v1.multiply(v2.getValue());
		Assert.assertEquals(Integer.valueOf(4), v1.getValue());
		Assert.assertEquals(Integer.valueOf(2), v2.getValue());
	}
	
	@Test
	public void testArithmeticForTwoDoubles() {
		ValueWrapper v1 = new ValueWrapper(Double.valueOf(5.0));
		ValueWrapper v2 = new ValueWrapper(Double.valueOf(2.0));
		
		v1.add(v2.getValue());
		Assert.assertEquals(Double.valueOf(7.0), v1.getValue());
		Assert.assertEquals(Double.valueOf(2.0), v2.getValue());
		
		v1.subtract(v2.getValue());
		Assert.assertEquals(Double.valueOf(5.0), v1.getValue());
		Assert.assertEquals(Double.valueOf(2.0), v2.getValue());
		
		v1.divide(v2.getValue());
		Assert.assertEquals(Double.valueOf(2.5), v1.getValue());
		Assert.assertEquals(Double.valueOf(2.0), v2.getValue());
		
		v1.multiply(v2.getValue());
		Assert.assertEquals(Double.valueOf(5.0), v1.getValue());
		Assert.assertEquals(Double.valueOf(2.0), v2.getValue());
	}
	
	@Test
	public void testArithmeticForIntegerAndDouble() {
		ValueWrapper v1 = new ValueWrapper(Integer.valueOf(5));
		ValueWrapper v2 = new ValueWrapper(Double.valueOf(2.0));
		
		v1.add(v2.getValue());
		Assert.assertEquals(Double.valueOf(7.0), v1.getValue());
		Assert.assertEquals(Double.valueOf(2.0), v2.getValue());
		
		v1.subtract(v2.getValue());
		Assert.assertEquals(Double.valueOf(5.0), v1.getValue());
		Assert.assertEquals(Double.valueOf(2.0), v2.getValue());
		
		v1.divide(v2.getValue());
		Assert.assertEquals(Double.valueOf(2.5), v1.getValue());
		Assert.assertEquals(Double.valueOf(2.0), v2.getValue());
		
		v1.multiply(v2.getValue());
		Assert.assertEquals(Double.valueOf(5.0), v1.getValue());
		Assert.assertEquals(Double.valueOf(2.0), v2.getValue());
	}
	
	@Test
	public void testArithmeticForDoubleAndInteger() {
		ValueWrapper v1 = new ValueWrapper(Double.valueOf(5.0));
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(2));
		
		v1.add(v2.getValue());
		Assert.assertEquals(Double.valueOf(7.0), v1.getValue());
		Assert.assertEquals(Integer.valueOf(2), v2.getValue());
		
		v1.subtract(v2.getValue());
		Assert.assertEquals(Double.valueOf(5.0), v1.getValue());
		Assert.assertEquals(Integer.valueOf(2), v2.getValue());
		
		v1.divide(v2.getValue());
		Assert.assertEquals(Double.valueOf(2.5), v1.getValue());
		Assert.assertEquals(Integer.valueOf(2), v2.getValue());
		
		v1.multiply(v2.getValue());
		Assert.assertEquals(Double.valueOf(5.0), v1.getValue());
		Assert.assertEquals(Integer.valueOf(2), v2.getValue());
	}
	
	@Test(expected=RuntimeException.class)
	public void testForInvalidString() {
		ValueWrapper v1 = new ValueWrapper("Ankica");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		v1.add(v2.getValue()); 
	}
	
	@Test
	public void testForStringScientific() {
		ValueWrapper v1 = new ValueWrapper("1.2E1");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		v1.add(v2.getValue()); 
		
		Assert.assertEquals(Double.valueOf(13.0), v1.getValue());
	}
	
	@Test
	public void testForStringInteger() {
		ValueWrapper v1 = new ValueWrapper("12");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		v1.add(v2.getValue());
		
		Assert.assertEquals(Integer.valueOf(13), v1.getValue());
	}
	
	@Test
	public void testForStringDouble() {
		ValueWrapper v1 = new ValueWrapper("12.2312");
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(1));
		v1.add(v2.getValue());
		
		Assert.assertEquals(Double.valueOf(13.2312), v1.getValue());
	}
	
	@Test
	public void testDetermineObjectType() {
		Assert.assertTrue(ValueWrapper.determineObjectType("12.0e2").getClass()==Double.class);
		Assert.assertTrue(ValueWrapper.determineObjectType(12.08).getClass()==Double.class);
		Assert.assertTrue(ValueWrapper.determineObjectType("12").getClass()==Integer.class);
		Assert.assertTrue(ValueWrapper.determineObjectType(12).getClass()==Integer.class);
		Assert.assertTrue(ValueWrapper.determineObjectType("12.03242").getClass()==Double.class);
		Assert.assertTrue(ValueWrapper.determineObjectType(null).getClass()==Integer.class);
	}
	
	@Test
	public void testNumCompareNulls() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		Assert.assertEquals(0, v1.numCompare(v2.getValue()));
	}
	
	@Test
	public void testNumCompareNullWithInteger() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(5);
		
		Assert.assertEquals(-1, v1.numCompare(v2.getValue()));
		Assert.assertEquals(1, v2.numCompare(v1.getValue()));
	}
	
	@Test
	public void testNumCompareTwoDoubles() {
		ValueWrapper v1 = new ValueWrapper(13.544);
		ValueWrapper v2 = new ValueWrapper("6.343");
		
		Assert.assertEquals(1, v1.numCompare(v2.getValue()));
		Assert.assertEquals(-1, v2.numCompare(v1.getValue()));
	}
	
}
