import java.util.Stack;
public class StackQueue {









	public static int[] ngor(int[] arr) {
		if ( arr == null || arr.length == 0) {
			return null;
		}

		int n = arr.length;
		int[] ans = new int[n];
		for (int i = 0; i < n; i++) {
			ans[i] = n; // assuming there is +inf at index arr.length
		}
		Stack<Integer> st = new Stack<>();

		for (int i = 0; i < n; i++) {
			while (st.size() != 0 && arr[i] > arr[st.peek()]) {
				ans[st.pop()] = i;
			}
			st.push(i);
		}

		return ans;
	}









	// 503. Next Greater Element II
	public int[] nextGreaterElements(int[] arr) {
		if ( arr == null || arr.length == 0) {
			return null;
		}

		int n = arr.length;
		int[] ans = new int[n];
		for (int i = 0; i < n; i++) {
			ans[i] = -1;
		}
		Stack<Integer> st = new Stack<>(); // stores index

		for (int i = 0; i < 2 * n; i++) {
			while (st.size() != 0 && arr[i % n] > arr[st.peek()]) {
				ans[st.pop()] = arr[i % n];
			}
			if (i < n) {
				st.push(i % n);
			}
		}

		return ans;
	}









	// https://www.geeksforgeeks.org/problems/stock-span-problem-1587115621/1
	// for static data: ans = NGOL
	public ArrayList<Integer> calculateSpan(int[] arr) {
		if (arr == null || arr.length == 0) {
			return null;
		}

		int n = arr.length;
		ArrayList<Integer> ans = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			ans.add(-1); // assuming there is +inf at index -1
		}

		Stack<Integer> st = new Stack<>();
		for (int i = n - 1; i >= 0; i--) {
			while (st.size() != 0 && arr[i] > arr[st.peek()]) {
				ans.set(st.pop(), i);
			}

			st.push(i);
		}

		for (int i = 0; i < n; i++) {
			ans.set(i, i - ans.get(i));
		}

		return ans;
	}









	// 901. Online Stock Span
	// MEMORIZE
	class StockSpanner {
		Stack<int[]> st;
		int idx = 0;
		public StockSpanner() {
			st = new Stack<>();
			st.push(new int[] {idx++, (int)1e9});
		}

		public int next(int price) {
			while (st.size() != 0 && price >= st.peek()[1]) {
				st.pop();
			}

			int span = idx - st.peek()[0];
			st.push(new int[] {idx++, price});
			return span;
		}
	}

	/**
	 * Your StockSpanner object will be instantiated and called as such:
	 * StockSpanner obj = new StockSpanner();
	 * int param_1 = obj.next(price);
	 */









	// 20. Valid Parentheses
	public boolean isValid(String str) {
		if (str == null || str.length() == 0) {
			return true;
		}

		int n = str.length();
		Stack<Character> st = new Stack<>();
		for (int i = 0; i < n; i++) {
			char ch = str.charAt(i);
			if (ch == '(' || ch == '[' || ch == '{') {
				st.push(ch);
			} else if (st.size() == 0) {
				return false;
			} else if (ch == ')' && st.pop() != '(') {
				return false;
			} else if (ch == ']' && st.pop() != '[') {
				return false;
			} else if (ch == '}' && st.pop() != '{') {
				return false;
			}
		}

		return st.size() == 0;
	}









	// 946. Validate Stack Sequences
	// MEMORIZE - take pushed array as reference and pop the elements acc to popped array
	public boolean validateStackSequences(int[] pushed, int[] popped) {
		if (pushed.length == 0 || popped.length == 0 || pushed.length != popped.length) {
			return true;
		}

		int n = pushed.length;
		int j = 0;
		Stack<Integer> st = new Stack<>();
		for (int i = 0; i < n; i++) {
			st.push(pushed[i]);
			while (st.size() != 0 && st.peek() == popped[j]) {
				st.pop();
				j++;
			}
		}

		return st.size() == 0;
	}









	// 1249. Minimum Remove to Make Valid Parentheses
	public String minRemoveToMakeValid(String str) {
		if (str == null || str.length() == 0) {
			return str;
		}

		int n = str.length();
		Stack<Integer> st = new Stack<>();
		boolean[] valid = new boolean[n];
		for (int i = 0; i < n; i++) {
			char ch = str.charAt(i);
			if (ch == '(') {
				st.push(i);
			} else if (ch == ')') {
				if (st.size() != 0 && str.charAt(st.peek()) == '(') {
					valid[st.peek()] = true;
					st.pop();
					valid[i] = true;
				}
			} else {
				valid[i] = true;
			}
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			if (valid[i] == true) {
				sb.append(str.charAt(i));
			}
		}

		return sb.toString();
	}









	// 32. Longest Valid Parentheses
	// MEMORIZE - always apply the basic valid parentheses logic, no matter what they are asking for min/max length
	public int longestValidParentheses(String str) {
		if (str == null || str.length() == 0) {
			return 0;
		}

		int n = str.length();
		boolean[] valid = new boolean[n];
		Stack<Integer> st = new Stack<>();

		for (int i = 0; i < n; i++) {
			char ch = str.charAt(i);
			if (ch == '(') {
				st.push(i);
			} else {
				if (st.size() != 0 && str.charAt(st.peek()) == '(') {
					valid[st.pop()] = true;
					valid[i] = true;
				}
			}
		}

		// ans = max consecutive true
		int ans = 0;
		int gans = 0;
		for (int i = 0; i < n; i++) {
			if (valid[i] == true) {
				ans++;
			} else {
				ans = 0;
			}
			gans = Math.max(gans, ans);
		}

		return gans;
	}









	// 735. Asteroid Collision
	public int[] asteroidCollision(int[] arr) {
		if (arr == null || arr.length == 0) {
			return arr;
		}

		int n = arr.length;
		ArrayList<Integer> al = new ArrayList<>();
		al.add(arr[0]);
		for (int i = 1; i < n; i++) {
			if (al.size() == 0 || arr[i] > 0) {
				al.add(arr[i]);
				continue;
			}

			// if i am here => i am -ve
			boolean iSurvived = true;
			while (al.size() != 0 && al.get(al.size() - 1) > 0) {
				// while i am -ve and peek is +ve collision is inevitable
				if (-arr[i] > al.get(al.size() - 1)) {
					al.remove(al.size() - 1);
				} else if (-arr[i] == al.get(al.size() - 1)) {
					al.remove(al.size() - 1);
					iSurvived = false;
					break;
				} else {
					iSurvived = false;
					break;
				}
			}

			if (iSurvived == true) {
				al.add(arr[i]);
			}
		}


		int[] ans = new int[al.size()];
		for (int i = 0; i < al.size(); i++) {
			ans[i] = al.get(i);
		}
		return ans;
	}









	// 84. Largest Rectangle in Histogram
	// MEMORIZE - nsol nsor
	public int largestRectangleArea(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}

		int n = arr.length;
		int ans = 0;

		Stack<Integer> st = new Stack<>();
		int[] nsor = new int[n];
		Arrays.fill(nsor, n);
		for (int i = 0; i < n; i++) {
			while (st.size() != 0 && arr[i] < arr[st.peek()]) {
				nsor[st.pop()] = i;
			}
			st.push(i);
		}

		int[] nsol = new int[n];
		Arrays.fill(nsol, -1);
		for (int i = n - 1; i >= 0; i--) {
			while (st.size() != 0 && arr[i] < arr[st.peek()]) {
				nsol[st.pop()] = i;
			}
			st.push(i);
		}

		for (int i = 0; i < n; i++) {
			ans = Math.max(ans, (nsor[i] - nsol[i] - 1 ) * arr[i]);
		}
		return ans;
	}
	public int largestRectangleArea(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}

		int n = arr.length;
		int ans = 0;
		for (int i = 0; i < n; i++) {
			int minHeight = arr[i];
			for (int j = i; j < n; j++) {
				minHeight = Math.min(minHeight, arr[j]);
				ans = Math.max(ans, minHeight * (j - i + 1));
			}
		}

		return ans;
	}









	// 85. Maximal Rectangle
	// MEMORIZE - find largestRectangleArea in 1D for each row & keep adding the values for subsequent rows
	// 84. Largest Rectangle in Histogram
	// MEMORIZE
	public int largestRectangleArea(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}

		int n = arr.length;
		int ans = 0;

		Stack<Integer> st = new Stack<>();
		int[] nsor = new int[n];
		Arrays.fill(nsor, n);
		for (int i = 0; i < n; i++) {
			while (st.size() != 0 && arr[i] < arr[st.peek()]) {
				nsor[st.pop()] = i;
			}
			st.push(i);
		}

		int[] nsol = new int[n];
		Arrays.fill(nsol, -1);
		for (int i = n - 1; i >= 0; i--) {
			while (st.size() != 0 && arr[i] < arr[st.peek()]) {
				nsol[st.pop()] = i;
			}
			st.push(i);
		}

		for (int i = 0; i < n; i++) {
			ans = Math.max(ans, (nsor[i] - nsol[i] - 1 ) * arr[i]);
		}
		return ans;
	}
	public int maximalRectangle(char[][] arr) {
		if (arr == null || arr.length == 0 || arr[0].length == 0) {
			return 0;
		}

		int n = arr.length;
		int m = arr[0].length;
		int[] temp = new int[m];
		for (int i = 0; i < m; i++) {
			temp[i] = arr[0][i] - '0';
		}

		int ans = largestRectangleArea(temp);

		for (int i = 1; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (arr[i][j] - '0' == 1) {
					temp[j] += 1;
				} else {
					temp[j] = 0;
				}
			}

			ans = Math.max(ans, largestRectangleArea(temp));
		}

		return ans;
	}









	// 221. Maximal Square
	// MEMORIZE - similar to Maximal Rectangle, just apply check for sqaure length == width
	// 84. Largest Rectangle in Histogram
	public int largestRectangleArea(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}

		int n = arr.length;
		int ans = 0;

		Stack<Integer> st = new Stack<>();
		int[] nsor = new int[n];
		Arrays.fill(nsor, n);
		for (int i = 0; i < n; i++) {
			while (st.size() != 0 && arr[i] < arr[st.peek()]) {
				nsor[st.pop()] = i;
			}
			st.push(i);
		}

		int[] nsol = new int[n];
		Arrays.fill(nsol, -1);
		for (int i = n - 1; i >= 0; i--) {
			while (st.size() != 0 && arr[i] < arr[st.peek()]) {
				nsol[st.pop()] = i;
			}
			st.push(i);
		}

		for (int i = 0; i < n; i++) {
			int l = arr[i];
			int w = nsor[i] - nsol[i] - 1;

			// WRONG
			// if (l == w) {
			// 	ans = Math.max(ans, l * w);
			// }
			// RIGHT
			// say l = 5 & w = 3 => this is not a square but we can make a square of side = min(l,w)

			int s = Math.min(l, w);
			ans = Math.max(ans, s * s);
		}
		return ans;
	}
	public int maximalSquare(char[][] arr) {
		if (arr == null || arr.length == 0 || arr[0].length == 0) {
			return 0;
		}

		int n = arr.length;
		int m = arr[0].length;
		int[] temp = new int[m];
		for (int i = 0; i < m; i++) {
			temp[i] = arr[0][i] - '0';
		}

		int ans = largestRectangleArea(temp);

		for (int i = 1; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (arr[i][j] - '0' == 1) {
					temp[j] += 1;
				} else {
					temp[j] = 0;
				}
			}

			ans = Math.max(ans, largestRectangleArea(temp));
		}

		return ans;
	}









	// 402. Remove K Digits
	public String removeKdigits(String str, int k) {
		if (str == null || str.length() == 0) {
			return str;
		}
		int n = str.length();
		ArrayList<Character> st = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			char ch = str.charAt(i);
			while (st.size() != 0 && ch < st.get(st.size() - 1) && k > 0) {
				st.remove(st.size() - 1);
				k--;
			}
			st.add(ch);
		}
		while (k-- > 0) {
			st.remove(st.size() - 1);
		}

		// remove beginning 0
		int i = 0;
		while (i < st.size() && st.get(i) == '0') {
			i++;
		}

		StringBuilder sb = new StringBuilder();
		while (i < st.size()) {
			sb.append(st.get(i));
			i++;
		}
		return sb.length() == 0 ? "0" : sb.toString();
	}









	// 316. Remove Duplicate Letters
	// MEMORIZE - how we used the freqMap, just decrease freq as soon as you process it
	public String removeDuplicateLetters(String str) {
		if (str == null || str.length() == 0) {
			return str;
		}

		int n = str.length();
		ArrayList<Character> st = new ArrayList<>();
		HashMap<Character, Integer> map = new HashMap<>();
		for (int i = 0; i < n; i++) {
			char ch = str.charAt(i);
			map.put(ch, map.getOrDefault(ch, 0) + 1);
		}

		boolean[] vis = new boolean[26];
		for (int i = 0; i < n; i++) {
			char ch = str.charAt(i);
			map.put(ch, map.get(ch) - 1);

			if (vis[ch - 'a'] == true) {
				continue;
			}

			while (st.size() != 0 && ch <= st.get(st.size() - 1) && map.get(st.get(st.size() - 1)) > 0) {
				char peekEle = st.get(st.size() - 1);
				st.remove(st.size() - 1);
				vis[peekEle - 'a'] = false;
			}

			st.add(ch);
			vis[ch - 'a'] = true;
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < st.size(); i++) {
			char ch = st.get(i);
			sb.append(ch);
		}

		return sb.toString();
	}









	// 42. Trapping Rain Water
	public int trap(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}

		int n = arr.length;
		int[] maxSupportOnLeft = new int[n];
		int[] maxSupportOnRight = new int[n];

		int maxSoFar = 0;
		for (int i = 0; i < n; i++) {
			// calculating maxSoFar including myself, to avoid negative check while calculating final ans
			maxSoFar = Math.max(maxSoFar, arr[i]);
			maxSupportOnLeft[i] = maxSoFar;
		}

		maxSoFar = 0;
		for (int i = n - 1; i >= 0; i--) {
			maxSoFar = Math.max(maxSoFar, arr[i]);
			maxSupportOnRight[i] = maxSoFar;
		}

		int ans = 0;
		for (int i = 0; i < n; i++) {
			ans += Math.min(maxSupportOnLeft[i] , maxSupportOnRight[i]) - arr[i];
		}

		return ans;
	}









	// 42. Trapping Rain Water
	// MEMORIZE
	public int trap(int[] arr) {
		if (arr == null || arr.length < 3) {
			return 0;
		}

		int n = arr.length;
		int ans = 0;
		int i = 0, j = n - 1;
		int maxLeft = 0;
		int maxRight = 0;
		while (i < j) {
			maxLeft = Math.max(maxLeft, arr[i]);
			maxRight = Math.max(maxRight, arr[j]);

			if (maxLeft < maxRight) {
				ans += maxLeft - arr[i];
				i++;
			} else {
				ans += maxRight - arr[j];
				j--;
			}
		}

		return ans;
	}









	// 155. Min Stack
	class MinStack {
		class Pair {
			int val = 0;
			int min = 0;
			Pair(int val, int min) {
				this.val = val;
				this.min = min;
			}
		}
		Stack<Pair> st = null;

		public MinStack() {
			st = new Stack<>();
			st.push(new Pair(Integer.MAX_VALUE, Integer.MAX_VALUE));
		}

		public void push(int val) {
			st.push(new Pair(val, Math.min(val, st.peek().min)));
		}

		public void pop() {
			st.pop();
		}

		public int top() {
			return st.peek().val;
		}

		public int getMin() {
			return st.peek().min;
		}
	}

	/**
	 * Your MinStack object will be instantiated and called as such:
	 * MinStack obj = new MinStack();
	 * obj.push(val);
	 * obj.pop();
	 * int param_3 = obj.top();
	 * int param_4 = obj.getMin();
	 */


	// 155. Min Stack
	// MEMORIZE - use 2 diff stack instead of Pair and only store val in minStack if it is <= current Min
	class MinStack {
		Stack<Integer> st = null;
		Stack<Integer> minStack = null;
		public MinStack() {
			st = new Stack<>();
			minStack = new Stack<>();
			st.push(Integer.MAX_VALUE);
			minStack.push(Integer.MAX_VALUE);
		}

		public void push(int val) {
			st.push(val);
			if (val <= minStack.peek()) {
				minStack.push(val);
			}
		}

		public void pop() {
			int rv = st.pop();
			if (rv == minStack.peek()) {
				minStack.pop();
			}
		}

		public int top() {
			return st.peek();
		}

		public int getMin() {
			return minStack.peek();
		}
	}

	/**
	 * Your MinStack object will be instantiated and called as such:
	 * MinStack obj = new MinStack();
	 * obj.push(val);
	 * obj.pop();
	 * int param_3 = obj.top();
	 * int param_4 = obj.getMin();
	 */


	// 155. Min Stack
	// MEMORIZE
	class MinStack {
		private Stack<Long> st; // Store the difference or actual value
		private long min; // Tracks the current minimum

		public MinStack() {
			st = new Stack<>();
		}

		public void push(int val) {
			if (st.size() == 0) {
				st.push(0L); // Push difference (val - min)
				min = val;      // Update min
				return;
			}


			st.push(val - min); // Push difference (val - min)
			if (val < min) {
				min = val; // Update min if val is the new minimum
			}

		}

		public void pop() {
			if (st.size() == 0) {
				return;
			}

			long top = st.pop();
			if (top < 0) {
				// If the top is negative, it means the minimum is being removed
				// top = currMin - oldMin
				// oldMin = currMin - top
				min = min - top;
			}
		}

		public int top() {
			long top = st.peek();
			if (top > 0) {
				return (int) (top + min); // Normal value
			} else {
				return (int) min; // If negative, return current minimum
			}
		}

		public int getMin() {
			return (int) min;
		}
	}









	// 121. Best Time to Buy and Sell Stock
	public int maxProfit(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}

		int n = arr.length;
		int ans = 0;
		int buyingPrice = (int)1e9;
		for (int i = 0; i < n; i++) {
			ans = Math.max(ans, arr[i] - buyingPrice);
			if (arr[i] < buyingPrice) {
				buyingPrice = arr[i];
			}
		}

		return ans;
	}




















































































































	public static void display(int[] ans) {
		System.out.print("ans - ");
		for (int x : ans) {
			System.out.print(x + " ");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		System.out.println("Hello, World!");
		int[] arr = new int[] {3, 2, 1, 0, 4, 5, 6, 10};
		int[] ans = ngor(arr);
		display(ans);
	}
}
