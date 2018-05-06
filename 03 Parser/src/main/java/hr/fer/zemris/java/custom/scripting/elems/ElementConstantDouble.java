package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Represents number elements of type Double in {@link hr.fer.zemris.java.custom.scripting.nodes.EchoNode EchoNode} 
 * or {@link hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode ForLoopNode} 
 * for {@link hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser SmartScriptParser}.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class ElementConstantDouble extends Element {
	private double value;

	/**
	 * Default constructor for ElementConstantDouble.
	 * @param value value of number
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}
	
	/**
	 * Returns ElementConstantDouble value as a double.
	 * @return value of ElementConstantDouble
	 */
	public double getValue() {
		return value;
	}
	
	/**
	 * Returns String representation of ElementConstantDouble.
	 * @return ElementConstantDouble value as text
	 */
	@Override
	public String asText() {
		return Double.toString(value);
	}
}
