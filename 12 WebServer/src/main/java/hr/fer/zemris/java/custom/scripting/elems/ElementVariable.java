package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Represents variable elements in {@link hr.fer.zemris.java.custom.scripting.nodes.EchoNode EchoNode} 
 * or {@link hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode ForLoopNode} 
 * for {@link hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser SmartScriptParser}.
 * Valid variable names are the ones that start with letter and can contain either letters, digits or underscores.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class ElementVariable extends Element {
	/**
	 * Variable name.
	 */
	private String name;

	/**
	 * Default constructor for ElementVariable.
	 * 
	 * @param name name of variable
	 */
	public ElementVariable(String name) {
		super();
		this.name = name;
	}
	
	/**
	 * Returns name of ElementVariable.
	 * 
	 * @return name of variable
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns string representation of ElementVariable.
	 * 
	 * @return variable as text
	 */
	@Override
	public String asText() {
		return name;
	}
}
