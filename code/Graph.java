import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashMap;

public class Graph {

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
		graph[v].add(new Edge(u, w));
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
		int idx2 = findEdge(graph, v, u);
		if (idx2 != -1) {
			graph[v].remove(idx2);
		}
	}

	// O(E^2)
	public static void removeVertex(ArrayList<Edge>[] graph, int u) {
		while (graph[u].size() != 0) {
			removeEdge(graph, u, graph[u].get(0).v);
		}
	}









// ================================================== DFS ==================================================
	// 1. mark
	// 2. for all unvisited nbr's
	// 	2.1 dfs(nbr)

	// O(E)
	// FOR 1 PATH DONT MARK VIS = FALSE AT THE END OF THE LOOP

	// because if a node is vis and we didn't got the answer
	// it means that there is no point in visiting it again as we can't reach the destination again
	public static boolean dfs(ArrayList<Edge>[] graph, int src, int des, boolean[] vis) {
		if (src == des) {
			return true;
		}

		boolean ans = false;
		vis[src] = true;
		for (Edge x : graph[src]) {
			if (!vis[x.v]) {
				ans = ans || dfs(graph, x.v, des, vis);
			}
		}

		return ans;
	}

	public static boolean hasPath(ArrayList<Edge>[] graph, int src, int des) {
		int V = graph.length;
		boolean[] vis = new boolean[V];
		return dfs(graph, src, des, vis);
	}

	// // dfs
	// 1. mark
	// 2. for all unvisited nbr's
	// 	2.1 dfs(nbr)
	// 3. unmark
	// FOR ALL PATH MARK VIS = FALSE AT THE END OF THE LOOP

	// because it is possible that we can get another path passing thorugh the same vertex
	public static int dfs(ArrayList<Edge>[] graph, int src, int des, boolean[] vis, ArrayList<Integer> psf, ArrayList<ArrayList<Integer>> ans) {
		if (src == des) {
			psf.add(src);
			// deep copy
			ans.add(new ArrayList<>(psf));
			psf.remove(psf.size() - 1);
			return 1;
		}

		int count = 0;
		vis[src] = true;
		psf.add(src);
		for (Edge x : graph[src]) {
			if (!vis[x.v]) {
				count += dfs(graph, x.v, des, vis, psf, ans);
			}
		}
		vis[src] = false;
		psf.remove(psf.size() - 1);

		return count;
	}
	public static int allPath(ArrayList<Edge>[] graph, int src, int des, ArrayList<Integer> psf, ArrayList<ArrayList<Integer>>ans) {
		int V = graph.length;
		boolean[] vis = new boolean[V];
		return dfs(graph, src, des, vis, psf, ans);
	}









	public static void preOrder(ArrayList<Edge>[] graph, int src, boolean[] vis, String psf, int wsf) {
		System.out.println(src + " -> " + psf + src + " @ " + wsf);
		vis[src] = true;
		for (Edge e : graph[src]) {
			if (!vis[e.v]) {
				preOrder(graph, e.v, vis, psf + src, wsf + e.w);
			}
		}
		vis[src] = false;
	}

	public static void postOrder(ArrayList<Edge>[] graph, int src, boolean[] vis, String psf, int wsf) {
		vis[src] = true;
		for (Edge e : graph[src]) {
			if (!vis[e.v]) {
				postOrder(graph, e.v, vis, psf + src, wsf + e.w);
			}
		}
		vis[src] = false;
		System.out.println(src + " -> " + psf + src + " @ " + wsf);
	}









	public static class Pair {
		String psf = "";
		int weight = 0;
		Pair() {

		}
		Pair(String psf, int weight) {
			this.psf = psf;
			this.weight = weight;
		}
	}

	public static Pair heaviestPath(ArrayList<Edge>[] graph, int src, int des, boolean[] vis) {
		if (src == des) {
			return new Pair("" + src, 0);
		}

		Pair myAns = new Pair("", -1);

		vis[src] = true;
		for (Edge e : graph[src]) {
			if (!vis[e.v]) {
				Pair recAns = heaviestPath(graph, e.v, des, vis);
				if (recAns.weight > -1 && e.w + recAns.weight > myAns.weight) {
					myAns.weight = e.w + recAns.weight;
					myAns.psf = src + recAns.psf;
				}
			}
		}
		vis[src] = false;

		return myAns;
	}

	public static Pair lightestPath(ArrayList<Edge>[] graph, int src, int des, boolean[] vis) {
		if (src == des) {
			return new Pair("" + src, 0);
		}

		Pair myAns = new Pair("", -1);

		vis[src] = true;
		for (Edge e : graph[src]) {
			if (!vis[e.v]) {
				Pair recAns = heaviestPath(graph, e.v, des, vis);
				if (recAns.weight > -1 && e.w + recAns.weight < myAns.weight) {
					myAns.weight = e.w + recAns.weight;
					myAns.psf = src + recAns.psf;
				}
			}
		}
		vis[src] = false;

		return myAns;
	}









	public static void hamiltonianPath(ArrayList<Edge>[] graph, int src, boolean[] vis, ArrayList<Integer> psf) {
		// System.out.println(src);
		vis[src] = true;
		psf.add(src);

		if (psf.size() == graph.length) {
			System.out.print("Path -> ");
			print1DList(psf);
		}

		for (Edge e : graph[src]) {
			if (!vis[e.v]) {
				hamiltonianPath(graph, e.v, vis, psf);
			}
		}
		vis[src] = false;
		psf.remove(psf.size() - 1);
	}

	public static void hamiltonianPathAndCycle(ArrayList<Edge>[] graph, int src, boolean[] vis, ArrayList<Integer> psf, int osrc) {
		// System.out.println(src);
		vis[src] = true;
		psf.add(src);

		if (psf.size() == graph.length) {
			int idx = findEdge(graph, src, osrc);
			if (idx != -1) {
				System.out.print("Cycle -> ");
			} else {
				System.out.print("Path -> ");
			}
			print1DList(psf);
		}

		for (Edge e : graph[src]) {
			if (!vis[e.v]) {
				hamiltonianPathAndCycle(graph, e.v, vis, psf, osrc);
			}
		}
		vis[src] = false;
		psf.remove(psf.size() - 1);
	}

	// get Connected Components
	// O(E + V)
	// +V if there are 0 edges & V vertices only
	public static void gcc_(ArrayList<Edge>[] graph, int src, boolean[] vis) {
		vis[src] = true;
		for (Edge e : graph[src]) {
			if (!vis[e.v]) {
				gcc_(graph, e.v, vis);
			}
		}
	}

	public static int gcc(ArrayList<Edge>[] graph) {
		int V = graph.length;
		boolean[] vis = new boolean[V];
		int count = 0;
		for (int i = 0; i < V; i++) {
			if (!vis[i]) {
				gcc_(graph, i, vis);
				count++;
			}
		}

		return count;
	}









	// 200. Number of Islands
	// T: O(n*m)
	// S: O(n*m)
	public void dfs_numIslands(char[][] graph, int r, int c, boolean[][] vis, int[][] dir) {
		int n = graph.length;
		int m = graph[0].length;
		vis[r][c] = true;

		for (int[] x : dir) {
			int nr = r + x[0];
			int nc = c + x[1];

			if (nr >= 0 && nr < n && nc >= 0 && nc < m && graph[nr][nc] == '1' && !vis[nr][nc]) {
				dfs_numIslands(graph, nr, nc, vis, dir);
			}
		}
	}

	public int numIslands(char[][] graph) {
		if (graph == null || graph.length == 0 || graph[0].length == 0) {
			return 0;
		}

		int count = 0;
		int n = graph.length;
		int m = graph[0].length;
		boolean[][] vis = new boolean[n][m];
		int[][] dir = new int[][] {{0, 1}, {0, -1}, {1, 0}, { -1, 0}};

		for (int r = 0; r < n; r++) {
			for (int c = 0; c < m; c++) {
				if (graph[r][c] == '1' && !vis[r][c]) {
					dfs_numIslands(graph, r, c, vis, dir);
					count++;
				}
			}
		}

		return count;
	}

	// T: O(n*m)
	// S: O(1)
	public void dfs_numIslands(char[][] graph, int r, int c, int[][] dir) {
		int n = graph.length;
		int m = graph[0].length;
		graph[r][c] = '0';

		for (int[] x : dir) {
			int nr = r + x[0];
			int nc = c + x[1];

			if (nr >= 0 && nr < n && nc >= 0 && nc < m && graph[nr][nc] == '1') {
				dfs_numIslands(graph, nr, nc, dir);
			}
		}
	}

	public int numIslands_2(char[][] graph) {
		if (graph == null || graph.length == 0 || graph[0].length == 0) {
			return 0;
		}

		int count = 0;
		int n = graph.length;
		int m = graph[0].length;
		int[][] dir = new int[][] {{0, 1}, {0, -1}, {1, 0}, { -1, 0}};

		for (int r = 0; r < n; r++) {
			for (int c = 0; c < m; c++) {
				if (graph[r][c] == '1') {
					dfs_numIslands(graph, r, c, dir);
					count++;
				}
			}
		}

		return count;
	}









	// 695. Max Area of Island
	public int dfs_maxAreaOfIsland(int[][] graph, int r, int c, int[][] dir) {
		int n = graph.length;
		int m = graph[0].length;

		int myArea = 1;
		graph[r][c] = 0;

		for (int[] x : dir) {
			int nr = r + x[0];
			int nc = c + x[1];

			if (nr >= 0 && nr < n && nc >= 0 && nc < m && graph[nr][nc] == 1) {
				myArea += dfs_maxAreaOfIsland(graph, nr, nc, dir);
			}
		}

		return myArea;
	}

	public int maxAreaOfIsland(int[][] graph) {
		if (graph == null || graph.length == 0 || graph[0].length == 0) {
			return 0;
		}

		int area = 0;
		int n = graph.length;
		int m = graph[0].length;
		int[][] dir = new int[][] {{0, 1}, {0, -1}, {1, 0}, { -1, 0}};

		for (int r = 0; r < n; r++) {
			for (int c = 0; c < m; c++) {
				if (graph[r][c] == 1) {
					int smallArea = dfs_maxAreaOfIsland(graph, r, c, dir);
					area = Math.max(area, smallArea);
				}
			}
		}

		return area;
	}









	// 463. Island Perimeter
	// EASY APPROACH
	public int islandPerimeter(int[][] arr) {
		if (arr == null || arr.length == 0 || arr[0].length == 0) {
			return 0;
		}

		int peri = 0;
		int n = arr.length;
		int m = arr[0].length;

		int oneCount = 0;
		int nbrCount = 0;
		int[][] dir = new int[][] {{0, 1}, {0, -1}, {1, 0}, { -1, 0}};

		for (int i = 0; i < n; i++) {
			for (int j = 0 ; j < m; j++) {
				if (arr[i][j] == 1) {
					oneCount++;

					for (int[] x : dir) {
						int nr = i + x[0];
						int nc = j + x[1];

						if (nr >= 0 && nr < n && nc >= 0 && nc < m && arr[nr][nc] == 1) {
							nbrCount++;
						}

					}
				}
			}
		}

		return 4 * oneCount - nbrCount;
	}









	// 130. Surrounded Regions
	public void dfs_solve(char[][] graph, int r, int c, int[][] dir) {
		int n = graph.length;
		int m = graph[0].length;

		graph[r][c] = '$';

		for (int[] x : dir) {
			int nr = r + x[0];
			int nc = c + x[1];

			if (nr >= 0 && nr < n && nc >= 0 && nc < m && graph[nr][nc] == 'O') {
				dfs_solve(graph, nr, nc, dir);
			}
		}
	}

	public void solve(char[][] graph) {
		if (graph == null || graph.length == 0 || graph[0].length == 0) {
			return;
		}

		int n = graph.length;
		int m = graph[0].length;
		int[][] dir = new int[][] {{0, 1}, {0, -1}, {1, 0}, { -1, 0}};

		for (int r = 0; r < n; r++) {
			for (int c = 0; c < m; c++) {
				if ((r == 0 || r == n - 1 || c == 0 || c == m - 1) && graph[r][c] == 'O') {
					dfs_solve(graph, r, c, dir);
				}
			}
		}

		for (int r = 0; r < n; r++) {
			for (int c = 0; c < m; c++) {
				if (graph[r][c] == '$') {
					graph[r][c] = 'O';
				} else if (graph[r][c] == 'O') {
					graph[r][c] = 'X';
				}
			}
		}
	}









	// 860 · Number of Distinct Islands
	public void dfs_numberofDistinctIslands(int[][] graph, int r, int c, int[][] dir, ArrayList<Integer> psf) {
		int n = graph.length;
		int m = graph[0].length;

		graph[r][c] = 0;

		for (int[] x : dir) {
			int nr = r + x[0];
			int nc = c + x[1];

			if (nr >= 0 && nr < n && nc >= 0 && nc < m && graph[nr][nc] == 1) {
				// x represent direction
				// {0,1} -> Right
				// {1,0} -> Down
				psf.add(x[0]);
				psf.add(x[1]);
				dfs_numberofDistinctIslands(graph, nr, nc, dir, psf);
			}
		}
		// 2 represent backtrack
		psf.add(2);
	}

	public int numberofDistinctIslands(int[][] graph) {
		if (graph == null || graph.length == 0 || graph[0].length == 0) {
			return 0;
		}

		int n = graph.length;
		int m = graph[0].length;
		int[][] dir = new int[][] {{0, 1}, {0, -1}, {1, 0}, { -1, 0}};
		HashMap<ArrayList<Integer>, Integer> map = new HashMap<>();

		for (int r = 0; r < n; r++) {
			for (int c = 0; c < m; c++) {
				if (graph[r][c] == 1) {
					ArrayList<Integer> psf = new ArrayList<>();
					dfs_numberofDistinctIslands(graph, r, c, dir, psf);
					map.put(psf, 1);
				}
			}
		}

		// for (var x : map.entrySet()) {
		// 	System.out.println("Key: " + x.getKey() + ", Value: " + x.getValue());
		// }

		return map.size();
	}

	// USING STRING
	String shape = "";
	public void numberofDistinctIslands_(int[][] graph, int sr, int sc, int[][] dir) {
		graph[sr][sc] = 0;
		int n = graph.length; int m = graph[0].length;

		for (var d : dir) {
			int nr = sr + d[0];
			int nc = sc + d[1];
			if (nr >= 0 && nr < n && nc >= 0 && nc < m && graph[nr][nc] == 1) {
				shape += d[0] + "";
				shape += d[1] + "";
				numberofDistinctIslands_(graph, nr, nc, dir);
			}
		}
		shape += "b";
	}
	public int numberofDistinctIslands_2(int[][] graph) {
		if (graph == null || graph.length == 0) {
			return 0;
		}
		int n = graph.length;
		int m = graph[0].length;
		int[][] dir = new int[][] {{0, 1}, {0, -1}, {1, 0}, { -1, 0}};
		HashMap<String, Integer> map = new HashMap<>();

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (graph[i][j] == 1) {
					shape = "";
					numberofDistinctIslands_(graph, i, j, dir);
					map.put(shape, 1);
				}
			}
		}

		// for (var x : map.entrySet()) {
		// 	System.out.println("Key: " + x.getKey() + ", Value: " + x.getValue());
		// }

		return map.size();
	}









// ================================================== BFS ==================================================
	/*
	1. enqueue src
	2. while queue is not empty
		2.1 dequeue vtx
		2.2 mark vis
		2.3 for all unvisited nbr's
			2.3.1 enqueue
	*/

	// O(E) - in fact it will cover all the edges no matter what
	// mark visited after popping out from the queue
	public static void bfs(ArrayList<Edge>[] graph, int src, boolean[] vis) {
		if (graph == null || graph.length == 0) {
			return;
		}
		LinkedList<Integer> que = new LinkedList<>();
		que.addLast(src);

		int level = 0;

		while (que.size() != 0) {
			System.out.print("level: " + level + " -> ");

			int size = que.size();
			while (size-- > 0) {
				int vtx = que.removeFirst();
				if (vis[vtx]) {
					System.out.println("Cycle detected");
					continue;
				}

				vis[vtx] = true;
				System.out.print(vtx + ", ");

				for (Edge e : graph[vtx]) {
					if (!vis[e.v]) {
						que.addLast(e.v);
					}
				}
			}

			level++;
			System.out.println();
		}
	}

	// O(V)
	// mark visited while pushing into the queue
	public static void bfs_withoutCycle(ArrayList<Edge>[] graph, int src, boolean[] vis) {
		if (graph == null || graph.length == 0) {
			return;
		}
		LinkedList<Integer> que = new LinkedList<>();
		que.addLast(src);
		vis[src] = true;

		int level = 0;

		while (que.size() != 0) {
			System.out.print("level: " + level + " -> ");

			int size = que.size();
			while (size-- > 0) {
				int vtx = que.removeFirst();
				System.out.print(vtx + ", ");

				for (Edge e : graph[vtx]) {
					if (!vis[e.v]) {
						que.addLast(e.v);
						vis[e.v] = true;
					}
				}
			}

			level++;
			System.out.println();
		}
	}









	// bipartitie graph -> if u can divide the vertices of the graph into 2 groups
	// such that no 2 adjacent vertices are in the same group
//	graph
//	if(non-cycle)
//		bipartite
//	else
//		if(even length cycle)
//			bipartite
//		else(odd length cycle)
//			not bipartite
	// 785. Is Graph Bipartite?
	public boolean bfs_isBipartite(int[][] graph, int src, int[] vis) {
		LinkedList<Integer> qu = new LinkedList<>();
		qu.addLast(src);
		int currColor = 1;

		while (qu.size() != 0) {
			int size = qu.size();
			while (size-- > 0) {
				int vtx = qu.removeFirst();

				if (vis[vtx] != 0) {// it means the vtx was already visited
					if (currColor != vis[vtx]) {// it means i am trying to vis the same vtx with 2 diff color
						return false;
					}
				}
				vis[vtx] = currColor;

				for (int x : graph[vtx]) {
					if (vis[x] == 0) {
						qu.addLast(x);
					}
				}
			}

			currColor = -currColor;
		}

		return true;
	}
	public boolean isBipartite(int[][] graph) {
		if (graph == null || graph.length == 0) {
			return true;
		}
		int n = graph.length;
		// boolean[] vis = new boolean[n];
		// we cant use boolean because we need to know both the details
		// 1. if the vtx is visited or not
		// 2. if it is already visited, what was the color of the vertex back then
		int[] vis = new int[n];
		// 0 -> not visited
		// 1 -> vis with red color
		// -1 -> vis with blue color

		boolean ans = true;

		for (int i = 0; i < n; i++) {
			if (vis[i] == 0) {
				ans = ans && bfs_isBipartite(graph, i, vis);
			}
		}

		return ans;
	}









	// 994. Rotting Oranges
	public int orangesRotting(int[][] graph) {
		int n = graph.length;
		int m = graph[0].length;
		LinkedList<Integer> qu = new LinkedList<>();
		int freshOrangeCount = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (graph[i][j] == 2) {
					qu.addLast(i * m + j);
				} else if (graph[i][j] == 1) {
					freshOrangeCount++;
				}
			}
		}

		if (freshOrangeCount == 0) {
			return 0;
		}

		int[][] dir = new int[][] {{0, 1}, {0, -1}, {1, 0}, { -1, 0}};
		int level = 0;
		while (qu.size() != 0) {
			int size = qu.size();
			while (size-- > 0) {
				int vtx = qu.removeFirst();
				// System.out.println(vtx/m + " " + vtx%m);

				int r = vtx / m;
				int c = vtx % m;

				for (int[] d : dir) {
					int nr = r + d[0];
					int nc = c + d[1];

					if (nr >= 0 && nr < n && nc >= 0 && nc < m && graph[nr][nc] == 1) {
						qu.addLast(nr * m + nc);
						graph[nr][nc] = 2;
						freshOrangeCount--;
					}
				}
			}

			level++;
		}

		return (freshOrangeCount == 0) ? level - 1 : -1;
	}









	// 1091. Shortest Path in Binary Matrix
	public int shortestPathBinaryMatrix(int[][] graph) {
		int n = graph.length;
		int m = graph[0].length;

		if (graph[0][0] == 1 || graph[n - 1][n - 1] == 1) {
			return -1;
		}

		LinkedList<Integer> qu = new LinkedList<>();
		qu.add(0 * m + 0);
		graph[0][0] = 1;

		int level = 0;
		int[][] dir = new int[][] { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 }, { 1, 1 }, { 1, -1 }, { -1, 1 }, { -1, -1 }};
		while (qu.size() != 0) {
			int size = qu.size();
			while (size-- > 0) {
				int vtx = qu.removeFirst();

				int r = vtx / m;
				int c = vtx % m;
				if (r == n - 1 && c == n - 1) {
					return level + 1;
				}

				for (int[] d : dir) {
					int nr = r + d[0];
					int nc = c + d[1];

					if (nr >= 0 && nr < n && nc >= 0 && nc < m && graph[nr][nc] == 0) {
						qu.addLast(nr * m + nc);
						graph[nr][nc] = 1;
					}
				}
			}

			level++;
		}

		return -1;
	}









	// 542. 01 Matrix
	// we need another matrix for storing ans because we can't distinguish between the original 1s and updated distance 1s
	public int[][] updateMatrix(int[][] graph) {
		int n = graph.length;
		int m = graph[0].length;
		LinkedList<Integer> qu = new LinkedList<>();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (graph[i][j] == 0) {
					qu.add(i * m + j);
				}
			}
		}

		int level = 0;
		int[][] dir = new int[][] {{0, 1}, {0, -1}, {1, 0}, { -1, 0}};
		int[][] ans = new int[n][m];
		while (qu.size() != 0) {
			int size = qu.size();
			while (size-- > 0) {
				int vtx = qu.removeFirst();
				int r = vtx / m;
				int c = vtx % m;

				for (int[] d : dir) {
					int nr = r + d[0];
					int nc = c + d[1];

					if (nr >= 0 && nr < n && nc >= 0 && nc < m && graph[nr][nc] == 1) {
						qu.addLast(nr * m + nc);
						graph[nr][nc] = 0;
						ans[nr][nc] = level + 1;
					}
				}
			}

			level++;
		}

		return ans;
	}

	// 663 · Walls and Gates
	// we dont need another matrix for storing ans because INF will never conflict with calculated distances
	public void wallsAndGates(int[][] graph) {
		int n = graph.length;
		int m = graph[0].length;
		LinkedList<Integer> qu = new LinkedList<>();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (graph[i][j] == 0) {
					qu.add(i * m + j);
				}
			}
		}

		int level = 0;
		int[][] dir = new int[][] {{0, 1}, {0, -1}, {1, 0}, { -1, 0}};
		while (qu.size() != 0) {
			int size = qu.size();
			while (size-- > 0) {
				int vtx = qu.removeFirst();
				int r = vtx / m;
				int c = vtx % m;

				for (int[] d : dir) {
					int nr = r + d[0];
					int nc = c + d[1];

					if (nr >= 0 && nr < n && nc >= 0 && nc < m && graph[nr][nc] == 2147483647) {
						qu.addLast(nr * m + nc);
						graph[nr][nc] = level + 1;
					}
				}
			}

			level++;
		}
	}









	// 1020. Number of Enclaves
	// USING DFS
	public void dfs_numEnclaves(int[][] graph, int r, int c, int[][] dir) {
		int n = graph.length;
		int m = graph[0].length;
		graph[r][c] = 0;

		for (int[] d : dir) {
			int nr = r + d[0];
			int nc = c + d[1];

			if (nr >= 0 && nr < n && nc >= 0 && nc < m && graph[nr][nc] == 1) {
				dfs_numEnclaves(graph, nr, nc, dir);
			}
		}
	}
	public int numEnclaves(int[][] graph) {
		int n = graph.length;
		int m = graph[0].length;

		int[][] dir = new int[][] {{0, 1}, {0, -1}, {1, 0}, { -1, 0}};

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if ((i == 0 || i == n - 1 || j == 0 || j == m - 1) && graph[i][j] == 1) {
					dfs_numEnclaves(graph, i, j, dir);
				}
			}
		}

		int count = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (graph[i][j] == 1) {
					count++;
				}
			}
		}

		return count;
	}

	// USING BFS
	public int numEnclaves(int[][] graph) {
		int n = graph.length;
		int m = graph[0].length;

		LinkedList<Integer> qu = new LinkedList<>();
		int[][] dir = new int[][] {{0, 1}, {0, -1}, {1, 0}, { -1, 0}};

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if ((i == 0 || i == n - 1 || j == 0 || j == m - 1) && graph[i][j] == 1) {
					qu.addLast(i * m + j);
					graph[i][j] = 0;
				}
			}
		}

		while (qu.size() != 0) {
			int size = qu.size();
			while (size-- > 0) {
				int vtx = qu.removeFirst();
				int r = vtx / m;
				int c = vtx % m;

				for (int[] d : dir) {
					int nr = r + d[0];
					int nc = c + d[1];

					if (nr >= 0 && nr < n && nc >= 0 && nc < m && graph[nr][nc] == 1) {
						qu.addLast(nr * m + nc);
						graph[nr][nc] = 0;
					}
				}
			}
		}

		int count = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (graph[i][j] == 1) {
					count++;
				}
			}
		}

		return count;
	}









	// https://www.hackerrank.com/challenges/journey-to-the-moon/problem
	// Journey to the Moon
	public static long nCr(int n, int r) {
		if (r > n) return 0;
		r = Math.min(r, n - r);
		long result = 1;
		for (int i = 0; i < r; i++) {
			result *= (n - i);
			result /= (i + 1);
		}
		return result;
	}
	// get Connected Components
	// O(E + V)
	// +V if there are 0 edges & V vertices only
	public static int gcc_(ArrayList<Integer>[] graph, int src, boolean[] vis) {
		int mySize = 1;
		vis[src] = true;
		for (Integer e : graph[src]) {
			if (!vis[e]) {
				mySize += gcc_(graph, e, vis);
			}
		}

		return mySize;
	}

	public static long journeyToMoon(int n, List<List<Integer>> list) {
		if (n < 2) {
			return 0;
		}

		long totalPairs = nCr(n, 2);

		ArrayList<Integer>[] graph = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			graph[i] = new ArrayList<>();
		}
		for (int i = 0; i < list.size(); i++) {
			int u = list.get(i).get(0);
			int v = list.get(i).get(1);

			graph[u].add(v);
			graph[v].add(u);
		}

		boolean[] vis = new boolean[n];
		for (int i = 0; i < n; i++) {
			if (!vis[i]) {
				int sizeOfEachGcc = gcc_(graph, i, vis);
				if (sizeOfEachGcc < 2) {
					continue;
				}
				long invalidPairs = nCr(sizeOfEachGcc, 2);
				totalPairs -= invalidPairs;
			}
		}

		return totalPairs;
	}









// ================================================== UNION FIND ==================================================
	static int[] par;
	static int[] size;
	public static int findPar(int u) { // O(log*(N)) = O(4)
		int myPar = par[u];
		if (myPar == u) {
			return u;
		}

		// return findPar(myPar); // simple return, no path compression
		return par[u] = findPar(myPar); // re-assign parent before return => path compression
	}

	public static void union(int u, int v) { // O(findPar) = O(4)
		int p1 = findPar(u);
		int p2 = findPar(v);

		if (p1 == p2) {
			// IMPORTANT CHECK if you want to maintain size array
			System.out.println("Cycle detected");
			return;
		}

		if (size[p1] > size[p2]) {
			par[p2] = p1;
			size[p1] += size[p2];
		} else {
			par[p1] = p2;
			size[p2] += size[p1];
		}

		// par[p2] = p1;
	}









	// 684. Redundant Connection
	int[] par;
	int[] ans;
	public int findPar(int u) {
		if (par[u] == u) {
			return u;
		}
		return par[u] = findPar(par[u]);  // Path compression
	}

	public void union(int u, int v) {
		int p1 = findPar(u);
		int p2 = findPar(v);

		if (p1 == p2) {
			ans = new int[] {u, v};
			return;
		}

		par[p2] = p1;
	}
	public int[] findRedundantConnection(int[][] arr) {
		if (arr == null || arr.length == 0) {
			return null;
		}

		int n = arr.length;
		par = new int[n + 1];
		for (int i = 0; i < n + 1; i++) {
			par[i] = i;
		}

		for (var x : arr) {
			int u = x[0];
			int v = x[1];

			union(u, v);
			if (ans != null) {
				break;
			}
		}

		return ans;
	}









	// 1061. Lexicographically Smallest Equivalent String
	int[] par;
	public int findPar(int u) {
		int myPar = par[u];
		if (myPar == u) {
			return u;
		}

		return par[u] = findPar(myPar); // path compression
	}
	public void union(int c1, int c2) {
		int p1 = findPar(c1);
		int p2 = findPar(c2);

		par[p1] = Math.min(p1, p2);
		par[p2] = Math.min(p1, p2);

		// if(p1 == p2){
		// 	return;
		// }

		// if (p1 < p2) {
		// 	par[p2] = p1;
		// } else {
		// 	par[p1] = p2;
		// }
	}
	public String smallestEquivalentString(String str1, String str2, String baseStr) {
		int n = str1.length();
		if (n == 0) {
			return baseStr;
		}

		par = new int[26];
		for (int i = 0; i < 26; i++) {
			par[i] = i;
		}

		for (int i = 0; i < n; i++) {
			char c1 = str1.charAt(i);
			char c2 = str2.charAt(i);

			union(c1 - 'a', c2 - 'a');
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < baseStr.length(); i++) {
			char ch = baseStr.charAt(i);
			char par = (char) (findPar(ch - 'a') + 'a');
			sb.append(par);
		}

		return sb.toString();
	}









	// 839. Similar String Groups
	// MEMORIZE - how to know strings similar to me - just check for al possible pairs
	int[] par;
	public boolean checkSimilar(String str1, String str2) {
		int n = str1.length();
		int misMatchCount = 0;
		for (int i = 0; i < n; i++) {
			if (str1.charAt(i) != str2.charAt(i)) {
				misMatchCount++;
			}
		}

		return misMatchCount <= 2;
	}

	public int findPar(int u) {
		int myPar = par[u];
		if (myPar == u) {
			return u;
		}

		return par[u] = findPar(myPar); // path compression
	}

	public void union(int u, int v) {
		int p1 = findPar(u);
		int p2 = findPar(v);

		if (p1 == p2) {
			return;
		}

		par[p2] = p1;
	}

	public int numSimilarGroups(String[] strs) {
		int n = strs.length;
		par = new int[n];
		for (int i = 0; i < n; i++) {
			par[i] = i;
		}
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				String str1 = strs[i];
				String str2 = strs[j];
				if (checkSimilar(str1, str2) == true) {
					union(i, j);
				}
			}

		}

		int count = 0;
		for (int i = 0; i < n; i++) {
			if (par[i] == i) {
				count++;
			}
		}

		return count;
	}









	// 695. Max Area of Island
	int[] par;
	int[] size;
	public int findPar(int u) {
		int myPar = par[u];
		if (myPar == u) {
			return u;
		}
		return par[u] = findPar(myPar); // path compression
	}
	public void union(int u, int v) {
		int p1 = findPar(u);
		int p2 = findPar(v);

		if (p1 == p2) {
			return;
		}

		par[p2] = p1;
		size[p1] += size[p2];
	}
	public int maxAreaOfIsland(int[][] arr) {
		if (arr == null || arr.length == 0 || arr[0].length == 0) {
			return 0;
		}

		int n = arr.length;
		int m = arr[0].length;
		par = new int[n * m];
		size = new int[n * m];
		for (int i = 0; i < n * m; i++) {
			par[i] = i;
			size[i] = 1;
		}
		int ans = 0;
		int[][] dir = new int[][] {{0, 1}, {1, 0}};
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (arr[i][j] == 1) {
					for (int[] d : dir) {
						int nr = i + d[0];
						int nc = j + d[1];
						if (nr >= 0 && nr < n && nc >= 0 && nc < m && arr[nr][nc] == 1) {
							union(i * m + j, nr * m + nc);
						}
					}
				}
			}
		}

		for (int i = 0; i < n * m; i++) {
			if (arr[i / m][i % m] == 1) {
				ans = Math.max(ans, size[i]);
			}
		}

		return ans;
	}









	// 1905. Count Sub Islands
	boolean match = true;
	int[][] dir = new int[][] {{0, 1}, {0, -1}, {1, 0}, { -1, 0}};
	public void dfs(int[][] arr2, int i, int j, int[][] arr1, int n, int m) {
		if (arr1[i][j] != 1) {
			match = false;
		}
		arr2[i][j] = 0;

		for (int[] d : dir) {
			int nr = i + d[0];
			int nc = j + d[1];

			if (nr >= 0 && nr < n && nc >= 0 && nc < m && arr2[nr][nc] == 1) {
				dfs(arr2, nr, nc, arr1, n , m);
			}
		}
	}
	public int countSubIslands(int[][] arr1, int[][] arr2) {
		int n = arr2.length;
		int m = arr2[0].length;

		int ans = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (arr2[i][j] == 1) {
					match = true;
					dfs(arr2, i, j, arr1, n, m);
					if (match == true) {
						ans++;
					}
				}
			}
		}

		return ans;
	}

	// 1905. Count Sub Islands
	// without static variable
	// boolean match = true;
	int[][] dir = new int[][] {{0, 1}, {0, -1}, {1, 0}, { -1, 0}};
	public void dfs(int[][] arr2, int i, int j, int[][] arr1, int n, int m, boolean[] match) {
		if (arr1[i][j] != 1) {
			match[0] = false;
		}
		arr2[i][j] = 0;

		for (int[] d : dir) {
			int nr = i + d[0];
			int nc = j + d[1];

			if (nr >= 0 && nr < n && nc >= 0 && nc < m && arr2[nr][nc] == 1) {
				dfs(arr2, nr, nc, arr1, n , m, match);
			}
		}
	}
	public int countSubIslands(int[][] arr1, int[][] arr2) {
		int n = arr2.length;
		int m = arr2[0].length;

		int ans = 0;
		boolean[] match = new boolean[1];
		match[0] = true;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (arr2[i][j] == 1) {
					match[0] = true;
					dfs(arr2, i, j, arr1, n, m, match);
					if (match[0] == true) {
						ans++;
					}
				}
			}
		}

		return ans;
	}









	// 305. Number of Islands II
	// 434 · Number of Islands II
	// https://www.lintcode.com/problem/434/
	int[] par;
	int count = 0;
	public int findPar(int u) {
		if (par[u] == u) {
			return u;
		}

		return par[u] = findPar(par[u]);
	}
	public void union(int u, int v) {
		int p1 = findPar(u);
		int p2 = findPar(v);

		if (p1 == p2) {
			return;
		}

		par[p2] = p1;
		count--; // decrement count as many times as the union actually happens
	}
	public List<Integer> numIslands2(int n, int m, Point[] arr) {
		List<Integer> ans = new ArrayList<>();
		if (arr == null || arr.length == 0) {
			return ans;
		}

		par = new int[n * m];
		for (int i = 0; i < n * m; i++) {
			par[i] = i;
		}
		int[][] grid = new int[n][m];

		int[][] dir = new int[][] {{0, 1}, {0, -1}, {1, 0}, { -1, 0}};
		for (int i = 0; i < arr.length; i++) {
			int r = arr[i].x;
			int c = arr[i].y;

			if (grid[r][c] == 1) {
				ans.add(count);
				continue;
			}

			grid[r][c] = 1;
			count++; // assume i am adding a new island

			for (int[] d : dir) {
				int nr = r + d[0];
				int nc = c + d[1];

				if (nr >= 0 && nr < n &&  nc >= 0 && nc < m && grid[nr][nc] == 1) {
					union(r * m + c, nr * m + nc);
				}
			}

			ans.add(count);
		}

		return ans;
	}

	// 305. Number of Islands II
	// 434 · Number of Islands II
	// https://www.lintcode.com/problem/434/
	// without using grid[][] - REDUCE SPACE by O(n*m)
	int[] par;
	int count = 0;
	public int findPar(int u) {
		if (par[u] == u) {
			return u;
		}

		return par[u] = findPar(par[u]);
	}
	public void union(int u, int v) {
		int p1 = findPar(u);
		int p2 = findPar(v);

		if (p1 == p2) {
			return;
		}

		par[p2] = p1;
		count--; // decrement count as many times as the union actually happens
	}
	public List<Integer> numIslands2(int n, int m, Point[] arr) {
		List<Integer> ans = new ArrayList<>();
		if (arr == null || arr.length == 0) {
			return ans;
		}

		par = new int[n * m];
		for (int i = 0; i < n * m; i++) {
			par[i] = -1; // assume the parent of each cell is -1
		}

		int[][] dir = new int[][] {{0, 1}, {0, -1}, {1, 0}, { -1, 0}};
		for (int i = 0; i < arr.length; i++) {
			int r = arr[i].x;
			int c = arr[i].y;

			if (par[r * m + c] != -1) {
				ans.add(count);
				continue;
			}

			par[r * m + c] = (r * m + c);
			count++; // assume i am adding a new island

			for (int[] d : dir) {
				int nr = r + d[0];
				int nc = c + d[1];

				if (nr >= 0 && nr < n &&  nc >= 0 && nc < m && par[nr * m + nc] != -1) {
					union(r * m + c, nr * m + nc);
				}
			}

			ans.add(count);
		}

		return ans;
	}









	// KRUSKAL'S MST ALGORITHM
	// 1. sort edges by weight
	// 2. apply UNION FIND









	// 1168. Optimize Water Distribution in a Village
	static int[] par = null;
	static int cost = 0;
	public static int findPar(int u) {
		if (par[u] == u) {
			return u;
		}

		return par[u] = findPar(par[u]); // path compression
	}
	public static void union(int u, int v, int w) {
		int p1 = findPar(u);
		int p2 = findPar(v);

		if (p1 == p2) {
			return;
		}

		par[p2] = p1;
		cost += w;
	}
	public static int supplyWater(int n, int k, int[] wells, int[][] pipes) {
		par = null;
		cost = 0;
		if (wells == null || wells.length == 0) {
			return 0;
		}

		ArrayList<int[]> al = new ArrayList<>();
		for (int i = 0; i < pipes.length; i++) {
			al.add(pipes[i]);
		}
		// 0 = dummy node
		for (int i = 0; i < wells.length; i++) {
			al.add(new int[] {0, i + 1, wells[i]});
		}

		Collections.sort(al, (a, b)-> {
			return a[2] - b[2]; // this - other => default bahaviour => min => increasing order
		});

		par = new int[n + 1];
		for (int i = 0; i < n + 1; i++) {
			par[i] = i;
		}

		for (int[] e : al) {
			int u = e[0];
			int v = e[1];
			int w = e[2];

			union(u, v, w);
		}

		return cost;
	}









	// Mr. President
	// https://www.hackerearth.com/practice/algorithms/graphs/minimum-spanning-tree/practice-problems/algorithm/mr-president/?purpose=login&source=problem-page&update=google
	class TestClass {
		static int[] par;
		static ArrayList<Integer> costs;
		static long totalCost = 0;
		static int gcc = 0;
		public static int findPar(int u) {
			if (par[u] == u) {
				return u;
			}

			return par[u] = findPar(par[u]); // path compression
		}
		public static void union(int u, int v, int w) {
			int p1 = findPar(u);
			int p2 = findPar(v);

			if (p1 == p2) {
				return;
			}

			par[p2] = p1;
			costs.add(w);
			totalCost += w;
			gcc--;
		}
		public static void main(String[] args) throws Exception {
			// Fast Input/Output
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter out = new PrintWriter(System.out);
			// Read the first line for N, M, and K
			String[] firstLine = br.readLine().split(" ");
			int N = Integer.parseInt(firstLine[0]); // Number of cities
			int M = Integer.parseInt(firstLine[1]); // Number of roads
			long K = Long.parseLong(firstLine[2]); // Desired sum of costs of maintenance
			// Store the roads in a List of int arrays
			List<int[]> roads = new ArrayList<>();
			for (int i = 0; i < M; i++) {
				String[] roadDetails = br.readLine().split(" ");
				int A = Integer.parseInt(roadDetails[0]); // Endpoint A
				int B = Integer.parseInt(roadDetails[1]); // Endpoint B
				int C = Integer.parseInt(roadDetails[2]); // Maintenance cost
				roads.add(new int[] {A, B, C});
			}

			// LOGIC STARTS
			par = null;
			costs = null;
			totalCost = 0;
			gcc = N;

			if (K < N - 1) {
				out.println(-1);
				out.flush();
				out.close();
				return;
			}

			Collections.sort(roads, (a, b)-> {
				return a[2] - b[2];
			});

			par = new int[N + 1];
			for (int i = 0; i < N + 1; i++) {
				par[i] = i;
			}
			costs = new ArrayList<>();
			totalCost = 0;

			for (int[] e : roads) {
				int u = e[0];
				int v = e[1];
				int w = e[2];

				union(u, v, w);
			}

			int ans = 0;
			for (int i = costs.size() - 1; i >= 0; i--) {
				if (totalCost <= K) {
					break;
				}

				totalCost = totalCost - costs.get(i) + 1;
				ans++;
			}

			if (totalCost > K) {
				ans = -1;
			}

			// EDGE CASE
			if (gcc > 1) {
				ans = -1;
				break;
			}

			out.println(ans);
			out.flush();
			out.close();
		}
	}









	// 1584. Min Cost to Connect All Points
	int[] par;
	int cost = 0;
	int gcc = 0;
	public int findPar(int u) {
		int myPar = par[u];
		if (myPar == u) {
			return u;
		}

		return par[u] = findPar(myPar); // path compression
	}

	public void union(int u, int v, int w) {
		int p1 = findPar(u);
		int p2 = findPar(v);

		if (p1 == p2) {
			return;
		}

		par[p2] = p1;
		cost += w;
		gcc--;
	}

	public int minCostConnectPoints(int[][] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}


		int n = arr.length;
		par = new int[n];
		for (int i = 0; i < n; i++) {
			par[i] = i;
		}
		cost = 0;
		gcc = n;

		ArrayList<int[]> edges = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n ; j++) {
				int x1 = arr[i][0];
				int y1 = arr[i][1];
				int x2 = arr[j][0];
				int y2 = arr[j][1];
				int d = Math.abs(x2 - x1) + Math.abs(y2 - y1);

				edges.add(new int[] {i, j, d});
			}
		}
		Collections.sort(edges, (a, b) -> {
			return a[2] - b[2];
		});

		for (int[] e : edges) {
			int i = e[0];
			int j = e[1];
			int d = e[2];

			union(i, j, d);

			if (gcc == 1) {
				break;
			}
		}

		return cost;
	}

	// USING HASHMAP for par
	HashMap<Long, Long> par;
	int cost = 0;
	public long findPar(long u) {
		long myPar = par.get(u);
		if (myPar == u) {
			return u;
		}

		par.put(u, findPar(myPar));

		return par.get(u); // path compression
	}

	public void union(long u, long v, int w) {
		long p1 = findPar(u);
		long p2 = findPar(v);

		if (p1 == p2) {
			return;
		}

		par.put(p2, p1);
		cost += w;
	}

	public int minCostConnectPoints(int[][] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int n = arr.length;
		par = new HashMap<>();
		cost = 0;
		int max = (int)1e7;

		ArrayList<int[]> edges = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				int x1 = arr[i][0];
				int y1 = arr[i][1];
				int x2 = arr[j][0];
				int y2 = arr[j][1];

				int md = Math.abs(x2 - x1) + Math.abs(y2 - y1);

				edges.add(new int[] { x1, y1, x2, y2, md});
				par.put(x1 * max + y1 + 0L, x1 * max + y1 + 0L);
				par.put(x2 * max + y2 + 0L, x2 * max + y2 + 0L);
			}
		}

		Collections.sort(edges, (a, b) -> {
			return a[4] - b[4];
		});

		for (int[] e : edges) {
			int x1 = e[0];
			int y1 = e[1];
			int x2 = e[2];
			int y2 = e[3];
			int md = e[4];

			union(x1 * max + y1, x2 * max + y2, md);
		}

		return cost;
	}









	// 924. Minimize Malware Spread
	int[] par;
	int[] population;
	public int findPar(int u) {
		if (par[u] == u) {
			return u;
		}

		return par[u] = findPar(par[u]); // path compression
	}
	public void union(int u, int v) {
		int p1 = findPar(u);
		int p2 = findPar(v);

		if (p1 == p2) {
			return;
		}

		par[p2] = p1;
		population[p1] += population[p2];
	}
	public int minMalwareSpread(int[][] graph, int[] initial) {
		if (graph == null || graph.length == 0 || initial == null || initial.length == 0) {
			return -1;
		}

		int n = graph.length;
		par = new int[n];
		population = new int[n];
		for (int i = 0; i < n; i++) {
			par[i] = i;
			population[i] = 1;
		}

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (graph[i][j] == 1) {
					union(i, j);
				}
			}
		}

		int[] infectedCount = new int[n];
		for (int i = 0; i < initial.length; i++) {
			int node = initial[i];
			int p = findPar(node);
			infectedCount[p]++;
		}

		Arrays.sort(initial);

		int ans = initial[0];
		int maxPopulation = 0;
		for (int i = 0; i < initial.length; i++) {
			int node = initial[i];
			int p = findPar(node);

			if (infectedCount[p] == 1 && population[p] > maxPopulation) {
				maxPopulation = population[p];
				ans = node;
			}
		}

		return ans;
	}









	// 959. Regions Cut By Slashes
	// MEMORIZE
	int[] par;
	int ans = 0;
	public int findPar(int u) {
		if (par[u] == u) {
			return u;
		}

		return par[u] = findPar(par[u]);
	}
	public void union(int u, int v) {
		int p1 = findPar(u);
		int p2 = findPar(v);

		if (p1 == p2) {
			ans++;
		}

		par[p2] = p1;
	}
	public int regionsBySlashes(String[] arr) {
		if (arr == null || arr.length == 0 || arr[0].length() == 0) {
			return 0;
		}

		int arrayLength = arr.length;
		int n = arrayLength + 1;
		par = new int[n * n];
		for (int i = 0; i < n * n; i++) {
			par[i] = i;
		}
		// assume (0,0) is the global parent of all the, edge touching vertices
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i == 0 || j == 0 || i == n - 1 || j == n - 1) {
					par[i * n + j] = 0 * n + 0;
				}
			}
		}
		ans = 1;

		for (int i = 0; i < arrayLength; i++) {
			for (int j = 0; j < arrayLength; j++) {
				int[] start = null;
				int[] end = null;
				if (arr[i].charAt(j) == '/') {
					// coordinates of the endpoints of the edge
					start = new int[] {i, j + 1};
					end = new int[] {i + 1, j};
				} else if (arr[i].charAt(j) == '\\') {
					start = new int[] {i, j};
					end = new int[] {i + 1, j + 1};
				}

				if (start != null && end != null) {
					union(start[0] * n + start[1], end[0] * n + end[1]);
				}
			}
		}

		return ans;
	}









	// 990. Satisfiability of Equality Equations
	int[] par;
	public int findPar(int u) {
		if (par[u] == u) {
			return u;
		}

		return par[u] = findPar(par[u]); // path compression
	}
	public void union(int u, int v) {
		int p1 = findPar(u);
		int p2 = findPar(v);

		if (p1 == p2) {
			return;
		}

		par[p2] = p1;
	}
	public boolean equationsPossible(String[] arr) {
		if (arr == null || arr.length == 0) {
			return true;
		}

		par = new int[26];
		for (int i = 0; i < 26; i++) {
			par[i] = i;
		}
		int n = arr.length;
		boolean ans = true;

		// first process all the eqality eqations
		for (int i = 0; i < n; i++) {
			String eq = arr[i];
			char v1 = eq.charAt(0);
			char eqType = eq.charAt(1);
			char v2 = eq.charAt(3);

			if (eqType == '=') {
				union(v1 - 'a', v2 - 'a');
			}
		}

		// then process all the non eqality eqations
		for (int i = 0; i < n; i++) {
			String eq = arr[i];
			char v1 = eq.charAt(0);
			char eqType = eq.charAt(1);
			char v2 = eq.charAt(3);

			if (eqType == '!') {
				int p1 = findPar(v1 - 'a');
				int p2 = findPar(v2 - 'a');

				if (p1 == p2) {
					ans = false;
					break;
				}
			}
		}

		return ans;
	}









	// 815. Bus Routes
	// MEMORIZE
	// multisource bfs from source to destination based on bus connectivity
	// We dont really care about the stops or the direction of the routes
	// all we care about is connectivity
	// MEMORY LIMIT EXCEEDED
	public int numBusesToDestination(int[][] routes, int source, int destination) {
		if (source == destination) {
			return 0;
		}

		int n = routes.length; // total no of buses

		HashMap<Integer, ArrayList<Integer>> map = new HashMap<>(); // stop -> list of buses mapping

		for (int i = 0; i < n ; i ++) {
			for (int j = 0; j < routes[i].length; j++) {
				int stop = routes[i][j];

				map.putIfAbsent(stop, new ArrayList<>());

				ArrayList<Integer> buses = map.get(stop);
				buses.add(i);
			}
		}

		if (!map.containsKey(source) || !map.containsKey(destination)) {
			return -1;
		}

		// ****************************** MEMORY LIMIT EXCEEDED ******************************
		// connectivity graph
		// array of arraylist
		ArrayList<Integer>[] graph = new ArrayList[n]; // intitialize array
		for (int i = 0; i < n ; i++) {
			graph[i] = new ArrayList<>(); // intitialize arraylist
		}

		for (var x : map.entrySet()) {
			int stop = x.getKey();
			ArrayList<Integer> buses = x.getValue();

			int m = buses.size();
			for (int i = 0; i < m; i++) {
				for (int j = i + 1; j < m; j++) {
					int bus1 = buses.get(i);
					int bus2 = buses.get(j);

					graph[bus1].add(bus2);
					graph[bus2].add(bus1);
				}
			}
		}
		// ****************************** MEMORY LIMIT EXCEEDED ******************************

		// multisource bfs from source to destination based on bus connectivity
		LinkedList<Integer> qu = new LinkedList<>();
		boolean[] vis = new boolean[n];
		// add all buses that pass through the source stop
		for (int bus : map.get(source)) {
			qu.addLast(bus);
			vis[bus] = true;
		}

		HashSet<Integer> destinationBuses = new HashSet<>();
		for (int bus : map.get(destination)) {
			destinationBuses.add(bus);
		}

		int level = 0;
		while (qu.size() != 0) {
			int size = qu.size();
			while (size-- > 0) {
				int bus = qu.removeFirst();
				if (destinationBuses.contains(bus)) {
					return level + 1;
				}

				for (int nbr : graph[bus]) {
					if (!vis[nbr]) {
						qu.addLast(nbr);
						vis[nbr] = true;
					}
				}
			}

			level++;
		}

		return -1;
	}

	// 815. Bus Routes
	// MEMORIZE
	// multisource bfs from source to destination based on bus connectivity
	// removed the bus connectivity graph construction from 1st approach to avoid MLE
	// we can figure out the nextBus using the routes array and stopToBuses HashMap
	// ACCEPTED
	public int numBusesToDestination(int[][] routes, int source, int destination) {
		if (source == destination) {
			return 0;
		}

		int n = routes.length; // total no of buses

		HashMap<Integer, ArrayList<Integer>> stopToBuses = new HashMap<>(); // stop -> list of buses mapping

		for (int i = 0; i < n ; i ++) {
			for (int j = 0; j < routes[i].length; j++) {
				int stop = routes[i][j];

				stopToBuses.putIfAbsent(stop, new ArrayList<>());

				ArrayList<Integer> buses = stopToBuses.get(stop);
				buses.add(i);
			}
		}

		if (!stopToBuses.containsKey(source) || !stopToBuses.containsKey(destination)) {
			return -1;
		}

		// multisource bfs from source to destination based on bus connectivity
		LinkedList<Integer> qu = new LinkedList<>();
		boolean[] vis = new boolean[n];
		// add all buses that pass through the source stop
		for (int bus : stopToBuses.get(source)) {
			qu.addLast(bus);
			vis[bus] = true;
		}

		HashSet<Integer> destinationBuses = new HashSet<>();
		for (int bus : stopToBuses.get(destination)) {
			destinationBuses.add(bus);
		}

		int level = 0;
		while (qu.size() != 0) {
			int size = qu.size();
			while (size-- > 0) {
				int bus = qu.removeFirst();
				if (destinationBuses.contains(bus)) {
					return level + 1;
				}

				for (int stop : routes[bus]) {
					for (int nextBus : stopToBuses.get(stop)) {
						if (vis[nextBus] == false) {
							vis[nextBus] = true;
							qu.addLast(nextBus);
						}
					}
				}

				// for (int nbr : graph[bus]) {
				// 	if (!vis[nbr]) {
				// 		qu.addLast(nbr);
				// 		vis[nbr] = true;
				// 	}
				// }
			}

			level++;
		}

		return -1;
	}

	// 815. Bus Routes
	// MEMORIZE
	// bfs from source to destination based on stops
	// ACCEPTED
	public int numBusesToDestination(int[][] routes, int source, int destination) {
		if (source == destination) {
			return 0;
		}

		int n = routes.length; // total no of buses

		HashMap<Integer, ArrayList<Integer>> stopToBuses = new HashMap<>(); // stop → list of buses mapping
		for (int i = 0; i < n ; i ++) {
			for (int j = 0; j < routes[i].length; j++) {
				int stop = routes[i][j];

				stopToBuses.putIfAbsent(stop, new ArrayList<>());

				ArrayList<Integer> buses = stopToBuses.get(stop);
				buses.add(i);
			}
		}

		if (!stopToBuses.containsKey(source) || !stopToBuses.containsKey(destination)) {
			return -1;
		}

		// bfs from source to destination based on stops
		LinkedList<Integer> qu = new LinkedList<>();
		HashSet<Integer> visitedStops = new HashSet<>();
		HashSet<Integer> visitedBuses = new HashSet<>();
		qu.addLast(source);
		visitedStops.add(source);

		int level = 0;
		while (qu.size() != 0) {
			int size = qu.size();
			while (size-- > 0) {
				int stop = qu.removeFirst();

				if (stop == destination) {
					return level;
				}

				for (int bus : stopToBuses.get(stop)) {
					if (visitedBuses.contains(bus) == false) {
						visitedBuses.add(bus);
						for (int nextStop : routes[bus]) {
							if (visitedStops.contains(nextStop) == false) {
								qu.addLast(nextStop);
								visitedStops.add(nextStop);
							}
						}
					}
				}
			}

			level++;
		}

		return -1;
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
		// Adjacency list representation of graph
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
		display(graph);
		// removeEdge(graph, 2, 1);
		// removeVertex(graph, 2);
		// display(graph);









		// System.out.println(hasPath(graph, 0 , 6));

		// ArrayList<Integer> psf = new ArrayList<>();
		// ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
		// System.out.println(allPath(graph, 0, 6, psf, ans));
		// print2DList(ans);

		// preOrder(graph, 0, new boolean[V], " ", 0);
		// postOrder(graph, 0, new boolean[V], " ", 0);









		// Pair heaviestPathAns = heaviestPath(graph, 0 , 6, new boolean[V]);
		// System.out.println(heaviestPathAns.psf + " @ " +  heaviestPathAns.weight);









		// addEdge (graph, 6, 8, 100);
		// hamiltonianPath(graph, 0, new boolean[V], new ArrayList<>());
		// hamiltonianPathAndCycle(graph, 0, new boolean[V], new ArrayList<>(), 0);
		// removeEdge(graph, 3, 4);
		// System.out.println(gcc(graph));









		// boolean[] vis = new boolean[V];
		// bfs(graph, 0, vis);
		// vis = new boolean[V];
		// bfs_withoutCycle(graph, 0, vis);









// ================================================== UNION FIND ==================================================
		int[][] edges = new int[][] {
		    new int[]{0, 1, 10},
		    new int[]{0, 3, 10},
		    new int[]{1, 2, 10},
		    new int[]{2, 3, 40},
		    new int[]{2, 7, 2},
		    new int[]{2, 8, 4},
		    new int[]{7, 8, 3},
		    new int[]{3, 4, 2},
		    new int[]{4, 5, 2},
		    new int[]{4, 6, 8},
		    new int[]{5, 6, 3},
		};

		// KRUSKAL'S MST ALGORITHM
		Arrays.sort(arr, (a, b)-> {
			return a[2] - b[2];
		});

		// union find // T:O(V + E)
		par = new int[V];
		size = new int[V];

		for (int i = 0; i < V; i++) { // O(V)
			par[i] = i;
			size[i] = 1;
		}

		for (int[] e : edges) { // O(E * 4) = O(E)
			int u = e[0];
			int v = e[1];

			union(u, v); // O(4)
		}


		for (int[] e : edges) { // O(E * 4) = O(E)
			int u = e[0];
			int v = e[1];

			union(u, v); // O(4)
		}









	}
}