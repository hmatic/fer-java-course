package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * This class describes command which removes turtle state from the top of the Context stack.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class PopCommand implements Command {

	/**
	 * Executes PopCommand. Pops top of the Context stack.
	 * @param ctx reference to Context stack
	 * @param painter reference to Painter
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.popState();		
	}

}
