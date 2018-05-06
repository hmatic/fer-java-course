package hr.fer.zemris.java.custom.collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ArrayIndexedCollectionTest {
	ArrayIndexedCollection col;
	ArrayIndexedCollection col2;
	
	@Before
	public void setUp() {
		col = new ArrayIndexedCollection();
		col2 = new ArrayIndexedCollection();
		col.add("Zagreb");
		col.add("Rijeka");
		col.add(4);
	}
	
	@Test
	public void addTest() {
		Assert.assertEquals(3, col.size());
		col.add("Zagreb");
		Assert.assertEquals(4, col.size());
	}
	
	@Test
	public void getTest() {
		Assert.assertEquals("Zagreb", col.get(0));
		Assert.assertEquals(4, col.get(2));
	}
	
	@Test
	public void insertTest() {
		col.insert("Osijek", 1);
		Assert.assertEquals("Osijek", col.get(1));
		Assert.assertEquals("Rijeka", col.get(2));
	}
	
	@Test
	public void indexOfTest() {
		Assert.assertEquals(1, col.indexOf("Rijeka"));
	}
	
	@Test
	public void removeTest() {
		col.remove(0);
		Assert.assertEquals("Rijeka", col.get(0));
	}
	
	@Test
	public void sizeTest() {
		Assert.assertEquals(3, col.size());
	}
	
	@Test
	public void forEachTest() {
		class P extends Processor {
			 public void process(Object value) {
			 	col2.add(value);
			 }
		};
		
		col.forEach(new P());
		
		Assert.assertEquals("Zagreb", col2.get(0));
		Assert.assertEquals(3, col2.size());
	}
	
	@Test
	public void toArrayTest() {
		Object[] expected = {"Zagreb", "Rijeka", 4};
		Assert.assertArrayEquals(expected, col.toArray());
	}
	
	@Test
	public void containsTest() {
		Assert.assertTrue(col.contains("Zagreb"));
		Assert.assertFalse(col.contains("Osijek"));
	}

	@Test
	public void clearTest() {
		col.clear();
		Assert.assertEquals(0, col.size());
	}
	
}
