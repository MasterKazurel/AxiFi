

public class LinkedListStack<T> extends newLinkedList<T>{

	@Override
	public void push(T data) {
		add(data, 0);
	}

	@Override
	public T pop() {
		return removeFirst();
	}

	@Override
	public boolean empty() {
		return isEmpty();
	}

}
