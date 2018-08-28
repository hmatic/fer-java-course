package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * TextNode is representation of TEXT in SmartScriptParser.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 * @see Node
 */
public class TextNode extends Node {
	/**
	 * Text value.
	 */
	private String text;

	/**
	 * Default constructor for TextNode.
	 * 
	 * @param text text value of node
	 */
	public TextNode(String text) {
		super();
		this.text = text;
	}
	
	/**
	 * Returns string representation of TextNode.
	 * 
	 * @return TextNode as text
	 */
	public String asText() {
		return text;
	}
	
}
