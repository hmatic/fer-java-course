package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * TextNode is representation of TEXT in SmartScriptParser.
 * 
 * @author Hrvoje Matić
 * @version 1.1
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
	 * Getter for TextNode text.
	 * @return TextNode text
	 */
	public String getText() {
		return text;
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
		return text;
	}
	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitTextNode(this);
	}
	
}
