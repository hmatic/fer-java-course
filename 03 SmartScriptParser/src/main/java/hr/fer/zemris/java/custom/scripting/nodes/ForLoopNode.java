package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * ForLoopNode is representation of ForLoopTag which stores 3 or 4 values.
 * First value must be ElementVariable, while rest can be any Element.
 * Step expression is optional so it can be null.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 * @see Node
 */
public class ForLoopNode extends Node {
	/**
	 * FOR variable.
	 */
	private ElementVariable variable;
	/**
	 * Start value.
	 */
	private Element startExpression;
	/**
	 * End value.
	 */
	private Element endExpression;
	/**
	 * Step value.
	 */
	private Element stepExpression;
	
	/**
	 * Default constructor for ForLoopNode.
	 * 
	 * @param variable variable in for loop
	 * @param startExpression start expression
	 * @param endExpression end expression
	 * @param stepExpression step expression
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}
	
	/**
	 * Returns string representation of ForLoopNode.
	 * 
	 * @return ForLoopNode as text
	 */
	public String asText() {
		StringBuilder sb = new StringBuilder();
		sb.append("{$FOR ");
		sb.append(variable.asText() + " ");
		sb.append(startExpression.asText() + " ");
		sb.append(endExpression.asText() + " ");
		if(stepExpression!=null) sb.append(stepExpression.asText() + " ");
		sb.append("$}");
		return sb.toString();
	}
	
	
}
