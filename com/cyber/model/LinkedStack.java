package cyber.model;

import java.util.LinkedList;

public class LinkedStack<T> extends LinkedList<T> {

	@Override
	public void push(T data) {
		add(0, data);
	}

	@Override
	public T pop() {
		return removeFirst();
	}

}
