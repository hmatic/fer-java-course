package hr.fer.zemris.java.hw07.shell.commands;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
/**
 * Prints or changes shell variables. Variables are PROMPT, MORELINES and MULTILINE.
 * Command takes 1 or 2 arguments. First argument is name of the shell variable.
 * Second argument is a new variable value which can be only one symbol.
 * If command is called with 1 argument, it will print current variable value.
 * If command is called with 2 arguments, it will change variable given in the first argument to value given in the second argument.
 *
 * @author Hrvoje Matić
 * @version 1.0
 */
public class SymbolShellCommand implements ShellCommand {

	/**
	 * Executes symbol command.
	 * 
	 * @param env shell environment
	 * @param arguments command arguments
	 * @return shell status after command
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		String[] argumentParts = arguments.split("\\s+");
		if(argumentParts.length==1) {
			switch(argumentParts[0]) {
				case "PROMPT":
					env.writeln("Symbol for PROMPT is '" + env.getPromptSymbol() + "'");
					break;
				case "MULTILINE":
					env.writeln("Symbol for MULTILINE is '" + env.getMultilineSymbol() + "'");
					break;
				case "MORELINES":
					env.writeln("Symbol for MORELINES is '" + env.getMorelinesSymbol() + "'");
					break;
				default:
					env.writeln("Can not recognize symbol. Please try again.");
			}
			
		} else if(argumentParts.length==2) {
			char newSymbol;
			if(argumentParts[1].length()==1) {
				newSymbol = argumentParts[1].charAt(0);
			} else {
				env.writeln("Invalid symbol command argument. "
						+ "New symbol must be a single char. "
						+ "Please try again.");
				return ShellStatus.CONTINUE;
			}
			
			char oldSymbol;
			switch(argumentParts[0]) {
				case "PROMPT":
					oldSymbol = env.getPromptSymbol();
					env.setPromptSymbol(newSymbol);
					env.writeln("Symbol for PROMPT changed from '" + oldSymbol + 
							"' to '" + env.getPromptSymbol() + "'");
					break;
				case "MULTILINE":
					oldSymbol = env.getMultilineSymbol();
					env.setMultilineSymbol(newSymbol);
					env.writeln("Symbol for MULTILINE changed from '" + oldSymbol + 
							"' to '" + env.getMultilineSymbol() + "'");
					break;
				case "MORELINES":
					oldSymbol = env.getMorelinesSymbol();
					env.setMorelinesSymbol(newSymbol);
					env.writeln("Symbol for MORELINES changed from '" + oldSymbol + 
							"' to '" + env.getMorelinesSymbol() + "'");
					break;
				default:
					env.writeln("Can not recognize symbol. Please try again.");
			}
		} else {
			env.writeln("This command only accepts 1 or 2 arguments. Please try again.");
		}
			
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "symbol";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("Prints or changes shell variables. Variables are PROMPT, MORELINES and MULTILINE.");
		desc.add("Command takes 1 or 2 arguments. First argument is name of the shell variable.");
		desc.add("Second argument is a new variable value which can be only one symbol.");
		desc.add("If command is called with 1 argument, it will print current variable value.");
		desc.add("If command is called with 2 arguments, "
				+ "it will change variable given in the first argument to value given in the second argument.");
		return desc;
	}
	
}
