package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Models visitor for node tree parsed by SmartScriptParser.
 * There are 4 possible nodes: DocumentNode(starting node), EchoNode,
 * TextNode and ForLoopNode.
 * Only DocumentNode and ForLoopNode can have children.
 * @author Hrvoje Matic
 */
public interface INodeVisitor {
	/**
	 * Visitor behavior implementation when it visits TextNode.
	 * @param node visited node
	 */
	public void visitTextNode(TextNode node);
	/**
	 * Visitor behavior implementation when it visits ForLoopNode.
	 * @param node visited node
	 */
	public void visitForLoopNode(ForLoopNode node);
	/**
	 * Visitor behavior implementation when it visits EchoNode.
	 * @param node visited node
	 */
	public void visitEchoNode(EchoNode node);
	/**
	 * Visitor behavior implementation when it visits DocumentNode.
	 * @param node visited node
	 */
	public void visitDocumentNode(DocumentNode node);
}

