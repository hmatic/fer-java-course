package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * This class describes command which changes current color of turtle.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class ColorCommand implements Command {
	/**
	 * Reference to new color.
	 */
	private Color color;
	
	/**
	 * Default constructor for ColorCommand.
	 * @param color new color
	 */
	public ColorCommand(Color color) {
		super();
		this.color = color;
	}

	/**
	 * Executes color switching.
	 * @param ctx reference to Context stack
	 * @param painter reference to Painter
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState current = ctx.getCurrentState();
		current.setColor(color);
	}

}
