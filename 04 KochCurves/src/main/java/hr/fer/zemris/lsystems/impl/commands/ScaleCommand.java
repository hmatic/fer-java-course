package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * This class describes command which scales turtle's step distance.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class ScaleCommand implements Command {
	/**
	 * Coefficient that scales turtle step.
	 */
	private double factor;
	
	/**
	 * Default constructor for ScaleCommand.
	 * @param factor scaling coefficient
	 */
	public ScaleCommand(double factor) {
		super();
		this.factor = factor;
	}

	/**
	 * Executes ScaleCommand. Scales distance of turtle step.
	 * @param ctx reference to Context stack
	 * @param painter reference to Painter
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState current = ctx.getCurrentState();
		current.setDistance(current.getDistance()*factor);		
	}

}
