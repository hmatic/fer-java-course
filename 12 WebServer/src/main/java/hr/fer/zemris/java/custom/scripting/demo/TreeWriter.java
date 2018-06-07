package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.custom.scripting.nodes.*;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Test program for {@link SmartScriptParser}.
 * It accepts 1 argument which is path to text file that needs to be parsed. 
 * After parsing it makes text from tree of Nodes and prints it.
 * 
 * @author Hrvoje Matic
 *
 */
public class TreeWriter {

	/**
	 * Program entry point. Arguments explained below.
	 * @param args path to the document
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Invalid number of arguments");
			System.exit(1);
		}

		
		Path filePath = Paths.get(args[0]);
		if(!filePath.getFileName().toString().endsWith(".smscr")) {
			System.out.println("Given file must be smartscript (.smscr).");
			System.exit(-1);
		}
		String docBody = null;
		try {
			docBody = new String(Files.readAllBytes(filePath),
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
		}  

		DocumentNode document = parser.getDocumentNode();
		WriterVisitor visitor = new WriterVisitor();
		document.accept(visitor);
	}

	/**
	 * Visitor that prints nodes back to their SmartScript syntax.
	 * @author Hrvoje Matic
	 *
	 */
	private static class WriterVisitor implements INodeVisitor {

		@Override
		public void visitTextNode(TextNode node) {
			System.out.print(node.toString());
			
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			System.out.print(node.toString());
			for(int i=0, max=node.numberOfChildren(); i<max; i++) {
				node.getChild(i).accept(this);
			}
			System.out.print("{$END$}");
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			System.out.print(node.toString());
			
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for(int i=0, max=node.numberOfChildren(); i<max; i++) {
				node.getChild(i).accept(this);
			}
			
		}
		
	}

}