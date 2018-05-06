package hr.fer.zemris.java.hw05.db;

import org.junit.Test;
import org.junit.Before;
import org.junit.Assert;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class StudentDatabaseTest {
	StudentDatabase studDB;
	@Before
	public void setUp() {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get("./src/main/resources/db.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.out.println("Can't read from given file. Please check if you entered correct path to valid file and then try again.");
			System.exit(1);
		}
		
		String[] dbRows = lines.toArray(new String[0]);
		
		studDB = new StudentDatabase(dbRows);
	}
	
	@Test
	public void forJMBAGTest() {
		StudentRecord actual = studDB.forJMBAG("0000000007");
		Assert.assertEquals(new StudentRecord("0000000007", "Čima",	"Sanjin", Integer.valueOf(4)), actual );
	}
	
	@Test
	public void filterTestFalse() {
		List<StudentRecord> actual = studDB.filter(record -> false);
		Assert.assertEquals(0, actual.size());
	}
	
	@Test
	public void filterTestTrue() {
		List<StudentRecord> actual = studDB.filter(record -> true);
		Assert.assertEquals(63, actual.size());
	}
}
