package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * This class describes command which moves turtle without leaving any trail.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class SkipCommand implements Command {
	/**
	 * Number of steps turtle will move.
	 */
	private double step;
	
	/**
	 * Default constructor for SkipCommand.
	 * @param step number of steps turtle will move
	 */
	public SkipCommand(double step) {
		super();
		this.step = step;
	}

	/**
	 * Executes SkipCommand.
	 * @param ctx reference to Context stack
	 * @param painter reference to Painter
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState current = ctx.getCurrentState();
		
		// Current position of turtle.
		Vector2D origin = current.getOrigin();
		// Current turtle position translated by angle vector which is scaled by number of steps multiplied by step distance.
		Vector2D destination = origin.translated(current.getAngle().scaled(step*current.getDistance()));
		
		current.setOrigin(destination); // Change current position to new one.
	}

}
