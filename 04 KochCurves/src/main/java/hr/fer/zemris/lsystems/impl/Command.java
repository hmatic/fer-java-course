package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * Interface models command classes which describe all actions that {@link TurtleState turtle} can make.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public interface Command {
	/**
	 * Executes current command.
	 * 
	 * @param ctx reference to Context stack
	 * @param painter reference to Painter
	 */
	void execute(Context ctx, Painter painter);

}
