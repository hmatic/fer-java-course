package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Demonstration program for SmartScript example: fibonacci.smscr
 * @author Hrvoje Matic
 */
public class SmartScriptEngineDemo4 {
	/**
	 * Program entry point.
	 * @param args list of arguments
	 */
	public static void main(String[] args) {
		String documentBody = null;
		try {
			documentBody = new String(Files.readAllBytes(
					Paths.get("webroot/scripts/fibonacci.smscr")),
					StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Map<String,String> parameters = new HashMap<String, String>();
		Map<String,String> persistentParameters = new HashMap<String, String>();
		ArrayList<RequestContext.RCCookie> cookies = new ArrayList<>();
		// create engine and execute it
		new SmartScriptEngine(
		new SmartScriptParser(documentBody).getDocumentNode(),
		new RequestContext(System.out, parameters, persistentParameters, cookies, new HashMap<>(), null)
		).execute();

	}
	
}
