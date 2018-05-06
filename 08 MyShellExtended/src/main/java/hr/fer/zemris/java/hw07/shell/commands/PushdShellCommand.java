package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Pushes current directory to stack and sets directory given in argument as current directory.
 * Takes only 1 argument which is path representing new current directory.
 * 
 * @author Hrvoje Matić
 */
public class PushdShellCommand implements ShellCommand {

	/**
	 * Executes {@link PushdShellCommand} command.
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
		
		Path path = null;
		try {
			path = Paths.get(argumentList.get(0));
		} catch(InvalidPathException e) {
			env.writeln("Given argument is not correct representation of path. Please try again.");
			return ShellStatus.CONTINUE;
		}
		
		if(!Files.isDirectory(path) || !Files.exists(path)) {
			env.writeln("Given path must represent existing directory. Please try again.");
			return ShellStatus.CONTINUE;
		}
		
		
		if(env.getSharedData("cdstack")==null) {
			Stack<Path> cdstack = new Stack<>();
			cdstack.push(env.getCurrentDirectory());
			env.setSharedData("cdstack", cdstack);
		} else {
			@SuppressWarnings("unchecked")
			Stack<Path> cdstack = (Stack<Path>)env.getSharedData("cdstack");
			cdstack.push(env.getCurrentDirectory());
		}
		
		env.setCurrentDirectory(path);
		env.writeln("Current directory added to \"cdstack\". "
				+ "Current directory changed to: " + path.toString());
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "pushd";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("Pushes current directory to stack and sets directory given in argument as current directory.");
		desc.add("Takes only 1 argument which is path representing new current directory.");
		return desc;
	}

}
