package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Represents number elements of type Double in {@link hr.fer.zemris.java.custom.scripting.nodes.EchoNode EchoNode} 
 * or {@link hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode ForLoopNode} 
 * for {@link hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser SmartScriptParser}.
 * Valid function names start with @ followed by letter and rest can be zero or more letters, digits and underscores.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class ElementFunction extends Element {
	/**
	 * Function name.
	 */
	private String name;

	/**
	 * Default constructor for ElementFunction.
	 * 
	 * @param name name of function
	 */
	public ElementFunction(String name) {
		super();
		this.name = name;
	}

	/**
	 * Returns ElementFunction name.
	 * @return name of element
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns String representation of ElementFunction.
	 * @return ElementFunction name as text
	 */
	@Override
	public String asText() {
		return "@" + name;
	}
}
