package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Pops directory from the top of the "cdstack" and makes it current directory.
 * Takes no arguments.
 * 
 * @author Hrvoje Matić
 */
public class PopdShellCommand implements ShellCommand {

	/**
	 * Executes {@link PopdShellCommand} command.
	 * 
	 * @param env shell environment
	 * @param arguments command arguments
	 * @return shell status after command
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(!arguments.equals("")) {
			env.writeln("Popd command takes no arguments. Please try again.");
			return ShellStatus.CONTINUE;
		}
		
		Object stack = env.getSharedData("cdstack");
		if(stack!=null) {
			@SuppressWarnings("unchecked")
			Stack<Path> cdstack = (Stack<Path>)stack;
			try {
				env.setCurrentDirectory(cdstack.pop());
			} catch(IllegalArgumentException e) {
				env.writeln("Directory from the top of the stack does not exist anymore, "
						+ "so it can not be set as current directory. "
						+ "It was removed from top of the stack.");
			}
		} else {
			env.writeln("Stack \"cdstack\" does not exist.");
			return ShellStatus.CONTINUE;
		}
		
		env.writeln("Directory \"" + env.getCurrentDirectory().toString() + 
				"\" was removed from top of the stack and set as current directory.");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "popd";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("Pops directory from the top of the stack and makes it current directory.");
		desc.add("Takes no arguments.");
		return desc;
	}

}
