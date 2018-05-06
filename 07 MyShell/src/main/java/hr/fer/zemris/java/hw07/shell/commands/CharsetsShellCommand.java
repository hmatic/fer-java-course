package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command which lists all available charsets on current platform.
 * @author Hrvoje
 *
 */
public class CharsetsShellCommand implements ShellCommand {

	/**
	 * Executes charsets command.
	 * Command takes no argument so it will not execute if called with any argument.
	 * 
	 * @param env shell environment
	 * @param arguments command arguments
	 * @return shell status after command
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments!=null) {
			env.writeln("Charsets command accepts no arguments.");
			return ShellStatus.CONTINUE;
		}
		SortedMap<?, ?> charsets = Charset.availableCharsets();
		charsets.forEach((x,y)->env.writeln(x.toString()));
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "charsets";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("Lists all available charsets.");
		return desc;
	}

}
