public class BSTSet<K extends Comparable<K>> implements Set<K> {
	public BSTSetNode<K> root; // Do not change this

	public BSTSet() {
	}

	@Override
	public int size() {
		return size(root);
	}

	private int size(BSTSetNode<K> node) {
		if (node == null)
			return 0;

		int size = 0;
		size = size + size(node.left);
		size = size + 1;
		size = size + size(node.right);

		return size;
	}

	@Override
	public boolean full() {
		return false;
	}

	@Override
	public void clear() {
		root = null;
	}

	private BSTSetNode<K> search(BSTSetNode<K> node, K k) {
		if (node == null)
			return null;
		if (k.compareTo(node.key) < 0) {
			return search(node.left, k);
		} else if (k.compareTo(node.key) > 0) {
			return search(node.right, k);
		} else {
			return node;
		}
	}

	@Override
	public boolean exists(K k) {
		BSTSetNode<K> node = search(root, k);

		if (node != null) {
			return true;
		}

		return false;
	}

	@Override
	public boolean insert(K k) {
		BSTSetNode<K> node = search(root, k);

		boolean result = false;
		if (node != null) {
			result = false;
		} else {
			root = insert(root, k, k);
			result = true;
		}

		return result;
	}

	private BSTSetNode<K> insert(BSTSetNode<K> node, K k, K e) {
		if (node == null) {
			BSTSetNode<K> n = new BSTSetNode<K>(k);
			insert(n);
			return n;
		} else {
			if (k.compareTo(node.key) <= 0) {
				node.left = insert(node.left, k, e);
			} else {
				node.right = insert(node.right, k, e);
			}

			return node;
		}
	}

	private void insert(BSTSetNode<K> node) {
		BSTSetNode<K> head = root;

		if (head != null) {
			while (head.left != null)
				head = head.left;

			if (node.key.compareTo(head.key) <= 0) {
				node.next = head;
				head.prev = node;
				head = node;
			} else {
				BSTSetNode<K> temp = head;
				while (temp.next != null
						&& node.key.compareTo(temp.next.key) > 0)
					temp = temp.next;

				if (temp.next == null) {
					temp.next = node;
					node.prev = temp;
				} else {
					node.next = temp.next;
					temp.next.prev = node;
					node.prev = temp;
					temp.next = node;
				}
			}
		} else {
			head = node;
		}
	}

	@Override
	public boolean remove(K k) {
		if (root == null) {
			return false;
		}

		if (root.left == null && root.right == null) {
			if (k.compareTo(root.key) == 0) {
				root = null;
				return true;
			} else {
				return false;
			}
		}
		BSTSetNode<K> node = search(root, k);
		if (node != null) {
			remove(node);
			root = remove(root, k);
			return true;
		}
		return false;
	}

	private BSTSetNode<K> remove(BSTSetNode<K> node, K k) {
		if (node == null)
			return node;

		if (k.compareTo(node.key) < 0) {
			node.left = remove(node.left, k);
		} else if (k.compareTo(node.key) > 0) {
			node.right = remove(node.right, k);
		} else {
			if (node.left != null && node.right != null) {
				BSTSetNode<K> temp = node.right;

				for (; temp.left != null;)
					temp = temp.left;

				{
					node.key = temp.key;
				}
				node.right = remove(node.right, temp.key);
			} else if (node.left != null) {
				node = node.left;
			} else {
				node = node.right;
			}

			// remove(node);
		}
		return node;
	}

	private void remove(BSTSetNode<K> node) {
		BSTSetNode<K> head = root;
		if (head != null) {
			while (head.left != null)
				head = head.left;

			if (node != head) {
				BSTSetNode<K> n = head;
				while (n.next != node)
					n = n.next;

				n.next = node.next;

				if (node.next != null)
					node.next.prev = n;
			} else if (node == head) {
				if (node.next != null)
					node.next.prev = null;

				head = head.next;
			}
		} else {
			return;
		}
	}

	@Override
	public Iterator<K> minIt() {
		BSTSetNode<K> head = root;
		if (head != null) {
			LinkedList<K> list = new LinkedList<K>();
			while (head.left != null)
				head = head.left;

			BSTSetNode<K> n = head;
			while (n != null) {
				list.add(n.key);
				n = n.next;
			}
			list.setValid(0);
			return list;
		} else {
			return new LinkedList<K>();
		}
	}

	@Override
	public Iterator<K> maxIt() {
		BSTSetNode<K> head = root;
		if (head != null) {
			LinkedList<K> list = new LinkedList<K>();
			while (head.left != null)
				head = head.left;

			BSTSetNode<K> n = head;
			while (n != null) {
				list.add(n.key);
				n = n.next;
			}
			list.setValid(1);
			return list;
		} else {
			return new LinkedList<K>();
		}
	}
}
