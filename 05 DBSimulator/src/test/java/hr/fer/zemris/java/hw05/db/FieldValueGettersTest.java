package hr.fer.zemris.java.hw05.db;

import org.junit.Test;
import org.junit.Before;
import org.junit.Assert;

public class FieldValueGettersTest {
	StudentRecord record;
	
	@Before
	public void setUp() {
		record = new StudentRecord("0036487400", "Matić", "Hrvoje", Integer.valueOf(5));
	}

	@Test
	public void firstNameGetterTest() {
		String actual = FieldValueGetters.FIRST_NAME.get(record);
		String expected = "Hrvoje";
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void lastNameGetterTest() {
		String actual = FieldValueGetters.LAST_NAME.get(record);
		String expected = "Matić";
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void jmbagGetterTest() {
		String actual = FieldValueGetters.JMBAG.get(record);
		String expected = "0036487400";
		Assert.assertEquals(expected, actual);
	}
	

}
