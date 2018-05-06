package hr.fer.zemris.java.hw05.db;

import org.junit.Test;
import org.junit.Before;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

public class QueryFilterTest {
	List<ConditionalExpression> expressions;
	
	@Before
	public void setUp() {
		expressions = new ArrayList<>();
		expressions.add(new ConditionalExpression(FieldValueGetters.FIRST_NAME, "Marin", ComparisonOperators.EQUALS));
	}
	
	@Test
	public void queryFilterTest() {
		QueryFilter filter = new QueryFilter(expressions);
		StudentRecord record = new StudentRecord("0000000004", "Božić",	"Marin", Integer.valueOf(5));
		Assert.assertTrue(filter.accepts(record));
		StudentRecord record2 = new StudentRecord("0000000005", "Božić",	"Marina", Integer.valueOf(5));
		Assert.assertFalse(filter.accepts(record2));
		expressions.add(new ConditionalExpression(FieldValueGetters.JMBAG, "43422332", ComparisonOperators.EQUALS));
		Assert.assertFalse(filter.accepts(record));
	}
}
