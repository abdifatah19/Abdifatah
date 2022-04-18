package edu.metrostate.ics340.p1.aa5013;
public class Node<T> implements TreeNode<T> {

	private T value;

	int count;

	private Node<T> leftChild = null, rightChild = null;

	public Node(T value, int count, Node<T> leftChild, Node<T> rightChild) {
		this.value = value;
		this.count = count;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
	}

	public Node(T value, int count) {
		this.value = value;
		this.count = count;
	}

	public Node() {
	}

	/*
	 * this method checks if node is leaf. node is leaf if it does not have left and
	 * right child.
	 */
	public static boolean isLeaf(Node n) {
		return n.leftChild == null && n.rightChild == null;
	}

	public Node<T> getLeftChild() {
		return leftChild;
	}

	public Node<T> getRightChild() {
		return rightChild;
	}

	public T getValue() {
		return value;
	}

}
