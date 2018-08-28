package hr.fer.zemris.java.hw16.trazilica.commands;

import hr.fer.zemris.java.hw16.trazilica.ConsoleStatus;
import hr.fer.zemris.java.hw16.trazilica.environment.Environment;

/**
 * Exit application command.
 * @author Hrvoje Matic
 * @version 1.0
 */
public class ExitCommand implements ConsoleCommand {

	@Override
	public ConsoleStatus execute(Environment env, String arguments) {
		return ConsoleStatus.TERMINATE;
	}

	@Override
	public String getCommandName() {
		return "exit";
	}
}
