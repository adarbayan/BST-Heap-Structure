package code;

import java.util.List;

import given.Entry;
import given.iAdaptablePriorityQueue;

/*
 * Implement a binary search tree based priority queue
 * Do not try to create heap behavior (e.g. no need for a last node)
 * Just use default binary search tree properties
 */

public class BSTBasedPQ<Key, Value> extends BinarySearchTree<Key, Value>
		implements iAdaptablePriorityQueue<Key, Value> {

	/*
	 * 
	 * YOUR CODE BELOW THIS
	 * 
	 */

	@Override
	public void insert(Key k, Value v) {
		super.put(k, v);
	}

	@Override
	public Entry<Key, Value> pop() {
		if (!isEmpty()) {
			Entry<Key, Value> top = top();
			Entry<Key, Value> pop = new Entry<Key, Value>();
			pop.setValue(top.getValue());
			pop.setKey(top.getKey());
			remove(top.getKey());
			return pop;
		} else {
			return null;
		}
	}

	@Override
	public Entry<Key, Value> top() {
		if (!isEmpty()) {
			return getNodesInOrder().get(0);
		} else {
			return null;
		}
	}

	@Override
	public Key replaceKey(Entry<Key, Value> entry, Key k) {
		List<BinaryTreeNode<Key, Value>> nodes = getNodesInOrder();
		for (Entry<Key, Value> node : nodes) {
			Key nodeKey = node.getKey();
			Key entryKey = entry.getKey();
			if (node.getKey() != null && entryKey.equals(nodeKey) && node.equals(entry)) {
				Value value = node.getValue();
				Key replacedKey = entryKey;
				remove(nodeKey);
				put(k, value);
				return replacedKey;
			}
		}
		return null;
	}

	@Override
	public Key replaceKey(Value v, Key k) {
		List<BinaryTreeNode<Key, Value>> nodes = getNodesInOrder();
		Key replacedKey = null;
		for (Entry<Key, Value> node : nodes) {
			Value value = node.getValue();
			Key nodeKey = node.getKey();
			if (nodeKey != null && value.equals(v)) {
				replacedKey = nodeKey;
				remove(nodeKey);
			}
		}
		put(k, v);
		return replacedKey;
	}

	@Override
	public Value replaceValue(Entry<Key, Value> entry, Value v) {
		Key entryKey = entry.getKey();
		Entry<Key, Value> node = getNode(entryKey);
		Value entryValue = entry.getValue();
		Value nodeValue = node.getValue();
		if (nodeValue == entryValue) {
			Value nodeValue2 = node.getValue();
			Value value = nodeValue2;
			node.setValue(v);
			return value;
		} else {
			return null;
		}

	}
}