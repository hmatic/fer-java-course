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
 * Copies directory tree from given source to given destination.
 * Takes 2 arguments which are paths to source and destination.
 * Source must be existing directory.
 * If destination directory exists, source directory will be copied inside it.
 * If destination directory does not exist, but parent directory does, 
 * only content of source directory will be copied into destination directory.
 * 
 * @author Hrvoje Matić
 */
public class CptreeShellCommand implements ShellCommand {

	/**
	 * Executes {@link CptreeShellCommand} command.
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
				
		if(argumentList.size()!=2) {
			env.writeln("This command accepts only 2 argument which is "
					+ "path to source and destination. Please try again.");
			return ShellStatus.CONTINUE;
		}
		
		String source = argumentList.get(0);
		Path sourcePath;
		try {
			sourcePath = env.getCurrentDirectory().resolve(source);
			if(!Files.exists(sourcePath) || !Files.isReadable(sourcePath) || !Files.isDirectory(sourcePath)) {
				env.writeln("Given path does not exist or is "
						+ "not readable directory. Please try again.");
				return ShellStatus.CONTINUE;
			}
		} catch(InvalidPathException e) {
			env.writeln("Given argument is not correct representation of path. Please try again.");
			return ShellStatus.CONTINUE;
		}
		
		String destination = argumentList.get(1);
		Path destPath;
		try {
			destPath = env.getCurrentDirectory().resolve(destination);
		} catch(InvalidPathException e) {
			env.writeln("Given argument is not correct representation of path. Please try again.");
			return ShellStatus.CONTINUE;
		} 
		try {
			if(Files.exists(destPath) && Files.isDirectory(destPath)) {
				Files.walkFileTree(sourcePath, new CptreeVisitor(destPath, true));		
			} else if(!Files.exists(destPath) && Files.exists(destPath.getParent())) {
				Files.createDirectory(destPath);
				Files.walkFileTree(sourcePath, new CptreeVisitor(destPath, false));	
			} 
		} catch (IOException e) {
			env.writeln("Problem with IO operation. Shell will exit now.");
			return ShellStatus.TERMINATE;
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "cptree";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("Copies directory tree from given source to given destination.");
		desc.add("Takes 2 arguments which are paths to source and destination.");
		return desc;
	}
	
	/**
	 * FileVisitor implementation for cptree command. 
	 * Walks file tree and copies files and directories.
	 * 
	 * @author Hrvoje Matić
	 */
	private static class CptreeVisitor implements FileVisitor<Path> {
		/**
		 * Root destination directory.
		 */
		private Path root;
		/**
		 * Flag determines if any directory was entered.
		 */
		private boolean firstTimePassed;
		
		/**
		 * Default constructor for CptreeVisitor
		 * @param root starting destination directory
		 * @param firstTimePassed flag for first time entering directory
		 */
		public CptreeVisitor(Path root, boolean firstTimePassed) {
			this.root = root;
			this.firstTimePassed = firstTimePassed;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException arg1) throws IOException {
			root = root.getParent();
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes arg1) throws IOException {
			if(firstTimePassed) {
				root = root.resolve(dir.getFileName());
				Files.createDirectory(root);
			}
			firstTimePassed = true;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes arg1) throws IOException {
			Files.copy(file, root.resolve(file.getFileName()));
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path arg0, IOException arg1) throws IOException {
			return FileVisitResult.CONTINUE;
		}
		
	}

}
