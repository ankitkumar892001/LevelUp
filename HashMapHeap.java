public class HashMapHeap {

	// 215. Kth Largest Element in an Array
	public int findKthLargest(int[] arr, int k) {
		int n = arr.length;
		int ans = -(int)1e9;
		if (k > n) {
			return ans;
		}

		PriorityQueue<Integer> pq = new PriorityQueue<>((a, b)-> {
			return a - b;
		});

		for (int i = 0; i < n; i++) {
			pq.add(arr[i]);
			if (pq.size() > k) {
				pq.poll();
			}
		}

		ans = pq.poll();
		return ans;
	}









	// 703. Kth Largest Element in a Stream
	class KthLargest {
		PriorityQueue<Integer> pq = new PriorityQueue<>((a, b)-> {
			return a - b;
		});
		int k;
		public KthLargest(int k, int[] arr) {
			this.k = k;
			int n = arr.length;
			for (int i = 0; i < n; i++) {
				pq.add(arr[i]);
				if (pq.size() > k) {
					pq.poll();
				}
			}
		}

		public int add(int val) {
			pq.add(val);
			if (pq.size() > this.k) {
				pq.poll();
			}
			if (pq.size() == k) {
				return pq.peek();
			}

			return 0;
		}
	}









	// 349. Intersection of Two Arrays
	public int[] intersection(int[] arr, int[] arr2) {
		HashSet<Integer> set = new HashSet();
		for (var x : arr) {
			set.add(x);
		}

		ArrayList<Integer> al = new ArrayList<>();
		for (var x : arr2) {
			if (set.contains(x)) {
				al.add(x);
				set.remove(x);
			}
		}

		int[] ans = new int[al.size()];
		int i = 0;
		for (var x : al) {
			ans[i++] = x;
		}

		return ans;
	}









	// 350. Intersection of Two Arrays II
	public int[] intersect(int[] arr, int[] arr2) {
		HashMap<Integer, Integer> map = new HashMap<>();
		for (var x : arr) {
			map.put(x, map.getOrDefault(x, 0) + 1);
		}

		ArrayList<Integer> al = new ArrayList<>();
		for (var x : arr2) {
			int freq = map.getOrDefault(x, 0);
			if (freq > 0) {
				al.add(x);
				freq--;
				map.put(x, freq);
			}
		}

		int[] ans = new int[al.size()];
		int i = 0;
		for (var x : al) {
			ans[i++] = x;
		}
		return ans;
	}









	// 128. Longest Consecutive Sequence
	// BRUTE FORCE - sort the array and find the max consecutive streak
	// MEMORIZE
	// pick any element and check
	// how much left you can go
	// and how much right you can go
	public int longestConsecutive(int[] arr) {
		if (arr.length == 0) {
			return 0;
		}

		HashSet<Integer> set = new HashSet<>();
		for (var x : arr) {
			set.add(x);
		}

		int ans = 1;
		for (var x : arr) {
			if (!set.contains(x)) {
				continue;
			}

			set.remove(x);
			int potLeft = x - 1;
			int potRight = x + 1;

			while (set.contains(potLeft)) {
				set.remove(potLeft);
				potLeft--;
			}

			while (set.contains(potRight)) {
				set.remove(potRight);
				potRight++;
			}


			ans = Math.max(ans , potRight - potLeft - 1);
		}

		return ans;
	}









	// 347. Top K Frequent Elements
	// To find the top K elements efficiently,
	// avoid using a maxPQ:
	// where you'd add all elements and then remove the largest K elements.
	// Instead, use a minPQ:
	// Add elements to the minPQ until its size reaches K.
	// Once the PQ has K elements, start removing the smallest elements (i.e., the root)
	// whenever a new element is added, ensuring the PQ size never exceeds K.
	// This way, the PQ will always contain the largest K elements.
	public int[] topKFrequent(int[] arr, int k) {
		HashMap<Integer, Integer> map = new HashMap<>();
		for (var x : arr) {
			map.put(x, map.getOrDefault(x, 0) + 1);
		}

		PriorityQueue<int []> pq = new PriorityQueue<>((a, b)-> {
			return a[1] - b[1]; // min PQ
		});
		for (var x : map.entrySet()) {
			pq.add(new int[] {x.getKey(), x.getValue()});
			if (pq.size() > k) {
				pq.remove();
			}
		}

		int[] ans = new int[pq.size()];
		int i = 0;
		while (pq.size() != 0) {
			ans[i++] = pq.remove()[0];
		}

		return ans;
	}









	// 973. K Closest Points to Origin
	// MEMORIZE Double.compare(a,b)
	class Pair {
		int x;
		int y;
		double d;
		Pair(int x, int y, double d) {
			this.x = x;
			this.y = y;
			this.d = d;
		}
	}
	public int[][] kClosest(int[][] arr, int k) {
		if (arr == null || arr.length == 0 || arr[0].length == 0) {
			return null;
		}

		PriorityQueue<Pair> pq = new PriorityQueue<>((a, b)-> {
			// return b.d - a.d;
			// return (int)(b.d - a.d);
			return Double.compare(b.d, a.d);
		});
		for (var x : arr) {
			double d = Math.sqrt(x[0] * x[0] + x[1] * x[1]);
			pq.add(new Pair(x[0], x[1], d));
			if (pq.size() > k) {
				pq.remove();
			}
		}

		int[][] ans = new int[pq.size()][2];
		int i = 0;
		while (pq.size() != 0) {
			Pair rv = pq.remove();
			ans[i++] = new int[] {rv.x, rv.y};
		}
		return ans;
	}

	// Instead of passing the pre calculated distance to the PQ, I am passing the index and wrote the logic inside PQ for comparison
	// Instead of comparing sqrt compare value inside sqrt
	public int[][] kClosest(int[][] arr, int k) {
		if (arr == null || arr.length == 0 || arr[0].length == 0) {
			return null;
		}

		PriorityQueue<Integer> pq = new PriorityQueue<>((a, b)-> { // a,b -> index
			int da = arr[a][0] * arr[a][0] + arr[a][1] * arr[a][1];
			int db = arr[b][0] * arr[b][0] + arr[b][1] * arr[b][1];
			// if db > da then sqrt(db) > sqrt(da)
			return db - da; // max PQ
		});
		for (int i = 0; i < arr.length; i++) {
			pq.add(i);
			if (pq.size() > k) {
				pq.remove();
			}
		}

		int[][] ans = new int[pq.size()][2];
		int i = 0;
		while (pq.size() != 0) {
			int idx = pq.remove();
			ans[i++] = arr[idx];
		}
		return ans;
	}









	// 378. Kth Smallest Element in a Sorted Matrix
	// MEMORIZE
	// smallest elements will be in 1st row, store all first row elements and keep traversing towards down OR
	// smallest elements will be in 1st COL, store all first COL elements and keep traversing towards RIGHT
	public int kthSmallest(int[][] arr, int k) {
		if (arr == null || arr.length == 0 || arr[0].length == 0) {
			return 0;
		}

		int n = arr.length;
		int m = arr[0].length;
		PriorityQueue<Integer> pq = new PriorityQueue<>((a, b)-> { // a,b -> 1D-index
			// decode 1D-index to 2D-index
			int r1 = a / m;
			int c1 = a % m;
			int r2 = b / m;
			int c2 = b % m;

			return arr[r1][c1] - arr[r2][c2]; // minPQ
		});

		for (int i = 0; i < n ; i++) {
			// [i,0] store all first COL ele
			// encode 2D-index to 1D-index
			pq.add(i * m + 0);
		}

		int ans = 0;
		while (k-- > 0) {
			int idx = pq.remove();
			int r = idx / m;
			int c = idx % m;
			ans = arr[r][c];
			if (c + 1 < n) {
				// [r,c+1]
				// encode 2D-index to 1D-index
				pq.add(r * m + c + 1);
			}
		}

		return ans;
	}









	// 380. Insert Delete GetRandom O(1)
	// MEMORIZE - HashMap + ArrayList
	class RandomizedSet {
		HashMap<Integer, Integer> map = null; // {val -> idx in arraylist}
		ArrayList<Integer> al = null;
		Random rand = null;

		public RandomizedSet() {
			map = new HashMap<>();
			al = new ArrayList<>();
			rand = new Random();
		}

		public boolean insert(int val) {
			if (map.containsKey(val)) {
				return false;
			}
			al.add(val);
			map.put(val, al.size() - 1);
			return true;
		}

		public boolean remove(int val) {
			if (!map.containsKey(val)) {
				return false;
			}
			int idx = map.remove(val);
			int lastEle = al.get(al.size() - 1);
			// swap val with last ele & remvoe last ele
			al.set(idx, lastEle);
			al.remove(al.size() - 1);
			// update idx of last ele
			map.put(lastEle, idx);
			// remove val from map
			map.remove(val);
			return true;
		}

		public int getRandom() {
			int idx = rand.nextInt(al.size()); // returns random value between [0, size-1]
			return al.get(idx);
		}
	}

	/**
	 * Your RandomizedSet object will be instantiated and called as such:
	 * RandomizedSet obj = new RandomizedSet();
	 * boolean param_1 = obj.insert(val);
	 * boolean param_2 = obj.remove(val);
	 * int param_3 = obj.getRandom();
	 */









	// 895. Maximum Frequency Stack
	// hashmap {ele -> arraylist of index}
	// TLE
	class FreqStack {
		HashMap<Integer, ArrayList<Integer>> map = null;
		int index = 1;
		public FreqStack() {
			map = new HashMap<>();
		}

		public void push(int key) { // O(1)
			map.putIfAbsent(key, new ArrayList<>());
			map.get(key).add(index++);
		}

		public int pop() { // O(n)
			int maxFreqEle = 0;
			int maxFreq = 0;
			ArrayList<Integer> maxFreqEleList = new ArrayList<>();
			for (var x : map.entrySet()) {
				int ele = x.getKey();
				ArrayList<Integer> list = x.getValue();
				if (list.size() > maxFreqEleList.size()) {
					maxFreqEle = ele;
					maxFreqEleList = list;
				} else if (list.size() != 0 && list.size() == maxFreqEleList.size()) {
					if (list.get(list.size() - 1) > maxFreqEleList.get(maxFreqEleList.size() - 1)) {
						maxFreqEle = ele;
						maxFreqEleList = list;
					}
				}
			}

			maxFreqEleList.remove(maxFreqEleList.size() - 1);
			if (maxFreqEleList.size() == 0) {
				map.remove(maxFreqEle);
			}
			return maxFreqEle;
		}
	}

	/**
	 * Your FreqStack object will be instantiated and called as such:
	 * FreqStack obj = new FreqStack();
	 * obj.push(val);
	 * int param_2 = obj.pop();
	 */


	// 895. Maximum Frequency Stack
	// hashmap {ele -> freq}
	// priority queue {pair of (ele, freq, index)}
	class FreqStack {
		class Pair {
			int ele = 0;
			int freq = 0;
			int index = 0;
			Pair(int ele , int freq, int index) {
				this.ele = ele;
				this.freq = freq;
				this.index = index;
			}
		}

		HashMap<Integer, Integer> map = new HashMap<>();
		PriorityQueue<Pair> pq = null;
		int index = 0;

		public FreqStack() {
			pq = new PriorityQueue<>((a, b)-> {
				if (b.freq == a.freq) {
					return b.index - a.index;
				}
				return b.freq - a.freq;
			});
		}

		public void push(int key) { // log(N)
			map.put(key, map.getOrDefault(key, 0) + 1);
			int freq = map.get(key);
			pq.add(new Pair(key, freq, index++));
		}

		public int pop() { // log(N)
			Pair p = pq.remove();
			int rv = p.ele;

			map.put(rv, map.get(rv) - 1);
			if (map.get(rv) <= 0) {
				map.remove(rv);
			}

			return rv;
		}
	}

	/**
	 * Your FreqStack object will be instantiated and called as such:
	 * FreqStack obj = new FreqStack();
	 * obj.push(val);
	 * int param_2 = obj.pop();
	 */


	// 895. Maximum Frequency Stack
	// MEMORIZE - seperate stack for each frequency
	// stack for frequency 1
	// stack for frequency 2
	// ...
	// stack for frequency n
	class FreqStack {
		HashMap<Integer, Integer> map = null; // ele -> freq
		int maxFreq = 0;
		ArrayList<Stack<Integer>> freqMap = null;
		public FreqStack() {
			freqMap = new ArrayList<>();
			freqMap.add(null); // for 0 freq
			map = new HashMap<>();
		}

		public void push(int key) { // O(1)
			map.put(key, map.getOrDefault(key, 0) + 1);
			int freq = map.get(key);
			this.maxFreq = Math.max(this.maxFreq, freq);

			if (freqMap.size() <= freq) {
				freqMap.add(new Stack<>());
			}
			Stack<Integer> st = freqMap.get(freq);
			st.push(key);
		}

		public int pop() { // // O(1)
			Stack<Integer> st = freqMap.get(this.maxFreq);
			int rv = st.pop();
			if (st.size() == 0) {
				this.maxFreq--;
			}

			map.put(rv, map.get(rv) - 1);
			if (map.get(rv) == 0) {
				map.remove(rv);
			}

			return rv;
		}
	}

	/**
	 * Your FreqStack object will be instantiated and called as such:
	 * FreqStack obj = new FreqStack();
	 * obj.push(val);
	 * int param_2 = obj.pop();
	 */









	// 407. Trapping Rain Water II
	// BRUTE FORCE - for each cell, find the largest height on right, left, up, down
	// if (min(r, l, u, d) > cell height) {
	// 	waterStored += min(r, l, u, d) - cell height
	// }
	// MEMORIZE - minPQ
	class Pair {
		int row = 0;
		int col = 0;
		Pair(int row, int col) {
			this.row = row;
			this.col = col;
		}
	}
	public int trapRainWater(int[][] arr) {
		if (arr == null || arr.length <= 2 || arr[0].length <= 2) {
			return 0;
		}

		PriorityQueue<Pair> pq = new PriorityQueue<>((a, b)-> {
			return arr[a.row][a.col] - arr[b.row][b.col] ; // minPQ based on height
		});

		int n = arr.length;
		int m = arr[0].length;
		boolean[][] vis = new boolean[n][m];
		// insert all boundries
		for (int r = 0; r < n; r++) {
			for (int c = 0; c < m; c++) {
				if (r == 0 || r == n - 1 || c == 0 || c == m - 1) {
					pq.add(new Pair(r, c));
					vis[r][c] = true;
				}
			}
		}

		int[][] dir = new int[][] {{0, 1}, {0, -1}, {1, 0}, { -1, 0}};
		int ans = 0;
		int minBoundary = 0; // minimum support from boundary // if i have 4 boundary of 2, 4, 6, 8 i can't fill water above 2
		while (pq.size() != 0) {
			Pair p = pq.remove();
			int myRow = p.row;
			int myCol = p.col;
			int myHeight = arr[myRow][myCol];
			if (minBoundary > myHeight) {
				ans += minBoundary - myHeight;
			}
			minBoundary = Math.max(minBoundary, myHeight);
			// we want the min element at each point and PQ is the sol

			// agar is point pe 1 niklta hai PQ se than means all the other elements are >= 1
			// so for any inner cell i have atleast a boundary of 1

			// next 5 nikalta hai PQ se that means all the other elements are >= 5
			// so for any inner cell i have atleast a boundary of 5

			for (var x : dir) {
				int nr = myRow + x[0];
				int nc = myCol + x[1];
				if (nr >= 0 && nr <= n - 1 && nc >= 0 && nc <= m - 1 && !vis[nr][nc]) {
					pq.add(new Pair(nr, nc));
					vis[nr][nc] = true;
				}
			}
		}

		return ans;
	}









	// 778. Swim in Rising Water
	// MEMORIZE - minPQ
	public int swimInWater(int[][] arr) {
		if (arr == null || arr.length == 0 || arr[0].length == 0) {
			return 0;
		}
		int n = arr.length;
		int m = arr[0].length;

		PriorityQueue<Integer> pq = new PriorityQueue<>((a, b)-> { // 1D-index
			int r1 = a / m;
			int c1 = a % m;
			int r2 = b / m;
			int c2 = b % m;

			return arr[r1][c1] - arr[r2][c2]; // minPQ based on height
		});

		int[][] dir = new int[][] {{0, 1}, {0, -1}, {1, 0}, { -1, 0}};
		boolean[][] vis = new boolean[n][m];

		int ans = 0;
		int minHeight = 0; // it should be the min among all ele, but because of constraints min ele is always 0
		pq.add(0 * m + 0); // insert (0,0)
		vis[0][0] = true;
		while (pq.size() != 0) {
			int idx = pq.remove();
			int r = idx / m;
			int c = idx % m;
			int myHeight = arr[r][c];
			if (myHeight > minHeight) {
				ans += myHeight - minHeight;
			}
			minHeight = Math.max(minHeight, myHeight);

			if (r == n - 1 && c == m - 1) {
				break;
			}

			for (var x : dir) {
				int nr = r + x[0];
				int nc = c + x[1];
				if (nr >= 0 && nr <= n - 1 && nc >= 0 && nc <= m - 1 && !vis[nr][nc]) {
					vis[nr][nc] = true;
					pq.add(nr * m + nc);
				}
			}
		}

		return ans;
	}









	// 295. Find Median from Data Stream
	// BRUTE FORCE - TLE - sort the arraylist after each addition
	class MedianFinder {
		ArrayList<Integer> al = null;
		public MedianFinder() {
			al  = new ArrayList<>();
		}

		public void addNum(int num) { // Nlog(N)
			al.add(num);
			Collections.sort(al, (a, b)-> {
				return a - b;
			});
		}

		public double findMedian() { // O(1)
			int size = al.size();
			int f = al.get(size / 2);
			int s = f;
			if (size % 2 == 0) {
				s = al.get(size / 2 - 1);
			}

			return (f + s) / 2.0;
		}
	}


	// 295. Find Median from Data Stream
	// MEMORIZE - use min & max PQ and always balance them out
	class MedianFinder {
		PriorityQueue<Integer> minPQ = null;
		PriorityQueue<Integer> maxPQ = null;

		public MedianFinder() {
			minPQ = new PriorityQueue<>((a, b)-> {
				return a - b; // this - other => minPQ
			});
			minPQ.add((int)1e9);
			maxPQ = new PriorityQueue<>((a, b)-> {
				return b - a; // other - this => maxPQ
			});
			maxPQ.add(-(int)1e9);
		}

		public void addNum(int num) { // log(N)
			if (num < minPQ.peek()) {
				maxPQ.add(num);
				if (maxPQ.size() >= minPQ.size() + 2) { // balance if overflow
					minPQ.add(maxPQ.remove());
				}
			} else { // if (num >= minPQ.peek())
				minPQ.add(num);
				if (minPQ.size() >= maxPQ.size() + 2) { // balance if overflow
					maxPQ.add(minPQ.remove());
				}
			}
		}

		public double findMedian() { // O(1)
			if (maxPQ.size() > minPQ.size()) {
				return maxPQ.peek();
			} else if (maxPQ.size() < minPQ.size()) {
				return minPQ.peek();
			}
			return (minPQ.peek() + maxPQ.peek()) / 2.0;
		}
	}

	/**
	 * Your MedianFinder object will be instantiated and called as such:
	 * MedianFinder obj = new MedianFinder();
	 * obj.addNum(num);
	 * double param_2 = obj.findMedian();
	 */









	// 632. Smallest Range Covering Elements from K Lists
	// MEMORIZE - minPQ
	// at any point in time the smallest and largest ele in the PQ makes a valid answer,
	// you just have to minimize the range as much as possible
	class Pair {
		int i = 0; // ith List
		int j = 0; // jth Ele
		Pair(int i, int j) {
			this.i = i;
			this.j = j;
		}
	}

	public int[] smallestRange(List<List<Integer>> list) {
		if (list == null || list.size() == 0 || list.get(0).size() == 0) {
			return new int[] { 0, 0 };
		}

		PriorityQueue<Pair> pq = new PriorityQueue<>((a, b) -> {
			return list.get(a.i).get(a.j) - list.get(b.i).get(b.j); // minPQ based on value
		});

		int k = list.size();
		int maxEle = -(int) 1e9;
		for (int i = 0; i < k; i++) {
			pq.add(new Pair(i, 0));
			maxEle = Math.max(maxEle, list.get(i).get(0));
		}

		int r1 = -(int) 1e9;
		int r2 = maxEle;
		int range = r2 - r1;
		while (pq.size() != 0) {
			Pair rp = pq.remove(); // rp - remove pair
			int r = rp.i; // r - row
			int c = rp.j; // c- col
			int val = list.get(r).get(c);
			if (maxEle - val < range) {
				r1 = val;
				r2 = maxEle;
				range = r2 - r1;
			}

			if (c + 1 >= list.get(r).size()) {
				break;
			}

			pq.add(new Pair(r, c + 1));
			maxEle = Math.max(maxEle, list.get(r).get(c + 1));
		}

		return new int[] {r1, r2};
	}









	// 23. Merge k Sorted Lists
	// time complexity to merge k sorted list each of length N => T(kN) = kNlog(k)
	public ListNode mergeKLists(ListNode[] lists) {
		if (lists.length == 0) {
			return null;
		}

		PriorityQueue<ListNode> pq = new PriorityQueue<>((a, b)-> {
			return a.val - b.val; // minPQ based in ndoe value
		});

		int n = lists.length;
		for (int i = 0; i < n; i++) {
			if (lists[i] != null) {
				pq.add(lists[i]);
			}
		}
		// T = log(1) + log(2) + log(3) + ... log(k)
		// T = log(1*2*3*...k)
		// T = log(k!)
		// since k! ~ k^k
		// T = log(k^k)
		// T = klog(k);

		ListNode dummy = new ListNode(-1);
		ListNode prev = dummy;
		while (pq.size() != 0) {
			ListNode rv = pq.remove();
			prev.next = rv;
			prev = rv;

			if (rv.next != null) {
				pq.add(rv.next);
			}
		}

		return dummy.next;
	}









	// 502. IPO
	public int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
		k = Math.min(k, capital.length);
		int ans = w;

		int n = profits.length;
		int[][]projects = new int[n][2];

		for (int i = 0; i < n; i++) {
			projects[i][0] = capital[i];
			projects[i][1] = profits[i];
		}

		Arrays.sort(projects, (a, b)-> {
			return a[0] - b[0]; // sort in increasing order of capital requirement
		});
		PriorityQueue<Integer> pq = new PriorityQueue<>((a, b)-> {
			return b - a; // maxPQ based on profit
		});

		int idx = 0;
		while (k-- > 0) {
			while (idx < n && projects[idx][0] <= w) {
				pq.add(projects[idx][1]);
				idx++;
			}

			if (pq.size() == 0) {
				break;
			}

			int p = pq.remove();
			w += p;
			ans += p;
		}

		return ans;
	}









	public static void main(String[] args) {
		System.out.println("Hello, World!");
	}
}
