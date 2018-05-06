package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Represents elements in {@link hr.fer.zemris.java.custom.scripting.nodes.EchoNode EchoNode} 
 * or {@link hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode ForLoopNode} 
 * for {@link hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser SmartScriptParser}.
 * It is base class for all other elements.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 * @see ElementConstantDouble
 * @see ElementConstantInteger
 * @see ElementFunction
 * @see ElementOperator
 * @see ElementString
 * @see ElementVariable
 */
public class Element {
	
	/**
	 * Returns String representation of element.
	 * 
	 * @return String representation of element
	 */
	public String asText() {
		return "";
	}
}
