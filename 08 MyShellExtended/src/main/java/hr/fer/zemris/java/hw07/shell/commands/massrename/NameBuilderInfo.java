package hr.fer.zemris.java.hw07.shell.commands.massrename;

/**
 * Interface which models object that stores information shared by all NameBuilders.
 * 
 * @author Hrvoje Matić
 */
public interface NameBuilderInfo {
	/**
	 * Getter for StringBuilder used by NameBuilder to generate new file names.
	 * @return
	 */
	StringBuilder getStringBuilder();
	/**
	 * Getter for group matched by matcher at given index.
	 * @param index group index
	 * @return group value
	 */
	String getGroup(int index);

}
