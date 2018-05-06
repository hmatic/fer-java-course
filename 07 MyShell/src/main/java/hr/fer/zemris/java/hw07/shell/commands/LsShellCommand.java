package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
/**
 * Displays a list of files and subdirectories in directory.
 * Takes only 1 argument which is a path representing directory.
 * Ouput consists of file status, creation date and file name.
 * File status is following: directory (d), readable (r),
 * writable (w) and executable (x).
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class LsShellCommand implements ShellCommand {

	/**
	 * Executes ls command.
	 * Command will not execute if argument number is not equal to 1.
	 * Also will not execute if given path is not directory or does not exist.
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
			env.writeln("This command accepts only 1 argument which is path to directory. "
					+ "Please try again.");
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
		
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
			for (Path entry : stream) {
				env.writeln(String.format("%4s %10d %s %s",
						fileStatus(entry), 
						Files.size(entry), 
						fileDate(entry), 
						entry.getFileName().toString()));
			}
		} catch (IOException e) {
			env.writeln("Cant read given directory. "
					+ "There has been problem with I/O operation. "
					+ "MyShell will exit now.");
			return ShellStatus.TERMINATE;
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "ls";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("Displays a list of files and subdirectories in directory.");
		desc.add("Takes only 1 argument which is a path representing directory.");
		return desc;
	}

	/**
	 * Builds status string for given Path.
	 * The output consists of 4 columns. 
	 * First column indicates if current object is directory (d), readable (r),
	 * writable (w) and executable (x).
	 * @param path path to file
	 * @return file status
	 */
	private String fileStatus(Path path) {
		StringBuilder status = new StringBuilder();
		status.append(Files.isDirectory(path) ? "d" : "-");
		status.append(Files.isReadable(path) ? "r" : "-");
		status.append(Files.isWritable(path) ? "w" : "-");
		status.append(Files.isExecutable(path) ? "x" : "-");
		return status.toString();
	}
	
	/**
	 * Builds creation date string for given Path.
	 * @param path path to file
	 * @return file date
	 * @throws IOException upon error with IO operations
	 */
	private String fileDate(Path path) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		BasicFileAttributeView faView = Files.getFileAttributeView(path, BasicFileAttributeView.class,
				LinkOption.NOFOLLOW_LINKS);
		BasicFileAttributes attributes = faView.readAttributes();
		FileTime fileTime = attributes.creationTime();
		return sdf.format(new Date(fileTime.toMillis()));
	}
}
