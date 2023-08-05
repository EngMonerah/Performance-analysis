import java.io.File;
import java.util.Scanner;

public class Recommender {

	// Read graph from file. The file is a text file where each line contains an
	// edge. The end and start of the edge are separated by space(s) or tabs
	// (see graph.txt).
	public static Graph<Integer> read(String fileName) {
		try {
			Graph<Integer> g = new MGraph<Integer>();
			Scanner input = new Scanner(new File(fileName));
			for (int i = 0, j = 0; input.hasNextInt(); i++, j++) {
				i = input.nextInt();
				j = input.nextInt();
				g.addNode(i);				
				g.addNode(j);
				g.addEdge(i, j);
			}
			input.close();
			return g;
		} catch (Exception e) {
			return null;
		}
	}

	// Return the top k recommended friends for user i using the popular users
	// method. If i does not exist, return null. In case of a tie, users with
	// the lowest id are selected.
	public static <K extends Comparable<K>> PQK<Double, K> recommendDeg(
			Graph<K> g, K i, int k) {
		boolean res = g.isNode(i);
		PQK<Double, K> array = null;
		if (!res) {
			return array;
		} else {
			array = new ArrayPQK<Double, K>(k);
			Iterator<K> it = g.nodesIt();
			while (it.isValid()) {
				K k2 = it.next();
				double deg = 0;
				if (!g.isEdge(i, k2) && k2.compareTo(i) != 0) {
					deg = g.deg(k2);
					array.enqueue(deg, k2);
				}
			}
			return array;
		}
	}

	// Return the top k recommended friends for user i using common neighbors
	// method. If i does not exist, return null. In case of a tie, users with
	// the lowest id are selected.
	public static <K extends Comparable<K>> PQK<Double, K> recommendCN(
			Graph<K> g, K i, int k) {
		boolean res = g.isNode(i);
		PQK<Double, K> q = null;
		if (!res) {
			return q;
		} else {
			q = new ArrayPQK<Double, K>(k);
			Iterator<K> it = g.nodesIt();
			while (true && it.isValid()) {
				K n = it.next();
				double num = 0;
				if (!g.isEdge(i, n) && n.compareTo(i) != 0) {
					Iterator<K> it1 = g.neighb(i).minIt();
					Iterator<K> it2;
					while (true && it1.isValid()) {
						K k1 = it1.next();
						it2 = g.neighb(n).minIt();
						while (true && it2.isValid()) {
							K k2 = it2.next();
							if (k1 != null && k2 != null) {
								if (k1.compareTo(k2) == 0)
									num++;
							}
						}
					}
					q.enqueue(num, n);
				}
			}
			return q;
		}
	}

	// Return the top k recommended friends for user i using weighted common
	// neighbors method. If i does not exist, return null. In case of a tie,
	// users with the lowest id are selected.
	public static <K extends Comparable<K>> PQK<Double, K> recommendWCN(
			Graph<K> g, K i, int k) {
		boolean res = g.isNode(i);
		PQK<Double, K> q = null;
		if (!res) {
			return q;
		} else {
			q = new ArrayPQK<Double, K>(k);
			Iterator<K> it = g.nodesIt();
			while (true && it.isValid()) {
				K n = it.next();
				double num = 0;
				if (!g.isEdge(i, n) && n.compareTo(i) != 0) {
					Iterator<K> it1 = g.neighb(i).minIt();
					Iterator<K> it2;
					while (true && it1.isValid()) {
						K k1 = it1.next();
						it2 = g.neighb(n).minIt();
						while (true && it2.isValid()) {
							K k2 = it2.next();
							if (k1 != null && k2 != null) {
								if (k1.compareTo(k2) == 0)
									num = num + 1.0 / g.deg(k2);
							}
						}
					}
					q.enqueue(num, n);
				}
			}
			return q;
		}
	}
}
