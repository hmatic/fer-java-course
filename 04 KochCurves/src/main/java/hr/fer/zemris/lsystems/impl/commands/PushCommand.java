package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * This class describes command which pushes copy of current turtle state to Context stack.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class PushCommand implements Command {

	/**
	 * Executes PushCommand. Pushes copy of current turtle state to the top of the stack.
	 * @param ctx reference to Context stack
	 * @param painter reference to Painter
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.pushState(ctx.getCurrentState().copy());
	}

}
