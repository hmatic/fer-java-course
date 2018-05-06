package hr.fer.zemris.java.custom.scripting.exec;

import org.junit.Test;

import java.util.EmptyStackException;

import org.junit.Assert;
import org.junit.Before;

public class ObjectMultistackTest {
	ObjectMultistack multistack;
	ValueWrapper year;
	ValueWrapper price;
	
	@Before
	public void setUp() {
		multistack = new ObjectMultistack();
		year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		price = new ValueWrapper(200.51);
		multistack.push("price", price);
	}
	
	@Test
	public void testPeek() {
		Assert.assertEquals(Integer.valueOf(2000), multistack.peek("year").getValue());
	}
	
	@Test
	public void testIsEmpty() {
		Assert.assertTrue(multistack.isEmpty("empty"));
	}
	@Test
	public void testPop() {
		Assert.assertEquals(Integer.valueOf(2000), multistack.pop("year").getValue());
		Assert.assertTrue(multistack.isEmpty("year"));
	}
	
	@Test(expected=EmptyStackException.class)
	public void peekOnEmptyStack() {
		multistack.peek("empty");
	}
	
	@Test(expected=EmptyStackException.class)
	public void popOnEmptyStack() {
		multistack.pop("empty");
	}
	
	@Test(expected=NullPointerException.class)
	public void popOnNull() {
		multistack.pop(null);
	}
	
	@Test
	public void testMultiplePushAndPop() {
		multistack.push("price", new ValueWrapper(20));
		multistack.push("price", new ValueWrapper("cijena"));
		multistack.push("price", new ValueWrapper(12.32));
		Assert.assertEquals(Double.valueOf(12.32), multistack.pop("price").getValue());
		Assert.assertEquals("cijena", multistack.pop("price").getValue());
		Assert.assertEquals(Integer.valueOf(20), multistack.pop("price").getValue());
		Assert.assertEquals(Double.valueOf(200.51), multistack.pop("price").getValue());
		Assert.assertTrue(multistack.isEmpty("price"));
	}
}
