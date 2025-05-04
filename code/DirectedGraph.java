import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Collections;

public class DirectedGraph {

	public static class Edge {
		int v = 0;
		int w = 0;
		Edge() {

		}
		Edge(int v, int w) {
			this.v = v;
			this.w = w;
		}
	}

	public static void display(ArrayList<Edge>[] graph) {
		int V = graph.length;
		for (int i = 0; i < graph.length; i++) {
			System.out.print(i + " -> ");
			for (int j = 0; j < graph[i].size(); j++) {
				Edge e = graph[i].get(j);
				System.out.print("(" + e.v + "," + e.w + ") ");
			}
			System.out.println();
		}
	}

	// O(1)
	public static void addEdge(ArrayList<Edge>[] graph, int u, int v, int w) {
		graph[u].add(new Edge(v, w));
	}

	// O(E)
	public static int findEdge(ArrayList<Edge>[] graph, int u, int v) {
		for (int i = 0; i < graph[u].size(); i++) {
			Edge e = graph[u].get(i);
			if (e.v == v) {
				return i;
			}
		}

		return -1;
	}

	// O(E)
	public static void removeEdge(ArrayList<Edge>[] graph, int u, int v) {
		int idx = findEdge(graph, u, v);
		if (idx != -1) {
			graph[u].remove(idx);
		}
	}

	// O(E^2)
	public static void removeVertex(ArrayList<Edge>[] graph, int u) {
		while (graph[u].size() != 0) {
			removeEdge(graph, u, graph[u].get(0).v);
		}
	}









// ================================================== TOPOLOGICAL ORDER ==================================================
	// Topological order for Directed Acyclic Graph (DAG) is a linear ordering of vertices such that for every directed edge u-v, vertex u comes before v in the ordering
	// NOTE:
	// Topological Order for a graph is not possible if the graph is not a DAG (Tree)
	// Topological order is not possible for bidirected / cyclic graph
	// Topological order may not be unique

	// KAHN'S ALGORITHM
	// 1. initialize in-degree for all vertices
	// 2. enqueue vertices with in-degree 0
	// 3. while queue is not empty
	//    3.1 dequeue vtx, add to result
	//    3.2 for each nbr
	//    	3.2.1 decrease in-degree
	//      3.2.2 enqueue nbr if in-degree is 0
	// 4. if result contains all vertices, return it; else cycle detected

	// WHY start with vertices with in-degree 0? vertices with in-degree 0 are the "starting points" because they are independent
	// NOTE: each level represents the list of tasks that can be performed parallely because they are independent of one another
	// each subsequent level represents the list of tasks that has to be performed serially
	// we can't perfrom level 2 tasks before completing level 0 & level 1 tasks

	public static void kahnsAlog(ArrayList<Edge>[] graph) {
		int n = graph.length;
		int[] inDegree = new int[n];
		for (ArrayList<Edge> x : graph) {
			for (Edge e : x) {
				inDegree[e.v]++;
			}
		}

		ArrayList<Integer> ans = new ArrayList<>();
		LinkedList<Integer> qu = new LinkedList<>();
		for (int i = 0; i < n; i++) {
			if (inDegree[i] == 0) {
				qu.addLast(i);
			}
		}

		int level = 0;
		while (qu.size() != 0) {
			int size = qu.size();
			System.out.print(level + " -> ");
			while (size-- > 0) {
				int vtx = qu.removeFirst();
				ans.add(vtx);
				System.out.print(vtx);

				for (Edge e : graph[vtx]) {
					if (--inDegree[e.v] == 0) {
						qu.addLast(e.v);
					}
				}
			}
			System.out.println();
			level++;
		}

		if (ans.size() != n) {
			System.out.println("Topological order not possible due to cycle");
		}
		System.out.print("Topological Order: ");
		print1DList(ans);
	}









	// TOPOLOGICAL ORDER USING DFS:
	// so the question is basically how to detect a cycle using dfs
	// if there is a cycle then the topological order is not possible
	// else topological order is the reverse of dfs

	// DFS
	// 1. mark
	// 2. for all unvisited nbr's
	//  2.1 dfs(nbr)

	// if we apply dfs using boolean[] vis
	// and a vertex is visited again, can we say that there is a cycle?
	// no, it does not necessarily mean there is a cycle
	// this approach cannot distinguish whether the repetition is part of the current path or another already-explored path

	// 0 --> 1 --> 2
	//		 ^     |
	//       |     v
	//       4 <-- 3
	// here 1 is vis twice because there is a cycle

	// 0 --> 1 --> 2
	//		 |     |
	//       v     v
	//       4 --> 3
	// here 3 is vis twice but there is no cycle

	// to handle this, we use an int[] vis with the following states:
	// vis == 0 => vtx is not visited yet
	// vis == 1 => vtx is visited and is part of the current dfs path (indicates a back edge/cycle)
	// vis == 2 => vtx is visited but is no longer part of the current dfs path (fully explored)
	public static boolean dfs_topologicalOrder(ArrayList<Edge>[] graph, int src, int[] vis, ArrayList<Integer> ans) {
		vis[src] = 1; // mark as part of the current dfs path

		boolean isPossible = true;
		for (Edge e : graph[src]) {
			int nbr = e.v;
			if (vis[nbr] == 0) { // if nbr is unvisited, perform dfs
				isPossible = isPossible && dfs_topologicalOrder(graph, nbr, vis, ans);
			} else if (vis[nbr] == 1) { // if nbr is part of the current dfs path, cycle detected
				return false;
			}
		}

		vis[src] = 2; // mark as fully explored
		ans.add(src); // add to result (postorder)

		return isPossible;
	}

	public static void topologicalOrder(ArrayList<Edge>[] graph) {
		int V = graph.length;
		int[] vis = new int[V];
		ArrayList<Integer> ans = new ArrayList<>();
		boolean isPossible = true;
		for (int i = 0 ; i < V; i++) {
			if (vis[i] == 0) {
				isPossible = isPossible && dfs_topologicalOrder(graph, i, vis, ans);
			}
		}

		if (!isPossible) {
			System.out.println("Topological order not possible due to cycle");
		}
		Collections.reverse(ans);  // reverse postorder to get topological order
		System.out.print("Topological Order: ");
		print1DList(ans);
	}









// ============================== QUESTIONS ==============================









	// 207. Course Schedule
	// // detect cyclic dependency
	// public boolean canFinish(int n, int[][] arr) {
	// 	if (arr.length == 0) {
	// 		return true;
	// 	}

	// 	ArrayList<Integer>[] graph = new ArrayList[n];
	// 	for (int i = 0; i < n; i++) {
	// 		graph[i] = new ArrayList<>();
	// 	}
	// 	int[] inDegree = new int[n];
	// 	for (int i = 0; i < arr.length; i++) {
	// 		int u = arr[i][1];
	// 		int v = arr[i][0];
	// 		graph[u].add(v);
	// 		inDegree[v]++;
	// 	}

	// 	int ansCount = 0;
	// 	LinkedList<Integer> qu = new LinkedList<>();
	// 	for (int i = 0; i < n; i++) {
	// 		if (inDegree[i] == 0) {
	// 			qu.addLast(i);
	// 		}
	// 	}

	// 	int level = 0;
	// 	while (qu.size() != 0) {
	// 		int size = qu.size();
	// 		while (size-- > 0) {
	// 			int vtx = qu.removeFirst();
	// 			ansCount++;

	// 			for (int v : graph[vtx]) {
	// 				if (--inDegree[v] == 0) {
	// 					qu.addLast(v);
	// 				}
	// 			}
	// 		}
	// 		level++;
	// 	}

	// 	return ansCount == n;
	// }

	// 207. Course Schedule
	public boolean dfs_canFinish(ArrayList<Integer>[] graph, int src, int[] vis) {
		vis[src] = 1;

		boolean ans = true;
		for (int v : graph[src]) {
			if (vis[v] == 1) {
				// cycle detected => can't finish
				return false;
			} else if (vis[v] == 0) {
				ans = ans && dfs_canFinish(graph, v, vis);
			}
		}

		vis[src] = 2;

		return ans;
	}

	public boolean canFinish(int n, int[][] arr) {
		if (arr == null || arr.length == 0) {
			return true;
		}

		ArrayList<Integer>[] graph = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			graph[i] = new ArrayList<>();
		}
		for (int i = 0; i < arr.length; i++) {
			int u = arr[i][1];
			int v = arr[i][0];
			graph[u].add(v);
		}

		int[] vis = new int[n];
		boolean ans = true;
		for (int i = 0; i < n; i++) { // for each gcc
			if (vis[i] == 0) {
				ans = ans && dfs_canFinish(graph, i, vis);
			}
		}

		return ans;
	}

	// 210. Course Schedule II
	public int[] findOrder(int n, int[][] arr) {
		ArrayList<Integer>[] graph = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			graph[i] = new ArrayList<>();
		}
		int[] inDegree = new int[n];
		for (int i = 0; i < arr.length; i++) {
			// [0,1] => 1->0
			int u = arr[i][1];
			int v = arr[i][0];
			graph[u].add(v);
			inDegree[v]++;
		}

		ArrayList<Integer> ans = new ArrayList<>();
		LinkedList<Integer> qu = new LinkedList<>();
		for (int i = 0; i < n; i++) {
			if (inDegree[i] == 0) {
				qu.addLast(i);
			}
		}

		int level = 0;
		while (qu.size() != 0) {
			int size = qu.size();
			while (size-- > 0) {
				int vtx = qu.removeFirst();
				ans.add(vtx);

				for (int v : graph[vtx]) {
					if (--inDegree[v] == 0) {
						qu.addLast(v);
					}
				}
			}
			level++;
		}

		if (ans.size() != n) {
			// Topological order not possible
			return new int[0];
		}

		int[] ans2 = new int[n];
		for (int i = 0; i < n; i++) {
			ans2[i] = ans.get(i);
		}

		return ans2;
	}









	// 329. Longest Increasing Path in a Matrix
	// INDENTIFY - all possible answers will start from the vertex with inDegree == 0
	// uske aage ham sirf sabse longest possible path find kar rhe hai using kahnsAlgo

	// 1 -> 8
	// 2 -> 8
	// 4 -> 5 -> 6 -> 7 -> 8
	// WHY push vtx with inDegree == 0 only, we can push 8 after 1 right away since it is a valid path
	// because that will lead to early processing of 8, and we might miss on other longest path like 4->5->6->7->8 in this case
	// so we must wait for all paths to reach to 8, which is after we resolve all dependencies of 8
	public int longestIncreasingPath(int[][] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int n = arr.length;
		int m = arr[0].length;
		int[][] dir = new int[][] {{0, 1}, {0, -1}, {1, 0}, { -1, 0}};
		int[][] inDegree = new int[n][m];
		for (int r = 0; r < n; r++) {
			for (int c = 0; c < m; c++) {
				for (int[] d : dir) {
					int nr = r + d[0];
					int nc = c + d[1];

					if (nr >= 0 && nr < n && nc >= 0 && nc < m && arr[nr][nc] > arr[r][c]) {
						inDegree[nr][nc]++;
					}
				}
			}
		}

		LinkedList<Integer> qu = new LinkedList<>();
		for (int r = 0; r < n; r++) {
			for (int c = 0; c < m; c++) {
				if (inDegree[r][c] == 0) {
					qu.addLast(r * m + c);
				}
			}
		}

		int level = 0;
		while (qu.size() != 0) {
			int size = qu.size();
			while (size-- > 0) {
				int vtx = qu.removeFirst();
				int r = vtx / m;
				int c = vtx % m;

				for (int[] d : dir) {
					int nr = r + d[0];
					int nc = c + d[1];

					if (nr >= 0 && nr < n && nc >= 0 && nc < m && arr[nr][nc] > arr[r][c] && --inDegree[nr][nc] == 0) {
						qu.addLast(nr * m + nc);
					}
				}
			}
			level++;
		}

		return level;
	}









// ================================================== SCC ==================================================
	// SCC (Strongly Connected Components) in a directed graph is a maximal subgraph where every pair of vertices is reachable from each other
	// If ABCD is a SCC then there should be a path
	// from A to B,C,D
	// from B to A,C,D
	// from C to A,B,D
	// from D to A,B,C

	// KOSARAJU’S ALGORITHM:
	// dfs
	// Transpose the Graph - g'
	// DFS on Transposed Graph - dfs'

	// NOTES:
	// The key intuition is that SCCs have a unique property:
	// If you reverse all edges of a graph, the SCCs remain the same, but the reachability relationships change.
	// Kosaraju's algorithm takes advantage of this property by:
	// Performing a DFS on the original graph to get the finishing times of vertices.
	// Reversing the graph’s edges.
	// Performing DFS in the order of decreasing finishing times on the reversed graph.
	// This way, we extract SCCs one by one, using the vertex with the highest finishing time first.

	public static void dfs_01(ArrayList<Edge>[] graph, int src, boolean[] vis, ArrayList<Integer> ans) {
		vis[src] = true;
		System.out.print(src + " ");

		for (Edge e : graph[src]) {
			int nbr = e.v;
			if (!vis[nbr]) {
				dfs_01(graph, nbr, vis, ans);
			}
		}

		ans.add(src); // add to result (postorder)
	}

	public static void dfs_02(ArrayList<Edge>[] graph, int src, boolean[] vis) {
		vis[src] = true;
		System.out.print(src + " ");

		for (Edge e : graph[src]) {
			int nbr = e.v;
			if (!vis[nbr]) {
				dfs_02(graph, nbr, vis);
			}
		}
	}

	public static void kosaraju(ArrayList<Edge>[] graph) {
		int V = graph.length;
		boolean[] vis = new boolean[V];
		ArrayList<Integer> ans = new ArrayList<>();

		// dfs
		for (int i = 0; i < V; i++) {
			if (!vis[i]) {
				System.out.print("dfs_01 -> ");
				dfs_01(graph, i, vis, ans);
				System.out.println(";");
			}
		}

		// g'
		ArrayList<Edge>[] graphT = new ArrayList[V];
		for (int i = 0; i < V; i++) {
			graphT[i] = new ArrayList<>();
		}
		for (int i = 0; i < V; i++) {
			for (Edge e : graph[i]) {
				int u = i;
				int v = e.v;
				int w = e.w;

				addEdge(graphT, v, u , w);
			}
		}
		display(graphT);

		// dfs'
		Arrays.fill(vis, false);
		int sccCount = 0;

		for (int i = ans.size() - 1 ; i >= 0; i--) { // remember to run the dfs' in the reverse order of ans
			int ele = ans.get(i);
			if (!vis[ele]) {
				System.out.print("scc -> ");
				dfs_02(graphT, ele, vis);
				System.out.println(";");
				sccCount++;
			}
		}

		System.out.println("sccCount -> " + sccCount);
	}









// ============================== QUESTIONS ==============================









	// Mother Vertex
	// https://www.geeksforgeeks.org/problems/mother-vertex/0
	// MEMORIZE
	public void dfs_findMotherVertex(ArrayList<ArrayList<Integer>> graph, int src, boolean[] vis) {
		vis[src] = true;

		for (Integer e : graph.get(src)) {
			int nbr = e;
			if (!vis[nbr]) {
				dfs_findMotherVertex(graph, nbr, vis);
			}
		}

		// ans.add(src); // no need, just to track the finishing time of each vtx
	}
	public int dfs_findMotherVertex_02(ArrayList<ArrayList<Integer>> graph, int src, boolean[] vis) {
		vis[src] = true;

		int count = 1;
		for (Integer e : graph.get(src)) {
			int nbr = e;
			if (!vis[nbr]) {
				count += dfs_findMotherVertex_02(graph, nbr, vis);
			}
		}

		return count;
	}
	public int findMotherVertex(int V, ArrayList<ArrayList<Integer>> graph) {
		boolean[] vis = new boolean[V];
		int lastFinishedVertex = -1;
		for (int i = 0; i < V; i++) {
			if (!vis[i]) {
				dfs_findMotherVertex(graph, i, vis);
				lastFinishedVertex = i; // valid candidate for mother vertex - because it finished last
			}

		}

		// verify the candidate - by counting the total no of vertices reachable for lastFinishedVertex
		boolean[] vis2 = new boolean[V];
		int count = dfs_findMotherVertex_02(graph, lastFinishedVertex, vis2);

		return count == V ? lastFinishedVertex : -1;
	}









	// END
















































































































































































	public static void print1DList(ArrayList<Integer> list) {
		System.out.print("[");
		for (var x : list) {

			System.out.print(x + ", ");
		}
		System.out.println("]");
	}

	public static void print2DList(ArrayList<ArrayList<Integer>>list) {
		for (var x : list) {
			print1DList(x);
		}
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		System.out.println("Hello World");
		int V = 9;
		ArrayList<Edge>[] graph = new ArrayList[V];
		for (int i = 0; i < V; i++) {
			graph[i] = new ArrayList<>();
		}

		addEdge(graph, 0, 1, 10);
		addEdge(graph, 0, 3, 10);
		addEdge(graph, 1, 2, 10);
		addEdge(graph, 2, 3, 40);
		addEdge(graph, 2, 7, 2);
		addEdge(graph, 2, 8, 4);
		addEdge(graph, 7, 8, 3);
		addEdge(graph, 3, 4, 2);
		addEdge(graph, 4, 5, 2);
		addEdge(graph, 4, 6, 8);
		addEdge(graph, 5, 6, 3);
		// display(graph);

		// removeEdge(graph, 2, 1);
		// removeVertex(graph, 2);
		// display(graph);









		// kahnsAlog(graph);


		ArrayList<Edge>[] graph2 = new ArrayList[V];
		for (int i = 0; i < V; i++) {
			graph2[i] = new ArrayList<>();
		}

		addEdge(graph2, 0, 1, 10);
		addEdge(graph2, 1, 2, 10);
		addEdge(graph2, 2, 3, 40);
		addEdge(graph2, 3, 0, 10);

		addEdge(graph2, 2, 7, 2);
		addEdge(graph2, 7, 8, 3);
		addEdge(graph2, 8, 2, 4);

		addEdge(graph2, 3, 4, 2);
		addEdge(graph2, 4, 5, 2);
		addEdge(graph2, 5, 6, 3);
		addEdge(graph2, 6, 4, 8);
		display(graph2);

		kosaraju(graph2);
	}
}