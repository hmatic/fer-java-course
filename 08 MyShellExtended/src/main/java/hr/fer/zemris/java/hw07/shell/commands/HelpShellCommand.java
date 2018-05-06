package hr.fer.zemris.java.hw07.shell.commands;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Lists all supported commands.
 * If called with command name in argument, prints command description.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class HelpShellCommand implements ShellCommand {

	/**
	 * Executes help command.
	 * Command accepts zero or one argument. Will not be executed if more than 1 argument is given.
	 * 
	 * @param env shell environment
	 * @param arguments command arguments
	 * @return shell status after command
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments.equals("")) {
			env.commands().forEach((k,v)->env.writeln(k));
		} else {
			ArgumentParser argParser = new ArgumentParser(arguments);
			List<String> argumentList;
			try {
				argumentList = argParser.parse();
			} catch (ArgumentParserException e) {
				env.writeln(e.getMessage() + " Please try again");
				return ShellStatus.CONTINUE;
			}
			
			if(argumentList.size()!=1) {
				env.writeln("This command accepts only 1 argument which is name of command. "
						+ "Please try again.");
				return ShellStatus.CONTINUE;
			}
			String commandString = argumentList.get(0);
			ShellCommand command = env.commands().get(commandString);
			if(command==null) {
				env.writeln("Can not recognize given command. "
						+ "Please use \"help\" to get list of available commands.");
				return ShellStatus.CONTINUE;
			} else {
				for(String line : command.getCommandDescription()) {
					env.writeln(line);
				}
			}
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "help";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("Lists all supported commands.");
		desc.add("If called with command name in argument, prints command description.");
		return desc;
	}

}
