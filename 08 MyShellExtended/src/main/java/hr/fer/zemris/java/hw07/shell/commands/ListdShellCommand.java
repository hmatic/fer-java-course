package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Prints content of "cdstack" starting from the most recently added directory.
 * Takes no arguments.
 * 
 * @author Hrvoje Matić
 */
public class ListdShellCommand implements ShellCommand {

	/**
	 * Executes {@link ListdShellCommand} command.
	 * 
	 * @param env shell environment
	 * @param arguments command arguments
	 * @return shell status after command
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(!arguments.equals("")) {
			env.writeln("Listd command takes no arguments. Please try again.");
			return ShellStatus.CONTINUE;
		}
		
		Object stack = env.getSharedData("cdstack");
		if(stack!=null) {
			@SuppressWarnings("unchecked")
			Stack<Path> cdstack = (Stack<Path>)stack;
			
			if(cdstack.isEmpty()) {
				env.writeln("No directories stored on \"cdstack\".");
				return ShellStatus.CONTINUE;
			}
			
			for(int i = cdstack.size()-1; i >= 0; i--) {
				env.writeln(cdstack.get(i).toString());
			}
		} else {
			env.writeln("Stack \"cdstack\" does not exist.");
			return ShellStatus.CONTINUE;
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "listd";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("Prints content of \"cdstack\" starting from the most recently added directory.");
		desc.add("Takes no arguments.");
		return desc;
	}

}
