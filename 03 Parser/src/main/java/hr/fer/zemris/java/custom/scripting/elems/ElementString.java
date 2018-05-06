package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Represents String elements in {@link hr.fer.zemris.java.custom.scripting.nodes.EchoNode EchoNode} 
 * or {@link hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode ForLoopNode} 
 * for {@link hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser SmartScriptParser}.
 * Valid strings start and end with quote marks(").
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class ElementString extends Element {
	private String value;

	/**
	 * Default constructor for ElementString.
	 * 
	 * @param value string value
	 */
	public ElementString(String value) {
		super();
		this.value = value;
	}

	/**
	 * Returns value of ElementString.
	 * 
	 * @return value of ElementString
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * Returns string representation of ElementString.
	 * 
	 * @return string as text
	 */
	@Override
	public String asText() {
		return "\"" + value + "\"";
	}
}
