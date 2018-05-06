package hr.fer.zemris.java.hw07.shell.commands;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Prints file given in argument in hex-output.
 * Takes only 1 argument which is a path representing file.
 * Hex-output is formated in following way:
 * Line starts with line-number as hex-value, followed by 16 hex values of file content, 
 * followed by file content printed only for chars with value ranging from 32 to 127.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class HexdumpShellCommand implements ShellCommand {
	/**
	 * Starting value for standard subset of characters.
	 */
	private static final byte START_CHAR = 32;
	/**
	 * Ending value for standard subset of characters.
	 */
	private static final byte END_CHAR = 127;

	/**
	 * Executes hexdump command.
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
		
		String fileName = argumentList.get(0);
		Path filePath;
		try {
			filePath = env.getCurrentDirectory().resolve(fileName);
			if(!Files.exists(filePath) || !Files.isReadable(filePath) || Files.isDirectory(filePath)) {
				env.writeln("Given file is either directory, does not exist or can not be read.");
				return ShellStatus.CONTINUE;
			}
		} catch(InvalidPathException e) {
			env.writeln("Given argument is not correct representation of path. Please try again.");
			return ShellStatus.CONTINUE;
		}
		fileName = filePath.toString();
		
		try (FileInputStream input = new FileInputStream(fileName)) {
			byte[] buffer = new byte[16];
			int bytesLoaded;
			int counter = 0;
			while ((bytesLoaded=input.read(buffer)) != -1) {
				env.write(padLeftWithZeros(Integer.toHexString(counter)) + ": ");
				env.write(getHexdumpLine(buffer, bytesLoaded));
				counter+=buffer.length;
			}
		} catch (IOException e) {
			env.writeln("Problem with IO operation. Shell will exit now.");
			return ShellStatus.TERMINATE;
		} 
	
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "hexdump";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("Prints file given in argument in hex-output.");
		desc.add("Takes only 1 argument which is a path representing file.");
		return desc;
	}
	
	/**
	 * Helper method which converts single byte to hex.
	 * @param byteArg
	 * @return
	 */
	public static String bytetohex(byte byteArg) {
		StringBuilder sb = new StringBuilder();
		sb.append(Character.forDigit((byteArg >> 4) & 0xF, 16));
		sb.append(Character.forDigit((byteArg & 0xF), 16));
		return sb.toString().toUpperCase();
	}
	
	/**
	 * Helper method used for printing hexdump lines.
	 * @param buffer array of bytes to be converted
	 * @param bytesLoaded bytes loaded in current buffer
	 * @return line to be printed
	 */
	private static String getHexdumpLine(byte[] buffer, int bytesLoaded) {
		StringBuilder sb = new StringBuilder();
		
		for(int i=0; i<buffer.length; i++) {
			if(i<bytesLoaded) {
				sb.append(bytetohex(buffer[i]));
			} else {
				sb.append("  ");
			}
			if(i==7) {
				sb.append("|");
			} else {
				sb.append(" ");
			}
		}
		
		sb.append("| ");
		for(int i=0; i<bytesLoaded; i++) {
			if(buffer[i]>=START_CHAR && buffer[i]<=END_CHAR) {
				sb.append((char)buffer[i]);
			} else {
				sb.append(".");
			}
		}
		
		sb.append("\n");
		
		return sb.toString();
	}
	
	/**
	 * Helper method which turns given string with length of 8 padded with zeros on the left.
	 * @param input input string
	 * @return padded string
	 */
	private String padLeftWithZeros(String input) {
		return ("00000000" + input).substring(input.length());
	}
}
