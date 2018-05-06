package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Creates new directory structure.
 * Takes only 1 argument which is path to new directory structure.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class MkdirShellCommand implements ShellCommand {

	/**
	 * Executes mkdir command.
	 * Command will not execute if directory already exists or invalid number of arguments is given.
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
		
		if(argumentList.size()!=1) {
			env.writeln("This command accepts only 1 argument which is path to directory. Please try again.");
			return ShellStatus.CONTINUE;
		}
		
		Path dir = null;
		try {
			dir = Paths.get(argumentList.get(0));
		} catch(InvalidPathException e) {
			env.writeln("Given argument is not correct representation of path. Please try again.");
			return ShellStatus.CONTINUE;
		}
		
		if(Files.exists(dir)) {
			env.writeln("Given directory already exists. Please try again.");
			return ShellStatus.CONTINUE;
		}
		
		try {
			Files.createDirectories(dir);
		} catch (IOException e) {
			env.writeln("Can't create such directory structure. "
					+ "There has been problem with I/O operation. "
					+ "MyShell will exit now.");
			return ShellStatus.TERMINATE;
		}
		env.writeln("Directory \"" + dir.getFileName() + "\" created.");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "mkdir";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("Creates new directory structure.");
		desc.add("Takes only 1 argument which is path to new directory structure.");
		return desc;
	}

}
