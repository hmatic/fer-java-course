package hr.fer.zemris.java.hw07.shell.commands;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellIOException;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Copies a file to another location.
 * Command takes 2 arguments. First argument is source and second argument is destination.
 * If file already exists on destination, user will be asked for permission.
 * If given destination is directory, source file will be copied inside given destination.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class CopyShellCommand implements ShellCommand {
	/**
	 * Buffer size for file reading.
	 */
	private static final int BUFFER_SIZE = 4096;

	/**
	 * Executes copy command.
	 * Command will stop if source file can not be opened.
	 * Will also stop if user does not permit overwriting.
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
			env.writeln("This command accepts only 2 argument which is path to source and destination. "
					+ "Please try again.");
			return ShellStatus.CONTINUE;
		}
		
		String source = argumentList.get(0);
		Path sourcePath;
		try {
			sourcePath = Paths.get(source);
			if(!Files.exists(sourcePath) || !Files.isReadable(sourcePath) || Files.isDirectory(sourcePath)) {
				env.writeln("Given file is either directory, does not exist or can not be read.");
				return ShellStatus.CONTINUE;
			}
		} catch(InvalidPathException e) {
			env.writeln("Given argument is not correct representation of path. Please try again.");
			return ShellStatus.CONTINUE;
		}
		
		String destination = argumentList.get(1);
		try {
			Path destPath = Paths.get(destination);
			if(Files.exists(destPath) && !Files.isDirectory(destPath) && Files.isWritable(destPath)) {
				
				env.writeln("Are you sure you want to overwrite existing file? (Y/N):");
				String line = env.readLine();
				if(line.equals("Y")) {
					
				} else if(line.equals("N")) {
					env.writeln("Copying was interrupted by user.");
					return ShellStatus.CONTINUE;
				} else {
					env.writeln("Answer can only be Y or N. Please try to use command again.");
					return ShellStatus.CONTINUE;
				}
				
			} else if(Files.isDirectory(destPath)) {
				destination += "\\" + sourcePath.getFileName();
			} 

		} catch(InvalidPathException e) {
			env.writeln("Given argument is not correct representation of path. Please try again.");
			return ShellStatus.CONTINUE;
		} 
		
		try (FileInputStream input = new FileInputStream(source);
				FileOutputStream output = new FileOutputStream(destination)) {
			byte[] buffer = new byte[BUFFER_SIZE];
			int nread = 0;
			while ((nread = input.read(buffer)) != -1) {
				output.write(buffer, 0, nread);;
			}
		} catch (IOException e) {
			throw new ShellIOException();
		}
		
		env.writeln("File \"" + source + "\" was copied to \"" + destination + "\".");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "copy";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("Copies a file to another location.");
		desc.add("Command takes 2 arguments. First argument is source and second argument is destination.");
		desc.add("If file already exists on destination, user will be asked for permission.");
		desc.add("If given destination is directory, source file will be copied inside given destination.");
		return desc;
	}

}
