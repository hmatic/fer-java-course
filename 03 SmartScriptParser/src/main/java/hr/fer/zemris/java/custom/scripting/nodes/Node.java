package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * Base class for all other nodes.
 * 
 * @author Hrvoje Matić
 * @see DocumentNode
 * @see EchoNode
 * @see ForLoopNode
 * @see TextNode
 */
public class Node {
	/**
	 * Collection of node's children.
	 */
	public ArrayIndexedCollection children;
	
	/**
	 * Adds other non-null node to this node as its child.
	 * 
	 * @param child node to be added
	 */
	public void addChildNode(Node child) {
		if(children==null) {
			children = new ArrayIndexedCollection();
		}
		children.add(child);
	}
	
	/**
	 * Counts number of children node has.
	 * 
	 * @return number of child nodes
	 */
	public int numberOfChildren() {
		if(children==null) return 0;
		return children.size();
	}
	
	/**
	 * Returns specific child node found by its index in children collection.
	 * @param index index of child node
	 * @return child node on given index
	 */
	public Node getChild(int index) {
		if(index<0 || index>children.size()-1) {
			throw new IndexOutOfBoundsException("Valid index is in range of 0 to size-1. Was " + index);
		}
		
		return (Node)children.get(index);
	}
	
	/**
	 * Returns String representation of node.
	 * 
	 * @return node as text
	 */
	public String asText() {
		return "";
	}
}
