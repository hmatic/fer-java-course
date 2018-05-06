package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program creates binary search tree (BST) and asks user to input integer numbers.
 * BST is implemented as tree with smaller values on the left and does not accept duplicate values.
 * Program ends when user inputs "kraj" and prints values in both ascending and descending order.
 * 
 * @author Hrvoje Matić
 * @version 1.0
 */
public class UniqueNumbers {
	
	/**
	 * Structure that represents Binary Search Tree node. 
	 * Stores value of node as Integer and references to left and right node.
	 */
	static class TreeNode {
		TreeNode left;
		TreeNode right;
		int value;
	}
	
	/**
	 * Method called upon start of the program. Arguments explained below.
	 * @param args Arguments from command line as array of Strings
	 */
	public static void main(String[] args) {
		TreeNode root = null;
		boolean end = false;
		
		try(Scanner scanner = new Scanner(System.in)) {
			while(!end) {
				System.out.print("Unesite broj > ");
				String input = scanner.nextLine();
				
				if(Validation.isValidInt(input)) {
					int value = Integer.parseInt(input);
					
					if(!containsValue(root, value)) {
						root = addNode(root, value);
						System.out.println("Dodano");
					} else {
						System.out.println("Broj već postoji. Preskačem.");
					}
				} else if(input.equals("kraj")) {
					end = true;
				} else {
					System.out.println("'" + input  + "' nije cijeli broj.");
				}
			}
		}
		
		System.out.print("Ispis od najmanjeg: ");
		printSortedAscending(root);
		System.out.print("\nIspis od najveceg: ");
		printSortedDescending(root);
	}
	
	/**
	 * Adds new node to binary tree if node with that value doesn't already exist.
	 * @param root reference to the root of binary tree
	 * @param number value that needs to be added to tree
	 * @return reference to the root of binary tree
	 */
	public static TreeNode addNode(TreeNode root, int value) {
		if(root == null) {
			root = new TreeNode();
			root.value = value;
			return root;
		}
		
		if(value<root.value) {
			root.left = addNode(root.left, value);
		} else if(value>root.value) {
			root.right = addNode(root.right, value);
		}
		
		return root;
	}
	
	/**
	 * Recursive method that counts number of nodes in binary tree.
	 * @param root reference to the root of binary tree
	 * @return number of tree nodes
	 */
	public static int treeSize(TreeNode root) {
		if(root==null) return 0;
		else {
			return 1 + treeSize(root.left) + treeSize(root.right);
		}
	}
	
	/**
	 * Recursive method that checks if value exists in binary tree.
	 * @param root reference to the root of binary tree
	 * @param value value to be checked
	 * @return true if value exists in tree, false otherwise
	 */
	public static boolean containsValue(TreeNode root, int value) {
		if(root!=null && value==root.value) {
			return true;
		} else if(root!=null && value<root.value) {
			return containsValue(root.left, value);
		} else if(root!=null && value>root.value) {
			return containsValue(root.right, value);
		}		
		return false;
	}
	
	/**
	 * Prints all values of binary tree in ascending order.
	 * @param root reference to the root of binary tree
	 */
	public static void printSortedAscending(TreeNode root) {
		if(root==null) return;
		
		printSortedAscending(root.left);
		System.out.print(root.value + " ");
		printSortedAscending(root.right);
	}
	
	/**
	 * Prints all values of binary tree in descending order.
	 * @param root reference to the root of binary tree
	 */
	public static void printSortedDescending(TreeNode root) {
		if(root==null) return;
		
		printSortedDescending(root.right);
		System.out.print(root.value + " ");
		printSortedDescending(root.left);
	}
}
