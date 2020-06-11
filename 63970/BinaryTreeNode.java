package code;

import given.Entry;

/*
 * The binary node class which extends the entry class.
 * This will be used in linked tree implementations
 * 
 */
public class BinaryTreeNode<Key, Value> extends Entry<Key, Value> {

	public BinaryTreeNode<Key, Value> leftChildren;
	public BinaryTreeNode<Key, Value> parent;
	public BinaryTreeNode<Key, Value> rightChildren;

	public BinaryTreeNode(Key k, Value v) {
		super(k, v);
		leftChildren = null;
		parent = null;
		rightChildren = null;
	}

	public Key getKey() {
		return k;
	}

	public void setKey(Key key) {
		this.k = key;
	}

	public Value getValue() {
		return v;
	}

	public void setValue(Value value) {
		this.v = value;
	}

	public BinaryTreeNode<Key, Value> getLeft() {
		return leftChildren;
	}

	public void setLeft(BinaryTreeNode<Key, Value> left) {

		this.leftChildren = left;
	}

	public BinaryTreeNode<Key, Value> getRight() {
		return rightChildren;
	}

	public void setRight(BinaryTreeNode<Key, Value> right) {
		this.rightChildren = right;
	}

	public BinaryTreeNode(Key k, Value v, BinaryTreeNode<Key, Value> parent) {
		super(k, v);
		this.parent = parent;
	}

	public void setLeftChild(BinaryTreeNode<Key, Value> leftChild) {
		this.leftChildren = leftChild;
	}

	public void setRightChild(BinaryTreeNode<Key, Value> rightChild) {
		this.rightChildren = rightChild;
	}

	public BinaryTreeNode<Key, Value> getParent() {
		return parent;
	}

	public void setParent(BinaryTreeNode<Key, Value> parent) {
		this.parent = parent;
	}

	public BinaryTreeNode<Key, Value> getLeftChild() {
		return leftChildren;
	}

	public BinaryTreeNode<Key, Value> getRightChild() {
		return rightChildren;
	}
}
