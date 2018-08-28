package hr.fer.zemris.java.hw16.trazilica.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import hr.fer.zemris.java.hw16.trazilica.ConsoleIOException;
import hr.fer.zemris.java.hw16.trazilica.ConsoleStatus;
import hr.fer.zemris.java.hw16.trazilica.environment.Environment;
import hr.fer.zemris.java.hw16.trazilica.environment.SearchResult;

/**
 * Command which prints contents of document in search results.
 * Takes one argument which is index of document in search results.
 * @author Hrvoje Matic
 * @version 1.0
 */
public class TypeCommand implements ConsoleCommand {
	
	@Override
	public ConsoleStatus execute(Environment env, String arguments) {
		int index;
		try {
			index = Integer.parseInt(arguments.trim());
		} catch(NumberFormatException e) {
			env.writeln("Argument to type command must be single number.");
			return ConsoleStatus.CONTINUE;
		}
		List<SearchResult> results = env.getResults();
		if(index<0 || index>results.size()-1) {
			env.writeln("Invalid index. Please try again.");
			return ConsoleStatus.CONTINUE;
		}
		Path filePath = results.get(index).getDocument().getDocPath();
		env.writeln("------------------------------------------");
		env.writeln("Document: " + filePath.toAbsolutePath().toString());
		env.writeln("------------------------------------------");
		try {
			for(String line : Files.readAllLines(filePath)) {
				env.writeln(line);
			}
		} catch (IOException e) {
			throw new ConsoleIOException("Error while reading file.");
		}
		env.writeln("------------------------------------------");
		
		return ConsoleStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "type";
	}

}
