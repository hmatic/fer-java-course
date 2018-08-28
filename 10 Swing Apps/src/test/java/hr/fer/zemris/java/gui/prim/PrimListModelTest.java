package hr.fer.zemris.java.gui.prim;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("javadoc")
public class PrimListModelTest {
	private PrimListModel listModel;
	
	@Before
	public void setUp() {
		listModel = new PrimListModel();
	}
	
	@Test
	public void testGetSize() {
		listModel.next();
		listModel.next();
		listModel.next();
		listModel.next();
		Assert.assertEquals(5, listModel.getSize());
	}
	
	@Test
	public void testGetElementAt() {
		listModel.next();
		listModel.next();
		listModel.next();
		listModel.next();
		
		Assert.assertEquals(1, listModel.getElementAt(0).intValue());
		Assert.assertEquals(2, listModel.getElementAt(1).intValue());
		Assert.assertEquals(3, listModel.getElementAt(2).intValue());
		Assert.assertEquals(5, listModel.getElementAt(3).intValue());
		Assert.assertEquals(7, listModel.getElementAt(4).intValue());
	}
	
	@Test
	public void noElementsAdded() {
		Assert.assertEquals(1, listModel.getElementAt(0).intValue());
		Assert.assertEquals(1, listModel.getSize());
	}
	
	@Test
	public void testIsPrime() {
		Assert.assertTrue(PrimListModel.isPrime(11));
		Assert.assertFalse(PrimListModel.isPrime(4));
	}
	
	@Test(expected=IndexOutOfBoundsException.class) 
	public void invalidIndex() {
		listModel.getElementAt(22);
	}
}
