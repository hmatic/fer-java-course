package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Stack;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * SmartScriptEngine executes SmartScript files (".smscr").
 * Stores request context object which stores all relevant data and writes to output stream.
 * @author Hrvoje Matic
 *
 */
public class SmartScriptEngine {
	/**
	 * Starting node of SmartScript.
	 */
	private DocumentNode documentNode;
	/**
	 * Request context.
	 */
	private RequestContext requestContext;
	/**
	 * SmartScriptEngine stack
	 */
	private ObjectMultistack multistack = new ObjectMultistack();
	/**
	 * Visitor implementation.
	 */
	private INodeVisitor visitor = new INodeVisitor() {

		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText());
			} catch (IOException e) {
				throw new SmartScriptEngineException("IOException in TextNode");
			}	
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			ValueWrapper variableValue = new ValueWrapper(
					Integer.parseInt(node.getStartExpression().asText()));
			Integer endValue = Integer.parseInt(node.getEndExpression().asText());
			Integer stepValue = Integer.parseInt(node.getStepExpression().asText());
			
			multistack.push(node.getVariable().asText(), 
					variableValue);
			while(variableValue.numCompare(endValue)!=1) {
				for(int i=0, max=node.numberOfChildren(); i<max; i++) {
					node.getChild(i).accept(this);
				}
				variableValue.add(stepValue);
			}
			multistack.pop(node.getVariable().asText());
			
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			Stack<Object> temp = new Stack<>();
			for(Element elem : node.getElements()) {
				if(elem.getClass()==ElementConstantDouble.class) {
					temp.push(((ElementConstantDouble) elem).getValue());
				} else if(elem.getClass()==ElementConstantInteger.class) {
					temp.push(((ElementConstantInteger) elem).getValue());
				} else if(elem.getClass()==ElementString.class) {
					temp.push(((ElementString) elem).getValue());
				} else if(elem.getClass()==ElementOperator.class) {
					executeOperation(temp, ((ElementOperator) elem).getSymbol());
				} else if(elem.getClass()==ElementVariable.class) {
					temp.push(multistack.peek(((ElementVariable)elem).getName()).getValue());
				} else if(elem.getClass()==ElementFunction.class) {
					executeFunction(((ElementFunction) elem).getName(), temp);
				}
			}
			
			temp.forEach(e -> {
				try {
					requestContext.write(e.toString());
				} catch (IOException e1) {
					throw new SmartScriptEngineException("IOException in EchoNode");
				}
			});
			
		}

		/**
		 * Execute arithmetic operation. 
		 * Method takes 2 values from temporary stack, performs operation on them and
		 * pushes result back to temp stack.
		 * Supported operations are: +, -, *, /
		 * @param temp temporary stack
		 * @param symbol operation symbol
		 */
		private void executeOperation(Stack<Object> temp, String symbol) {
			Object second = new ValueWrapper(temp.pop()).getValue();
			ValueWrapper first = new ValueWrapper(temp.pop());
			
			try {
				switch(symbol) {
				case "+":
					first.add(second);
					break;
				case "-":
					first.subtract(second);
					break;
				case "/":
					first.divide(second);
					break;
				case "*":
					first.multiply(second);
					break;
				}
			} catch (IllegalArgumentException e) {
				throw new SmartScriptEngineException("Can not do operations on given parameters.");
			}
			temp.push(first.getValue());
		}
		
		/**
		 * Execute SmartScript function given in format of: "@functionname".
		 * @param name function name
		 * @param temp temporary stack
		 */
		private void executeFunction(String name, Stack<Object> temp) {
			switch(name.toLowerCase()) {
				case "sin":
					Object value = temp.pop();
					ValueWrapper wrapper = new ValueWrapper(value);
					Double parsedValue = Double.parseDouble(wrapper.getValue().toString());
					temp.push(Math.sin(Math.toRadians(parsedValue)));
					break;
				case "decfmt":
					DecimalFormat dcmft = new DecimalFormat(temp.pop().toString());
					temp.push(dcmft.format(new ValueWrapper(temp.pop()).getValue()));
					break;
				case "dup":
					temp.push(temp.peek());
					break;
				case "swap":
					Object a = temp.pop();
					Object b = temp.pop();
					temp.push(a);
					temp.push(b);
					break;
				case "setmimetype":
					requestContext.setMimeType(temp.pop().toString());
					break;
				case "paramget":
					getParameter(temp, param -> requestContext.getParameter(param));
					break;
				case "pparamget":
					getParameter(temp, param -> requestContext.getPersistentParameter(param));
					break;
				case "pparamset":
					setParameter(temp, (k,v) -> requestContext.setPersistentParameter(k,v));
					break;
				case "pparamdel":
					delParameter(temp, k -> requestContext.removePersistentParameter(k));
					break;
				case "tparamget":
					getParameter(temp, param -> requestContext.getTemporaryParameter(param));
					break;
				case "tparamset":
					setParameter(temp, (k,v) -> requestContext.setTemporaryParameter(k,v));
					break;
				case "tparamdel":
					delParameter(temp, k -> requestContext.removeTemporaryParameter(k));
					break;
				default:
					throw new SmartScriptEngineException("Invalid function name given in script.");
			}
			
		}

		/**
		 * Get parameter from collection given by getter method handle.
		 * @param temp temporary stack
		 * @param getter handle to setter method
		 */
		private void getParameter(Stack<Object> temp, Function<String, String> getter) {
			String df = temp.pop().toString();
			String name = temp.pop().toString();
			String value = getter.apply(name);
			temp.push(value==null ? df : value);	
		}
		
		/**
		 * Set parameter in collection given by setter method handle.
		 * @param temp temporary stack
		 * @param setter handle to setter method
		 */
		private void setParameter(Stack<Object> temp, BiConsumer<String, String> setter) {
			String name = temp.pop().toString();
			String value = temp.pop().toString();
	
			setter.accept(name, value);
		}
		
		/**
		 * Delete parameter from collection given by delete method handle.
		 * @param temp temporary stack
		 * @param deleter handle to delete method
		 */
		private void delParameter(Stack<Object> temp, Consumer<String> deleter) {
			String name = temp.pop().toString();
			deleter.accept(name);
		}
		

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for(int i=0, max=node.numberOfChildren(); i<max; i++) {
				node.getChild(i).accept(this);
			}
		}
		
	};

	/**
	 * Default constructor for SmartScriptEngine.
	 * @param documentNode starting node acquired from parser
	 * @param requestContext request context
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		super();
		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}

	/**
	 * Executes SmartScriptEngine on document node.
	 */
	public void execute() {
		documentNode.accept(visitor);
	}
}
