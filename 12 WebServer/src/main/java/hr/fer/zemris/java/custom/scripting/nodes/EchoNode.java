package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * EchoNode is representation of EchoTag which stores unlimited amount of {@link hr.fer.zemris.java.custom.scripting.elems.Element Elements}.
 * 
 * @author Hrvoje Matić
 * @version 1.1
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
	 * Getter for elements array
	 * @return elements array
	 */
	public Element[] getElements() {
		return elements;
	}


	@Override
	public void addChildNode(Node child) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Node getChild(int index) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public int numberOfChildren() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{$= ");
		for(int i=0; i<elements.length; i++) {
			sb.append(elements[i].asText());
			sb.append(" ");
		}
		sb.append("$}");
		return sb.toString();
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitEchoNode(this);
	}
}
