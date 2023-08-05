public class BSTMap<K extends Comparable<K>, T> implements Map<K, T> {
	public BSTMapNode<K, T> root; // Do not change this

	public BSTMap() {
	}

	@Override
	public int size() {
		return size(root);
	}

	private int size(BSTMapNode<K, T> node) {
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

	private BSTMapNode<K, T> search(BSTMapNode<K, T> node, K k) {
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
	public boolean update(K k, T e) {
		BSTMapNode<K, T> node = search(root, k);

		boolean result = false;
		if (node != null) {
			node.data = e;
			result = true;
		}
		return result;
	}

	@Override
	public Pair<Boolean, T> retrieve(K k) {
		BSTMapNode<K, T> node = search(root, k);

		boolean result = false;
		T e = null;
		if (node != null) {
			e = node.data;
			result = true;
		}
		return new Pair<Boolean, T>(result, e);
	}

	@Override
	public boolean insert(K k, T e) {
		BSTMapNode<K, T> node = search(root, k);

		boolean result = false;
		if (node != null) {
			result = false;
		} else {
			root = insert(root, k, e);
			result = true;
		}

		return result;
	}

	private BSTMapNode<K, T> insert(BSTMapNode<K, T> node, K k, T e) {
		if (node == null) {
			BSTMapNode<K, T> n = new BSTMapNode<K, T>(k, e);
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

	private void insert(BSTMapNode<K, T> node) {
		BSTMapNode<K, T> head = root;

		if (head != null) {
			while (head.left != null)
				head = head.left;

			if (node.key.compareTo(head.key) <= 0) {
				node.next = head;
				head.prev = node;
				head = node;
			} else {
				BSTMapNode<K, T> temp = head;
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

		BSTMapNode<K, T> node = search(root, k);
		if (node != null) {
			remove(node);
			root = remove(root, k);

			return true;
		}
		return false;
	}

	private BSTMapNode<K, T> remove(BSTMapNode<K, T> node, K k) {
		if (node == null)
			return node;

		if (k.compareTo(node.key) < 0) {
			node.left = remove(node.left, k);
		} else if (k.compareTo(node.key) > 0) {
			node.right = remove(node.right, k);
		} else {
			// BSTMapNode<K, T> delNode = node;
			if (node.left != null && node.right != null) {
				BSTMapNode<K, T> temp = node.right;

				for (; temp.left != null;)
					temp = temp.left;

				{
					node.data = temp.data;
					node.key = temp.key;
				}
				node.right = remove(node.right, temp.key);
			} else if (node.left != null) {
				node = node.left;
			} else {
				node = node.right;
			}
			// remove(delNode);
		}
		return node;
	}

	private void remove(BSTMapNode<K, T> node) {
		BSTMapNode<K, T> head = root;
		if (head != null) {
			while (head.left != null)
				head = head.left;

			if (node != head) {
				BSTMapNode<K, T> n = head;
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
	public Iterator<Pair<K, T>> minIt() {
		BSTMapNode<K, T> head = root;
		if (head != null) {
			LinkedList<Pair<K, T>> list = new LinkedList<Pair<K, T>>();
			while (head.left != null)
				head = head.left;

			BSTMapNode<K, T> n = head;
			while (n != null) {
				list.add(new Pair<K, T>(n.key, n.data));
				n = n.next;
			}
			list.setValid(0);
			return list;
		} else {
			return new LinkedList<Pair<K, T>>();
		}
	}

	@Override
	public Iterator<Pair<K, T>> maxIt() {
		BSTMapNode<K, T> head = root;
		if (head != null) {
			LinkedList<Pair<K, T>> list = new LinkedList<Pair<K, T>>();
			while (head.left != null)
				head = head.left;

			BSTMapNode<K, T> n = head;
			while (n != null) {
				list.add(new Pair<K, T>(n.key, n.data));
				n = n.next;
			}
			list.setValid(1);
			return list;
		} else {
			return new LinkedList<Pair<K, T>>();
		}
	}

}
