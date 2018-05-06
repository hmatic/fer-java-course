package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Changes current directory to new one. Can be used with relative path.
 * Takes only 1 argument which is path representing new directory.
 * 
 * @author Hrvoje Matić
 */
public class CdShellCommand implements ShellCommand {

	/**
	 * Executes {@link CdShellCommand} command.
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
		
		if(argumentList.size()!=1) {
			env.writeln("This command accepts only 1 argument which is path to directory. "
					+ "Please try again.");
			return ShellStatus.CONTINUE;
		}
		
		Path newDir = null;
		try {
			newDir = Paths.get(argumentList.get(0));
		} catch(InvalidPathException e) {
			env.writeln("Given argument is not correct representation of path. Please try again.");
			return ShellStatus.CONTINUE;
		}
		
		try {
			env.setCurrentDirectory(env.getCurrentDirectory().resolve(newDir));
		} catch(IllegalArgumentException e) {
			env.writeln("Given directory does not exist. Please try again.");
			return ShellStatus.CONTINUE;
		}
		
		env.writeln("Current directory changed to: " + env.getCurrentDirectory().toString());
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "cd";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("Changes current directory to new one. Can be used with relative path.");
		desc.add("Takes only 1 argument which is path representing new directory.");
		return desc;
	}

}
