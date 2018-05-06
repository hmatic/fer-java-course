package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.commands.massrename.NameBuilder;
import hr.fer.zemris.java.hw07.shell.commands.massrename.NameBuilderInfo;
import hr.fer.zemris.java.hw07.shell.commands.massrename.NameBuilderParser;
import hr.fer.zemris.java.hw07.shell.commands.massrename.NameBuilderParserException;

/**
 * Renames/moves files from source directory matched by regex pattern into destination directory.<br>
 * Arguments are: source, destination, CMD, regex, expression.<br>
 * CMD can be: filter, groups, show, execute<br>
 * Filter prints file names matched by regex.<br>
 * Groups prints matched groups of matched file names.<br>
 * Show prints matched file names and their new name based on expression.<br>
 * Execute renames/moves files from given source to given destination.<br>
 * <br>
 * Regex is processed by {@link Pattern}.<br>
 * Expressions consist of string constants and substitution commands.<br>
 * Substitution commands start with '${' and end with '}' 
 * and represent matched group on index given inside brackets.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class MassrenameShellCommand implements ShellCommand {
	
	/**
	 * Executes {@link MassrenameShellCommand} command.
	 * 
	 * @param env shell environment
	 * @param arguments command arguments
	 * @return shell status after command
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		ArgumentParser argParser = new ArgumentParser(arguments);
		List<String> argumentList;
		try {
			argumentList = argParser.parse("NO_ESCAPE");
		} catch (ArgumentParserException e) {
			env.writeln(e.getMessage() + " Please try again");
			return ShellStatus.CONTINUE;
		}
		
		if(argumentList.size()<4) {
			env.writeln("This command accepts at least 4 arguments which are source, "
					+ "destination, command type and regex pattern. "
					+ "Please try again.");
			return ShellStatus.CONTINUE;
		}
		
		Path source = null;
		try {
			source = env.getCurrentDirectory().resolve(argumentList.get(0));
		} catch(InvalidPathException e) {
			env.writeln("Given source is not correct representation of path. Please try again.");
			return ShellStatus.CONTINUE;
		}
		
		Path destination = null;
		try {
			destination = env.getCurrentDirectory().resolve(argumentList.get(1));
		} catch(InvalidPathException e) {
			env.writeln("Given destination is not correct representation of path. Please try again.");
			return ShellStatus.CONTINUE;
		}
		
		String regex = argumentList.get(3);
		Pattern pattern = Pattern.compile(regex);
		
		try {
			List<Path> pathList = filter(source, pattern);
			if(pathList.isEmpty()) {
				env.writeln("No files matched given regex. Please try again.");
				return ShellStatus.CONTINUE;
			}
			switch(argumentList.get(2)) {
				case "filter":
					for (Path path : pathList) {
						env.writeln(path.getFileName().toString());
					}
					break;
				case "groups":
					for(Path path : pathList) {
						env.write(path.getFileName().toString() + " ");
						List<String> groups = groups(path, pattern);
						for(int i=0, max=groups.size(); i<max; i++) {
							env.write(i + ": " + groups.get(i) + " ");
						}
						env.write("\n");
					}					
					break;
				case "show":
					if(argumentList.size()!=5) {
						env.writeln("Invalid number of arguments for show command. Please try again.");
						return ShellStatus.CONTINUE;
					}
					NameBuilderParser parser = new NameBuilderParser(argumentList.get(4));
					NameBuilder builder;
					try {
						builder = parser.getNameBuilder();
					} catch(NameBuilderParserException e) {
						env.writeln("Given naming expression can not be parsed. Please try again.");
						return ShellStatus.CONTINUE;
					}
					for(Path path : pathList) {
						String newName = generateNewName(pattern, path, builder);
						env.writeln(path.getFileName().toString() + " => " + newName);
					}
					break;
				case "execute":
					if(argumentList.size()!=5) {
						env.writeln("Invalid number of arguments for execute command. Please try again.");
						return ShellStatus.CONTINUE;
					}
					NameBuilderParser execParser = new NameBuilderParser(argumentList.get(4));
					NameBuilder execBuilder;
					try {
						execBuilder = execParser.getNameBuilder();
					} catch(NameBuilderParserException e) {
						env.writeln("Given naming expression can not be parsed. Please try again.");
						return ShellStatus.CONTINUE;
					}
					for(Path path : pathList) {
						String newName = generateNewName(pattern, path, execBuilder);
						env.writeln(path.getFileName().toString() + " => " + newName);
						Files.move(path, destination.resolve(newName));
					}
					break;
				default: 
					env.writeln("Third argument must be command type which "
							+ "can be only: filter, groups, show or execute. Please try again.");
					return ShellStatus.CONTINUE;
			}
		} catch (IOException e) {
			env.writeln("Problem with IO operation. Shell will exit now.");
			return ShellStatus.TERMINATE;
		}
		
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "massrename";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("Renames/moves files from source directory matched by regex pattern into destination directory.");
		desc.add("Arguments are: source, destination, CMD, regex, expression.");
		desc.add("CMD can be: filter, groups, show, execute");
		desc.add("Filter prints file names matched by regex.");
		desc.add("Groups prints matched groups of matched file names.");
		desc.add("Show prints matched file names and their new name based on expression.");
		desc.add("Execute renames/moves files from given source to given destination.");
		return desc;
	}

	/**
	 * NameBuilderInfo implementation. NameBuilderInfo is used to store StringBuilder and generate new files names.
	 * 
	 * @author Hrvoje Matić
	 */
	private static class NameBuilderInfoImpl implements NameBuilderInfo {
		/**
		 * StringBuilder for building file names.
		 */
		private StringBuilder sb = new StringBuilder();
		/**
		 * Reference to matcher of current file name.
		 */
		private Matcher matcher;
		
		/**
		 * Default constructor for NameBuilderInfoImpl.
		 * @param matcher reference to matcher
		 */
		public NameBuilderInfoImpl(Matcher matcher) {
			this.matcher = matcher;
		}

		@Override
		public StringBuilder getStringBuilder() {
			return sb;
		}

		@Override
		public String getGroup(int index) {
			return matcher.group(index);
		}
	}
	
	/**
	 * Generates a list of files matched by pattern given in argument.
	 * @param source source directory
	 * @param pattern reference to Pattern 
	 * @return list of matched files
	 * @throws IOException upon error while doing IO operations
	 */
	private static List<Path> filter(Path source, Pattern pattern) throws IOException {
		List<Path> filteredPaths = new ArrayList<>();
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(source)) {
			for (Path entry : stream) {
				String fileName = entry.getFileName().toString();
				Matcher matcher = pattern.matcher(fileName);
				if(matcher.matches()) {
					filteredPaths.add(entry);
				}
			}
		}
		return filteredPaths;	
	}
	
	/**
	 * Generates a list of groups matched by pattern on given file name.
	 * @param path path to file
	 * @param pattern reference to Pattern
	 * @return list of groups
	 */
	private static List<String> groups(Path path, Pattern pattern) {
		List<String> groups = new ArrayList<>();
		Matcher matcher = pattern.matcher(path.getFileName().toString());
		matcher.matches();
		for(int i=0, count=matcher.groupCount(); i<=count; i++) {
			groups.add(matcher.group(i));
		}
		return groups;
	}
	
	/**
	 * Generates new file name for file given in argument. Uses NameBuilder generated by expression parser.
	 * @param pattern reference to Pattern
	 * @param path path to file
	 * @param builder NameBuilder used to build new file name
	 * @return new file name
	 */
	private static String generateNewName(Pattern pattern, Path path, NameBuilder builder) {
		Matcher matcher = pattern.matcher(path.getFileName().toString());
		matcher.matches();
		NameBuilderInfo info = new NameBuilderInfoImpl(matcher);
		builder.execute(info);
		return info.getStringBuilder().toString();
	}
}
