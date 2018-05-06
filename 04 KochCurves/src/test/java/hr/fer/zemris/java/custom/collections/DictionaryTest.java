package hr.fer.zemris.java.custom.collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DictionaryTest {
	private Dictionary dict;
	
	@Before
	public void setUp() {
		dict = new Dictionary();
		dict.put("kljuc1", "vrijednost1");
		dict.put("kljuc2", "vrijednost2");
		dict.put("kljuc3", "vrijednost3");
	}
		
	@Test
	public void testSize() {
		Assert.assertEquals(3, dict.size());
	}
	
	@Test
	public void testClear() {
		dict.clear();
		Assert.assertEquals(0, dict.size());
	}
	
	@Test
	public void testIsEmpty() {
		Assert.assertFalse(dict.isEmpty());
		dict.clear();
		Assert.assertTrue(dict.isEmpty());
	}
	
	@Test
	public void testGet() {
		Assert.assertEquals("vrijednost2", dict.get("kljuc2"));
	}
	
	@Test
	public void testPutExisting() {
		dict.put("kljuc2", "nova vrijednost");
		Assert.assertEquals("nova vrijednost", dict.get("kljuc2"));
	}
	
	@Test
	public void testPutNew() {
		dict.put("kljuc4", "vrijednost4");
		Assert.assertEquals(4, dict.size());
	}
	
	@Test(expected = NullPointerException.class) 
	public void testPutNull() {
		dict.put(null, "vrijednost");
	}
}
