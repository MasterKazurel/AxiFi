

import java.util.AbstractList;

class newLinkedList<T> extends AbstractList {
	private Node head;
	private int listCount;

	//makes list and null head to start making list from
	public newLinkedList() {
		head = new Node(null);
		listCount = 0;
	}

	// adds object to end of list
	public void addLast(T data) {
		Node temp = new Node(data);
		Node current = head;
		while (current.getNext() != null) {
			current = current.getNext();
		}
		current.setNext(temp);
		listCount++;
	}

	
	// inserts passed object at the specified position
	public void add(T data, int index)
	{
		Node temp = new Node(data);
		Node current = head;
		for (int i = 1; i < index && current.getNext() != null; i++) {
			current = current.getNext();
		}
		temp.setNext(current.getNext());
		current.setNext(temp);
		listCount++;
	}
	
	// returns the element at that index
	public T get(int index)
	{
		if (index <= 0)
			return null;

		Node current = head.getNext();
		for (int i = 1; i < index; i++) {
			if (current.getNext() == null)
				return null;

			current = current.getNext();
		}
		return current.getData();
	}

	//had to put this in to make it work as a List in java. 
	public T remove(int index) {
		throw new UnsupportedOperationException();
	}

	//my actual remove method!
	public boolean myRemove(int index)
	{
		if (index < 1 || index > size())
			return false;

		Node current = head;
		for (int i = 1; i < index; i++) {
			if (current.getNext() == null)
				return false;

			current = current.getNext();
		}
		current.setNext(current.getNext().getNext());
		listCount--; 
		return true;
	}

	public T removeFirst() {
		Node oldFirst = head;
		head = head.next;
		listCount--;
		return (T) oldFirst.data;
	}

	// returns the number of objects
	public int size()
	{
		return listCount;
	}

	public boolean isEmpty() {
		return head == null;
	}

	public String toString() {
		Node current = head.getNext();
		String output = "";
		while (current != null) {
			output += "[" + current.getData().toString() + "]";
			current = current.getNext();
		}
		return output;
	}

	public void push(T data) {
	}

	public T pop() {
		return removeFirst();
	}

	public boolean empty() {
		return isEmpty();
	}

	//node class. In here with this because easier.
	private class Node {

		Node next;

		T data;

		public Node(T dataValue) {
			next = null;
			data = dataValue;
		}


		public Node(T dataValue, Node nextValue) {
			next = nextValue;
			data = dataValue;
		}

		// these methods should be self-explanatory
		public T getData() {
			return data;
		}

		public void setData(T dataValue) {
			data = dataValue;
		}

		public Node getNext() {
			return next;
		}

		public void setNext(Node nextValue) {
			next = nextValue;
		}
	}
}
