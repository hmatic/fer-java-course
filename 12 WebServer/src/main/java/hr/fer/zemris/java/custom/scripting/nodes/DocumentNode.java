package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Node which represents start of the document.
 * 
 * @author Hrvoje Matić
 * @version 1.1
 * @see Node
 */
public class DocumentNode extends Node {

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitDocumentNode(this);
	}
	
}
