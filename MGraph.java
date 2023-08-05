public class MGraph<K extends Comparable<K>> implements Graph<K> {
	public Set<K> nodes; // Do not change this
	public Map<K, Set<K>> adj; // Do not change this

	public MGraph() {
		nodes = new BSTSet<K>();
		adj = new BSTMap<K, Set<K>>();
	}

	@Override
	public boolean addNode(K i) {
		boolean res = nodes.insert(i);
		if (res)
			adj.insert(i, new BSTSet<K>());
		return res;
	}

	@Override
	public boolean isNode(K i) {
		boolean res = adj.retrieve(i).first;
		return (res == true);
	}

	@Override
	public boolean addEdge(K i, K j) {
		if (!isNode(i) || !isNode(j))
			return false;
		
		boolean res = isEdge(i, j);
		
		if (!res) {
			adj.retrieve(i).second.insert(j);
			adj.retrieve(j).second.insert(i);
		}

		return (res == false);
	}

	@Override
	public boolean isEdge(K i, K j) {
		boolean res = (isNode(i) && isNode(j));
		
		if (res) {
			res = adj.retrieve(i).second.exists(j);
		}
		return (res == true);
	}

	@Override
	public Set<K> neighb(K i) {
		boolean res = isNode(i);
		if (res) {
			return adj.retrieve(i).second;
		} else {
			return null;
		}
	}

	@Override
	public int deg(K i) {
		boolean res = isNode(i);
		if (res == true) {
			return adj.retrieve(i).second.size();
		} else {
			return -1;
		}
	}

	@Override
	public Iterator<K> nodesIt() {
		return nodes.minIt();
	}

}
