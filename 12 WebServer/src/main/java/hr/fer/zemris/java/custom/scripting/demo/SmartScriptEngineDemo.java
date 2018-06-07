package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Demonstration program for SmartScriptEngine.
 * Takes one argument which is path to .smscr file and writes execution results to console.
 * @author Hrvoje Matic
 */
public class SmartScriptEngineDemo {
	/**
	 * Program entry point.
	 * @param args list of arguments
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
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		ArrayList<RCCookie> cookies = new ArrayList<>();
		
		parameters.put("a", "4");
		parameters.put("b", "2");
		persistentParameters.put("brojPoziva", "3");
		RequestContext rc = new RequestContext(System.out,
				parameters,
				persistentParameters,
				cookies);

		// create engine and execute it
		new SmartScriptEngine(new SmartScriptParser(docBody).getDocumentNode(),
				rc).execute();
		System.out.println("Vrijednost u mapi: "+rc.getPersistentParameter("brojPoziva"));
	}
	
}
