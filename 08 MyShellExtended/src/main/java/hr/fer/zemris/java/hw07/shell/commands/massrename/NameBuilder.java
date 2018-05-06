package hr.fer.zemris.java.hw07.shell.commands.massrename;

/**
 * Interface which models NameBuilder objects. 
 * NameBuilders are used to generate file names in massrename command.
 * @author Hrvoje
 *
 */
public interface NameBuilder {
	/**
	 * Executes current NameBuilder object.
	 * Takes NameBuilderInfo as argument where informations are shared between all NameBuilders.
	 * @param info reference to NameBuilderInfo
	 */
	void execute(NameBuilderInfo info);
}
