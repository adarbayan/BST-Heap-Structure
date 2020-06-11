package code;

import java.util.ArrayList;
import java.util.Comparator;

import given.Entry;
import given.iAdaptablePriorityQueue;

/*
 * Implement an array based heap
 * Note that you can just use Entry here!
 * 
 */

public class ArrayBasedHeap<Key, Value> implements iAdaptablePriorityQueue<Key, Value> {

	// Use this arraylist to store the nodes of the heap.
	// This is required for the autograder.
	// It makes your implementation more verbose (e.g. nodes[i] vs nodes.get(i)) but
	// then you do not have to deal with dynamic resizing
	protected ArrayList<Entry<Key, Value>> nodes = new ArrayList<Entry<Key, Value>>();
	Comparator<Key> comparator;
	private int size = 0;
	private int index;

	/*
	 * 
	 * YOUR CODE BELOW THIS
	 * 
	 */
	public int rightChild(int i) {
		return (2 * i + 2);
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int j) {
		index = j;
	}

	public int leftChild(int i) {
		return (2 * i + 1);
	}

	public int parent(int i) {
		return (i - 1) / 2;
	}

	public boolean hasLeftChild(int i) {
		if (nodes.size() > leftChild(i)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean hasRightChild(int i) {
		if (nodes.size() > rightChild(i)) {
			return true;
		} else {
			return false;
		}
	}

	public void swap(int i, int j) {
		Entry<Key, Value> e = nodes.get(i);
		nodes.set(i, nodes.get(j));
		nodes.set(j, e);
	}// taken from the slide

	public void upheap(int i) {
		while (i > 0) {
			int p = parent(i);
			if (comparator.compare(nodes.get(i).getKey(), nodes.get(p).getKey()) >= 0)
				break;
			swap(i, p);
			i = p;
		}
	}

	// taken from the slide
	public void downheap(int i) {
		while (hasLeftChild(i)) {
			int leftChild = leftChild(i);
			if (hasRightChild(i)) {
				int rightChild = rightChild(i);
				if (comparator.compare(nodes.get(rightChild).getKey(), nodes.get(leftChild).getKey()) < 0) {
					leftChild = rightChild;
				}
			}
			if (comparator.compare(nodes.get(leftChild).getKey(), nodes.get(i).getKey()) >= 0)
				break;
			swap(i, leftChild);
			i = leftChild;
		}
	}

	@Override
	public int size() {
		return nodes.size();
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
	public void setComparator(Comparator<Key> C) {
		comparator = C;
	}

	@Override
	public Comparator<Key> getComparator() {
		return comparator;
	}

	@Override
	public void insert(Key k, Value v) {
		Entry<Key, Value> insertedNode = new Entry<Key, Value>(k, v);
		nodes.add(insertedNode);
		upheap(size() - 1);
	}

	@Override
	public Entry<Key, Value> pop() {
		if (nodes.isEmpty()) {
			return null;
		}
		Entry<Key, Value> popped = top();
		remove(top().getKey());
		return popped;
	}

	@Override
	public Entry<Key, Value> top() {
		if (nodes.isEmpty()) {
			return null;
		} else {
			return nodes.get(0);
		}
	}

	@Override
	public Value remove(Key k) {
		int size = size();
		for (int i = 0; i < nodes.size(); i++) {
			if (k.equals(nodes.get(i).getKey())) {
				int count = i;
				Entry<Key, Value> node = nodes.get(i);
				int swappedValue = size - 1;
				Value value = node.getValue();
				swap(count, swappedValue);
				nodes.remove(swappedValue);
				for (int j = size / 2; j >= 0; j--) { // this is from the slide
					while (hasLeftChild(j)) {
						int leftChild = leftChild(j);
						if (hasRightChild(j)) {
							int rightChild = rightChild(j);
							Key rightChildKey = nodes.get(rightChild).getKey();
							Key leftChildKey = nodes.get(leftChild).getKey();
							if (comparator.compare(rightChildKey, leftChildKey) < 0) {
								leftChild = rightChild;
							}
						}
						Key leftChildKey1 = nodes.get(leftChild).getKey();
						if (comparator.compare(leftChildKey1, nodes.get(j).getKey()) >= 0)
							break;
						swap(j, leftChild);
						j = leftChild;
					}
				}
				return value;
			}
		}
		return null;
	}

	@Override
	public Key replaceKey(Entry<Key, Value> entry, Key k) {
		for (int i = 0; i < size(); i++) {
			Key entryKey = entry.getKey();
			Key nodesKey = nodes.get(i).getKey();
			Value nodesValue = nodes.get(i).getValue();
			if (entryKey.equals(nodesKey) && entry.getValue().equals(nodesValue)) {
				Key replacedKey = nodes.get(i).getKey();
				Entry<Key, Value> node = nodes.get(i);
				node.setKey(k);
				for (int j = size() / 2; j >= 0; j--) { // this is from the slide
					while (hasLeftChild(j)) {
						int leftChildren = leftChild(j);
						if (hasRightChild(j)) {
							int rightChild = rightChild(j);
							Key rightKey = nodes.get(rightChild).getKey();
							Key leftKey = nodes.get(leftChildren).getKey();
							if (comparator.compare(rightKey, leftKey) < 0) {
								leftChildren = rightChild;
							}
						}
						Key leftKey = nodes.get(leftChildren).getKey();
						if (comparator.compare(leftKey, nodes.get(j).getKey()) >= 0)
							break;
						swap(j, leftChildren);
						j = leftChildren;
					}
				}
				return replacedKey;
			}
		}
		return null;
	}

	@Override
	public Key replaceKey(Value v, Key k) {
		int size = size();
		for (int i = 0; i < size; i++) {
			if (nodes.get(i).getValue().equals(v)) {
				Entry<Key, Value> node = nodes.get(i);
				Key replacedKey = node.getKey();
				node.setKey(k);
				for (int j = size / 2; j >= 0; j--) {
					while (hasLeftChild(j)) {
						int leftChildren = leftChild(j);
						if (hasRightChild(j)) {
							int rightChildren = rightChild(j);
							Key rightKey = nodes.get(rightChildren).getKey();
							Key leftKey = nodes.get(leftChildren).getKey();
							if (comparator.compare(rightKey, leftKey) < 0) {
								leftChildren = rightChildren;
							}
						}
						Key leftKey = nodes.get(leftChildren).getKey();
						if (comparator.compare(leftKey, nodes.get(j).getKey()) >= 0)
							break;
						swap(j, leftChildren);
						j = leftChildren;
					}
				}
				return replacedKey;
			}
		}
		return null;
	}

	@Override
	public Value replaceValue(Entry<Key, Value> entry, Value v) {
		int size = size();
		for (int i = 0; i < size; i++) {
			Entry<Key, Value> node = nodes.get(i);
			Value value = node.getValue();
			Key nodeKey = node.getKey();
			Key entryKey = entry.getKey();
			if (value.equals(entry.getValue()) && entryKey.equals(nodeKey)) {
				node.setValue(v);
				return value;
			}
		}
		return null;
	}
}