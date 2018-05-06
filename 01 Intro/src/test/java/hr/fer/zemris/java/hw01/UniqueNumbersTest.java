package hr.fer.zemris.java.hw01;

import org.junit.Test;
import org.junit.Before;
import org.junit.Assert;

public class UniqueNumbersTest {
	UniqueNumbers.TreeNode root = null;
	
	@Before
	public void setUp() {
		root = UniqueNumbers.addNode(root, 42);
		root = UniqueNumbers.addNode(root, 76);
		root = UniqueNumbers.addNode(root, 31);
		root = UniqueNumbers.addNode(root, 35);		
	}
	
	@Test
	public void forAddNode() {		
		Assert.assertEquals(42, root.value);
		Assert.assertEquals(31, root.left.value);
		Assert.assertEquals(35, root.left.right.value);
		Assert.assertEquals(76, root.right.value);
	}
	
	@Test
	public void forAddNodeExistingNumber() {
		root = UniqueNumbers.addNode(root, 31);
		Assert.assertEquals(4, UniqueNumbers.treeSize(root));
	}
	
	@Test
	public void forTreeSize() {
		Assert.assertEquals(4, UniqueNumbers.treeSize(root));
		root = UniqueNumbers.addNode(root, 21);
		Assert.assertEquals(5, UniqueNumbers.treeSize(root));
	}
	
	@Test
	public void forContainsValueForExistingNumber() {
		Assert.assertTrue(UniqueNumbers.containsValue(root, 35));
	}
	
	@Test
	public void forContainsValueForMissingNumber() {
		Assert.assertFalse(UniqueNumbers.containsValue(root, 5));
	}
}
