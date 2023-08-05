public class LinkedList<T> implements Iterator<T> {

	private Node<T> head;
	private Node<T> curr;

	public LinkedList() {
		head = null;
		curr = null;
	}

	public boolean isValid() {
		if (curr != null) {
			return true;
		} else {
			return false;
		}
	}

	public T next() {
		T e = null;
		if (curr != null) {
			e = curr.value;
			curr = curr.next;
		}
		return e;
	}

	public T prev() {
		T e = null;
		if (curr != null) {
			e = curr.value;
			curr = curr.prev;
		}
		return e;
	}

	public void setValid(int type) {
		if (type == 0)
			curr = head;
		else {
			curr = head;
			while (curr != null && curr.next != null)
				curr = curr.next;
		}
	}

	public void add(T val) {
		if (head != null) {
			Node<T> n = curr.next;
			curr.next = new Node<T>();
			curr.next.value = val;
			curr.next.prev = curr;
			curr = curr.next;
			curr.next = n;
		} else {
			head = new Node<T>();
			head.value = val;
			curr = head;
		}
	}

	class Node<TT> {
		public TT value;
		public Node<TT> next;
		public Node<TT> prev;

		public Node() {
			value = null;
			next = null;
		}
	}

}
