package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * This class describes command which rotates direction that turtle is facing.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class RotateCommand implements Command {
	/**
	 * Angle which turtle direction will be rotated by.
	 */
	private double angle;
	
	/**
	 * Default constructor for RotateCommand
	 * @param angle angle
	 */
	public RotateCommand(double angle) {
		super();
		this.angle = angle;
	}

	/**
	 * Executes RotateCommand. Rotates turtle's direction by given angle.
	 * @param ctx reference to Context stack
	 * @param painter reference to Painter
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState current = ctx.getCurrentState();
		current.getAngle().rotate(angle);
	}

}
