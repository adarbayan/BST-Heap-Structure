package code;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import given.iBinarySearchTree;
import given.iMap;

/*
 * Implement a vanilla binary search tree using a linked tree representation
 * Use the BinaryTreeNode as your node class
 */

public class BinarySearchTree<Key, Value> implements iBinarySearchTree<Key, Value>, iMap<Key, Value> {

	/*
	 * 
	 * YOUR CODE BELOW THIS
	 * 
	 */
	private ArrayList<Key> keys = new ArrayList<Key>();
	private int size = 0;
	private BinaryTreeNode<Key, Value> root = new BinaryTreeNode<Key, Value>(null, null);;
	protected Comparator<Key> comparator;

	@Override
	public Value get(Key k) {
		return getValue(k);
	}

	@Override
	public Value put(Key k, Value v) {
		BinaryTreeNode<Key, Value> searchedNode = getNode(k);
		Value value = searchedNode.getValue();
		searchedNode.setValue(v);
		BinaryTreeNode<Key, Value> newValuedNode = searchedNode;
		if (isExternal(newValuedNode)) {
			newValuedNode.setKey(k);
			BinaryTreeNode<Key, Value> rightChild = new BinaryTreeNode<Key, Value>(null, null);
			BinaryTreeNode<Key, Value> parentOfRight = rightChild.parent;
			rightChild.parent = newValuedNode;
			rightChild.parent.rightChildren = rightChild;
			BinaryTreeNode<Key, Value> leftChild = new BinaryTreeNode<Key, Value>(null, null);
			leftChild.parent = newValuedNode;
			leftChild.parent.leftChildren = leftChild;
			size++;
		}
		return value;
	}

//this is taken from the book
	protected BinaryTreeNode<Key, Value> search(Key k, BinaryTreeNode<Key, Value> node) {
		if (isExternal(node)) {
			return node;
		}
		if (comparator.compare(k, node.getKey()) > 0) {
			return search(k, node.getRight());
		} else if (k.equals(node.getKey())) {
			return node;
		} else {
			return search(k, node.getLeft());
		}
	}

//this idea is taken from the instructor itself
	protected BinaryTreeNode<Key, Value> methodForTemp(BinaryTreeNode<Key, Value> node) {
		BinaryTreeNode<Key, Value> temp = node;
		while (node.getLeft() != null) {
			temp = node.getLeft();
			node = node.getLeft();
		}
		return temp.getParent();
	}

	@Override
	public Value remove(Key k) {
		BinaryTreeNode<Key, Value> node = search(k, root);
		Value value = node.getValue();
		if (isInternal(node)) {
			BinaryTreeNode<Key, Value> nodeRight = node.getRight();
			BinaryTreeNode<Key, Value> nodeRightRight = nodeRight.getRight();
			BinaryTreeNode<Key, Value> nodeRightLeft = nodeRight.getLeft();
			BinaryTreeNode<Key, Value> nodeLeft = node.getLeft();
			BinaryTreeNode<Key, Value> nodeLeftRight = nodeLeft.getRight();
			BinaryTreeNode<Key, Value> nodeLeftLeft = nodeLeft.getLeft();
			Key leftKey = nodeLeft.getKey();
			Value valueOfLeft = nodeLeft.getValue();
			Value valueOfRight = nodeRight.getValue();
			if (isExternal(nodeRight) && isExternal(nodeLeft)) {
				node.setRight(null);
				node.setLeft(null);
				node.setKey(null);
				node.setValue(null);
				size--;
			} else if (isExternal(nodeRight) || isExternal(nodeLeft)) {
				BinaryTreeNode<Key, Value> nodeLeft1 = node.getLeft();
				if (isInternal(nodeLeft)) {
					node.setRight(nodeLeftRight);
					nodeRight.setParent(node);
					node.setKey(leftKey);
					node.setLeft(nodeLeftLeft);
					node.getLeft().setParent(node);
					node.setValue(valueOfLeft);
					size--;
				} else {

					Key rightKey = nodeRight.getKey();
					node.setRight(nodeRightRight);
					node.getRight().setParent(node);
					node.setKey(rightKey);
					node.setLeft(nodeRightLeft);
					node.getLeft().setParent(node);
					node.setValue(valueOfRight);
					size--;
				}
			} else {
				BinaryTreeNode<Key, Value> searched = methodForTemp(nodeRight);
				BinaryTreeNode<Key, Value> searchedRight = searched.getRight();
				BinaryTreeNode<Key, Value> searchedLeft = searched.getLeft();
				BinaryTreeNode<Key, Value> searchedLeftLeft = searchedLeft.getLeft();
				BinaryTreeNode<Key, Value> searchedLeftRight = searchedLeft.getRight();
				BinaryTreeNode<Key, Value> searchedRightRight = searchedRight.getRight();
				BinaryTreeNode<Key, Value> searchedRightLeft = searchedRight.getLeft();
				Key searchedKey = searched.getKey();
				Key searchedRightKey = searchedRight.getKey();
				Key searchedLeftKey = searchedLeft.getKey();
				Value minLeftValue = searchedLeft.getValue();
				Value minRightValue = searchedRight.getValue();
				node.setKey(searchedKey);
				node.setValue(searched.getValue());

				if (isExternal(searchedRight) && isExternal(searchedLeft)) {
					searched.setRight(null);
					searched.setLeft(null);
					searched.setKey(null);
					searched.setValue(null);
					size--;
				} else if (isExternal(searchedRight) && isInternal(searchedLeft)) {
					searched.setRight(searchedLeftRight);
					searchedRight.setParent(searched);
					searched.setKey(searchedLeftKey);
					searched.setLeft(searchedLeftLeft);
					searchedLeft.setParent(searched);
					searched.setValue(minLeftValue);
					size--;
				} else if (isInternal(searchedRight) && isExternal(searchedLeft)) {
					searched.setRight(searchedRightRight);
					searchedRight.setParent(searched);
					searched.setKey(searchedRightKey);
					searched.setLeft(searchedRightLeft);
					searched.getLeft().setParent(searched);
					searched.setValue(minRightValue);
					size--;
				}
			}
		}
		return value;
	}

	@Override
	public Iterable<Key> keySet() {
		ArrayList<Key> keySet = new ArrayList<Key>();
		for (BinaryTreeNode<Key, Value> node : getNodesInOrder()) {
			keySet.add(node.getKey());
		}
		return keySet;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		if (size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public BinaryTreeNode<Key, Value> getRoot() {
		return root;
	}

	@Override
	public BinaryTreeNode<Key, Value> getParent(BinaryTreeNode<Key, Value> node) {
		return node.parent;
	}

	@Override
	public boolean isInternal(BinaryTreeNode<Key, Value> node) {
		if (node == null) {
			return false;
		} else if (node.leftChildren != null || node.rightChildren != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isExternal(BinaryTreeNode<Key, Value> node) {
		if (node == null) {
			return false;
		} else if (node.leftChildren == null && node.rightChildren == null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isRoot(BinaryTreeNode<Key, Value> node) {
		if (node == root) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public BinaryTreeNode<Key, Value> getNode(Key k) {
		BinaryTreeNode<Key, Value> node = search(k, root);
		return node;

	}

	@Override
	public Value getValue(Key k) {
		return getNode(k).getValue();
	}

	@Override
	public BinaryTreeNode<Key, Value> getLeftChild(BinaryTreeNode<Key, Value> node) {
		return node.leftChildren;
	}

	@Override
	public BinaryTreeNode<Key, Value> getRightChild(BinaryTreeNode<Key, Value> node) {
		return node.rightChildren;
	}

	@Override
	public BinaryTreeNode<Key, Value> sibling(BinaryTreeNode<Key, Value> node) {
		if (isRoot(node)) {
			return null;
		} else if (isRightChild(node)) {
			return node.getParent().getLeftChild();
		} else
			return node.getParent().getRightChild();
	}

	@Override
	public boolean isLeftChild(BinaryTreeNode<Key, Value> node) {
		if (isRoot(node)) {
			return false;
		}
		if (node.parent.leftChildren == node) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean isRightChild(BinaryTreeNode<Key, Value> node) {
		if (isRoot(node)) {
			return false;
		} else if (node.parent.rightChildren == node) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<BinaryTreeNode<Key, Value>> getNodesInOrder() {
		List<BinaryTreeNode<Key, Value>> list = new ArrayList<BinaryTreeNode<Key, Value>>();
		helperMethodInOrderTraversal(list, root);
		return list;
	}

	// recursive function for ýnorderTraversal, this idea is taken from the
	// youtube/theAIGuy
	public void helperMethodInOrderTraversal(List<BinaryTreeNode<Key, Value>> list, BinaryTreeNode<Key, Value> node) {
		if (!isExternal(node)) {
			helperMethodInOrderTraversal(list, node.getLeft());
			list.add(node);
			helperMethodInOrderTraversal(list, node.getRight());
		}
	}

	@Override
	public void setComparator(Comparator<Key> C) {
		this.comparator = C;
	}

	@Override
	public Comparator<Key> getComparator() {
		return comparator;
	}

	@Override
	public BinaryTreeNode<Key, Value> ceiling(Key k) {
		int sizeForCeiling = getNodesInOrder().size();
		for (int i = 0; i < sizeForCeiling; i++) {
			BinaryTreeNode<Key, Value> ceilingNode = getNodesInOrder().get(i);
			Key key = ceilingNode.getKey();
			if (comparator.compare(key, k) >= 0) {
				return ceilingNode;
			}
		}
		return null;
	}

	@Override
	public BinaryTreeNode<Key, Value> floor(Key k) {
		int sizeForCeiling = getNodesInOrder().size() - 1;
		for (int i = sizeForCeiling; i >= 0; i--) {
			BinaryTreeNode<Key, Value> floorNode = getNodesInOrder().get(i);
			Key key = floorNode.getKey();
			if (comparator.compare(getNodesInOrder().get(i).getKey(), k) <= 0) {
				return floorNode;
			}
		}
		return null;
	}
}
