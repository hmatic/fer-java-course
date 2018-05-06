package hr.fer.zemris.java.hw07.shell.commands;

import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
/**
 * Interface which models every Shell command.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public interface ShellCommand {
	/**
	 * Executes command on given environment given in first argument with arguments given in second argument.
	 * 
	 * @param env shell environment
	 * @param arguments command arguments
	 * @return shell status after command
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	
	/**
	 * Returns name of command as String.
	 * 
	 * @return name of command
	 */
	String getCommandName();
	
	/**
	 * Returns description of command as a list of String lines.
	 * 
	 * @return command description
	 */
	List<String> getCommandDescription();
}
