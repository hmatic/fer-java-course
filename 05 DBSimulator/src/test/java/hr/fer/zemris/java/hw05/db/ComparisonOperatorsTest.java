package hr.fer.zemris.java.hw05.db;

import org.junit.Test;
import org.junit.Assert;

public class ComparisonOperatorsTest {
	@Test
	public void lessOperatorTest() {
		IComparisonOperator oper = ComparisonOperators.LESS;
		Assert.assertTrue(oper.satisfied("Ana", "Jasna"));
		Assert.assertFalse(oper.satisfied("Ana", "Ana"));
	}
	
	@Test
	public void greaterOperatorTest() {
		IComparisonOperator oper = ComparisonOperators.GREATER;
		Assert.assertTrue(oper.satisfied("Jasna", "Ana"));
		Assert.assertFalse(oper.satisfied("Jasna", "Jasna"));
	}
	
	@Test
	public void equalsOperatorTest() {
		IComparisonOperator oper = ComparisonOperators.EQUALS;
		Assert.assertTrue(oper.satisfied("Ana", "Ana"));
		Assert.assertFalse(oper.satisfied("Jasna", "Ana"));
	}
	
	@Test
	public void notEqualsOperatorTest() {
		IComparisonOperator oper = ComparisonOperators.NOT_EQUALS;
		Assert.assertTrue(oper.satisfied("Ana", "Jasna"));
		Assert.assertFalse(oper.satisfied("Ana", "Ana"));
	}
	
	@Test
	public void greaterOrEqualsOperatorTest() {
		IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;
		Assert.assertTrue(oper.satisfied("Jasna", "Ana"));
		Assert.assertTrue(oper.satisfied("Jasna", "Jasna"));
		Assert.assertFalse(oper.satisfied("Ana", "Jasna"));
	}
	
	@Test
	public void lessOrEqualsOperatorTest() {
		IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUALS;
		Assert.assertTrue(oper.satisfied("Ana", "Jasna"));
		Assert.assertTrue(oper.satisfied("Ana", "Ana"));
		Assert.assertFalse(oper.satisfied("Jasna", "Ana"));
	}
	
	@Test
	public void likeOperatorTest() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		Assert.assertFalse(oper.satisfied("Zagreb", "Aba*"));
		Assert.assertFalse(oper.satisfied("AAA", "AA*AA"));
		Assert.assertTrue(oper.satisfied("AAAA", "AA*AA"));
	}
	

}
