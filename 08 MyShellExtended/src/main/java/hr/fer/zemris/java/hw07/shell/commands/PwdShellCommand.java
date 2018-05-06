package hr.fer.zemris.java.hw07.shell.commands;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Prints current directory to terminal. This command takes no arguments.
 *
 * @author Hrvoje Matić
 */
public class PwdShellCommand implements ShellCommand {

	/**
	 * Executes {@link PwdShellCommand} command.
	 * 
	 * @param env shell environment
	 * @param arguments command arguments
	 * @return shell status after command
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(!arguments.equals("")) {
			env.writeln("Pwd command takes no arguments.");
			return ShellStatus.CONTINUE;
		}
		
		env.writeln(env.getCurrentDirectory().toString());
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "pwd";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("Prints current directory.");
		desc.add("Takes no arguments.");
		return desc;
	}

}
