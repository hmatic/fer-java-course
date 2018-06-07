package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Represents number elements of type Integer in {@link hr.fer.zemris.java.custom.scripting.nodes.EchoNode EchoNode} 
 * or {@link hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode ForLoopNode} 
 * for {@link hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser SmartScriptParser}.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class ElementConstantInteger extends Element {
	/**
	 * Element value.
	 */
	private int value;

	/**
	 * Default constructor for ElementConstantInteger.
	 * @param value value of number
	 */
	public ElementConstantInteger(int value) {
		super();
		this.value = value;
	}

	/**
	 * Returns ElementConstantInteger value as a double.
	 * @return value of ElementConstantInteger
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Returns String representation of ElementConstantInteger.
	 * @return ElementConstantInteger value as text
	 */
	@Override
	public String asText() {
		return Integer.toString(value);
	}
}
