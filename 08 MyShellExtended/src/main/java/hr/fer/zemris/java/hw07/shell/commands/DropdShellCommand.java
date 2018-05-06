package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Pops directory from the top of the stack but does not change current directory.
 * Takes no arguments.
 * 
 * @author Hrvoje Matić
 */
public class DropdShellCommand implements ShellCommand {

	/**
	 * Executes {@link DropdShellCommand} command.
	 * 
	 * @param env shell environment
	 * @param arguments command arguments
	 * @return shell status after command
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(!arguments.equals("")) {
			env.writeln("Dropd command takes no arguments. Please try again.");
			return ShellStatus.CONTINUE;
		}
		
		Object stack = env.getSharedData("cdstack");
		if(stack!=null) {
			@SuppressWarnings("unchecked")
			Stack<Path> cdstack = (Stack<Path>)stack;
			cdstack.pop();
		} else {
			env.writeln("Stack \"cdstack\" does not exist.");
			return ShellStatus.CONTINUE;
		}
		
		env.writeln("Directory from the top of the \"cdstack\" was removed.");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "dropd";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("Pops directory from the top of the stack but does not change current directory.");
		desc.add("Takes no arguments.");
		return desc;
	}

}
