package hr.fer.zemris.java.hw07.shell;

import java.nio.file.Path;
import java.util.SortedMap;

import hr.fer.zemris.java.hw07.shell.commands.ShellCommand;

/**
 * Interface which models Shell environment. All communication with user is through this interface.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public interface Environment {
	/**
	 * Reads a single line from user input stream.
	 * 
	 * @return read line as String
	 * @throws ShellIOException if IOException is thrown
	 */
	String readLine() throws ShellIOException;

	/**
	 * Writes text given in argument to user without adding new line.
	 * 
	 * @param text given text
	 * @throws ShellIOException if IOException is thrown
	 */
	void write(String text) throws ShellIOException;

	/**
	 * Writes text given in argument to user in one line.
	 * 
	 * @param text given text
	 * @throws ShellIOException if IOException is thrown
	 */
	void writeln(String text) throws ShellIOException;

	/**
	 * Returns unmodifiable sorted map of available commands.
	 * 
	 * @return map of commands
	 */
	SortedMap<String, ShellCommand> commands();

	/**
	 * Getter for MULTILINE symbol.
	 * 
	 * @return MULTILINE symbol
	 */
	Character getMultilineSymbol();

	/**
	 * Setter for MULTILINE symbol.
	 * 
	 * @param symbol new symbol value
	 */
	void setMultilineSymbol(Character symbol);
	
	/**
	 * Getter for PROMPT symbol.
	 * 
	 * @return PROMPT symbol
	 */
	Character getPromptSymbol();

	/**
	 * Setter for PROMPT symbol.
	 * 
	 * @param symbol new symbol value
	 */
	void setPromptSymbol(Character symbol);
	
	/**
	 * Getter for MORELINES symbol.
	 * 
	 * @return MORELINES symbol
	 */
	Character getMorelinesSymbol();

	/**
	 * Setter for MORELINES symbol.
	 * 
	 * @param symbol new symbol value
	 */
	void setMorelinesSymbol(Character symbol);
	
	/**
	 * Getter for current directory.
	 * 
	 * @return current directory
	 */
	Path getCurrentDirectory();
	
	/**
	 * Setter for current directory.
	 * 
	 * @param path new current directory path
	 */
	void setCurrentDirectory(Path path);
	
	/**
	 * Returns object storing shared data between commands. 
	 * Object is placed inside map so method takes one argument which is key.
	 * @param key map key
	 * @return object storing shared data
	 */
	Object getSharedData(String key);
	
	/**
	 * Puts object for storing shared data into map of shared data objects.
	 * @param key map key
	 * @param value reference to object storing shared data
	 */
	void setSharedData(String key, Object value);
}
