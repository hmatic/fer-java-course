package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
/**
 * Database simulation program.<br>
 * IMPORTANT: In order to work, program must recieve single argument which is path to database text file.<br>
 * Please use "./src/main/resources/db.txt" as only argument. <br><br>
 * How to use: <br>
 * Program accepts queries and returns list of student record which satisfy given query.<br>
 * Each query must start with keyword "query".<br>
 * After that you must enter one or more conditional expressions separated by keyword AND.<br>
 * Valid form of conditional expression is: name of attribute, followed by operator, followed by string literal. <br>
 * Valid attributes are: jmbag, firstName, lastName.<br>
 * Valid operators are: <, >, =, !=, <=, >=, LIKE.<br>
 * Operator LIKE uses wildcard(*) on start, middle or end of string literal to match all characters, works same as regex.<br>
 * Valid string literals start with quotation and end with quotation marks.<br>
 * You can end program with keyword "exit".<br><br>
 * 
 * Examples of usage: <br>
 * query jmbag = "0000000003"
 * query jmbag = "0000000003" AND lastName LIKE "B*"<br>
 * query jmbag = "0000000003" AND lastName LIKE "L*"<br>
 * query lastName LIKE "B*"<br>
 * query lastName LIKE "Be*"<br>

 * @author Hrvoje Matić
 * @version 1.0
 */
public class StudentDB {
	/**
	 * Entry point to program. First argument must be path to database file.
	 * @param args array of arguments
	 */
	public static void main(String[] args) {
		if(args.length!=1) {
			System.out.println("Program must have a single argument which is the path to the database.");
			System.exit(1);
		}
		
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get(args[0]), StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.out.println("Can't read from given file. Please check if you entered correct path to valid file and then try again.");
			System.exit(1);
		}
		
		String[] dbRows = lines.toArray(new String[0]);
		
		StudentDatabase studDB = new StudentDatabase(dbRows);
		
		Scanner sc = new Scanner(System.in);
		String line = "";
		
		while(true) {
			System.out.print("> ");
			
			line = sc.nextLine().trim();
			if(line.toLowerCase().equals("exit")) break;
			
			String[] query = line.split("\\s+", 2);
			if(!(query[0].toLowerCase().equals("query"))) {
				System.out.println("Invalid query, query must start with keyword \"query\", please try again.");
				continue;
			}
			
			QueryParser parser = new QueryParser(query[1]);
			TableBuilder tb = new TableBuilder();
			int size = 0;
			
			if(parser.isDirectQuery()) {
				StudentRecord record = studDB.forJMBAG(parser.getQueriedJMBAG());
				System.out.println("Using index for record retrieval.");
				if(record!=null) {
					tb.addRow(record.getJmbag(), record.getLastName(), record.getFirstName(), String.valueOf(record.getFinalGrade()));
					size=1;
				}
			} else {
				QueryFilter filter;
				List<StudentRecord> filteredStudentList;
				try {
					filter = new QueryFilter(parser.getQuery());
					filteredStudentList = studDB.filter(filter);
				} catch(QueryParserException | QueryLexerException | IllegalArgumentException e) {
					System.out.println(e.getMessage() + " Please try with another query.");
					continue;
				}
				for(StudentRecord record : filteredStudentList) {
					tb.addRow(record.getJmbag(), record.getLastName(), record.getFirstName(), String.valueOf(record.getFinalGrade()));
					size++;
				}
			}
			
			if(size!=0) {
				System.out.println(tb.toString());
			}
			System.out.println("Records selected: " + size);
		} 	
		
		sc.close();
		System.out.println("Goodbye!");
	}
}
