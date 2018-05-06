package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.*;
import hr.fer.zemris.math.Vector2D;

/**
 * LSystemBuilder is class which builds new LSystem. Used to configure all commands, productions and starting parameters of turtle.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class LSystemBuilderImpl implements LSystemBuilder {
	/**
	 * Dictionary/map which stores all defined commands.
	 */
	private Dictionary commands;
	/**
	 * Dictionary/map which stores all defined productions.
	 */
	private Dictionary productions;
	/**
	 * Distance of one turtle step.
	 */
	private double unitLength;
	/**
	 * Coefficient used to scale produced fractals so dimensions fit inside screen.
	 */
	private double unitLengthDegreeScaler;
	/**
	 * Starting position of turtle.
	 */
	private Vector2D origin;
	/**
	 * Starting angle that turtle faces.
	 */
	private double angle;
	/**
	 * Starting axiom used by productions.
	 */
	private String axiom;
	
	/**
	 * Default constructor. Creates new instances of dictionaries and sets variables to their default values.
	 */
	public LSystemBuilderImpl() {
		commands = new Dictionary();
		productions = new Dictionary();
		unitLength = 0.1;
		unitLengthDegreeScaler = 1;
		origin = new Vector2D(0, 0);
		angle = 0;
		axiom = "";
	}

	/**
	 * Inner nested class used to build new LSystem.
	 * @author Hrvoje Matić
	 * @version 1.0
	 */
	public class LSystemImpl implements LSystem {

		/**
		 * Generates and draws fractal using Painter.
		 * @param arg0 level of production
		 * @param arg1 reference to Painter
		 */
		@Override
		public void draw(int arg0, Painter arg1) {
			String produced = generate(arg0);
			Context ctx = new Context();
			TurtleState turtle = new TurtleState(origin, new Vector2D(1,0).rotated(angle), new Color(0,0,0), unitLength*Math.pow(unitLengthDegreeScaler,arg0));
			ctx.pushState(turtle);
			
			for (int i = 0; i < produced.length(); i++){
			    char c = produced.charAt(i); 
			    
			    if(commands.get(c)!=null) {
			    	String commandString = (String)commands.get(c);
			    	
			    	Command command = determineCommand(commandString);
			    	command.execute(ctx, arg1);
			    	
			    }
			}
		}

		/**
		 * Generates new production as String using given production rules.
		 * Each symbol that has production rule is expanded with every iteration.
		 * @param arg0 level of production
		 */
		@Override
		public String generate(int arg0) {
			String produced = axiom;
			StringBuilder sb = new StringBuilder();
			
			for(int i=0; i<arg0; i++) {
				for (int j = 0; j < produced.length(); j++){
				    char c = produced.charAt(j); 
				    
				    if(productions.get(c)!=null) {
				    	sb.append(productions.get(c));
				    } else {
				    	sb.append(c);
				    }
				}
				produced = sb.toString();
				sb.setLength(0);
			}
			return produced;
		}
		
		/**
		 * Determines command from given String and returns instance of that command with additional parameters.
		 * @param commandString string to be determined
		 * @return instance of Command class
		 */
		private Command determineCommand(String commandString) {
			commandString.toLowerCase().trim().replaceAll(" +", " ");
			
			String[] splittedCommandString = commandString.split(" ");
			String commandName = splittedCommandString[0];
			String commandArg = "";
			if(splittedCommandString.length==2) {
				commandArg = splittedCommandString[1];
			}
			
			Command command;
			switch(commandName) {
				case "draw":					
					command = new DrawCommand(Double.parseDouble(commandArg));
					return command;
				case "skip":
					command = new SkipCommand(Double.parseDouble(commandArg));
					return command;
				case "scale":
					command = new ScaleCommand(Double.parseDouble(commandArg));
					return command;
				case "rotate":
					command = new RotateCommand(Double.parseDouble(commandArg));
					return command;
				case "push":
					command = new PushCommand();
					return command;
				case "pop":
					command = new PopCommand();
					return command;
				case "color":
					command = new ColorCommand(Color.decode("#" + commandArg));
					return command;					
				default:
					throw new IllegalArgumentException("Can't parse this command");
			}	
		}
	}
	
	/**
	 * Builds new LSystem using new instance of inner class LSystemImpl.
	 * @return instance of LSystem
	 */
	@Override
	public LSystem build() {
		return new LSystemImpl();
	}

	/**
	 * Parses given array of Strings and configures new LSystemBuilder.
	 * @param arg0 array of input String configurations
	 * @return reference to current LSystemBuilder
	 */
	@Override
	public LSystemBuilder configureFromText(String[] arg0) {
		String[] input = arg0;
		for(int i=0; i<input.length; i++) {
			input[i].trim().replaceAll("\\s+", " ");
			
			if (input[i].length()!=0) {
				String[] splittedInput = input[i].split("[\\s]+", 2);
				String keyword = splittedInput[0];
			
				switch(keyword) {
					case "origin":
						String[] originArguments = splittedInput[1].split("[\\s]+");
						setOrigin(Double.parseDouble(originArguments[0]), Double.parseDouble(originArguments[1]));
						break;
					case "angle":
						setAngle(Double.parseDouble(splittedInput[1]));
						break;
					case "unitLength":
						setUnitLength(Double.parseDouble(splittedInput[1]));
						break;
					case "unitLengthDegreeScaler":
						String[] scalerArguments = splittedInput[1].split("[\\s/]+");
						if(scalerArguments.length==1) {
							setUnitLengthDegreeScaler(Double.parseDouble(scalerArguments[0]));
						} else {	
							setUnitLengthDegreeScaler(Double.parseDouble(scalerArguments[0]) / Double.parseDouble(scalerArguments[1]));
						}
						break;
					case "command":
						String[] commandArguments = splittedInput[1].split("[\\s]+", 2);
						registerCommand(commandArguments[0].charAt(0), commandArguments[1]);
						break;
					case "axiom":
						setAxiom(splittedInput[1]);
						break;
					case "production":
						String[] productionArguments = splittedInput[1].split("[\\s]+", 2);
						registerProduction(productionArguments[0].charAt(0), productionArguments[1]);
						break;
					default:
						break;
				}
			}
		}
		return this;
	}

	/**
	 * Registers new command definition for a given symbol.
	 * @param arg0 symbol for command execution
	 * @param arg1 command definition
	 * @return reference to current LSystemBuilder
	 */
	@Override
	public LSystemBuilder registerCommand(char arg0, String arg1) {
		commands.put(arg0, arg1);
		return this;
	}

	/**
	 * Registers new production definition for a given symbol
	 * @param arg0 given symbol
	 * @param arg1 production definition
	 * @return reference to current LSystemBuilder
	 */
	@Override
	public LSystemBuilder registerProduction(char arg0, String arg1) {
		productions.put(arg0,  arg1);
		return this;
	}

	/**
	 * Sets starting angle of turtle in degrees.
	 * @param arg0 starting angle in degrees
	 * @return reference to current LSystemBuilder
	 */
	@Override
	public LSystemBuilder setAngle(double arg0) {
		angle = arg0;
		return this;
	}

	/**
	 * Sets starting axiom of LSystemBuilder.
	 * @param arg0 starting axiom
	 * @return reference to current LSystemBuilder
	 */
	@Override
	public LSystemBuilder setAxiom(String arg0) {
		axiom = arg0;
		return this;
	}

	/**
	 * Sets starting turtle position.
	 * @param arg0 x coordinate of starting position
	 * @param arg1 y coordinate of starting position
	 * @return reference to current LSystemBuilder
	 */
	@Override
	public LSystemBuilder setOrigin(double arg0, double arg1) {
		origin = new Vector2D(arg0, arg1);
		return this;
	}
	
	/**
	 * Sets turtle's step distance.
	 * @param arg0 step distance
	 * @return reference to current LSystemBuilder
	 */
	@Override
	public LSystemBuilder setUnitLength(double arg0) {
		unitLength = arg0;
		return this;
	}

	/**
	 * Sets unitLengthDegreeScaler which is used to scale produced fractals so they fit screen.
	 * @param arg0 scaler coefficient
	 * @return reference to current LSystemBuilder
	 */
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double arg0) {
		unitLengthDegreeScaler = arg0;
		return this;
	}

}
