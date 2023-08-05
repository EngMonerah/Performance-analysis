public class ArrayPQK<P extends Comparable<P>, T> implements PQK<P, T> {
	private int length;
	private int max_length;
	private BSTMapNode<P, T> head, tail;

	public ArrayPQK(int k) {
		max_length = k;
		length = 0;
		head = tail = null;
	}

	@Override
	public int length() {
		return length;
	}

	@Override
	public void enqueue(P pr, T e) {
		BSTMapNode<P, T> node = new BSTMapNode<P, T>(pr, e);
		node.data = e;
		node.key = pr;

		if (head != null) {
			if (pr.compareTo(head.key) > 0) {
				node.next = head;
				head = node;
			} else {
				BSTMapNode<P, T> temp = head;
				while (temp.next != null && pr.compareTo(temp.next.key) <= 0)
					temp = temp.next;

				if (temp.next == null) {
					tail.next = node;
					tail = node;
				} else {
					node.next = temp.next;
					temp.next = node;
				}
			}

			if (length < max_length)
				length++;
		} else {
			head = tail = node;
			if (length < max_length)
				length++;
		}
	}

	@Override
	public Pair<P, T> serve() {
		if (head == null)
			return null;

		if (length == 0)
			return null;

		BSTMapNode<P, T> node = head;
		if (length == 1) {
			head = tail = null;
		} else {
			head = head.next;
			node.next = null;
		}

		length--;
		return new Pair<P, T>(node.key, node.data);
	}
}
