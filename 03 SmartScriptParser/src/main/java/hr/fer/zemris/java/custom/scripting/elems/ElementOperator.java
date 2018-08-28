package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Represents operator elements in {@link hr.fer.zemris.java.custom.scripting.nodes.EchoNode EchoNode} 
 * or {@link hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode ForLoopNode} 
 * for {@link hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser SmartScriptParser}.
 * Valid operators are: + (plus), - (minus), * (multiplication), / (division), ^ (power).
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class ElementOperator extends Element {
	private String symbol;

	/**
	 * Default constructor for ElementOperator.
	 * 
	 * @param symbol operator symbol
	 */
	public ElementOperator(String symbol) {
		super();
		this.symbol = symbol;
	}

	/**
	 * Returns operator symbol.
	 * 
	 * @return operator symbol
	 */
	public String getSymbol() {
		return symbol;
	}
	
	/**
	 * Returns String representation of ElementOperator.
	 * 
	 * @return operator symbol as text
	 */
	@Override
	public String asText() {
		return symbol;
	}
}
