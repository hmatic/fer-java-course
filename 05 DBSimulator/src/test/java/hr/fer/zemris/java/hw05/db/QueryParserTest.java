package hr.fer.zemris.java.hw05.db;

import org.junit.Test;
import org.junit.Before;
import org.junit.Assert;

public class QueryParserTest {
	QueryParser qp1;
	QueryParser qp2;
	@Before
	public void setUp() {
		qp1 = new QueryParser(" jmbag =\"0123456789\" ");
		qp2 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
	}
	@Test
	public void isDirectQueryTest() {
		Assert.assertTrue(qp1.isDirectQuery());
		Assert.assertFalse(qp2.isDirectQuery());
	}
	
	@Test
	public void getQueriedJMBAGTest() {
		String actual = qp1.getQueriedJMBAG();
		String expected = "0123456789";
		Assert.assertEquals(expected, actual);
	}
	
	@Test(expected=IllegalStateException.class)
	public void getQueriedJMBAGTestForException() {
		qp2.getQueriedJMBAG();
	}
	
	@Test
	public void getQueryTest() {
		Assert.assertEquals(1,  qp1.getQuery().size());
		Assert.assertEquals(2,  qp2.getQuery().size());
	}
}
