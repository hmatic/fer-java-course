package hr.fer.zemris.java.hw03;

import hr.fer.zemris.java.custom.scripting.nodes.*;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Test program for {@link SmartScriptParser}.
 * It accepts 1 argument which is path to text file that needs to be parsed. 
 * After parsing it makes text from tree of Nodes and prints it.
 * 
 * @author Hrvoje Matic
 *
 */
public class SmartScriptTester {

	/**
	 * Method called upon start of program. Arguments explained below.
	 * 
	 * @param args path to the document
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Invalid number of arguments");
			System.exit(1);
		}

		String filepath = args[0];
		String docBody = null;
		try {
			docBody = new String(Files.readAllBytes(Paths.get(filepath)),
					StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}

		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document! \n" + e.getMessage());
			System.exit(-1);
		}  catch(Exception e) {
			 System.out.println("If this line ever executes, you have failed this class!");
			 System.exit(-1);
		}

		DocumentNode document = parser.getDocumentNode();
		String originalBodyDocument = createOriginalDocumentBody(document);
		System.out.println(originalBodyDocument);
	}

	/**
	 * Method builds text from tree of Nodes created by SmartScriptParser.
	 * 
	 * @param document DocumentNode
	 * @return String representing {@link DocumentNode} text.
	 */
	public static String createOriginalDocumentBody(Node document) {
		if (document == null) {
			throw new NullPointerException("Null argument is not valid.");
		}

		int numberOfChildren = document.numberOfChildren();
		StringBuilder sb = new StringBuilder();
		sb.append(document.asText());
		
		if (numberOfChildren != 0) {
			for (int i = 0; i < numberOfChildren; i++) {
				sb.append(createOriginalDocumentBody(document.getChild(i)));
			}
		}
		
		if (document instanceof ForLoopNode) {
			sb.append("{$END$}");
		}
		
		return sb.toString();
	}

}