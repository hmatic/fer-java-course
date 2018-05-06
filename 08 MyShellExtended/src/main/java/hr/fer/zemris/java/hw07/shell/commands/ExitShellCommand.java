package hr.fer.zemris.java.hw07.shell.commands;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
/**
 * Command which exits shell.
 * 
 * @author Hrvoje Matić
 *
 */
public class ExitShellCommand implements ShellCommand {

	/**
	 * Executes exit command.
	 * This command takes no arguments. 
	 * Will print appropriate message if arguments are given, but will still exit shell.
	 * @param env shell environment
	 * @param arguments command arguments
	 * @return shell status after command
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments!=null) {
			env.writeln("Exit command accepts no arguments. Shell will exit regardless.");
		}
		return ShellStatus.TERMINATE;
	}

	@Override
	public String getCommandName() {
		return "exit";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("Exits shell.");
		desc.add("Command takes no arguments.");
		return desc;
	}

}
