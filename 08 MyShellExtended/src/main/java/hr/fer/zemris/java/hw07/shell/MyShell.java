package hr.fer.zemris.java.hw07.shell;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw07.shell.commands.*;

/**
 * Represents custom shell program called MyShell.
 * Use "help" command to get list of available commands.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class MyShell {
	/**
	 * Available commands.
	 */
	private static SortedMap<String, ShellCommand> commands;
	
	/**
	 * Commands build.
	 */
	static {
		commands = new TreeMap<>();
		List<ShellCommand> commandsList = new ArrayList<>();
		
		commandsList.add(new CharsetsShellCommand());
		commandsList.add(new LsShellCommand());
		commandsList.add(new ExitShellCommand());
		commandsList.add(new SymbolShellCommand());
		commandsList.add(new TreeShellCommand());
		commandsList.add(new MkdirShellCommand());
		commandsList.add(new HelpShellCommand());
		commandsList.add(new CatShellCommand());
		commandsList.add(new HexdumpShellCommand());
		commandsList.add(new CopyShellCommand());
		commandsList.add(new PwdShellCommand());
		commandsList.add(new CdShellCommand());
		commandsList.add(new PushdShellCommand());
		commandsList.add(new PopdShellCommand());
		commandsList.add(new ListdShellCommand());
		commandsList.add(new DropdShellCommand());
		commandsList.add(new MassrenameShellCommand());
		commandsList.add(new CptreeShellCommand());
		commandsList.add(new RmtreeShellCommand()); 
		
		for(ShellCommand command : commandsList) {
			commands.put(command.getCommandName(), command);
		}
	}
	
	/**
	 * Program entry point.
	 * 
	 * @param args string array of arguments
	 */
	public static void main(String[] args) {
		/**
		 * Environment setup.
		 */
		Environment env = new Environment() {
			/**
			 * PROMPT symbol.
			 */
			private Character prompt = '>';
			/**
			 * MULTILINE symbol.
			 */
			private Character multiline = '|';
			/**
			 * MORELINES symbol.
			 */
			private Character morelines = '\\';
			
			/**
			 * Input stream.
			 */
			private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			/**
			 * Output stream.
			 */
			private BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
			
			
			private Map<String,Object> sharedData = new HashMap<>();
			
			private Path currentDirectory;
			
			@Override
			public String readLine() throws ShellIOException {
				StringBuilder result = new StringBuilder();
				try {
					writer.write(prompt + " ");
					writer.flush();
					
					String line = reader.readLine();
					while(line.trim().endsWith(morelines.toString())) {
						writer.write(multiline + " ");
						writer.flush();
						
						result.append(line.substring(0, line.endsWith(morelines.toString()) ? line.length()-1 : line.length()));
						line = reader.readLine();
					} 
					result.append(line);	
				} catch (IOException e) {
					throw new ShellIOException();
				}
				
				return result.toString();
			}

			@Override
			public void write(String text) throws ShellIOException {
				try {
					writer.write(text);
					writer.flush();
				} catch (IOException e) {
					throw new ShellIOException();
				}			
			}

			@Override
			public void writeln(String text) throws ShellIOException {		
				write(text + "\n");
			}

			@Override
			public SortedMap<String, ShellCommand> commands() {
				return Collections.unmodifiableSortedMap(commands);
			}

			@Override
			public Character getMultilineSymbol() {
				return multiline;
			}

			@Override
			public void setMultilineSymbol(Character symbol) {
				multiline = symbol;				
			}

			@Override
			public Character getPromptSymbol() {
				return prompt;
			}

			@Override
			public void setPromptSymbol(Character symbol) {
				prompt = symbol;
			}

			@Override
			public Character getMorelinesSymbol() {
				return morelines;
			}

			@Override
			public void setMorelinesSymbol(Character symbol) {
				morelines = symbol;
			}

			@Override
			public Path getCurrentDirectory() {
				return currentDirectory;
			}

			@Override
			public void setCurrentDirectory(Path path) {
				if(!Files.exists(path) && !Files.isDirectory(path)) {
					throw new IllegalArgumentException("Given directory does not exist.");
				}
				currentDirectory = path;	
			}

			@Override
			public Object getSharedData(String key) {
				return sharedData.get(key);
			}

			@Override
			public void setSharedData(String key, Object value) {
				sharedData.put(key,  value);
			}
			
		};
		
		System.out.println("Welcome to MyShell v 1.0");
		env.setCurrentDirectory(Paths.get(".").toAbsolutePath().normalize());
		ShellStatus status = ShellStatus.CONTINUE;
		ShellCommand command;
		
		String line = "";
		do {
			try {
				line = env.readLine();
			} catch(ShellIOException e) {
				System.err.println("There has been problem with IO operation. Shell will now exit.");
				status = ShellStatus.TERMINATE;
			}
			
			String[] lineParts = line.trim().split("\\s+", 2);
			String commandName = lineParts[0];
			String arguments = "";
			if(lineParts.length==2) {
				arguments = lineParts[1];
			}
			
			command = commands.get(commandName);
			if(command!=null) {
				try { 
					status = command.executeCommand(env, arguments);
				} catch(ShellIOException e) {
					System.err.println("There has been problem with IO operation. Shell will now exit.");
					status = ShellStatus.TERMINATE;
				}
			} else {
				System.out.println("Can not recognize command. Please try again.");
				status = ShellStatus.CONTINUE;
			}
		} while(status != ShellStatus.TERMINATE);
		
		System.out.println("Thank you for using MyShell.");
	}
}
