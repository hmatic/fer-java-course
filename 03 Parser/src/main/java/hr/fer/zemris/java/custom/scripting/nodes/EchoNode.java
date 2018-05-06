package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * EchoNode is representation of EchoTag which stores unlimited amount of {@link hr.fer.zemris.java.custom.scripting.elems.Element Elements}.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 * @see Node
 */
public class EchoNode extends Node {
	/**
	 * Array of elements contained in EchoNode.
	 */
	private Element[] elements;

	/**
	 * Default constructor for EchoNode.
	 * @param elements array of elements
	 */
	public EchoNode(Element[] elements) {
		this.elements = elements;
	}
	
	/**
	 * Returns string representation of EchoNode.
	 * 
	 * @return EchoNode as text
	 */
	public String asText() {
		StringBuilder sb = new StringBuilder();
		sb.append("{$= ");
		for(int i=0; i<elements.length; i++) {
			sb.append(elements[i].asText());
			sb.append(" ");
		}
		sb.append("$}");
		return sb.toString();
	}
}
