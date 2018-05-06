package hr.fer.zemris.java.hw07.shell.commands;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
/**
 * Prints contents of file using given charset.
 * First argument is path to the file and second argument is charset.
 * Charset is optional. If called without second argument, default charset will be used.
 * @author Hrvoje Matić
 * @version 1.0
 */
public class CatShellCommand implements ShellCommand {

	/**
	 * Executes cat command.
	 * If there is no arguments or more than 2 argument, error message is printed.
	 * Writes appropriate message if given file doesn't exist, if it is directory or can not be read.
	 * 
	 * @param env shell environment
	 * @param arguments command arguments
	 * @return shell status after command
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		ArgumentParser argParser = new ArgumentParser(arguments);
		List<String> argumentList;
		try {
			argumentList = argParser.parse();
		} catch (ArgumentParserException e) {
			env.writeln(e.getMessage() + " Please try again");
			return ShellStatus.CONTINUE;
		}
				
		String charset = null;
		if(argumentList.size()==2) {
			if(Charset.isSupported(argumentList.get(1))) {
				charset = argumentList.get(1);
			} else {
				env.writeln("This charset is not supported. Please try again.");
				return ShellStatus.CONTINUE;
			}
			
		} else if(argumentList.size()==1){
			charset = Charset.defaultCharset().name();
		} else {
			env.writeln("This command accepts only 1 argument which is path to directory. "
					+ "Please try again.");
			return ShellStatus.CONTINUE;
		}
		
		String fileName = argumentList.get(0);
		try {
			Path filePath = Paths.get(fileName);
			if(!Files.exists(filePath) || !Files.isReadable(filePath) || Files.isDirectory(filePath)) {
				env.writeln("Given file is either directory, does not exist or can not be read.");
				return ShellStatus.CONTINUE;
			}
		} catch(InvalidPathException e) {
			env.writeln("Given argument is not correct representation of path. Please try again.");
			return ShellStatus.CONTINUE;
		}
		
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(fileName), charset))) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				env.writeln(line);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		return null;
	}

	@Override
	public String getCommandName() {
		return "cat";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("Prints contents of file using given charset.");
		desc.add("First argument is path to the file and second argument is charset.");
		desc.add("Charset is optional. If called without second argument, default charset will be used.");
		return desc;
	}

}
