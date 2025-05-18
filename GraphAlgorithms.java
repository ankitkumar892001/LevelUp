import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class GraphAlgorithms {

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









// ============================== GRAPH ALGORITHMS ==============================









	// TODO START
	// DIJKSTRA'S ALGORITHM
	// 1. Finds the shortest distance to each node from a given source node
	// 2. Source-dependent algorithm - results change based on starting node
	// 3. BFS with cycle detection - mark vis after popping out from the queue
	// 2. but use PriorityQueue instead of Queue & sort on the basis on wsf
	// 3. always processes the node with the shortest known distance first
	// 4. Creates a shortest path spanning tree (not necessarily an MST)

	// - Store additional information (parent, edge weight) in the Pair class as needed
	// - Retrieve this information when popping from the priority queue

	// Limitations:
	// 1. Cannot work correctly with negative edges, even in positive cycles
	//    - Negative edges break the greedy property of the algorithm
	//    - Once a node is processed, its distance is considered final
	//    - A longer initial path could become shorter later due to negative edges
	// 2. Cannot work with negative cycles
	//    - In negative cycles, you can find continuously improving paths
	//    - It's theoretically impossible to find shortest paths with negative cycles

	// avoid dijkstra_Better - same worst case complexity, increased chances of error


	// DIJKSTRA'S (SHORTEST PATH SPANNING TREE) ALGORITHM
	// 1. shortest distance to each node from a given source node
	// 1. source dependent algorithm
	// 4. BFS - with cycle detection - mark vis after popping out from the queue
	// 2. but use PriorityQueue instead of Queue & sort on the basis on wsf
	// LOGIC - always process the node with the shortest known wsf first, at any point of time i want to process that node jaha tak jane ka cost(wsf) sabse min ho, from all available options
	// whatever information you need apart from node & wsf just keep adding it into the Pair like par , w and fetch it after popping Pair from the queue
	// dijkstra graph gives you a spanning tree , not necessarily mst
	// 4. dijkstra does not work well with -ve edges OR -ve cycle graphs
	// The algorithm works correctly only when we can guarantee that the first time we reach a node, it's through the shortest possible path. Negative edges violate this guarantee because a longer initial path could later become shorter due to negative edges.
	// because in case of -ve cycle you can always find a better(min cost) path with each iteration of th cycle
	// so is is theoretically not possible to find the shortest distance to a node with -ve cycle
	// 6. avoid dijkstra_Better - same worst case complexity, increased chances of error
	// TODO END

	// DIJKSTRA'S ALGORITHM
	// single-source shortest path algorithm
	public static class Pair {
		int node; // current node
		int wsf; // total distance from source to curr node
		int w; // distacne from par to curr node
		int par; // not mandatory - just for making the dijkstrasGraph & primsGraph

		// Dijkstra's
		Pair(int node, int wsf, int w, int par) { // node & wsf are sufficient for Dijkstra's
			this.node = node;
			this.wsf = wsf;
			this.w = w;
			this.par = par;
		}

		// Prim's
		Pair(int node, int w, int par) { // node and w are sufficient for Prim's
			this.node = node;
			this.w = w;
			this.par = par;
		}

		// Dijkstra's Better
		Pair(int node, int wsf) {
			this.node = node;
			this.wsf = wsf;
		}

	}

	// since this is based on bfs with cycle detection - all the edges will be processed
	public static void dijkstrasAlgorithm(ArrayList<Edge>[] graph, int src) {
		int V = graph.length;
		ArrayList<Edge>[] dijkstrasGraph = new ArrayList[V];
		for (int i = 0; i < V; i++) {
			dijkstrasGraph[i] = new ArrayList<>();
		}

		boolean[] vis = new boolean[V];
		PriorityQueue<Pair> pq = new PriorityQueue<>((a, b)-> {
			return a.wsf - b.wsf; // sort on the basis of wsf -> Dijkstra's
		});
		pq.add(new Pair(src, 0, 0, src));

		while (pq.size() != 0 ) {
			Pair rp = pq.remove();
			int node = rp.node;
			int wsf = rp.wsf;

			if (vis[node] == true) {
				continue;
			}
			vis[node] = true;
			System.out.println("reached " + node + " with wsf " + wsf);
			if (node != src) {
				addEdge(dijkstrasGraph, node, rp.par, rp.w);
			}

			for (Edge e : graph[node]) {
				int nbr = e.v;
				if (!vis[nbr]) {
					pq.add(new Pair(nbr, rp.wsf + e.w, e.w, node));
				}
			}
		}

		System.out.println("DIJKSTRAS GRAPH");
		display(dijkstrasGraph);
	}









	// DIJKSTRA'S ALGORITHM BETTER
	// reducing the total no of comparisons in the PQ by not adding the options that are pointless
	// Example: If (node=1, wsf=10) is already added, there’s no need to add (node=1, wsf=80)
	// it can handle -ve edge because we are updating the dis array every time we find a shorter path
	// but it cannot detect -ve cycle and it will result in an infinite loop
	// we can't expect a problem that can have -ve edge , will not have -ve cycle

	// hence for -ve edge & -ve cycle
	// the 1st approach with will give you WRONG ANSWER
	// and the 2nd approach will give you TLE / INFINITE LOOP
	// A --(1)--> B --(1)--> C
	//            ^          |
	//            |          |
	//          (-10)       (1)
	//            |          |
	//            |          V
	//            E <--(1)-- D

	public static void dijkstrasAlgorithm_Better(ArrayList<Edge>[] graph, int src) {
		int V = graph.length;

		int[] dis = new int[V];
		Arrays.fill(dis, (int)1e9);
		PriorityQueue<Pair> pq = new PriorityQueue<>((a, b)-> {
			return a.wsf - b.wsf;
		});
		pq.add(new Pair(src, 0));
		dis[src] = 0;

		while (pq.size() != 0 ) {
			Pair rp = pq.remove();
			int node = rp.node;
			int wsf = rp.wsf;

			if (wsf > dis[node]) {
				continue;
			}
			System.out.println("reached " + node + " with wsf " + wsf);

			for (Edge e : graph[node]) {
				int nbr = e.v;
				int newCost = rp.wsf + e.w;
				if (newCost < dis[nbr]) {
					pq.add(new Pair(nbr, newCost));
					dis[nbr] = newCost;
				}
			}
		}

		for (int i = 0; i < V; i++) {
			System.out.println(i + " -> " + dis[i]);
		}
	}









	// PRIM'S MST ALGORITHM
	// similar to Dijkstra - just sort on the basis of w
	// 1. BFS - with cycle detection - mark vis after popping out from the queue
	// 2. but use PriorityQueue instead of Queue & sort on the basis on w
	public static void primsAlgorithm(ArrayList<Edge>[] graph, int src) {
		int V = graph.length;
		ArrayList<Edge>[] primsGraph = new ArrayList[V];
		for (int i = 0; i < V; i++) {
			primsGraph[i] = new ArrayList<>();
		}

		boolean[] vis = new boolean[V];
		PriorityQueue<Pair> pq = new PriorityQueue<>((a, b)-> {
			return a.w - b.w; // sort on the basis of w -> Prim's
		});
		pq.add(new Pair(src, 0, src));

		while (pq.size() != 0 ) {
			Pair rp = pq.remove();
			int node = rp.node;
			int w = rp.w;

			if (vis[node] == true) {
				System.out.println("Cycle detected at " + node);
				continue;
			}
			vis[node] = true;
			System.out.println("reached " + node + " with w " + w);
			if (node != src) {
				addEdge(primsGraph, node, rp.par, rp.w);
			}

			for (Edge e : graph[node]) {
				int nbr = e.v;
				if (!vis[nbr]) {
					pq.add(new Pair(nbr, e.w, node));
				}
			}
		}

		System.out.println("PRIMS GRAPH");
		display(primsGraph);
	}









	// BELLMAN–FORD ALGORITHM
	// single-source shortest path algorithm
	// source dependent algorithm - results can change based on starting node
	// handle -ve edges
	// detect -ve cycles
	// it can work with edges array , no need to construct graph
	// T - E * (V+1) - O(E*V)
	public static void bellmanFordAlgorithm(int V, int[][] edges, int src) {
		int[] prev = new int[V];
		int[] curr = new int[V];
		Arrays.fill(prev, (int)1e9);
		prev[src] = 0; // if you are using atmost 0 edge, then you can only reach to src

		boolean isNegativeCycle = false;
		// edgeCount -> the shortest distance to each ndoe by using atmost edgeCount(1,2,...) edges
		for (int edgeCount = 1; edgeCount <= V; edgeCount++) {
			// copy prev to curr
			for (int i = 0; i < V; i++) {
				curr[i] = prev[i];
			}

			boolean isAnyUpdate = false;

			for (int[] e : edges) {
				int u = e[0];
				int v = e[1];
				int w = e[2];

				if (prev[u] != (int) 1e9 && prev[u] + w < curr[v]) { // dis[u] != inf -> avoid integer overflow
					curr[v] = prev[u] + w;
					isAnyUpdate = true;
				}
			}

			if (!isAnyUpdate) {
				break;
			}

			// if there is an update in the Vth iteration => -ve cycle
			if (edgeCount == V && isAnyUpdate == true) {
				isNegativeCycle = true;
			}


			// copy curr to prev
			for (int i = 0; i < V; i++) {
				prev[i] = curr[i];
			}
		}

		for (int i = 0 ; i < V; i++) {
			System.out.println(src + " -> " + i + " shortest path " + curr[i]);
		}

		// System.out.println("isNegativeCycle -> " + isNegativeCycle);
	}

	public static void bellmanFordAlgorithm_1(int V, int[][] edges, int src) {
		int[] dis = new int[V];
		Arrays.fill(dis, (int)1e9);
		dis[src] = 0;

		boolean isNegativeCycle = false;

		for (int i = 1; i <= V; i++) {
			boolean isAnyUpdate = false;

			for (int[] e : edges) {
				int u = e[0];
				int v = e[1];
				int w = e[2];

				if (dis[u] != (int) 1e9 && dis[u] + w < dis[v]) {
					dis[v] = dis[u] + w;
					isAnyUpdate = true;
				}
			}

			if (!isAnyUpdate) {
				break;
			}

			if (i == V && isAnyUpdate == true) {
				isNegativeCycle = true;
			}
		}

		for (int i = 0; i < V; i++) {
			System.out.println(src + " -> " + i + " shortest path " + dis[i]);
		}

		// System.out.println("isNegativeCycle -> " + isNegativeCycle);
	}









// ============================== QUESTIONS ==============================









	// 743. Network Delay Time
	public class Edge {
		int v;
		int w;
		Edge(int v, int w) {
			this.v = v;
			this.w = w;
		}
	}

	public class Pair {
		int node;
		int wsf;
		Pair(int node , int wsf) {
			this.node = node;
			this.wsf = wsf;
		}
	}

	public int dijkstrasAlgorithm(ArrayList<Edge>[] graph, int src) {
		int V = graph.length;

		boolean[] vis = new boolean[V];
		int[] dis = new int[V];
		Arrays.fill(dis, (int)1e9);
		PriorityQueue<Pair> pq = new PriorityQueue<>((a, b)-> {
			return a.wsf - b.wsf;
		});
		pq.add(new Pair(src, 0));

		while (pq.size() != 0) {
			Pair p = pq.remove();
			int node = p.node;
			int wsf = p.wsf;

			if (vis[node] == true) {
				continue;
			}
			vis[node] = true;
			dis[node] = wsf;

			for (Edge e : graph[node]) {
				int nbr = e.v;
				if (!vis[nbr]) {
					pq.add(new Pair(nbr, wsf + e.w));
				}
			}
		}

		int maxWsf = 0;
		for (int i = 1; i < V; i++) {
			if (dis[i] == (int)1e9) {
				return -1;
			}
			maxWsf = Math.max(maxWsf , dis[i]);
		}

		return maxWsf;
	}

	public int dijkstrasAlgorithm_Better(ArrayList<Edge>[] graph, int src) {
		int V = graph.length;

		boolean[] vis = new boolean[V];
		int[] dis = new int[V];
		Arrays.fill(dis, (int)1e9);
		PriorityQueue<Pair> pq = new PriorityQueue<>((a, b)-> {
			return a.wsf - b.wsf;
		});
		dis[src] = 0;
		pq.add(new Pair(src, 0));


		while (pq.size() != 0) {
			Pair rp = pq.remove();
			int node = rp.node;
			int wsf = rp.wsf;

			if (vis[node] == true) {
				continue;
			}
			vis[node] = true;

			for (Edge e : graph[node]) {
				int nbr = e.v;
				int newCost = wsf + e.w;
				if (!vis[nbr] && newCost < dis[nbr]) {
					dis[nbr] = newCost;
					pq.add(new Pair(nbr, newCost));
				}
			}
		}

		int maxWsf = 0;
		for (int i = 1; i < V; i++) {
			if (dis[i] == (int)1e9) {
				return -1;
			}
			maxWsf = Math.max(maxWsf , dis[i]);
		}

		return maxWsf;
	}

	public int networkDelayTime(int[][] arr, int n, int k) {
		if (arr == null) {
			return -1;
		}

		ArrayList<Edge>[] graph = new ArrayList[n + 1];
		for (int i = 0; i < graph.length; i++) {
			graph[i] = new ArrayList<>();
		}

		for (int[] e : arr) {
			int u = e[0];
			int v = e[1];
			int w = e[2];

			graph[u].add(new Edge(v, w));
		}

		// return dijkstrasAlgorithm(graph, k);
		return dijkstrasAlgorithm_Better(graph, k);
	}









	// 787. Cheapest Flights Within K Stops
	public class Edge {
		int v;
		int w;
		Edge(int v, int w) {
			this.v = v;
			this.w = w;
		}
	}

	public class Pair {
		int node;
		int wsf;

		Pair(int node, int wsf) {
			this.node = node;
			this.wsf = wsf;
		}
	}

	// DFS
	// TLE
	int ans = (int)1e9;
	public void findCheapestPrice_dfs(ArrayList<Edge>[] graph, int node, int des, int k, int currPathCost, boolean[] vis) {
		if (k < 0) {
			return;
		}
		if (node == des) {
			ans = Math.min(ans, currPathCost);
			return;
		}
		if (currPathCost > ans) {
			return;
		}

		vis[node] = true;
		for (Edge e : graph[node]) {
			int nbr = e.v;
			int cost = e.w;
			if (!vis[nbr]) {
				findCheapestPrice_dfs(graph, nbr, des, k - 1, currPathCost + cost, vis);
			}
		}
		vis[node] = false; // unmark - to explore all the paths
	}

	// BFS
	// TLE
	public int findCheapestPrice_bfs(ArrayList<Edge>[] graph, int src, int des, int k) {
		int ans = (int)1e9;
		LinkedList<Pair> qu = new LinkedList<>();
		qu.addLast(new Pair(src, 0));

		int level = 0;
		while (qu.size() != 0) {
			if (level > k) {
				break;
			}

			int size = qu.size();
			while (size-- > 0) {
				Pair rp = qu.removeFirst();
				int vtx = rp.node;
				int wsf = rp.wsf;

				if (vtx == des) {
					ans = Math.min(ans, wsf);
				}
				if (wsf > ans) {
					continue;
				}

				for (Edge e : graph[vtx]) {
					int nbr = e.v;
					int cost = e.w;
					qu.addLast(new Pair(nbr, wsf + cost));
				}
			}
			level++;
		}

		return ans;
	}

	// bellmanFordAlgorithm
	// ACCEPTED
	public int findCheapestPrice_bellmanFordAlgorithm(int V, int[][] edges, int src, int des, int k) {
		int[] prev = new int[V];
		int[] curr = new int[V];
		Arrays.fill(prev, (int)1e9);
		prev[src] = 0;

		// i can use atmost k + 1 edges
		for (int edgeCount = 1; edgeCount <= k + 1; edgeCount++) {
			// copy prev to curr
			for (int i = 0 ; i < V; i++) {
				curr[i] = prev[i];
			}

			boolean isAnyUpdate = false;

			for (int[] e : edges) {
				int u = e[0];
				int v = e[1];
				int w = e[2];

				if (prev[u] != (int)1e9 && prev[u] + w < curr[v]) {
					curr[v] = prev[u] + w;
					isAnyUpdate = true;
				}
			}

			if (!isAnyUpdate) {
				break;
			}

			// copy curr to prev
			for (int i = 0 ; i < V; i++) {
				prev[i] = curr[i];
			}
		}

		return curr[des] == (int)1e9 ? -1 : curr[des];
	}

	public int findCheapestPrice(int n, int[][] flights, int src, int des, int k) {
		if (src == des) {
			return 0;
		}

		return findCheapestPrice_bellmanFordAlgorithm(n, flights, src, des, k);

		// ArrayList<Edge>[] graph = new ArrayList[n];
		// for (int i = 0; i < n; i++) {
		// 	graph[i] = new ArrayList<>();
		// }

		// for (int[] e : flights) {
		// 	int u = e[0];
		// 	int v = e[1];
		// 	int w = e[2];

		// 	graph[u].add(new Edge(v, w));
		// }

		// DFS
		// max stops i can take excluding source and including des = k + 1
		// boolean[] vis = new boolean[n];
		// findCheapestPrice_dfs(graph, src, des, k + 1, 0, vis);

		// BFS
		// max level till which i can explore = k + 1
		// int ans = findCheapestPrice_bfs(graph, src, des, k + 1);

		// return ans == (int)1e9 ? -1 : ans;
	}









	// 787 · The Maze
	// https://www.lintcode.com/problem/787/
	// 490. The Maze
	// BFS without cycle detection & JUMP
	public class Pair {
		int r;
		int c;
		Pair(int r, int c) {
			this.r = r;
			this.c = c;
		}
	}
	int[][] dir = new int[][] {{0, 1}, {0, -1}, {1, 0}, { -1, 0}};
	public boolean hasPath_bfs(int[][] arr, int[] src, int[] des, boolean[][] vis) {
		int n = arr.length;
		int m = arr[0].length;

		LinkedList<Pair> qu = new LinkedList<>();
		qu.addLast(new Pair(src[0], src[1]));
		vis[src[0]][src[1]] = true;

		while (qu.size() != 0) {
			Pair rp = qu.removeFirst();
			int r = rp.r;
			int c = rp.c;

			if (r == des[0] && c == des[1]) {
				return true;
			}

			for (int[] d : dir) {
				int nr = r;
				int nc = c;
				// jump
				while (nr >= 0 && nr < n && nc >= 0 && nc < m && arr[nr][nc] == 0) {
					nr += d[0];
					nc += d[1];
				}
				nr -= d[0];
				nc -= d[1];
				// we will always stop 1 cell before boundary/obstacle

				if (!vis[nr][nc]) { // now check if that cell is already visited or not
					qu.addLast(new Pair(nr, nc));
					vis[nr][nc] = true;
				}
			}
		}

		return false;
	}
	public boolean hasPath(int[][] arr, int[] src, int[] des) {
		int n = arr.length;
		int m = arr[0].length;
		boolean[][] vis = new boolean[n][m];

		return hasPath_bfs(arr, src, des, vis);
	}









	// 788 · The Maze II
	// https://www.lintcode.com/problem/788/
	// 505. The Maze II
	public class Pair {
		int r;
		int c;
		int dis;
		Pair(int r, int c, int dis) {
			this.r = r;
			this.c = c;
			this.dis = dis;
		}
	}
	int[][] dir = new int[][] {{0, 1}, {0, -1}, {1, 0}, { -1, 0}};
	// bfs with cycle detection & JUMP -> mark vis after popping out from the queue
	// WRONG ANSWER

	// BFS (using LinkedList) with/without cycle detection guarantees shortest path only in unweighted/same wighted (typically 1) graphs, as it explores nodes in layers
	// but in this problem, each move can have a different "weight" (distance traveled), so BFS cannot guarantee the shortest distance when moving from one node to another
	public int shortestDistance_bfs(int[][] arr, int[] src, int[] des) {
		int n = arr.length;
		int m = arr[0].length;
		boolean[][] vis = new boolean[n][m];

		LinkedList<Pair> qu = new LinkedList<>();
		qu.addLast(new Pair(src[0], src[1], 0));

		while (qu.size() != 0) {
			Pair rp = qu.removeFirst();
			int r = rp.r;
			int c = rp.c;
			int dis = rp.dis;

			if (r == des[0] && c == des[1]) {
				return dis;
			}
			if (vis[r][c] == true) {
				continue;
			}
			vis[r][c] = true;

			for (int[] d : dir) {
				int nr = r;
				int nc = c;
				int jump = 0;
				while (nr >= 0 && nr < n && nc >= 0 && nc < m && arr[nr][nc] == 0) {
					nr += d[0];
					nc += d[1];
					jump++;
				}
				nr -= d[0];
				nc -= d[1];
				jump--;

				if (!vis[nr][nc]) {
					qu.addLast(new Pair(nr, nc, dis + jump));
				}
			}
		}

		return -1;
	}
	// when moves have different "weight" (distances traveled), the first path found is not necessarily the shortest
	// marking nodes as vis after exploring them prevents us from discovering potentially shorter paths that visit the same node later

	// use PriorityQueue (Dijkstra) to find the shortest distance in weighted graphs
	// ACCEPTED
	public int shortestDistance_dijkstra(int[][] arr, int[] src, int[] des) {
		int n = arr.length;
		int m = arr[0].length;
		boolean[][] vis = new boolean[n][m];

		PriorityQueue<Pair> qu = new PriorityQueue<>((a, b)-> {
			return a.dis - b.dis;
		});
		qu.add(new Pair(src[0], src[1], 0));

		while (qu.size() != 0) {
			Pair rp = qu.remove();
			int r = rp.r;
			int c = rp.c;
			int dis = rp.dis;

			if (r == des[0] && c == des[1]) {
				return dis;
			}
			if (vis[r][c] == true) {
				continue;
			}
			vis[r][c] = true;

			for (int[] d : dir) {
				int nr = r;
				int nc = c;
				int jump = 0;
				while (nr >= 0 && nr < n && nc >= 0 && nc < m && arr[nr][nc] == 0) {
					nr += d[0];
					nc += d[1];
					jump++;
				}
				nr -= d[0];
				nc -= d[1];
				jump--;

				if (!vis[nr][nc]) {
					qu.add(new Pair(nr, nc, dis + jump));
				}
			}
		}

		return -1;
	}
	public int shortestDistance_dijkstra_optimized(int[][] arr, int[] src, int[] des) {
		int n = arr.length;
		int m = arr[0].length;

		int[][] distanceArray = new int[n][m];
		for (int[] row : distanceArray) {
			Arrays.fill(row, (int)1e9);
		}
		PriorityQueue<Pair> qu = new PriorityQueue<>((a, b) -> a.dis - b.dis);
		qu.add(new Pair(src[0], src[1], 0));
		distanceArray[src[0]][src[1]] = 0;

		while (qu.size() != 0) {
			Pair rp = qu.remove();
			int r = rp.r;
			int c = rp.c;
			int dis = rp.dis;

			if (dis > distanceArray[r][c]) {
				continue;
			}
			distanceArray[r][c] = dis;

			for (int[] d : dir) {
				int nr = r;
				int nc = c;
				int jump = 0;
				while (nr >= 0 && nr < n && nc >= 0 && nc < m && arr[nr][nc] == 0) {
					nr += d[0];
					nc += d[1];
					jump++;
				}
				nr -= d[0];
				nc -= d[1];
				jump--;

				int newDis = dis + jump;
				if (newDis < distanceArray[nr][nc]) {
					qu.add(new Pair(nr, nc, newDis));
					distanceArray[nr][nc] = newDis;
				}
			}
		}

		return distanceArray[des[0]][des[1]] == (int)1e9 ? -1 : distanceArray[des[0]][des[1]];
	}
	public int shortestDistance(int[][] arr, int[] src, int[] des) {
		// return shortestDistance_bfs(arr, src, des);
		return shortestDistance_dijkstra(arr, src, des);
		// return shortestDistance_dijkstra_optimized(arr, src, des);
	}









	// 499. The Maze III
	// MEMORIZE
	public class Pair {
		int r;
		int c;
		int wsf;
		String psf;

		Pair(int r, int c, int wsf, String psf) {
			this.r = r;
			this.c = c;
			this.wsf = wsf;
			this.psf = psf;
		}
	}
	int[][] dir = new int[][] {{0, 1}, {0, -1}, {1, 0}, { -1, 0}};
	String[] dirSymbol = new String[] {"r", "l", "d", "u"};
	public String findShortestWay(int[][] arr, int[] src, int[] des) {
		int n = arr.length;
		int m = arr[0].length;

		PriorityQueue<Pair> pq = new PriorityQueue<>((a, b)-> {
			if (a.wsf == b.wsf) {
				return a.psf.compareTo(b.psf); // minPQ for Strings (lexicographical order)
			}
			return a.wsf - b.wsf;
		});
		pq.add(new Pair(src[0], src[1], 0, ""));
		boolean[][] vis = new boolean[n][m];

		while (pq.size() != 0) {
			Pair rp = pq.remove();
			int r = rp.r;
			int c = rp.c;
			int wsf = rp.wsf;
			String psf = rp.psf;

			if (r == des[0] && c == des[1]) {
				return psf;
			}
			if (vis[r][c] == true) {
				continue;
			}
			vis[r][c] = true;

			for (int i = 0; i < 4; i++) {
				int nr = r;
				int nc = c;
				int jump = 0;
				while (nr >= 0 && nr < n && nc >= 0 && nc < m && arr[nr][nc] == 0 && !(nr == des[0] && nc == des[1])) {
					nr += dir[i][0];
					nc += dir[i][1];
					jump++;
				}

				// subtract only if the ball stopped because of hitting the boundary/obstacle
				if (!(nr == des[0] && nc == des[1])) {
					nr -= dir[i][0];
					nc -= dir[i][1];
					jump--;
				}

				if (!vis[nr][nc]) {
					pq.add(new Pair(nr, nc, wsf + jump, psf + dirSymbol[i]));
				}
			}
		}

		return "impossible";
	}









	// Chocolate Journey
	// https://www.hackerearth.com/practice/algorithms/graphs/shortest-path-algorithms/practice-problems/algorithm/successful-marathon-0691ec04/
	class TestClass {
		public static class Pair {
			int vtx;
			int wsf;
			Pair(int vtx, int wsf) {
				this.vtx = vtx;
				this.wsf = wsf;
			}
		}
		static int[] srcToShop = null;
		static int[] desToShop = null;

		public static void srcToShop_dijkstra(ArrayList<int[]>[] graph, int src, int V) {
			PriorityQueue<Pair> qu = new PriorityQueue<>((a, b)-> {
				return a.wsf - b.wsf;
			});
			qu.add(new Pair(src, 0));
			boolean[] vis = new boolean[V];

			while (qu.size() != 0) {
				Pair rp = qu.remove();
				int vtx = rp.vtx;
				int wsf = rp.wsf;

				if (vis[vtx] == true) {
					continue;
				}
				vis[vtx] = true;
				srcToShop[vtx] = wsf;

				for (int[] e : graph[vtx]) {
					int nbr = e[0];
					int newCost = wsf + e[1];
					if (!vis[nbr]) {
						qu.add(new Pair(nbr, newCost));
					}
				}
			}
		}
		public static void srcToShop_dijkstra_better(ArrayList<int[]>[] graph, int src, int V) {
			PriorityQueue<Pair> qu = new PriorityQueue<>((a, b)-> {
				return a.wsf - b.wsf;
			});
			qu.add(new Pair(src, 0));
			// boolean[] vis = new boolean[V];
			srcToShop[src] = 0;

			while (qu.size() != 0) {
				Pair rp = qu.remove();
				int vtx = rp.vtx;
				int wsf = rp.wsf;

				// if (vis[vtx] == true) {
				// 	continue;
				// }
				// vis[vtx] = true;
				// srcToShop[vtx] = wsf;
				if (wsf > srcToShop[vtx]) {
					continue;
				}

				for (int[] e : graph[vtx]) {
					int nbr = e[0];
					int newCost = wsf + e[1];
					// if (!vis[nbr]) {
					// 	qu.add(new Pair(nbr, newCost));
					// }
					if (newCost < srcToShop[nbr]) {
						srcToShop[nbr] = newCost;
						qu.add(new Pair(nbr, newCost));
					}
				}
			}
		}

		public static void desToShop_dijkstra(ArrayList<int[]>[] graph, int src, int V) {
			PriorityQueue<Pair> qu = new PriorityQueue<>((a, b)-> {
				return a.wsf - b.wsf;
			});
			qu.add(new Pair(src, 0));
			boolean[] vis = new boolean[V];

			while (qu.size() != 0) {
				Pair rp = qu.remove();
				int vtx = rp.vtx;
				int wsf = rp.wsf;

				if (vis[vtx] == true) {
					continue;
				}
				vis[vtx] = true;
				desToShop[vtx] = wsf;

				for (int[] e : graph[vtx]) {
					int nbr = e[0];
					int newCost = wsf + e[1];
					if (!vis[nbr]) {
						qu.add(new Pair(nbr, newCost));
					}
				}
			}
		}
		public static void desToShop_dijkstra_better(ArrayList<int[]>[] graph, int src, int V) {
			PriorityQueue<Pair> qu = new PriorityQueue<>((a, b)-> {
				return a.wsf - b.wsf;
			});
			qu.add(new Pair(src, 0));
			// boolean[] vis = new boolean[V];
			desToShop[src] = 0;

			while (qu.size() != 0) {
				Pair rp = qu.remove();
				int vtx = rp.vtx;
				int wsf = rp.wsf;

				// if (vis[vtx] == true) {
				// 	continue;
				// }
				// vis[vtx] = true;
				// desToShop[vtx] = wsf;
				if (wsf > desToShop[vtx]) {
					continue;
				}

				for (int[] e : graph[vtx]) {
					int nbr = e[0];
					int newCost = wsf + e[1];
					// if (!vis[nbr]) {
					// 	qu.add(new Pair(nbr, newCost));
					// }
					if (newCost < desToShop[nbr]) {
						desToShop[nbr] = newCost;
						qu.add(new Pair(nbr, newCost));
					}
				}
			}
		}

		public static void main(String[] args) throws IOException {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			StringTokenizer st = new StringTokenizer(br.readLine());

			int V = Integer.parseInt(st.nextToken()) + 1; // vertex are numbered from 1 not 0
			int E = Integer.parseInt(st.nextToken());
			int K = Integer.parseInt(st.nextToken());
			int X = Integer.parseInt(st.nextToken());

			st = new StringTokenizer(br.readLine());
			int[] chocoCities = new int[K];
			// System.out.print("ChocoCities -> ");
			for (int i = 0; i < K; i++) {
				chocoCities[i] = Integer.parseInt(st.nextToken());
				// System.out.print(chocoCities[i] + " ");
			}

			int[][] edges = new int[E][3];
			for (int i = 0; i < E; i++) {
				st = new StringTokenizer(br.readLine());
				edges[i][0] = Integer.parseInt(st.nextToken());
				edges[i][1] = Integer.parseInt(st.nextToken());
				edges[i][2] = Integer.parseInt(st.nextToken());
				// System.out.println("Edge -> " + edges[i][0] + " " + edges[i][1] + " " + edges[i][2]);
			}

			st = new StringTokenizer(br.readLine());
			int src = Integer.parseInt(st.nextToken());
			int des = Integer.parseInt(st.nextToken());

			// System.out.println("V -> " + V);
			// System.out.println("E -> " + E);
			// System.out.println("K -> " + K);
			// System.out.println("X -> " + X);
			// System.out.println("Source -> " + src);
			// System.out.println("Destination -> " + des);

			ArrayList<int[]>[] graph = new ArrayList[V]; // initialize array
			for (int i = 0; i < V; i++) {
				graph[i] = new ArrayList<>(); // initialize each arrayList
			}

			for (int[] e : edges) {
				int u = e[0];
				int v = e[1];
				int w = e[2];

				graph[u].add(new int[] {v, w});
				graph[v].add(new int[] {u, w});
			}

			// Find the shortest distance from source to each shop
			srcToShop = new int[V];
			Arrays.fill(srcToShop, (int)1e9);
			srcToShop_dijkstra(graph, src, V);
			// srcToShop_dijkstra_better(graph, src, V);

			// Find the shortest distance from destination to each shop
			desToShop = new int[V];
			Arrays.fill(desToShop, (int)1e9);
			desToShop_dijkstra(graph, des, V);
			// desToShop_dijkstra_better(graph, des, V);

			int ans = (int)1e9;
			for (int i = 0; i < chocoCities.length; i++) {
				int city = chocoCities[i];

				if (desToShop[city] <= X && desToShop[city] != (int)1e9 && srcToShop[city] != (int)1e9) {
					ans = Math.min(ans, srcToShop[city] + desToShop[city]);
				}
			}

			if (ans == (int)1e9) {
				ans = -1;
			}
			System.out.println(ans);
		}
	}









	// 1631. Path With Minimum Effort
	// Dijkstra - BFS using PriorityQueue
	class Pair {
		int r;
		int c;
		int wsf;
		Pair(int r, int c, int wsf) {
			this.r = r;
			this.c = c;
			this.wsf = wsf;
		}
	}
	public int dijkstra_minimumEffortPath(int[][] arr) {
		int n = arr.length;
		int m = arr[0].length;

		PriorityQueue<Pair> pq = new PriorityQueue<>((a, b)-> {
			return a.wsf - b.wsf; // Sort by distance in increasing order
		});
		pq.add(new Pair(0, 0, 0));
		boolean[][] vis = new boolean[n][m];
		int[][] dir = new int[][] {{0, 1}, {0, -1}, {1, 0}, { -1, 0}};

		while (pq.size() != 0) {
			Pair rp = pq.remove();
			int r = rp.r;
			int c = rp.c;
			int wsf = rp.wsf;

			if (r == n - 1 && c == m - 1) {
				return wsf;
			}
			if (vis[r][c]) {
				continue;
			}
			vis[r][c] = true;

			for (int[] d : dir) {
				int nr = r + d[0];
				int nc = c + d[1];

				if (nr >= 0 && nr < n && nc >= 0 && nc < m && !vis[nr][nc]) {
					int newCost = Math.max(wsf, Math.abs(arr[nr][nc] - arr[r][c]));
					pq.add(new Pair(nr, nc, newCost));
				}
			}
		}

		return 0;
	}
	public int minimumEffortPath(int[][] arr) {
		if (arr == null || arr.length == 0 || arr[0].length == 0) {
			return 0;
		}

		return dijkstra_minimumEffortPath(arr);
	}









	// 1192. Critical Connections in a Network
	public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {

	}















































































































































































































































































































































































































































































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
		int[][] edges = new int[][] {
			{0, 1, 10},
			{0, 3, 10},
			{1, 2, 10},
			{2, 3, 40},
			{2, 7, 2},
			{2, 8, 4},
			{7, 8, 3},
			{3, 4, 2},
			{4, 5, 2},
			{4, 6, 8},
			{5, 6, 3}
		};

		ArrayList<Edge>[] graph = new ArrayList[V];
		for (int i = 0; i < V; i++) {
			graph[i] = new ArrayList<>();
		}

		for (int[] e : edges) {
			int u = e[0];
			int v = e[1];
			int w = e[2];
			addEdge(graph, u, v, w);
		}
		display(graph);
		// removeEdge(graph, 2, 1);
		// removeVertex(graph, 2);
		// display(graph);









// ============================== GRAPH ALGORITHMS ==============================
		// dijkstrasAlgorithm(graph, 0);
		// dijkstrasAlgorithm_Better(graph, 0);
		// primsAlgorithm(graph, 0);
		// bellmanFordAlgorithm(V, edges, 0);
		// bellmanFordAlgorithm_1(V, edges, 0);
	}
}