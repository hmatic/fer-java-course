package hr.fer.zemris.java.hw16.trazilica.commands;

import hr.fer.zemris.java.hw16.trazilica.ConsoleStatus;
import hr.fer.zemris.java.hw16.trazilica.environment.Environment;

/**
 * Interface which models all console commands.
 * @author Hrvoje Matic
 * @version 1.0
 */
public interface ConsoleCommand {
	/**
	 * Execute console command.
	 * @param env console environment
	 * @param arguments command arguments
	 * @return console status
	 */
	public abstract ConsoleStatus execute(Environment env, String arguments);
	
	/**
	 * Get command name.
	 * @return command name
	 */
	public abstract String getCommandName();
}
