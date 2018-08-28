package hr.fer.zemris.java.hw16.trazilica;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import hr.fer.zemris.java.hw16.trazilica.commands.ConsoleCommand;
import hr.fer.zemris.java.hw16.trazilica.commands.ExitCommand;
import hr.fer.zemris.java.hw16.trazilica.commands.QueryCommand;
import hr.fer.zemris.java.hw16.trazilica.commands.ResultsCommand;
import hr.fer.zemris.java.hw16.trazilica.commands.TypeCommand;
import hr.fer.zemris.java.hw16.trazilica.environment.Environment;
import hr.fer.zemris.java.hw16.trazilica.environment.EnvironmentImpl;

/**
 * Search engine console application.
 * Supports following commands:
 * query args - generates search results
 * results - prints search results
 * type arg - prints contents of search result on index given in arg
 * exit - exits application
 * 
 * @author Hrvoje Matic
 * @version 1.0
 */
public class Konzola {
	/**
	 * Available commands.
	 */
	private static Map<String, ConsoleCommand> commands;
	
	/**
	 * Commands build.
	 */
	static {
		commands = new HashMap<>();
		List<ConsoleCommand> commandsList = new ArrayList<>();
		
		commandsList.add(new QueryCommand());
		commandsList.add(new TypeCommand());
		commandsList.add(new ResultsCommand());
		commandsList.add(new ExitCommand());

		for(ConsoleCommand command : commandsList) {
			commands.put(command.getCommandName(), command);
		}
	}
	
	/**
	 * Program entry point.
	 * @param args path to directory containing documents for search purposes
	 */
	public static void main(String[] args) {
		if(args.length!=1) {
			System.out.println("Application takes single argument which is path to "
					+ "directory containing documents used as search database.");
			System.exit(-1);
		}
		Environment env = null;
		try {
			env = new EnvironmentImpl(Paths.get(args[0]));
		} catch(ConsoleIOException ex) {
			System.err.println(ex.getMessage());
			System.exit(-1);
		}
		ConsoleStatus status = ConsoleStatus.CONTINUE;
		ConsoleCommand command;
		
		env.writeln("Veličina rječnika je " + env.getVocabulary().size() + " riječi.");
		
		String line = "";
		do {
			try {
				line = env.readLine();
			} catch(ConsoleIOException e) {
				System.err.println("There has been problem with IO operation. Shell will now exit.");
				System.exit(-1);
			}
			
			String[] lineParts = line.trim().split("\\s+", 2);
			String commandName = lineParts[0];
			String arguments = null;
			if(lineParts.length==2) {
				arguments = lineParts[1];
			}
			
			command = commands.get(commandName);
			if(command!=null) {
				try { 
					status = command.execute(env, arguments);
				} catch(ConsoleIOException e) {
					System.err.println("There has been problem with IO operation. Shell will now exit.");
					System.exit(-1);
				}
			} else {
				System.out.println("Can not recognize command. Please try again.");
				status = ConsoleStatus.CONTINUE;
			}
		} while(status != ConsoleStatus.TERMINATE);
		
		env.writeln("Thank you for using my search console.");	
	}
}
