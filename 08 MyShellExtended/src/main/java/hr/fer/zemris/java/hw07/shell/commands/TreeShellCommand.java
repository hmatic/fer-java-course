package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Graphically displays the folder structure of a drive or a path.
 * Takes only 1 argument which is path representing starting directory.
 * @author Hrvoje Matić
 * @version 1.0
 */
public class TreeShellCommand implements ShellCommand {

	/**
	 * Executes tree command.
	 * Will not execute if given path does not exist or if it is not directory.
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
			env.writeln("This command accepts only 1 argument which is path to directory. Please try again.");
			return ShellStatus.CONTINUE;
		}
		
		Path dir = null;
		try {
			dir = Paths.get(argumentList.get(0));
		} catch(InvalidPathException e) {
			env.writeln("Given argument is not correct representation of path. Please try again.");
			return ShellStatus.CONTINUE;
		}
		
		if(!Files.isDirectory(dir) || !Files.exists(dir)) {
			env.writeln("Given path must represent existing directory. Please try again.");
			return ShellStatus.CONTINUE;
		}
		
		try {
			Files.walkFileTree(dir, new FileVisitor<Path>() {
				/**
				 * Indentation counter in file tree
				 */
				private int indent = 0;
				
				/**
				 * Decreases indent upon returning from directory.
				 */
				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
					indent--;
					return FileVisitResult.CONTINUE;
				}

				/**
				 * Increases indent and prints entered directory.
				 */
				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
					env.writeln(fileName(dir));
					indent++;
					return FileVisitResult.CONTINUE;
				}

				/**
				 * Prints file name upon visiting file.
				 */
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					env.writeln(fileName(file));
					return FileVisitResult.CONTINUE;
				}

				/**
				 * Skip upon error.
				 */
				@Override
				public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
					return FileVisitResult.CONTINUE;
				}
				
				/**
				 * Helper method that formats filename output.
				 * @param file path to file
				 * @return filename output
				 * @throws IOException if error occurs on IO operation
				 */
				private String fileName(Path file) throws IOException {
					if (indent == 0) {
						return file.normalize().toAbsolutePath().toString();
					} else {
						return String.format("%" + 2 * indent + "s%s", "", file.getFileName());
					}
				}
			});
		} catch (IOException e) {
			env.writeln("Cant read directory. "
					+ "There has been problem with I/O operation. "
					+ "MyShell will exit now.");
			return ShellStatus.TERMINATE;
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "tree";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("Graphically displays the folder structure of a drive or a path.");
		desc.add("Takes only 1 argument which is path representing starting directory.");
		return desc;
	}

}
