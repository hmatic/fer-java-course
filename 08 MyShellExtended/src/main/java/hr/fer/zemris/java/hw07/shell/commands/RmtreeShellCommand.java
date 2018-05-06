package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Recursively deletes whole directory tree.
 * Takes only 1 argument which is path to starting directory in tree.
 * Use this command carefully as it can easily delete MyShell project directory.
 * 
 * @author Hrvoje Matić
 */
public class RmtreeShellCommand implements ShellCommand {

	/**
	 * Executes {@link RmtreeShellCommand} command.
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
			argumentList = argParser.parse();
		} catch (ArgumentParserException e) {
			env.writeln(e.getMessage() + " Please try again");
			return ShellStatus.CONTINUE;
		}
				
		if(argumentList.size()!=1) {
			env.writeln("This command accepts only 1 argument which is path to source. "
					+ "Please try again.");
			return ShellStatus.CONTINUE;
		}
		
		String source = argumentList.get(0);
		Path sourcePath;
		try {
			sourcePath = env.getCurrentDirectory().resolve(source);
			if(!Files.exists(sourcePath) || !Files.isReadable(sourcePath) || Files.isDirectory(sourcePath)) {
				env.writeln("Given path is either not directory, "
						+ "does not exist or can not be read. Please try again.");
				return ShellStatus.CONTINUE;
			}
		} catch(InvalidPathException e) {
			env.writeln("Given argument is not correct representation of path. Please try again.");
			return ShellStatus.CONTINUE;
		}
		
		try {
			Files.walkFileTree(sourcePath, new RmtreeVisitor());
		} catch (IOException e) {
			env.writeln("Problem with IO operation. Shell will exit now.");
			return ShellStatus.TERMINATE;
		}
		
		env.writeln("Deleted: " + sourcePath.toString());
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "rmtree";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("Recursively deletes whole directory tree.");
		desc.add("Takes only 1 argument which is path to starting directory in tree.");
		return desc;
	}
	
	/**
	 * FileVisitor implementation for rmtree command.
	 * Walks file tree and deletes files and directories.
	 * 
	 * @author Hrvoje Matić
	 */
	private static class RmtreeVisitor implements FileVisitor<Path> {
		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException arg1) throws IOException {
			Files.delete(dir);
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes arg1) throws IOException {
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes arg1) throws IOException {
			Files.delete(file);
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path arg0, IOException arg1) throws IOException {
			return FileVisitResult.CONTINUE;
		}
		
	}

}
