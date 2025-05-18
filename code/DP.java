import java.util.*;

public class DP {

	// 1. Faith
	// 2. RecursiveTree
	// 3. RecursiveCode -> Memoization
	// 4. Observation
	// 5. Tabulation
	// 6. Optimization

	// how to convert rec -> memo
	// 1. initialize dp[] with a default value that is highly unlikely to be a valid answer (ex -1)
	// 2. save ans before returning
	// 3. if ans is already present then return
	// if you initialize dp[] with a value that could be a valid answer (like 0 or 1 in some cases), it becomes ambiguous whether that value was calculated or not.
	// That’s why we usually pick a value that:
	// can almost never be a valid answer (like -1).
	// clearly indicates that the subproblem has not been solved yet.
	// even if you accidentally pick a value that can become the answer, the logic would still technically work—it just wouldn’t be efficient. You’d recalculate the same subproblem every time, losing the whole purpose of memoization

	// how to convert memo -> tabu
	// 1. observe the patterns -> loop kis drection me chalana hai, for ex. left -> right , bottom -> top
	// 2. change parameter variable -> capitalize, n -> N, but keep inner method variable same as memo, n
	// 3. replace return -> continue
	// 4. remove - if (dp[n] != -1) {return dp[n];}
	// 4. replace rercursive call -> dp call

	// NOTES:
	// Hard part is 1, 2, 3 - mostly sufficient
	// How wo know size of dp[] - dry run to see all the possible calls that can be made
	// Optimization are mostly memorized
	// each cell of dp has the same meaing in both memo & tabu









	// ======================================== 2 POINTER SET ========================================









	public static int fibo(int n) {
		if (n <= 1) {
			return n;
		}

		int ans = fibo(n - 1) + fibo(n - 2);
		return ans;
	}

	// T:O(n), S:O(n)
	public static int fibo_memo(int n, int[] dp) {
		if (n <= 1) {
			return dp[n] = n;
		}

		if (dp[n] != -1) {
			return dp[n];
		}

		int ans = fibo_memo(n - 1, dp) + fibo_memo(n - 2, dp);
		return dp[n] = ans;
	}

	// T:O(n), S:O(n)
	public static int fibo_tabu(int N, int[] dp) {
		for (int n = 0; n <= N; n++) {
			if (n <= 1) {
				// return dp[n] = n;
				dp[n] = n;
				continue;
			}

			// if (dp[n] != -1) {
			// 	return dp[n];
			// }

			// int ans = fibo_memo(n - 1, dp) + fibo_memo(n - 2, dp);
			int ans = dp[n - 1] + dp[n - 2];
			// return dp[n] = ans;
			dp[n] = ans;
			continue;
		}

		return dp[N];
	}

	// since we need only last 2 values, no need to store all the value in dp[]
	// T:O(n), S:O(1)
	public static int fibo_opti(int N) {
		if (N <= 1) {
			return N;
		}

		int a = 0;
		int b = 1;
		for (int n = 2; n <= N; n++) {
			int sum = a + b;
			a = b;
			b = sum;
		}

		return b;
	}

	public static void fibo() {
		int n = 10;
		int[] dp = new int[n + 1];
		Arrays.fill(dp, -1);

		System.out.println(fibo(n));
		System.out.println(fibo_memo(n, dp));
		System.out.println(fibo_tabu(n, dp));
		System.out.println(fibo_opti(n));
	}









	static int[][] dir = new int[][] {{0, 1}, {1, 0}, {1, 1}};
	public static int mazePath(int n , int m, int sr, int sc, int er, int ec) {
		if (sr == er && sc == ec) {
			return 1;
		}

		int ans = 0;
		for (int[] d : dir) {
			int nr = sr + d[0];
			int nc = sc + d[1];

			if (nr >= 0 && nr < n && nc >= 0 && nc < m) {
				ans += mazePath(n, m , nr, nc, er, ec);
			}
		}

		return ans;
	}

	public static int mazePath_memo(int n , int m, int sr, int sc, int er, int ec, int[][] dp) {
		if (sr == er && sc == ec) {
			return dp[sr][sc] = 1;
		}

		if (dp[sr][sc] != 0) {
			return dp[sr][sc];
		}

		int ans = 0;
		for (int[] d : dir) {
			int nr = sr + d[0];
			int nc = sc + d[1];

			if (nr >= 0 && nr < n && nc >= 0 && nc < m) {
				ans += mazePath_memo(n, m , nr, nc, er, ec, dp);
			}
		}

		return dp[sr][sc] = ans;
	}

	public static int mazePath_tabu(int n , int m, int SR, int SC, int ER, int EC, int[][] dp) {
		for (int sr = ER; sr >= SR; sr--) {
			for (int sc = EC; sc >= SC; sc--) {

				if (sr == ER && sc == EC) {
					// return dp[sr][sc] = 1;
					dp[sr][sc] = 1;
					continue;
				}

				// if (dp[sr][sc] != 0) {
				// 	return dp[sr][sc];
				// }

				int ans = 0;
				for (int[] d : dir) {
					int nr = sr + d[0];
					int nc = sc + d[1];

					if (nr >= 0 && nr < n && nc >= 0 && nc < m) {
						// ans += mazePath_memo(n, m , nr, nc, er, ec, dp);
						ans += dp[nr][nc];
					}
				}

				// return dp[sr][sc] = ans;
				dp[sr][sc] = ans;
				continue;

			}
		}

		return dp[SR][SC];
	}

	public static void mazePath() {
		int n = 3;
		int m = 3;
		int[][] dp = new int[n][m];

		// System.out.println(mazePath(n, m, 0, 0, n - 1, m - 1));
		// System.out.println(mazePath_memo(n, m, 0, 0, n - 1, m - 1, dp));
		System.out.println(mazePath_tabu(n, m, 0, 0, n - 1, m - 1, dp));
		display2D(dp);
	}









	public static int mazePathJump_memo(int n , int m, int sr, int sc, int er, int ec, int[][] dp) {
		if (sr == er && sc == ec) {
			return dp[sr][sc] = 1;
		}

		if (dp[sr][sc] != 0) {
			return dp[sr][sc];
		}

		int ans = 0;
		for (int[] d : dir) {
			int nr = sr + d[0];
			int nc = sc + d[1];

			while (nr >= 0 && nr < n && nc >= 0 && nc < m) {
				ans += mazePathJump_memo(n, m , nr, nc, er, ec, dp);
				nr += d[0];
				nc += d[1];
			}
		}

		return dp[sr][sc] = ans;
	}

	public static int mazePathJump_tabu(int n , int m, int SR, int SC, int ER, int EC, int[][] dp) {
		for (int sr = ER; sr >= SR; sr--) {
			for (int sc = EC; sc >= SC; sc--) {

				if (sr == ER && sc == EC) {
					dp[sr][sc] = 1;
					continue;
				}

				int ans = 0;
				for (int[] d : dir) {
					int nr = sr + d[0];
					int nc = sc + d[1];

					while (nr >= 0 && nr < n && nc >= 0 && nc < m) {
						ans += dp[nr][nc];
						nr += d[0];
						nc += d[1];
					}
				}

				dp[sr][sc] = ans;
				continue;

			}
		}

		return dp[SR][SC];
	}

	public static void mazePathJump() {
		int n;
		int m;
		n = m = 4;
		int[][] dp = new int[n][m];

		// System.out.println(mazePathJump_memo(n, m, 0, 0, n - 1, m - 1, dp));
		System.out.println(mazePathJump_tabu(n, m, 0, 0, n - 1, m - 1, dp));
		display2D(dp);
	}









	// 70. Climbing Stairs
	public int climbStairs_memo(int n, int[] dp) {
		if (n <= 1) {
			return dp[n] = 1;
		}

		if (dp[n] != 0) {
			return dp[n];
		}

		int ans = climbStairs_memo(n - 1, dp) + climbStairs_memo(n - 2, dp);
		return dp[n] = ans;
	}

	public int climbStairs(int n) {
		int[] dp = new int[n + 1];
		return climbStairs_memo(n, dp);
	}









	// 746. Min Cost Climbing Stairs
	// MEMORIZE
	// faith - the method will return the minCost i have to pay to reach the top from si step
	public int minCostClimbingStairs_memo(int[] arr, int n, int si, int[] dp) {
		if (si >= n) {
			return dp[si] = 0;
		}

		if (dp[si] != -1) {
			return dp[si];
		}

		int minCost = Math.min(minCostClimbingStairs_memo(arr, n, si + 1, dp), minCostClimbingStairs_memo(arr, n, si + 2, dp));

		return dp[si] = arr[si] + minCost;
	}

	public int minCostClimbingStairs(int[] arr) {
		int n = arr.length;
		int[]dp = new int[n + 2];
		// default value = 0 is not acceptable - since 0 could be a valid ans for many states (si) => TLE
		Arrays.fill(dp, -1);
		// intitially i have 2 choices, i can start from stair 0 / 1
		return Math.min(minCostClimbingStairs_memo(arr, n, 0, dp), minCostClimbingStairs_memo(arr, n, 1, dp));
	}









	// // 62. Unique Paths
	// int[][] dir = new int[][] {{0, 1}, {1, 0}};
	// // faith - the method will return the total no of paths from (sr,sc) to (n-1,m-1)
	// public int uniquePaths_memo(int n, int m, int sr, int sc, int[][] dp) {
	// 	if (sr == n - 1 && sc == m - 1) {
	// 		return dp[sr][sc] = 1;
	// 	}

	// 	if (dp[sr][sc] != 0) {
	// 		return dp[sr][sc];
	// 	}

	// 	int ans = 0;
	// 	for (int[] d : dir) {
	// 		int nr = sr + d[0];
	// 		int nc = sc + d[1];

	// 		if (nr >= 0 && nr < n && nc >= 0 && nc < m) {
	// 			ans += uniquePaths_memo(n, m, nr, nc, dp);
	// 		}
	// 	}

	// 	return dp[sr][sc] = ans;
	// }
	// public int uniquePaths(int n, int m) {
	// 	int[][] dp = new int[n][m];
	// 	// there are very few states (sr,sc) where the ans could be 0, so we can take 0 as default value
	// 	return uniquePaths_memo(n, m , 0, 0, dp);
	// }









	// // 63. Unique Paths II
	// int[][] dir = new int[][] {{0, 1}, {1, 0}};
	// // faith - the method will return the total no of paths from (sr,sc) to (n-1,m-1)
	// public int uniquePathsWithObstacles_memo(int[][] arr, int n, int m, int sr, int sc, int[][] dp) {
	// 	if (sr == n - 1 && sc == m - 1) {
	// 		return dp[sr][sc] = 1;
	// 	}

	// 	if (dp[sr][sc] != 0) {
	// 		return dp[sr][sc];
	// 	}

	// 	int ans = 0;
	// 	for (int[] d : dir) {
	// 		int nr = sr + d[0];
	// 		int nc = sc + d[1];

	// 		if (nr >= 0 && nr < n && nc >= 0 && nc < m && arr[nr][nc] != 1) {
	// 			ans += uniquePathsWithObstacles_memo(arr, n, m, nr, nc, dp);
	// 		}
	// 	}

	// 	return dp[sr][sc] = ans;
	// }
	// public int uniquePathsWithObstacles(int[][] arr) {
	// 	int n = arr.length;
	// 	int m = arr[0].length;

	// 	if (arr[0][0] == 1 || arr[n - 1][m - 1] == 1) {
	// 		return 0;
	// 	}

	// 	int[][] dp = new int[n][m];
	// 	return uniquePathsWithObstacles_memo(arr, n, m , 0, 0, dp);
	// }









	static int[] dice = new int[] {1 , 2, 3, 4, 5, 6};
	// faith - this method will return the total no of ways to reach from si -> ei
	public static int boardPath(int si, int ei) {
		if (si == ei) {
			return 1;
		}

		int ans = 0;
		for (int d : dice) {
			int nbr = si + d;
			if (nbr <= ei) {
				ans += boardPath(nbr, ei);
			}
		}

		return ans;
	}
	public static int boardPath_memo(int si, int ei, int[] dp) {
		if (si == ei) {
			return dp[si] = 1;
		}

		if (dp[si] != 0) {
			return dp[si];
		}

		int ans = 0;
		for (int d : dice) {
			int nbr = si + d;
			if (nbr <= ei) {
				ans += boardPath_memo(nbr, ei, dp);
			}
		}

		return dp[si] = ans;
	}
	public static int boardPath_tabu(int SI, int ei, int[] dp) {
		for (int si = ei; si >= SI; si--) {
			if (si == ei) {
				// return dp[si] = 1;
				dp[si] = 1;
				continue;
			}

			// if (dp[si] != 0) {
			// 	return dp[si];
			// }

			int ans = 0;
			for (int d : dice) {
				int nbr = si + d;
				if (nbr <= ei) {
					// ans += boardPath_tabu(nbr, ei, dp);
					ans += dp[nbr];
				}
			}

			// return dp[si] = ans;
			dp[si] = ans;
			continue;
		}

		return dp[SI];
	}

	// T:1n - absolute 1n not O(n)
	// S:O(1)
	public static int boardPath_opti(int si, int ei) {
		return 0;
		// TODO
	}

	public static void boardPath() {
		int si = 1;
		int ei = 10;
		int[] dp = new int[ei + 1];

		// System.out.println(boardPath(si, ei));
		// System.out.println(boardPath_memo(si, ei, dp));
		// System.out.println(boardPath_tabu(si, ei, dp));
		System.out.println(boardPath_opti(si, ei));
	}









	// 91. Decode Ways
	// MEMORIZE
	// faith - this method will return the total number of ways to decode the substring str[si, n)
	// str = "54321"
	// numDecodings(str, 2) -> return the total no of ways to decode "321"
	public int numDecodings_memo(String str, int si, int[] dp) {
		int n = str.length();
		if (si >= n) {
			return dp[si] = 1;
		}
		if (str.charAt(si) == '0') {
			return dp[si] = 0;
		}
		if (si == n - 1) {
			return dp[si] = 1;
		}

		if (dp[si] != -1) {
			return dp[si];
		}

		// decode by treating str[si] as a single digit
		int ans = numDecodings_memo(str, si + 1, dp);

		// decode by treating str[si] and str[si+1] as a two-digit number (if valid)
		// char ch = str.charAt(si);
		// if (ch == '1') {
		// 	ans += numDecodings_memo(str, si + 2, dp);
		// } else if (ch == '2') {
		// 	char nextCh = str.charAt(si + 1);
		// 	if (nextCh - '0' >= 0 && nextCh - '0' <= 6) {
		// 		ans += numDecodings_memo(str, si + 2, dp);
		// 	}
		// }
		char ch = str.charAt(si);
		char nextCh = str.charAt(si + 1);
		int num = (ch - '0') * 10 + (nextCh - '0');
		if (num >= 10 && num <= 26) {
			ans += numDecodings_memo(str, si + 2, dp);
		}

		return dp[si] = ans;
	}
	public int numDecodings_tabu(String str, int SI, int[] dp) {
		int n = str.length();
		for (int si = n ; si >= SI; si--) {
			if (si >= n) {
				// return dp[si] = 1;
				dp[si] = 1;
				continue;
			}
			if (str.charAt(si) == '0') {
				// return dp[si] = 0;
				dp[si] = 0;
				continue;
			}
			if (si == n - 1) {
				// return dp[si] = 1;
				dp[si] = 1;
				continue;
			}

			// if (dp[si] != -1) {
			// 	return dp[si];
			// }

			// int ans = numDecodings_tabu(str, si + 1, dp);
			int ans = dp[si + 1];

			char ch = str.charAt(si);
			char nextCh = str.charAt(si + 1);
			int num = (ch - '0') * 10 + (nextCh - '0');
			if (num >= 10 && num <= 26) {
				// ans += numDecodings_tabu(str, si + 2, dp);
				ans += dp[si + 2];
			}

			// return dp[si] = ans;
			dp[si] = ans;
			continue;
		}

		return dp[SI];
	}
	// T:O(n)
	// S:O(1)
	public int numDecodings_opti(String str, int si) {
		return 0;
		// TODO
	}
	public int numDecodings(String str) {
		int n = str.length();
		int[] dp = new int[n + 1];
		Arrays.fill(dp , -1);
		// return numDecodings_memo(str, 0, dp);
		// return numDecodings_tabu(str, 0, dp);
		return numDecodings_opti(str, 0);
	}









	// 639. Decode Ways II
	// MEMORIZE
	// faith - this method will return the total number of ways to decode the substring str[si, n)
	long MOD = (long)1e9 + 7;
	public long numDecodings_memo(String str, int n, int si, long[] dp) {
		if (si == n) {
			return dp[si] = 1; // 1 represents that we found an ans
		}

		if (dp[si] != -1) {
			return dp[si];
		}

		long ans = 0;

		// 4 possibilities
		// * *
		// * c
		// c *
		// c c
		char ch = str.charAt(si);

		if (ch == '0') {
			return dp[si] = 0;
		} else if (ch == '*') {
			// decode by treating str[si] as a single digit
			ans += 9 * numDecodings_memo(str, n, si + 1, dp);
			// decode by treating str[si] & str[si+1] as a two-digit number (if valid)
			if (si < n - 1) {
				char ch2 = str.charAt(si + 1);
				// * *
				if (ch2 == '*') { // 11,12...19,21...26 // -2 for excluding 10,20
					ans += (26 - 10 + 1 - 2) * numDecodings_memo(str, n, si + 2, dp);
				}
				// * c
				if (ch2 - '0' >= 0 && ch2 - '0' <= 6) { // (1,2)1 // (1,2)2 // ... // (1,2)6
					ans += 2 * numDecodings_memo(str, n, si + 2, dp);
				}
				if (ch2 - '0' >= 7 && ch2 - '0' <= 9) { // 17 // 18 // 19
					ans += 1 * numDecodings_memo(str, n, si + 2, dp);
				}
			}
		} else {
			// decode by treating str[si] as a single digit
			ans += numDecodings_memo(str, n, si + 1, dp);
			// decode by treating str[si] & str[si+1] as a two-digit number (if valid)
			if (si < n - 1) {
				char ch2 = str.charAt(si + 1);
				// c *
				if (ch2 == '*' && ch == '1') { // 1(1,2,3...9)
					ans += 9 * numDecodings_memo(str, n, si + 2, dp);
				}
				if (ch2 == '*' && ch == '2') { // 2(1,2,3...6)
					ans += 6 * numDecodings_memo(str, n, si + 2, dp);
				}
				// c c
				if (ch2 != '*') {
					int num = (ch - '0') * 10 + (ch2 - '0');
					if (num >= 10 && num <= 26) {
						ans += numDecodings_memo(str, n, si + 2, dp);
					}
				}
			}
		}

		return dp[si] = ans % MOD;
	}
	// T:O(n)
	// S:O(1)
	public int numDecodings_opti(String str, int n, int si) {
		return 0;
		// TODO
	}
	public int numDecodings(String str) {
		int n = str.length();
		long[] dp = new long[n + 1];
		Arrays.fill(dp , -1);
		// return (int)numDecodings_memo(str, n, 0, dp);
		return (int)numDecodings_opti(str, n, 0);
	}









	// Gold Mine Problem
	// https://www.geeksforgeeks.org/problems/gold-mine-problem2608/1?itm_source=geeksforgeeks&itm_medium=article&itm_campaign=practice_card
	// faith - maxGold_memo will return the maximum amount of gold i can collect starting from position (r,c)
	int[][] dir = new int[][] {{0, 1}, {1, 1}, { -1, 1}};
	public int maxGold_memo(int arr[][], int n , int m, int r, int c, int[][] dp) {
		if (c == m - 1) {
			return dp[r][c] = arr[r][c];
		}

		if (dp[r][c] != -1) {
			return dp[r][c];
		}

		int ans = 0;
		for (int[] d : dir) {
			int nr = r + d[0];
			int nc = c + d[1];

			if (nr >= 0 && nr < n && nc >= 0 && nc < m) {
				ans = Math.max(ans, maxGold_memo(arr, n, m, nr, nc, dp));
			}
		}

		return dp[r][c] = ans + arr[r][c];
	}
	public int maxGold(int arr[][]) {
		if (arr == null || arr.length == 0 || arr[0].length == 0) {
			return 0;
		}

		int n = arr.length;
		int m = arr[0].length;
		int[][] dp = new int[n][m];
		for (int[] d : dp) {
			Arrays.fill(d, -1);
		}

		int ans = 0;
		for (int i = 0; i < n; i++) {
			ans = Math.max(ans, maxGold_memo(arr, n, m, i, 0, dp));
		}

		return ans;
	}









	// Minimum Cost Path
	// https://www.geeksforgeeks.org/min-cost-path-dp-6/
	// faith - minCost_memo will return the min cost to reach from (r,c) to (n-1,m-1)
	static int[][] dir = new int[][] {{0, 1}, {1, 0}, {1, 1}};
	static int minCost_memo(List<List<Integer>> arr, int n, int m, int r, int c, int[][] dp) {
		if (r == n - 1 && c == m - 1) {
			return dp[r][c] = arr.get(r).get(c);
		}

		if (dp[r][c] != -1) {
			return dp[r][c];
		}

		int ans = (int)1e9;
		for (int[] d : dir) {
			int nr = r + d[0];
			int nc = c + d[1];

			if (nr >= 0 && nr < n && nc >= 0 && nc < m) {
				ans = Math.min(ans, minCost_memo(arr, n, m, nr, nc, dp));
			}
		}

		return dp[r][c] = ans + arr.get(r).get(c);
	}
	static int minCost(List<List<Integer>> cost) {
		int n = cost.size();
		int m = cost.get(0).size();

		int[][] dp = new int[n][m];
		for (int[] d : dp) {
			Arrays.fill(d, -1);
		}

		return minCost_memo(cost, n, m, 0, 0, dp);
	}








	// Friends Pairing Problem
	// https://www.geeksforgeeks.org/problems/friends-pairing-problem5425/1?itm_source=geeksforgeeks&itm_medium=article&itm_campaign=practice_card
	// MEMORIZE
	// always think about yourself ex. n = 5 -> {1,2,3,4,5}
	// 1 has 2 choice
	// 1 decides to remain single - count = 1 * {2,3,4,5}
	// 1 decides to be in some pair - count = 12{3,4,5} | 13{2,4,5} | 14{2,3,5} | 15{2,3,4} = 4 * {a,b,c}
	// T: O(n)
	// S: O(n) + recursive stack
	public long countFriendsPairings_memo(int n, long[] dp) {
		if (n <= 1) {
			return dp[n] = 1;
		}

		if (dp[n] != -1) {
			return dp[n];
		}

		// when i am single
		long single = 1 * countFriendsPairings_memo(n - 1, dp);

		// when i am in pair
		long pairUp = (n - 1) * countFriendsPairings_memo(n - 2, dp);

		return dp[n] = single + pair;
	}
	// T: O(n)
	// S: O(n)
	public long countFriendsPairings_tabu(int N, long[] dp) {
		for (int n = 0; n <= N; n++) {
			if (n <= 1) {
				// return dp[n] = 1;
				dp[n] = 1;
				continue;
			}

			// if (dp[n] != -1) {
			// 	return dp[n];
			// }

			// when i am single
			// long single += 1 * countFriendsPairings_memo(n - 1, dp);
			long single = 1 * dp[n - 1];

			// when i am in pair
			// long pairUp += (n - 1) * countFriendsPairings_memo(n - 2, dp);
			long pairUp = (n - 1) * dp[n - 2];

			// return dp[n] = single + pair;
			dp[n] = single + pair;
			continue;
		}

		return dp[N];
	}
	// T: O(n)
	// S: O(1)
	public long countFriendsPairings_opti(int N) {
		long a = 1;
		long b = 1;
		for (int n = 2; n <= N; n++) {

			long c = 0;
			// when i am single
			c += 1 * b;
			// when i am in pair
			c += (n - 1) * a;

			a = b;
			b = c;
		}

		return b;
	}
	public long countFriendsPairings(int n) {
		if (n <= 1) {
			return n;
		}
		long[] dp = new long[n + 1];
		Arrays.fill(dp, -1);

		// return countFriendsPairings_memo(n, dp);
		// return countFriendsPairings_tabu(n, dp);
		return countFriendsPairings_opti(n);
	}









	// Count number of ways to partition a set into k subsets
	// https://www.geeksforgeeks.org/count-number-of-ways-to-partition-a-set-into-k-subsets/?ref=gcse_ind
	// MEMORIZE
	public int count_memo(int n, int k, int[][] dp) {
		if (n == k || k == 1) {
			return dp[n][k] = 1;
		}

		if (dp[n][k] != -1) {
			return dp[n][k];
		}

		int selfGroup = count_memo(n - 1, k - 1, dp);
		int partOfGroup = count_memo(n - 1, k, dp) * k;
		// * k, because i can decide to be part of any of the k groups

		return dp[n][k] = selfGroup + partOfGroup;
	}
	// observation order - left -> right , top -> bottom
	public int count_tabu(int N, int K, int[][] dp) {
		for (int n = 1; n <= N; n++) {
			for (int k = 1; k <= K; k++) {
				if (n == k || k == 1) {
					// return dp[n][k] = 1;
					dp[n][k] = 1;
					continue;
				}

				// if (dp[n][k] != -1) {
				// 	return dp[n][k];
				// }

				// int selfGroup = count_tabu(n - 1, k - 1, dp);
				int selfGroup = dp[n - 1][k - 1];
				// int partOfGroup = count_tabu(n - 1, k, dp) * k;
				int partOfGroup = dp[n - 1][k] * k;

				// return dp[n][k] = selfGroup + partOfGroup;
				dp[n][k] = selfGroup + partOfGroup;
				continue;
			}
		}

		return dp[N][K];
	}
	public int countNumberOfWaysToPartitionASetIntoKSubsets(int n, int k) {
		if (k > n) {
			return 0;
		}
		int[][] dp = new int[n + 1][k + 1];
		for (int[] d : dp) {
			Arrays.fill(d, -1);
		}
		// return count_memo(n , k, dp);
		return count_tabu(n , k, dp);
	}









	// Count the number of ways to divide N in k groups incrementally
	// https://www.geeksforgeeks.org/problems/divide-in-incremental-groups--170647/1?itm_source=geeksforgeeks&itm_medium=article&itm_campaign=practice_card
	public int countWaystoDivide_memo(int n, int k, int minAllowed, int[][][] dp) {
		if (k == 1) {
			return dp[n][k][minAllowed] = 1;
		}

		if (dp[n][k][minAllowed] != -1) {
			return dp[n][k][minAllowed];
		}

		int count = 0;
		for (int i = minAllowed; i <= n - i; i++) {
			if (k - 1 > n - i) {
				break;
			} else {
				count += countWaystoDivide_memo(n - i, k - 1, i, dp);
			}
		}

		return dp[n][k][minAllowed] = count;
	}
	public int countWaystoDivide(int n, int k) {
		if (k > n) {
			return 0;
		}

		int[][][] dp = new int[n + 1][k + 1][n + 1];
		for (int[][] d : dp) {
			for (int[] e : d) {
				Arrays.fill(e, -1);
			}
		}

		return countWaystoDivide_memo(n, k, 1, dp);
	}









	// ======================================== STRING SET ========================================
	// always think about 2 cases
	// if s[i] == s[j]
	// if s[i] != s[j]









	// 516. Longest Palindromic Subsequence
	public int longestPalindromeSubseq_memo(String str, int si, int ei, int[][] dp) {
		if (si > ei) {
			return dp[si][ei] = 0;
		}
		if (si == ei) {
			return dp[si][ei] = 1;
		}

		if (dp[si][ei] != 0) {
			return dp[si][ei];
		}

		int ans = 0;
		if (str.charAt(si) == str.charAt(ei)) {
			ans = longestPalindromeSubseq_memo(str, si + 1, ei - 1, dp) + 2;
		} else {
			ans = Math.max(ans, longestPalindromeSubseq_memo(str, si, ei - 1, dp));
			ans = Math.max(ans, longestPalindromeSubseq_memo(str, si + 1, ei, dp));
			// ans = Math.max(ans, longestPalindromeSubseq_memo(si + 1, ei - 1, dp));
			// not necessary, it will be covered in above 2 calls anyways
		}

		return dp[si][ei] = ans;
	}
	// left -> right
	// bottom -> top
	public int longestPalindromeSubseq_tabu(String str, int SI, int EI, int[][] dp) {
		int n = str.length();
		for (int si = n - 1; si >= SI; si--) {
			for (int ei = 0; ei <= EI; ei++) {
				if (si > ei) {
					dp[si][ei] = 0;
					continue;
				}
				if (si == ei) {
					dp[si][ei] = 1;
					continue;
				}

				int ans = 0;
				if (str.charAt(si) == str.charAt(ei)) {
					ans = dp[si + 1][ei - 1] + 2; // longestPalindromeSubseq_tabu(str, si + 1, ei - 1, dp) + 2;
				} else {
					// ans = Math.max(ans, longestPalindromeSubseq_tabu(str, si, ei - 1, dp));
					ans = Math.max(ans, dp[si][ei - 1]);
					// ans = Math.max(ans, longestPalindromeSubseq_tabu(str, si + 1, ei, dp));
					ans = Math.max(ans, dp[si + 1][ei]);
				}

				dp[si][ei] = ans;
				continue;
			}
		}

		return dp[SI][EI];
	}
	// GAP STRATEGY -> to traverse on diagonals in a matrix
	public int longestPalindromeSubseq_tabu_gapStrategy(String str, int SI, int EI, int[][] dp) {
		int n = str.length();
		for (int gap = 0; gap < n; gap++) {
			for (int si = 0, ei = gap; ei < n; si++, ei++) {
				if (si > ei) {
					dp[si][ei] = 0;
					continue;
				}
				if (si == ei) {
					dp[si][ei] = 1;
					continue;
				}

				int ans = 0;
				if (str.charAt(si) == str.charAt(ei)) {
					ans = dp[si + 1][ei - 1] + 2; // longestPalindromeSubseq_tabu(str, si + 1, ei - 1, dp) + 2;
				} else {
					// ans = Math.max(ans, longestPalindromeSubseq_tabu(str, si, ei - 1, dp));
					ans = Math.max(ans, dp[si][ei - 1]);
					// ans = Math.max(ans, longestPalindromeSubseq_tabu(str, si + 1, ei, dp));
					ans = Math.max(ans, dp[si + 1][ei]);
				}

				dp[si][ei] = ans;
				continue;
			}
		}

		return dp[SI][EI];
	}

	public int longestPalindromeSubseq(String str) {
		int n = str.length();
		int[][] dp = new int[n][n];
		// return longestPalindromeSubseq_memo(str, 0, n - 1, dp);
		// return longestPalindromeSubseq_tabu(str, 0, n - 1, dp);
		int lps = longestPalindromeSubseq_tabu_gapStrategy(str, 0, n - 1, dp);
		return lps;
	}









	// 1143. Longest Common Subsequence
	public int longestCommonSubsequence_memo(String str1, String str2, int idx1, int idx2, int[][] dp) {
		if (idx1 == str1.length() || idx2 == str2.length()) {
			return dp[idx1][idx2] = 0;
		}

		if (dp[idx1][idx2] != -1) {
			return dp[idx1][idx2];
		}

		int ans = 0;
		char c1 = str1.charAt(idx1);
		char c2 = str2.charAt(idx2);
		if (c1 == c2) {
			ans = longestCommonSubsequence_memo(str1, str2, idx1 + 1, idx2 + 1, dp) + 1;
		} else {
			ans = Math.max(ans, longestCommonSubsequence_memo(str1, str2, idx1 + 1, idx2, dp));
			ans = Math.max(ans, longestCommonSubsequence_memo(str1, str2, idx1, idx2 + 1, dp));
		}

		return dp[idx1][idx2] = ans;
	}
	public int longestCommonSubsequence_tabu(String str1, String str2, int IDX1, int IDX2, int[][] dp) {
		int n = str1.length();
		int m = str2.length();
		for (int idx1 = n; idx1 >= 0; idx1--) {
			for (int idx2 = m; idx2 >= 0; idx2--) {
				if (idx1 == n || idx2 == m) {
					dp[idx1][idx2] = 0;
					continue;
				}

				int ans = 0;
				char c1 = str1.charAt(idx1);
				char c2 = str2.charAt(idx2);
				if (c1 == c2) {
					ans = dp[idx1 + 1][idx2 + 1] + 1;
				} else {
					ans = Math.max(ans, dp[idx1 + 1][idx2]);
					ans = Math.max(ans, dp[idx1][idx2 + 1]);
				}

				dp[idx1][idx2] = ans;
				continue;
			}
		}

		return dp[IDX1][IDX2];
	}
	public int longestCommonSubsequence(String str1, String str2) {
		int[][] dp = new int[str1.length() + 1][str2.length() + 1];
		for (int[] d : dp) {
			Arrays.fill(d, -1);
		}
		// return longestCommonSubsequence_memo(str1, str2, 0, 0, dp);
		int lcs = longestCommonSubsequence_tabu(str1, str2, 0, 0, dp);
		return lcs;
	}









	// 72. Edit Distance
	public int minDistance_memo(String s1, String s2, int i, int j, int[][] dp) {
		int n = s1.length();
		int m = s2.length();
		if (i == n) {
			return dp[i][j] = m - 1 - j + 1; // INSERT
		}
		if (j == m) {
			return dp[i][j] = n - 1 - i + 1; // DELETE
		}

		if (dp[i][j] != 0) {
			return dp[i][j];
		}

		int ans = (int)1e9;
		if (s1.charAt(i) == s2.charAt(j)) {
			ans = minDistance_memo(s1, s2, i + 1, j + 1, dp);
		} else {
			ans = Math.min(ans, minDistance_memo(s1, s2, i + 1, j, dp) + 1); // DELETE
			ans = Math.min(ans, minDistance_memo(s1, s2, i, j + 1, dp) + 1); // INSERT
			ans = Math.min(ans, minDistance_memo(s1, s2, i + 1, j + 1, dp) + 1); // REPLACE
		}

		return dp[i][j] = ans;
	}
	public int minDistance(String s1, String s2) {
		int n = s1.length();
		int m = s2.length();
		int[][] dp = new int[n + 1][m + 1];
		return minDistance_memo(s1, s2, 0, 0, dp);
	}









	// 115. Distinct Subsequences
	public int numDistinct_memo(String s, String t, int i, int j, int[][] dp) {
		int n = s.length();
		int m = t.length();
		int s1 = n - i + 1;
		int s2 = m - j + 1;

		if (s1 < s2) {
			return dp[i][j] = 0;
		}
		if (j == m) {
			return dp[i][j] = 1;
		}

		if (dp[i][j] != -1) {
			return dp[i][j];
		}

		int ans = 0;
		if (s.charAt(i) == t.charAt(j)) {
			ans += numDistinct_memo(s, t, i + 1, j + 1, dp);
			ans += numDistinct_memo(s, t, i + 1, j, dp);
		} else {
			ans += numDistinct_memo(s, t, i + 1, j, dp);
		}

		return dp[i][j] = ans;
	}
	// row - bottom -> top
	// col - right -> left
	public int numDistinct_tabu(String s, String t, int I, int J, int[][] dp) {
		int n = s.length();
		int m = t.length();
		for (int i = n; i >= I; i--) {
			for (int j = m; j >= J; j--) {
				int s1 = n - i + 1;
				int s2 = m - j + 1;

				if (s1 < s2) {
					dp[i][j] = 0;
					continue;
				}
				if (j == m) {
					dp[i][j] = 1;
					continue;
				}

				int ans = 0;
				if (s.charAt(i) == t.charAt(j)) {
					ans += dp[i + 1][j + 1];
					ans += dp[i + 1][j];
				} else {
					ans += dp[i + 1][j];
				}

				dp[i][j] = ans;
				continue;
			}
		}

		return dp[I][J];
	}
	public int numDistinct(String s, String t) {
		int n = s.length();
		int m = t.length();
		int[][] dp = new int[n + 1][m + 1];
		for (int[] d : dp) {
			Arrays.fill(d, -1);
		}

		// return numDistinct_memo(s, t, 0, 0, dp);
		return numDistinct_tabu(s, t, 0, 0, dp);
	}









	// 44. Wildcard Matching
	public String removeRedundantAsterisk(String w) {
		Stack<Character> st = new Stack<>();
		for (int i = w.length() - 1; i >= 0; i--) {
			char ch = w.charAt(i);
			if (ch == '*' && st.size() != 0 && st.peek() == '*') {
				continue;
			}
			st.push(ch);
		}

		StringBuilder sb = new StringBuilder();
		while (st.size() != 0) {
			sb.append(st.pop());
		}

		return sb.toString();
	}
	public int isMatch_memo(String s, String w, int i, int j, int[][] dp) {
		int n = s.length();
		int m = w.length();
		if (i == n && j == m) {
			return dp[i][j] = 1;
		}
		if (j == m) {
			return dp[i][j] = 0;
		}
		if (i == n) {
			if (j == m - 1 && w.charAt(j) == '*') {
				return dp[i][j] = 1;
			}
			return dp[i][j] = 0;
		}

		if (dp[i][j] != -1) {
			return dp[i][j];
		}

		int ans = 0;
		char c1 = s.charAt(i);
		char c2 = w.charAt(j);
		if (c1 == c2) {
			ans = isMatch_memo(s, w, i + 1, j + 1, dp);
		} else {
			if (c2 == '?') {
				ans = isMatch_memo(s, w, i + 1, j + 1, dp);
			} else if (c2 == '*') {
				ans += isMatch_memo(s, w, i, j + 1, dp); // * = empty string
				ans += isMatch_memo(s, w, i + 1, j, dp); // * = match the curr char and stay on *
				// ans += isMatch_memo(s, w, i + 1, j + 1, dp); // * = match the curr char and drop
				// redundant call, as it will be covered in above 2 calls
			} else {
				ans = 0;
			}
		}

		return dp[i][j] = (ans == 0 ? 0 : 1);
	}
	public boolean isMatch(String s, String w) {
		w = removeRedundantAsterisk(w);
		int n = s.length();
		int m = w.length();
		int[][] dp = new int[n + 1][m + 1];
		for (int[] d : dp) {
			Arrays.fill(d, -1);
		}
		// return isMatch_memo(s, w, 0, 0, dp) == 1;
		return isMatch_tabu(s, w, 0, 0, dp) == 1;
	}









	// 10. Regular Expression Matching
	// MEMORIZE
	// always treat * in combination with its prev char, never treat it individually, because * does not have any significance individually
	// you need to handle the * case first, because if we just check c1 != c2 and return false, we may ignore case str = "a" pattern "c*a"
	// base case - only 1 pattern can satisfy empty string - a*b*c*.*
	public String removeRedundantAsterisk(String pattern) {
		Stack<Character> st = new Stack<>();
		for (int i = pattern.length() - 1; i >= 0; i--) {
			char ch = pattern.charAt(i);
			if (ch == '*' && st.size() != 0 && st.peek() == '*') {
				continue;
			}
			st.push(ch);
		}

		StringBuilder sb = new StringBuilder();
		while (st.size() != 0) {
			sb.append(st.pop());
		}

		return sb.toString();
	}
	public int isMatch_memo(String str, String pattern, int i, int j, int[][] dp) {
		System.out.println(i + " " + j);
		int n = str.length();
		int m = pattern.length();

		if (j == m && i == n) {
			return dp[i][j] = 1;
		}
		if (j == m) {
			return dp[i][j] = 0;
		}
		if (i == n) {
			// only 1 pattern can satisfy empty string - a*b*c*.*
			return dp[i][j] = (j + 1 < m && pattern.charAt(j + 1) == '*') ? isMatch_memo(str, pattern, i, j + 2, dp) : 0;
		}

		if (dp[i][j] != -1) {
			return dp[i][j];
		}

		char c1 = str.charAt(i);
		char c2 = pattern.charAt(j);

		int ans = 0;
		// you need to handle the * case first, because if we just check c1 != c2 and return false, we may ignore case str = "a" pattern "c*a"
		if (j + 1 < m && pattern.charAt(j + 1) == '*') {
			ans += isMatch_memo(str, pattern, i, j + 2, dp); // * = 0 = c2(0)
			if (c1 == c2 || c2 == '.') {
				ans += isMatch_memo(str, pattern, i + 1, j, dp); // * = 1 = c2(1) match the curr char but keep *
			}
		} else if (c1 == c2 || c2 == '.') {
			ans = isMatch_memo(str, pattern, i + 1, j + 1, dp);
		} else {
			ans = 0;
		}

		return dp[i][j] = ans == 0 ? 0 : 1;
	}
	public boolean isMatch(String str, String pattern) {
		pattern = removeRedundantAsterisk(pattern);
		int n = str.length();
		int m = pattern.length();
		int[][] dp = new int[n + 1][m + 1];
		for (int[] d : dp) {
			Arrays.fill(d, -1);
		}
		return isMatch_memo(str, pattern, 0, 0, dp) == 1;
	}









	// 1035. Uncrossed Lines
	public int maxUncrossedLines_memo(int[] arr1, int[] arr2, int i, int j, int[][] dp) {
		int n = arr1.length;
		int m = arr2.length;
		if (i == n || j == m) {
			return dp[i][j] = 0;
		}

		if (dp[i][j] != -1) {
			return dp[i][j];
		}

		int ans = 0;
		if (arr1[i] == arr2[j]) {
			ans = maxUncrossedLines_memo(arr1, arr2, i + 1, j + 1, dp) + 1;
		} else {
			ans = Math.max(ans, maxUncrossedLines_memo(arr1, arr2, i + 1, j, dp));
			ans = Math.max(ans, maxUncrossedLines_memo(arr1, arr2, i, j + 1, dp));
			// ans = Math.max(ans, maxUncrossedLines_memo(arr1, arr2, i + 1, j + 1));
		}

		return dp[i][j] = ans;
	}
	public int maxUncrossedLines(int[] arr1, int[] arr2) {
		int n = arr1.length;
		int m = arr2.length;
		int[][] dp = new int[n + 1][m + 1];
		for (int[] d : dp) {
			Arrays.fill(d, -1);
		}

		// LCS
		return maxUncrossedLines_memo(arr1, arr2, 0, 0, dp);
	}









	// 1458. Max Dot Product of Two Subsequences
	public int maxDotProduct_memo(int[] arr1, int[] arr2, int i, int j, Integer[][] dp) {
		int n = arr1.length;
		int m = arr2.length;
		if (i == n || j == m) {
			return dp[i][j] = -(int)1e9;
		}

		if (dp[i][j] != null) {
			return dp[i][j];
		}

		int ans = arr1[i] * arr2[j]; // only include i&j
		ans = Math.max(ans, arr1[i] * arr2[j] + maxDotProduct_memo(arr1, arr2, i + 1, j + 1, dp)); // include i, j
		ans = Math.max(ans, maxDotProduct_memo(arr1, arr2, i, j + 1, dp)); // include i exclude j
		ans = Math.max(ans, maxDotProduct_memo(arr1, arr2, i + 1, j, dp)); // include j exclude i
		// ans = Math.max(ans, maxDotProduct_memo(arr1, arr2, i + 1, j + 1, dp)); // exclude i,j
		// not possible to exclude both, becasue final ans can't be empty subseq

		return dp[i][j] = ans;
	}
	public int maxDotProduct(int[] arr1, int[] arr2) {
		int n = arr1.length;
		int m = arr2.length;
		Integer[][] dp = new Integer[n + 1][m + 1]; // use Integer instead of int
		return maxDotProduct_memo(arr1, arr2, 0, 0, dp);
	}









	// 5. Longest Palindromic Substring
	// MEMORIZE
	// faith - each cell (i,j) of the dp represents the lpss between [i,j] INCLUDING i&j
	// no point of memoization => because you have to use static variable => forceful recursion
	// direct tabulation
	public String longestPalindrome(String str) {
		int n = str.length();
		boolean[][] dp = new boolean[n][n];
		int si = 0;
		int ei = 0;

		for (int gap = 0; gap < n; gap++) {
			for (int i = 0, j = gap; j < n; i++, j++) {
				if (gap == 0) {
					dp[i][j] = true;
				} else if (gap == 1) {
					dp[i][j] = (str.charAt(i) == str.charAt(j)) ? true : false;
				} else {
					dp[i][j] = (str.charAt(i) == str.charAt(j) && dp[i + 1][j - 1]) ? true : false;
				}

				if (dp[i][j] && (j - i > ei - si)) {
					si = i;
					ei = j;
				}
			}
		}

		return str.substring(si, ei + 1);
	}









	// Longest Common Substring
	// https://www.geeksforgeeks.org/problems/longest-common-substring1452/1
	// faith - each cell (i,j) of the dp represents the lcss between [i,n-1] & [j,m-1] INCLUDING i&j
	public int longestCommonSubstr(String s1, String s2) {
		int n = s1.length();
		int m = s2.length();
		int[][] dp = new int[n + 1][m + 1];
		int gMax = 0;

		for (int i = n; i >= 0; i--) {
			for (int j = m; j >= 0; j--) {
				if (i == n || j == m) {
					dp[i][j] = 0;
				} else {
					dp[i][j] = s1.charAt(i) == s2.charAt(j) ? dp[i + 1][j + 1] + 1 : 0;
				}

				gMax = Math.max(gMax, dp[i][j]);
			}
		}

		return gMax;
	}









	// 583. Delete Operation for Two Strings
	public int longestCommonSubsequence_memo(String str1, String str2, int idx1, int idx2, int[][] dp) {
		if (idx1 == str1.length() || idx2 == str2.length()) {
			return dp[idx1][idx2] = 0;
		}

		if (dp[idx1][idx2] != -1) {
			return dp[idx1][idx2];
		}

		int ans = 0;
		char c1 = str1.charAt(idx1);
		char c2 = str2.charAt(idx2);
		if (c1 == c2) {
			ans = longestCommonSubsequence_memo(str1, str2, idx1 + 1, idx2 + 1, dp) + 1;
		} else {
			ans = Math.max(ans, longestCommonSubsequence_memo(str1, str2, idx1 + 1, idx2, dp));
			ans = Math.max(ans, longestCommonSubsequence_memo(str1, str2, idx1, idx2 + 1, dp));
		}

		return dp[idx1][idx2] = ans;
	}
	public int longestCommonSubsequence(String str1, String str2) {
		int n = str1.length();
		int m = str2.length();
		int[][] dp = new int[n + 1][m + 1];
		for (int[] d : dp) {
			Arrays.fill(d, -1);
		}
		return longestCommonSubsequence_memo(str1, str2, 0, 0, dp);
	}
	public int minDistance(String s1, String s2) {
		int n = s1.length();
		int m = s2.length();

		// LCS
		return n + m - 2 * longestCommonSubsequence(s1, s2);
	}









	// 131. Palindrome Partitioning
	// BACKTRACKING
	// MEMORIZE
	// currAns saves the palindrome partioning of the current path
	public void partition_backtracking(String str, boolean[][] palindromeDp, int si, List<String> currAns, List<List<String>> ans) {
		int n = str.length();
		if (si == n) {
			List<String> currAnsDeepCopy = new ArrayList<>(currAns);
			ans.add(currAnsDeepCopy);
			return;
		}

		for (int cut = si; cut < n; cut++) {
			if (palindromeDp[si][cut] == true) {
				String substring = str.substring(si, cut + 1);
				currAns.add(substring);
				partition_backtracking(str, palindromeDp, cut + 1, currAns, ans);
				currAns.remove(currAns.size() - 1);
			}
		}
	}
	public List<List<String>> partition(String str) {
		int n = str.length();
		boolean[][] palindromeDp = new boolean[n][n];

		for (int gap = 0; gap < n; gap++) {
			for (int si = 0, ei = si + gap; ei < n; si++, ei++) {
				if (gap == 0) {
					palindromeDp[si][ei] = true;
				} else if (gap == 1) {
					palindromeDp[si][ei] = str.charAt(si) == str.charAt(ei);
				} else {
					palindromeDp[si][ei] = palindromeDp[si + 1][ei - 1] && str.charAt(si) == str.charAt(ei);
				}
			}
		}

		List<String> currAns = new ArrayList<>();
		List<List<String>> ans = new ArrayList<>();
		partition_backtracking(str, palindromeDp, 0, currAns, ans);
		return ans;
	}









	// 132. Palindrome Partitioning II
	// MEMORIZE
	// faith - this method will return me the
	// min no of cuts needed for palindrome partitioning the string from [si,n-1]
	// WRONG APPROACH - find the longest palindrome in the given string and make recursive call for the left & right sub-string
	// RIGHT APPROACH - BRUTE FORCE - NOT PREFERRED - find all the palindromes in the given string and make recursive call for the left & right sub-string
	// RIGHT APPROACH - OPTIMIZED - PREFERRED - explore all possible sub-strings [si,idx] that is a palindarome and make recursive call for the rest right sub-string
	// test case - a(10)ba(5)
	// WRONG APPROACH ans = ["a(10)","b","a(5)"]
	// RIGHT APPROACH ans = ["a(5)","a(5)ba(5)"]
	public int minCut_memo (String str, int si, boolean[][] pdp, int[] dp) {
		int n = str.length();
		if (pdp[si][n - 1]) {
			return dp[si] = 0;
		}

		if (dp[si] != -1) {
			return dp[si];
		}

		int ans = (int)1e9;
		for (int cut = si; cut <= n - 1; cut++) {
			if (pdp[si][cut] && cut + 1 < n) {
				ans = Math.min(ans, minCut_memo(str, cut + 1, pdp, dp) + 1);
			}
		}

		return dp[si] = ans;
	}
	public int minCut(String str) {
		int n = str.length();
		boolean[][] pdp = new boolean[n][n];

		for (int gap = 0; gap < n; gap++) {
			for (int i = 0, j = gap; j < n; i++, j++) {
				if (gap == 0) {
					pdp[i][j] = true;
				} else if (gap == 1) {
					pdp[i][j] = str.charAt(i) == str.charAt(j);
				} else {
					pdp[i][j] = (str.charAt(i) == str.charAt(j) && pdp[i + 1][j - 1]) ? true : false;
				}
			}
		}

		int[] dp = new int[n];
		Arrays.fill(dp, -1);
		return minCut_memo(str, 0, pdp, dp);
	}









	// 1278. Palindrome Partitioning III
	// MEMORIZE
	public int[][] minChanges(String str) {
		int n = str.length();
		int[][] dp = new int[n][n];

		for (int gap = 0; gap < n; gap++) {
			for (int si = 0, ei = si + gap; ei < n; si++, ei++) {
				if (gap == 0) {
					dp[si][ei] = 0;
				} else if (gap == 1) {
					dp[si][ei] = str.charAt(si) == str.charAt(ei) ? 0 : 1;
				} else {
					dp[si][ei] = str.charAt(si) == str.charAt(ei) ? 0 : 1;
					dp[si][ei] += dp[si + 1][ei - 1];
				}
			}
		}

		return dp;
	}
	// TLE
	public int palindromePartition_recu(String str, int si, int k, int[][] minChangesDp) {
		int n = str.length();
		if (k == 1) {
			return minChangesDp[si][n - 1];
		}

		int myAns = (int)1e9;
		for (int cut = si; cut < n && (n - 1) - (cut + 1) + 1 >= k - 1; cut++) {
			int currAns = minChangesDp[si][cut];
			int recAns = palindromePartition_recu(str, cut + 1, k - 1, minChangesDp);

			myAns = Math.min(myAns, currAns + recAns);
		}

		return myAns;
	}
	public int palindromePartition_memo(String str, int si, int k, int[][] minChangesDp, int[][] dp) {
		int n = str.length();
		if (k == 1) {
			return dp[si][k] = minChangesDp[si][n - 1];
		}

		if (dp[si][k] != -1) {
			return dp[si][k];
		}

		int myAns = (int)1e9;
		for (int cut = si; cut < n && (n - 1) - (cut + 1) + 1 >= k - 1; cut++) { // no of char in the string >= k
			int currAns = minChangesDp[si][cut];
			int recAns = palindromePartition_memo(str, cut + 1, k - 1, minChangesDp, dp);

			myAns = Math.min(myAns, currAns + recAns);
		}

		return dp[si][k] = myAns;
	}

	public int palindromePartition(String str, int k) {
		int n = str.length();
		int[][] minChangesDp = minChanges(str);
		int[][] dp = new int[n][k + 1];
		for (int[]d : dp) {
			Arrays.fill(d, -1);
		}

		// return palindromePartition_recu(str, 0, k, minChangesDp);
		return palindromePartition_memo(str, 0, k, minChangesDp, dp);
	}









	// Count subsequences of type a^i, b^j, c^k
	// https://www.geeksforgeeks.org/problems/count-subsequences-of-type-ai-bj-ck4425/1
	// MEMORIZE
	public int fun(String s) {
		int n = s.length();

		long emyptyCount = 1;
		long aCount = 0; // no of sub-seq ending with a of the form a^i
		long bCount = 0; // no of sub-seq ending with b of the form a^i b^j
		long cCount = 0;

		long MOD = (long)1e9 + 7;

		for (int i = 0; i < n; i++) {
			char ch = s.charAt(i);
			if (ch == 'a') {
				aCount = aCount + (emyptyCount + aCount) % MOD;
			} else if (ch == 'b') {
				bCount = bCount + (aCount + bCount) % MOD;
			} else if (ch == 'c') {
				long excludeC = cCount; // if c decides not to come, then no of sub-seq ending with c of the form a^i b^j c^k
				long includeC = (bCount + cCount) % MOD;
				// if c decides to come, then no of sub-seq ending with c of the form a^i b^j c^k will be bCount + cCount because c can
				// either come after bCount (a^i b^j) to make a^i b^j c^1 OR cCount (a^i b^j c^k) to make a^i b^j c^k+1
				cCount = includeC + excludeC;
			}
		}

		return (int)(cCount % MOD);
	}









	// 139. Word Break
	public int wordBreak_memo(String str, int si, HashSet<String> dict, int[] dp) {
		int n = str.length();
		int maxLen = 0;
		for (String word : dict) {
			maxLen = Math.max(maxLen, word.length());
		}

		if (si == n) {
			return dp[si] = 1;
		}

		if (dp[si] != -1) {
			return dp[si];
		}

		int ans = 0;
		for (int cut = si; cut < n && cut - si + 1 <= maxLen; cut++) {
			if (dict.contains(str.substring(si, cut + 1))) {
				ans += wordBreak_memo(str, cut + 1, dict, dp);
				if (ans > 0) {
					break;
				}
			}
		}

		return dp[si] = ans;
	}
	public int wordBreak_tabu(String str, int SI, HashSet<String> dict, int[] dp) {
		int n = str.length();
		int maxLen = 0;
		for (String word : dict) {
			maxLen = Math.max(maxLen, word.length());
		}

		for (int si = n; si >= SI; si--) {
			if (si == n) {
				dp[si] = 1;
				continue;
			}

			int ans = 0;
			for (int cut = si; cut < n && cut - si + 1 <= maxLen; cut++) {
				if (dict.contains(str.substring(si, cut + 1))) {
					ans += dp[cut + 1]; // wordBreak_tabu(str, i + 1, dict, dp);
					if (ans > 0) {
						break;
					}
				}
			}

			dp[si] = ans;
			continue;
		}

		return dp[SI];
	}
	public boolean wordBreak(String str, List<String> wordDict) {
		HashSet<String> dict = new HashSet<>();
		for (String s : wordDict) {
			dict.add(s);
		}

		int n = str.length();
		int[] dp = new int[n + 1];
		Arrays.fill(dp, -1);
		return wordBreak_memo(str, 0, dict, dp) == 1;
		// return wordBreak_tabu(str, 0, dict, dp) == 1;
	}









	// 516. Longest Palindromic Subsequence
	// we are usign dp[][] to make the right call instead of calling every branch like recursion
	public int longestPalindromeSubseq_memo(String str, int si, int ei, int[][] dp) {
		if (si > ei) {
			return dp[si][ei] = 0;
		}
		if (si == ei) {
			return dp[si][ei] = 1;
		}

		if (dp[si][ei] != 0) {
			return dp[si][ei];
		}

		int ans = 0;
		if (str.charAt(si) == str.charAt(ei)) {
			ans = longestPalindromeSubseq_memo(str, si + 1, ei - 1, dp) + 2;
		} else {
			ans = Math.max(ans, longestPalindromeSubseq_memo(str, si, ei - 1, dp));
			ans = Math.max(ans, longestPalindromeSubseq_memo(str, si + 1, ei, dp));
		}

		return dp[si][ei] = ans;
	}
	public String longestPalindromeSubseq_reverse_engineering(String str, int si, int ei, int[][] dp) {
		int n = str.length();
		if (si > ei) {
			return "";
		}
		if (si == ei) {
			return str.charAt(si) + "";
		}

		String ans = "";
		if (str.charAt(si) == str.charAt(ei)) {
			String s1 = longestPalindromeSubseq_reverse_engineering(str, si + 1, ei - 1, dp);
			ans += str.charAt(si) + s1 + str.charAt(si);
		} else {
			if (dp[si][ei - 1] > dp[si + 1][ei]) {
				ans = longestPalindromeSubseq_reverse_engineering(str, si, ei - 1, dp);
			} else {
				ans = longestPalindromeSubseq_reverse_engineering(str, si + 1, ei, dp);
			}
		}

		return ans;
	}
	public int longestPalindromeSubseq(String str) {
		int n = str.length();
		int[][] dp = new int[n][n];
		longestPalindromeSubseq_memo(str, 0, n - 1, dp);
		return longestPalindromeSubseq_reverse_engineering(str, 0, n - 1, dp).length();
	}









	// Gold Mine Problem
	// https://www.geeksforgeeks.org/problems/gold-mine-problem2608/1?itm_source=geeksforgeeks&itm_medium=article&itm_campaign=practice_card
	// faith - maxGold_memo will return the maximum amount of gold i can collect starting from position (r,c)
	int[][] dir = new int[][] {{0, 1}, {1, 1}, { -1, 1}};
	public int maxGold_memo(int arr[][], int n , int m, int r, int c, int[][] dp) {
		if (c == m - 1) {
			return dp[r][c] = arr[r][c];
		}

		if (dp[r][c] != -1) {
			return dp[r][c];
		}

		int ans = 0;
		for (int[] d : dir) {
			int nr = r + d[0];
			int nc = c + d[1];

			if (nr >= 0 && nr < n && nc >= 0 && nc < m) {
				ans = Math.max(ans, maxGold_memo(arr, n, m, nr, nc, dp));
			}
		}

		return dp[r][c] = ans + arr[r][c];
	}
	public void maxGold_reverse_engineering(int arr[][], int r, int c, int[][] dp, String psf) {
		int n = arr.length;
		int m = arr[0].length;

		if (c == m - 1) {
			psf += "(" + r + "," + c + ")";
			System.out.println(psf);
			return;
		}

		psf += "(" + r + "," + c + ") -> ";

		int maxRow = -1;
		int maxCol = -1;
		int maxGold = -1;
		for (int[] d : dir) {
			int nr = r + d[0];
			int nc = c + d[1];

			if (nr >= 0 && nr < n && nc >= 0 && nc < m && dp[nr][nc] > maxGold) {
				maxGold = dp[nr][nc];
				maxRow = nr;
				maxCol = nc;
			}
		}

		maxGold_reverse_engineering(arr, maxRow, maxCol, dp, psf);
	}
	public int maxGold(int arr[][]) {
		if (arr == null || arr.length == 0 || arr[0].length == 0) {
			return 0;
		}

		int n = arr.length;
		int m = arr[0].length;
		int[][] dp = new int[n][m];
		for (int[] d : dp) {
			Arrays.fill(d, -1);
		}

		int ans = 0;
		for (int i = 0; i < n; i++) {
			ans = Math.max(ans, maxGold_memo(arr, n, m, i, 0, dp));
		}

		int maxRow = -1;
		int maxCol = 0;
		int maxGold = -1;
		for (int i = 0; i < n; i++) {
			if (dp[i][0] > maxGold) {
				maxGold = dp[i][0];
				maxRow = i;
			}
		}

		maxGold_reverse_engineering(arr, maxRow, 0, dp, "psf -> ");

		return ans;
	}









	// 140. Word Break II
	public int wordBreak_memo(String str, int si, HashSet<String> dict, int[] dp) {
		int n = str.length();
		int maxLen = 0;
		for (String word : dict) {
			maxLen = Math.max(maxLen, word.length());
		}

		if (si == n) {
			return dp[si] = 1;
		}

		if (dp[si] != -1) {
			return dp[si];
		}

		int ans = 0;
		for (int cut = si; cut < n && cut - si + 1 <= maxLen; cut++) {
			if (dict.contains(str.substring(si, cut + 1))) {
				ans += wordBreak_memo(str, cut + 1, dict, dp);
				if (ans > 0) {
					break;
				}
			}
		}

		return dp[si] = ans;
	}
	public int wordBreak_tabu(String str, int SI, HashSet<String> dict, int[] dp) {
		int n = str.length();
		int maxLen = 0;
		for (String word : dict) {
			maxLen = Math.max(maxLen, word.length());
		}

		for (int si = n; si >= SI; si--) {
			if (si == n) {
				dp[si] = 1;
				continue;
			}

			int ans = 0;
			for (int cut = si; cut < n && cut - si + 1 <= maxLen; cut++) {
				if (dict.contains(str.substring(si, cut + 1))) {
					ans += dp[cut + 1]; // wordBreak_tabu(str, i + 1, dict, dp);
					if (ans > 0) {
						break;
					}
				}
			}

			dp[si] = ans;
			continue;
		}

		return dp[SI];
	}
	List<String> ans = new ArrayList<>();
	public void wordBreak_reverse_engineering(String str, int si, HashSet<String> dict, int[] dp, String sentence) {
		int n = str.length();
		if (si == n) {
			// ignore space at the end
			ans.add(sentence.substring(0, sentence.length() - 1));
			return;
		}

		for (int cut = si; cut <= n; cut++) {
			if (dp[cut] == 1 && dict.contains(str.substring(si, cut))) {
				wordBreak_reverse_engineering(str, cut, dict, dp, sentence + str.substring(si, cut) + " ");
			}
		}
	}
	public List<String> wordBreak(String str, List<String> wordDict) {
		HashSet<String> dict = new HashSet<>();
		for (String s : wordDict) {
			dict.add(s);
		}

		int n = str.length();
		int[] dp = new int[n + 1];
		Arrays.fill(dp, -1);
		// return wordBreak_memo(str, 0, dict, dp) == 1;
		// return wordBreak_tabu(str, 0, dict, dp) == 1;

		wordBreak_tabu(str, 0, dict, dp);
		// for (int d : dp) {
		// 	System.out.print(d + " ");
		// }

		if (dp[0] == 1) {
			wordBreak_reverse_engineering(str, 0, dict, dp, "");
		}

		return ans;
	}









	// ======================================== LIS SET ========================================









	// 300. Longest Increasing Subsequence
	// {0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15, 14}
	// faith - this method will return the length of the lis ending at ei including ei
	// T: O(n^2)
	public int lengthOfLIS_memo(int[] arr, int ei, int[] dp) {
		if (ei == 0) {
			return dp[ei] = 1;
		}

		if (dp[ei] != 0) {
			return dp[ei];
		}

		int maxLen = 1;
		for (int i = ei - 1; i >= 0; i--) {
			if (arr[i] < arr[ei]) {
				maxLen = Math.max(maxLen, lengthOfLIS_memo(arr, i, dp) + 1);
			}
		}

		return dp[ei] = maxLen;
	}
	public int lengthOfLIS(int[] arr) {
		int n = arr.length;
		int[] dp = new int[n];

		int lis = 0;
		for (int i = 0; i < n; i++) {
			lis = Math.max(lis, lengthOfLIS_memo(arr, i, dp));
		}
		return lis;
	}

	public int lengthOfLIS(int[] arr) {
		int n = arr.length;
		int[] dp = new int[n];

		int maxLen = 0;
		for (int i = 0; i < n; i++) {
			dp[i] = 1;
			for (int j = i - 1; j >= 0; j--) {
				if (arr[j] < arr[i]) {
					dp[i] = Math.max(dp[i], dp[j] + 1);
				}
			}
			maxLen = Math.max(maxLen, dp[i]);
		}
		return maxLen;
	}









	// Longest Bitonic subsequence
	// https://www.geeksforgeeks.org/problems/longest-bitonic-subsequence0824/1
	public static int lis_memo(int[] arr, int ei, int[] dp) {
		int n = arr.length;

		if (ei == 0) {
			return dp[ei] = 1;
		}

		if (dp[ei] != 0) {
			return dp[ei];
		}

		int maxLen = 1;
		for (int i = ei - 1; i >= 0; i--) {
			if (arr[i] < arr[ei]) {
				maxLen = Math.max(maxLen, lis_memo(arr, i, dp) + 1);
			}
		}

		return dp[ei] = maxLen;
	}
	public static int lds_memo(int[] arr, int si, int[] dp) {
		int n = arr.length;

		if (si == n - 1) {
			return dp[si] = 1;
		}

		if (dp[si] != 0) {
			return dp[si];
		}

		int maxLen = 1;
		for (int i = si + 1; i < n; i++) {
			if (arr[i] < arr[si]) {
				maxLen = Math.max(maxLen, lds_memo(arr, i, dp) + 1);
			}
		}

		return dp[si] = maxLen;
	}
	public static int LongestBitonicSequence(int n, int[] arr) {
		int[] lis = new int[n];
		int[] lds = new int[n];

		for (int i = 0; i < n; i++) {
			lis_memo(arr, i, lis);
		}

		for (int i = 0; i < n; i++) {
			lds_memo(arr, i, lds);
		}

		int lbs = 0;
		for (int i = 0; i < n; i++) {
			if (lis[i] > 1 && lds[i] > 1) {
				lbs = Math.max(lbs, lis[i] + lds[i] - 1);
			}
		}
		return lbs;
	}









	// Max Sum Increasing Subsequence
	// https://www.geeksforgeeks.org/problems/maximum-sum-increasing-subsequence4749/1
	// faith - this method returns me the maxSumIS ending at ei including ei
	public int maxSumIS_memo(int[] arr, int ei, int[] dp) {
		int n = arr.length;

		if (ei == 0) {
			return dp[ei] = arr[ei];
		}

		if (dp[ei] != 0) {
			return dp[ei];
		}

		int maxLen = arr[ei];
		for (int i = ei - 1; i >= 0; i--) {
			if (arr[i] < arr[ei]) {
				maxLen = Math.max(maxLen, maxSumIS_memo(arr, i, dp) + arr[ei]);
			}
		}

		return dp[ei] = maxLen;
	}
	public int maxSumIS(int arr[]) {
		int n = arr.length;
		int[] dp = new int[n];

		int lis = 0;
		for (int i = 0; i < n; i++) {
			lis = Math.max(lis, maxSumIS_memo(arr, i, dp));
		}
		return lis;
	}









	// Maximum Sum Bitonic Subsequence
	// https://www.geeksforgeeks.org/problems/maximum-sum-bitonic-subsequence1857/1
	public static int maxSumIS_memo(int[] arr, int ei, int[] dp) {
		int n = arr.length;

		if (ei == 0) {
			return dp[ei] = arr[ei];
		}

		if (dp[ei] != 0) {
			return dp[ei];
		}

		int maxLen = arr[ei];
		for (int i = ei - 1; i >= 0; i--) {
			if (arr[i] < arr[ei]) {
				maxLen = Math.max(maxLen, maxSumIS_memo(arr, i, dp) + arr[ei]);
			}
		}

		return dp[ei] = maxLen;
	}
	public static int maxSumDS_memo(int[] arr, int si, int[] dp) {
		int n = arr.length;

		if (si == n - 1) {
			return dp[si] = arr[si];
		}

		if (dp[si] != 0) {
			return dp[si];
		}

		int maxLen = arr[si];
		for (int i = si + 1; i < n; i++) {
			if (arr[i] < arr[si]) {
				maxLen = Math.max(maxLen, maxSumDS_memo(arr, i, dp) + arr[si]);
			}
		}

		return dp[si] = maxLen;
	}
	public int maxSumBS(int[] arr) {
		int n = arr.length;
		int[] lis = new int[n];
		int[] lds = new int[n];

		for (int i = 0; i < n; i++) {
			maxSumIS_memo(arr, i, lis);
		}

		for (int i = 0; i < n; i++) {
			maxSumDS_memo(arr, i, lds);
		}

		int lbs = 0;
		for (int i = 0; i < n; i++) {
			if (lis[i] > 1 && lds[i] > 1) {
				lbs = Math.max(lbs, lis[i] + lds[i] - arr[i]);
			}
		}
		return lbs;
	}









	// Minimum number of deletions to make a sorted sequence
	// https://www.geeksforgeeks.org/problems/minimum-number-of-deletions-to-make-a-sorted-sequence3248/1
	public int minDeletions(int[] arr, int ei, int[] dp) {
		int n = arr.length;

		if (ei == 0) {
			return dp[ei] = 1;
		}

		if (dp[ei] != 0) {
			return dp[ei];
		}

		int maxLen = 1;
		for (int i = ei - 1; i >= 0; i--) {
			if (arr[i] < arr[ei]) {
				maxLen = Math.max(maxLen, minDeletions(arr, i, dp) + 1);
			}
		}

		return dp[ei] = maxLen;
	}
	public int minDeletions(int arr[], int n) {
		int[] dp = new int[n];

		int lis = 0;
		for (int i = 0; i < n; i++) {
			lis = Math.max(lis, minDeletions(arr, i, dp));
		}

		return n - lis;
	}









	// 673. Number of Longest Increasing Subsequence
	// faith - this method will return me the
	// lengthOflis ending at ei including ei
	// & countOfLis ending at ei including ei
	// MEMORIZE - use 2 diff array instead of 1 2D array
	public int findNumberOfLIS(int[] arr) {
		int n = arr.length;
		int[] length = new int[n];
		int[] count = new int[n];

		int maxLength = 0;
		int maxCount = 0;
		for (int i = 0; i < n; i++) {
			length[i] = 1;
			count[i] = 1;
			for (int j = i - 1; j >= 0; j--) {
				if (arr[j] < arr[i]) {
					if (length[j] + 1 > length[i]) {
						length[i] = length[j] + 1;
						count[i] = count[j];
					} else if (length[j] + 1 == length[i]) {
						count[i] += count[j];
					}
				}
			}

			if (length[i] > maxLength) {
				maxLength = length[i];
				maxCount = count[i];
			} else if (length[i] == maxLength) {
				maxCount += count[i];
			}
		}

		return maxCount;
	}

	public int[] findNumberOfLIS_memo(int[] arr, int ei, int[][] dp) {
		// System.out.println("At index " + ei + ", dp[ei] = " + (dp[ei] == null ? "null" : Arrays.toString(dp[ei])));
		int n = arr.length;

		if (ei == 0) {
			return dp[ei] = new int[] {1, 1};
		}

		if (dp[ei] != null) {
			return dp[ei];
		}

		int[] myAns = new int[] {1, 1};
		for (int i = ei - 1; i >= 0; i--) {
			if (arr[i] < arr[ei]) {
				int[] recAns = findNumberOfLIS_memo(arr, i, dp);

				if (recAns[0] + 1 > myAns[0]) {
					myAns[0] = recAns[0] + 1;
					myAns[1] = recAns[1];
				} else if (recAns[0] + 1 == myAns[0]) {
					myAns[1] += recAns[1];
				}
			}
		}

		return dp[ei] = myAns;
	}
	public int findNumberOfLIS(int[] arr) {
		int n = arr.length;
		int[][] dp = new int[n][]; // {length of lis, count of lis} ending at ei

		int lengthOfLIS = 0;
		int countOfLis = 0;
		for (int i = 0; i < n; i++) {
			int[] smallAns = findNumberOfLIS_memo(arr, i, dp);
			if (smallAns[0] > lengthOfLIS) {
				lengthOfLIS = smallAns[0];
				countOfLis = smallAns[1];
			} else if (smallAns[0] == lengthOfLIS) {
				countOfLis += smallAns[1];
			}
		}

		return countOfLis;
	}









	// Building Bridges
	// https://www.geeksforgeeks.org/dynamic-programming-building-bridges/
	// MEMORIZE
	public static int maximumBridges(int[][] arr) {
		// sort the bridges by their north-coordinates
		// Why? So that we only need to worry about the south end
		Arrays.sort(arr, (a, b) -> {
			return a[0] - b[0];
		});

		// find the LIS based on the south-coordinates of the bridges
		// Why?
		// After sorting by north bank, the north ends are already in correct order.
		// Now, we only need to ensure south ends are in increasing order to avoid bridge crossing.
		// Finding LIS (Longest Increasing Subsequence) on south coordinates ensures:
		//   No two bridges cross (since south[i] < south[j] for i < j).
		//   We get the maximum number of non-overlapping bridges.
		int n = arr.length;
		int[] dp = new int[n];
		int maxLen = 0;

		for (int i = 0; i < n; i++) {
			dp[i] = 1;
			for (int j = i - 1; j >= 0; j--) {
				if (arr[j][1] < arr[i][1]) {
					dp[i] = Math.max(dp[i], dp[j] + 1);
				}
				// if (arr[j][1] < arr[i][1]) {
				// this makes sure that the south end does not overlap
				// if you don't want the north end to overlap as well
				// if (arr[j][1] < arr[i][1] && arr[j][0] < arr[i][0]) {
			}
			maxLen = Math.max(dp[i], maxLen);
		}

		return maxLen;
	}









	// 354. Russian Doll Envelopes
	// MEMORIZE - sort by width then height
	// TLE - optimize LIS from O(n^2) -> O(nlogn) using binary search
	public int maxEnvelopes(int[][] arr) {
		// sort the envelopes by their width
		Arrays.sort(arr, (a, b) -> {
			if (a[0] != b[0]) {
				return a[0] - b[0]; // increasing order of width
			} else {
				return b[1] - a[1]; // decreasing order of height
			}
		});

		// find the LIS based on the height of the envelopes
		int n = arr.length;
		int[] dp = new int[n];
		int maxLen = 0;
		for (int i = 0; i < n; i++) {
			dp[i] = 1;
			for (int j = i - 1; j >= 0; j--) {
				if (arr[j][1] < arr[i][1]) {
					dp[i] = Math.max(dp[i], dp[j] + 1);
				}
			}
			maxLen = Math.max(dp[i], maxLen);
		}

		return maxLen;
	}









	// ============================== TARGET SET ========================================









	// Infinite Supply
	public static int permutation_memo(int[] arr, int tar, int[] dp) {
		if (tar == 0) {
			return dp[tar] = 1;
		}

		if (dp[tar] != -1) {
			return dp[tar];
		}

		int count = 0;
		for (int ele : arr) {
			if (ele <= tar) {
				count += permutation_memo(arr, tar - ele, dp);
			}
		}

		return dp[tar] = count;
	}
	public static int permutation_tabu(int[] arr, int TAR, int[] dp) {
		for (int tar = 0; tar <= TAR; tar++) {
			if (tar == 0) {
				dp[tar] = 1;
				continue;
			}

			int count = 0;
			for (int ele : arr) {
				if (ele <= tar) {
					count += dp[tar - ele]; // permutation_tabu(arr, tar - ele, dp);
				}
			}

			dp[tar] = count;
			continue;
		}

		return dp[TAR];
	}
	public static int permutation(int[] arr, int tar) {
		int n = arr.length;
		int[] dp = new int[tar + 1];
		Arrays.fill(dp, -1);

		// int count = permutation_memo(arr, tar, dp);
		int count = permutation_tabu(arr, tar, dp);
		display1D(dp);
		return count;
	}









	// Infinite Supply
	// Combination = Permutaton, but you can't go back, similar to subseq
	// {2,3,5,7} -> if you have picked 5 you can't pick 2 / 3
	public static int combination_memo(int[] arr, int tar, int idx, int[][] dp) {
		int n = arr.length;
		if (tar == 0) {
			return dp[tar][idx] = 1;
		}

		if (dp[tar][idx] != -1) {
			return dp[tar][idx];
		}

		int count = 0;
		for (int i = idx; i < n; i++) {
			if (arr[i] <= tar) {
				count += combination_memo(arr, tar - arr[i], i, dp);
			}
		}

		return dp[tar][idx] = count;
	}
	// top -> bottom
	// right -> left
	// T: O(n * tar)
	// S: O(n * tar)
	public static int combination_tabu(int[] arr, int TAR, int IDX, int[][] dp) {
		int n = arr.length;
		for (int tar = 0; tar <= TAR; tar++) {
			for (int idx = n - 1; idx >= IDX; idx--) {
				if (tar == 0) {
					dp[tar][idx] = 1;
					continue;
				}

				int count = 0;
				for (int i = idx; i < n; i++) {
					if (arr[i] <= tar) {
						count += dp[tar - arr[i]][i]; // combination_tabu(arr, tar - arr[i], i, dp);
					}
				}

				dp[tar][idx] = count;
				continue;
			}
		}

		return dp[TAR][IDX];
	}
	// MEMORIZE
	// the coe principle still remains the same - we will be following forward order, and we wont ask for prev ele
	// loop over elements first, then over target (left to right)
	// this avoids duplicate permutations and ensures combination count
	// T: O(n * tar)
	// S: O(tar)
	public static int combination_opti(int[] arr, int TAR, int[] dp) {
		int n = arr.length;
		dp[0] = 1;
		for (int ele : arr) {
			for (int tar = ele; tar <= TAR; tar++) {
				dp[tar] += dp[tar - ele];
			}
		}

		return dp[TAR];
	}
	public static int combination(int[] arr, int tar) {
		int n = arr.length;
		int[][] dp = new int[tar + 1][n];
		for (int[] d : dp) {
			Arrays.fill(d, -1);
		}

		// int count = combination_memo(arr, tar, 0, dp);
		// int count = combination_tabu(arr, tar, 0, dp);
		// display2D(dp);

		int[] dp1D = new int[tar + 1];
		// initialize dp with 0 not -1
		int count = combination_opti(arr, tar, dp1D);
		display1D(dp1D);

		return count;
	}









	// 377. Combination Sum IV
	public static int permutation_memo(int[] arr, int tar, int[] dp) {
		if (tar == 0) {
			return dp[tar] = 1;
		}

		if (dp[tar] != -1) {
			return dp[tar];
		}

		int count = 0;
		for (int ele : arr) {
			if (ele <= tar) {
				count += permutation_memo(arr, tar - ele, dp);
			}
		}

		return dp[tar] = count;
	}
	public static int permutation_tabu(int[] arr, int TAR, int[] dp) {
		for (int tar = 0; tar <= TAR; tar++) {
			if (tar == 0) {
				dp[tar] = 1;
				continue;
			}

			int count = 0;
			for (int ele : arr) {
				if (ele <= tar) {
					count += dp[tar - ele];
				}
			}

			dp[tar] = count;
			continue;
		}

		return dp[TAR];
	}
	public int combinationSum4(int[] arr, int tar) {
		int n = arr.length;
		int[] dp = new int[tar + 1];
		Arrays.fill(dp, -1);
		return permutation_memo(arr, tar, dp);
		// return permutation_tabu(arr, tar, dp);
	}









	// 322. Coin Change
	public static int permutation_memo(int[] arr, int TAR, int[] dp) {
		// TODO
	}
	public static int permutation_tabu(int[] arr, int TAR, int[] dp) {
		dp[0] = 0;
		for (int tar = 1; tar <= TAR; tar++) {
			for (int ele : arr) {
				if (ele <= tar) {
					dp[tar] = Math.min(dp[tar], dp[tar - ele] + 1);
				}
			}
		}

		return dp[TAR];
	}
	public int coinChange(int[] arr, int tar) {
		int[] dp = new int[tar + 1];
		Arrays.fill(dp, (int)1e9);
		int count = permutation_tabu(arr, tar, dp);
		int count = permutation_memo(arr, tar, dp);
		return count == (int)1e9 ? -1 : count;
	}









	// Subset Sum Problem
	// https://www.geeksforgeeks.org/problems/subset-sum-problem-1611555638/1
	// Limited Suppply
	// Subset = Subseq -> every coin has a choice it may / may not come
	static Boolean isSubsetSum_recu(int arr[], int tar, int idx) {
		int n = arr.length;
		if (tar == 0) {
			return true;
		}
		if (idx == n) {
			return false;
		}

		Boolean ans = false;
		if (arr[idx] <= tar) {
			ans = ans || isSubsetSum_recu(arr, tar - arr[idx], idx + 1); // i want to come
		}
		ans = ans || isSubsetSum_recu(arr, tar, idx + 1); // i don't want to come

		return ans;
	}
	static Boolean isSubsetSum_memo(int arr[], int tar, int idx) {
		// TODO
	}
	static Boolean isSubsetSum_tabu(int arr[], int tar, int idx) {
		// TODO
	}
	static Boolean isSubsetSum_reverse_engineering(int arr[], int tar, int idx) {
		// TODO
	}
	static Boolean isSubsetSum(int arr[], int tar) {
		return isSubsetSum_recu(arr, tar, 0);
		// return isSubsetSum_memo(arr, tar, 0);
		// return isSubsetSum_tabu(arr, tar, 0);
		// isSubsetSum_reverse_engineering(arr, tar, 0);
	}









	// 0 - 1 Knapsack Problem
	// https://www.geeksforgeeks.org/problems/0-1-knapsack-problem0945/1
	// Limited Supply
	static int knapsack_memo(int[] val, int[] wt, int idx, int capacity, int[][] dp) {
		int n = val.length;
		if (capacity == 0) {
			return 0;
		}
		if (idx == n) {
			return 0;
		}


		if (dp[idx][capacity] != -1) {
			return dp[idx][capacity];
		}

		int maxVal = 0;
		if (wt[idx] <= capacity) {
			maxVal = Math.max(maxVal, val[idx] + knapsack_memo(val, wt, idx + 1, capacity - wt[idx], dp)); // include
		}
		maxVal = Math.max(maxVal, knapsack_memo(val, wt, idx + 1, capacity, dp)); // exclude

		return dp[idx][capacity] = maxVal;
	}
	// bottom -> top
	// right -> left
	static int knapsack_tabu(int[] val, int[] wt, int IDX, int CAPACITY, int[][] dp) {
		int n = val.length;
		for (int idx = n; idx >= IDX; idx--) {
			for (int capacity = 0; capacity <= CAPACITY; capacity++) {
				if (capacity == 0) {
					dp[idx][capacity] = 0;
					continue;
				}
				if (idx == n) {
					dp[idx][capacity] = 0;
					continue;
				}

				int maxVal = 0;
				if (wt[idx] <= capacity) {
					maxVal = Math.max(maxVal, val[idx] + dp[idx + 1][capacity - wt[idx]]); // include
				}
				maxVal = Math.max(maxVal, dp[idx + 1][capacity]); // exclude

				dp[idx][capacity] = maxVal;
			}
		}

		return dp[IDX][CAPACITY];
	}
	static int knapsack(int capacity, int[] val, int[] wt) {
		int n = wt.length;
		int[][] dp = new int[n + 1][capacity + 1];
		for (int[] d : dp) {
			for (int i = 0 ; i <= capacity; i++) {
				d[i] = -1;
			}
		}

		// return knapsack_memo(val, wt, 0, capacity, dp);
		return knapsack_tabu(val, wt, 0, capacity, dp);
	}










	// Unbounded Knapsack (Repetition of items allowed)
	// Knapsack with Duplicate Items
	// https://www.geeksforgeeks.org/problems/knapsack-with-duplicate-items4201/1
	// Infinite Supply
	static int permutation_memo(int val[], int wt[], int capacity, int[] dp) {
		if (capacity == 0) {
			return dp[capacity] = 0;
		}

		if (dp[capacity] != -1) {
			return dp[capacity];
		}

		int maxVal = 0;
		for (int i = 0; i < wt.length; i++) {
			if (wt[i] <= capacity) {
				maxVal = Math.max(maxVal, val[i] + permutation_memo(val, wt, capacity - wt[i], dp));
			}
		}

		return dp[capacity] = maxVal;
	}
	static int knapSack(int val[], int wt[], int capacity) {
		int[] dp = new int[capacity + 1];
		Arrays.fill(dp, -1);
		return permutation_memo(val, wt, capacity, dp);
	}









	// Find number of solutions of a linear equation of n variables
	// https://www.geeksforgeeks.org/find-number-of-solutions-of-a-linear-equation-of-n-variables/
	// Infinite Supply
	public static int combination_memo(int[] arr, int tar, int idx, int[][] dp) {
		int n = arr.length;
		if (tar == 0) {
			return dp[tar][idx] = 1;
		}

		if (dp[tar][idx] != -1) {
			return dp[tar][idx];
		}

		int count = 0;
		for (int i = idx; i < n; i++) {
			if (arr[i] <= tar) {
				count += combination_memo(arr, tar - arr[i], i, dp);
			}
		}

		return dp[tar][idx] = count;
	}
	static int countSol(int coeff[], int rhs) {
		int n = coeff.length;
		int[][] dp = new int[rhs + 1][n + 1];
		for (int[] d : dp) {
			Arrays.fill(d, -1);
		}

		return combination_memo(coeff, rhs, 0, dp);
	}









	// 416. Partition Equal Subset Sum
	// Limited Supply
	// 0 -> not possible
	// 1 -> possible
	// -1 -> not explored
	public int canPartition_memo(int[] arr, int idx, int tar, int[][] dp) {
		int n = arr.length;
		if (tar == 0) {
			return dp[idx][tar] = 1;
		}

		if (idx == n) {
			return dp[idx][tar] = 0;
		}

		if (dp[idx][tar] != -1) {
			return dp[idx][tar];
		}

		int ans = 0;
		ans += canPartition_memo(arr, idx + 1, tar, dp); // i don't want to come
		if (ans < 1 && arr[idx] <= tar) {
			ans += canPartition_memo(arr, idx + 1, tar - arr[idx], dp); // i want to come
		}

		return dp[idx][tar] = ans;
	}
	public boolean canPartition(int[] arr) {
		int n = arr.length;

		int tar = 0;
		for (int ele : arr) {
			tar += ele;
		}
		if ((tar & 1) == 1) { // odd tar
			return false;
		}
		tar = tar >> 1;

		int[][] dp = new int[n + 1][tar + 1];
		for (int [] d : dp) {
			Arrays.fill(d, -1);
		}

		return canPartition_memo(arr, 0, tar, dp) == 1;
	}









	// 494. Target Sum
	// every ele has choice to be +ve or -ve
	// MEMORIZE
	public int findTargetSumWays_recu(int[] arr, int idx, int tar) {
		int n = arr.length;
		if (idx == n) {
			if (tar == 0) {
				return 1;
			} else {
				return 0;
			}
		}

		int count = 0;
		count += findTargetSumWays_recu(arr, idx + 1, tar - arr[idx]); // i want to be positive
		count += findTargetSumWays_recu(arr, idx + 1, tar + arr[idx]); // i want to be egative

		return count;
	}
	int sum = 0;
	int offset = 0;
	// objective - mere ko target ko 0 pe leke jana hai
	// by convention if you want to know how many ways are there to achieve target 5 you will look into dp[5]
	// but if you want to know how many ways are there to achieve target -4 you can't look into dp[-4] INVALID
	// the problem is target can be -ve but the array index can't be -ve
	// we use an offset to convert -ve targets to valid indices
	// remember, target did not changed just the place(index) to store the answer changed
	public int findTargetSumWays_memo(int[] arr, int idx, int tar, int[][] dp) {
		int n = arr.length;
		if (idx == n) {
			return dp[idx][tar + offset] = (tar == 0 ? 1 : 0);
		}

		if (dp[idx][tar + offset] != -1) {
			return dp[idx][tar + offset];
		}

		int count = 0;
		if (-sum <= tar - (arr[idx]) && tar - (arr[idx]) <= sum) {
			// if tar goes beyond sum (say sum + x) there is no way we can get back to 0,
			// because even if all the rest of the ele are -ve we will get -sum so (tar = sum + x) -sum will never becoe 0
			// plus we don't have index to save (tar = sum + x) as well
			count += findTargetSumWays_memo(arr, idx + 1, tar - (arr[idx]), dp); // i want to be positive
		}
		if (-sum <= tar - (-arr[idx]) && tar - (-arr[idx]) <= sum) {
			count += findTargetSumWays_memo(arr, idx + 1, tar - (-arr[idx]), dp); // i want to be egative
		}

		return dp[idx][tar + offset] = count;
	}
	// always tar ko 0 pe le jana is not a good idea
	// sometimes currSum ko tar pe le jana is a better idea, clean code
	public int findTargetSumWays_memo(int[] arr, int idx, int currSum, int tar, int[][] dp) {
		int n = arr.length;
		if (idx == n) {
			return dp[idx][currSum + offset] = (currSum == tar ? 1 : 0);
		}

		if (dp[idx][currSum + offset] != -1) {
			return dp[idx][currSum + offset];
		}

		int count = 0;
		// currSum can never go beyond the range, so no need for check
		count += findTargetSumWays_memo(arr, idx + 1, currSum + arr[idx], tar, dp); // i want to be positive
		count += findTargetSumWays_memo(arr, idx + 1, currSum - arr[idx], tar, dp); // i want to be positive

		return dp[idx][currSum + offset] = count;
	}
	public int findTargetSumWays(int[] arr, int tar) {
		int n = arr.length;
		// return findTargetSumWays_recu(arr, 0, tar);

		for (int ele : arr) {
			sum += ele;
		}
		if (tar < -sum || tar > sum) {
			return 0;
		}
		offset = sum;

		int[][] dp = new int[n + 1][2 * sum + 1];
		for (int[] d : dp) {
			Arrays.fill(d, -1);
		}

		// return findTargetSumWays_memo(arr, 0, tar, dp);
		return findTargetSumWays_memo(arr, 0, 0, tar, dp);
	}









	// 698. Partition to K Equal Sum Subsets
	// HARD
	// MEMORIZE
	public boolean canPartitionKSubsets_recu(int[] arr, int idx, int currSum, int target, int k, boolean[] vis) {
		if (k == 0) return true;
		if (currSum > target) return false;
		if (currSum == target)
			return canPartitionKSubsets_recu(arr, 0, 0, target, k - 1, vis);

		for (int i = idx; i < arr.length; i++) {
			if (!vis[i]) {
				vis[i] = true;
				if (canPartitionKSubsets_recu(arr, i + 1, currSum + arr[i], target, k, vis))
					return true;
				vis[i] = false;
			}
		}
		return false;
	}
	public boolean canPartitionKSubsets(int[] arr, int k) {
		int n = arr.length;
		int sum = 0;
		int maxEle = 0;
		for (int ele : arr) {
			sum += ele;
			maxEle = Math.max(maxEle, ele);
		}
		int tar = sum / k;
		if ((sum % k) != 0 || maxEle > tar) {
			return false;
		}

		boolean[] vis = new boolean[n];
		return canPartitionKSubsets_recu(arr, 0, 0, tar, k, vis);
	}









	// ======================================== CUT SET ========================================









	// Matrix Chain Multiplication
	// https://www.geeksforgeeks.org/problems/matrix-chain-multiplication0303/1
	static int matrixMultiplication_memo(int arr[], int si, int ei, int[][] dp) {
		int n = arr.length;
		if (si + 1 == ei) {
			return dp[si][ei] = 0;
		}

		if (dp[si][ei] != -1) {
			return dp[si][ei];
		}

		int minCost = (int)1e9;
		for (int cut = si + 1 ; cut <= ei - 1; cut++) {
			int leftCost = matrixMultiplication_memo(arr, si, cut, dp);
			int rightCost = matrixMultiplication_memo(arr, cut, ei, dp);
			int myCost = leftCost + rightCost + arr[si] * arr[cut] * arr[ei];
			minCost = Math.min(minCost, myCost);
		}

		return dp[si][ei] = minCost;
	}
	// observation: gap strategy
	static int matrixMultiplication_tabu(int arr[], int SI, int EI, int[][] dp) {
		int n = arr.length;
		for (int gap = 0; gap < n; gap++) {
			for (int si = 0, ei = gap; ei < n; si++, ei++) {
				if (si + 1 == ei) {
					dp[si][ei] = 0;
					continue;
				}

				int minCost = (int)1e9;
				for (int cut = si + 1 ; cut <= ei - 1; cut++) {
					int leftCost = dp[si][cut]; // matrixMultiplication_tabu(arr, si, cut, dp);
					int rightCost =  dp[cut][ei]; // matrixMultiplication_tabu(arr, cut, ei, dp);
					int myCost = leftCost + rightCost + arr[si] * arr[cut] * arr[ei];
					minCost = Math.min(minCost, myCost);
				}

				dp[si][ei] = minCost;
				continue;
			}
		}

		return dp[SI][EI];
	}
	static int matrixMultiplication(int arr[]) {
		int n = arr.length;
		int[][] dp = new int[n][n];
		for (int[]d : dp) {
			Arrays.fill(d, -1);
		}
		// return matrixMultiplication_memo(arr, 0, n - 1, dp);
		return matrixMultiplication_tabu(arr, 0, n - 1, dp);
	}









	// Brackets in Matrix Chain Multiplication
	// just keep storing the string dp every time u store the int dp
	static int matrixChainOrder_memo(int arr[], int si, int ei, int[][] dp, String[][] sdp) {
		int n = arr.length;
		if (si + 1 == ei) {
			sdp[si][ei] = (char)(si + 'A') + "";
			return dp[si][ei] = 0;
		}

		if (dp[si][ei] != -1) {
			return dp[si][ei];
		}

		int minCost = (int)1e9;
		for (int cut = si + 1 ; cut <= ei - 1; cut++) {
			int leftCost = matrixChainOrder_memo(arr, si, cut, dp, sdp);
			int rightCost = matrixChainOrder_memo(arr, cut, ei, dp, sdp);
			int myCost = leftCost + rightCost + arr[si] * arr[cut] * arr[ei];
			if (myCost < minCost) {
				minCost = myCost;
				sdp[si][ei] = "(" + sdp[si][cut] + sdp[cut][ei] + ")";
			}
		}

		return dp[si][ei] = minCost;
	}
	// observation: gap strategy
	static int matrixChainOrder_tabu(int arr[], int SI, int EI, int[][] dp, String[][] sdp) {
		int n = arr.length;
		for (int gap = 0; gap < n; gap++) {
			for (int si = 0, ei = gap; ei < n; si++, ei++) {
				if (si + 1 == ei) {
					sdp[si][ei] = (char)(si + 'A') + "";
					dp[si][ei] = 0;
					continue;
				}

				int minCost = (int)1e9;
				for (int cut = si + 1 ; cut <= ei - 1; cut++) {
					int leftCost = dp[si][cut]; // matrixChainOrder_tabu(arr, si, cut, dp, sdp);
					int rightCost = dp[cut][ei]; // matrixChainOrder_tabu(arr, cut, ei, dp, sdp);
					int myCost = leftCost + rightCost + arr[si] * arr[cut] * arr[ei];
					if (myCost < minCost) {
						minCost = myCost;
						sdp[si][ei] = "(" + sdp[si][cut] + sdp[cut][ei] + ")";
					}
				}

				dp[si][ei] = minCost;
				continue;
			}
		}

		return dp[SI][EI];
	}
	static String matrixChainOrder(int arr[]) {
		int n = arr.length;
		int[][] dp = new int[n][n];
		for (int[]d : dp) {
			Arrays.fill(d, -1);
		}
		String[][] sdp = new String[n][n];
		// matrixChainOrder_memo(arr, 0, n - 1, dp, sdp);
		matrixChainOrder_tabu(arr, 0, n - 1, dp, sdp);
		return sdp[0][n - 1];
	}









	// Minimum and Maximum values of an expression with * and +
	// https://www.geeksforgeeks.org/minimum-maximum-values-expression/
	// HARD
	// MEMORIZE
	static class Pair {
		int minVal;
		int maxVal;
		Pair() {

		}
		Pair(int minVal, int maxVal) {
			this.minVal = minVal;
			this.maxVal = maxVal;
		}
	}
	// if op == '-' then we have to evaluate all 4 possibilities for min max
	// min can bwe l.min op r.min / l.min op r.max / l.max op r.min / l.max op r.max
	static Pair evaluate(Pair lhs, Pair rhs, char op) {
		Pair self = new Pair();
		if (op == '+') {
			self.minVal = lhs.minVal + rhs.minVal;
			self.maxVal = lhs.maxVal + rhs.maxVal;
		} else {
			self.minVal = lhs.minVal * rhs.minVal;
			self.maxVal = lhs.maxVal * rhs.maxVal;
		}

		return self;
	}
	static Pair printMinAndMaxValueOfExp_memo(String str, int si, int ei, Pair[][] dp) {
		int n = str.length();
		if (si == ei) {
			return dp[si][ei] = new Pair(str.charAt(si) - '0', str.charAt(si) - '0');
		}

		if (dp[si][ei] != null) {
			return dp[si][ei];
		}

		Pair myAns = new Pair((int)1e9, -(int)1e9);
		for (int cut = si + 1; cut <= ei - 1; cut += 2) {
			Pair lhs = printMinAndMaxValueOfExp_memo(str, si, cut - 1, dp);
			Pair rhs = printMinAndMaxValueOfExp_memo(str, cut + 1, ei, dp);
			Pair self = evaluate(lhs, rhs, str.charAt(cut));

			myAns.minVal = Math.min(myAns.minVal, self.minVal);
			myAns.maxVal = Math.max(myAns.maxVal, self.maxVal);
		}

		return dp[si][ei] = myAns;
	}
	static void printMinAndMaxValueOfExp(String str) {
		int n = str.length();
		Pair[][] dp = new Pair[n][n];

		Pair ans = printMinAndMaxValueOfExp_memo(str, 0, n - 1, dp);
		System.out.println("minVal: " + ans.minVal);
		System.out.println("maxVal: " + ans.maxVal);
	}
	public static void main(String[] args) {
		System.out.println("Try programiz.pro");
		printMinAndMaxValueOfExp("1+2*3+4*5");
	}









	// 312. Burst Balloons
	// MEMORIZE

	// BRUTE FORCE: TLE / SLE
	// 1. Pick a balloon to burst first
	// 2. Form a new array without that balloon
	// 3. Recursively burst the remaining ones
	// Very inefficient due to array copying at every step

	// OPTIMIZATION:
	// 1. Pick a balloon to burst last in a given subarray [si, ei]
	// 2. Recursively burst all balloons in the left & right subarrays
	// 3. Burst the picked balloon last, using the coins from both sides

	// WHY BURST THE PICKED BALLOON LAST:
	// Bursting the picked balloon first breaks the structure: you'd need to create and manage a new array every time => BRUTE FORCE
	// If we burst it first, the left & right subarrays wouldn’t know what their actual boundaries are.
	// By bursting the balloon last, we ensure both left and right subarrays are already solved, allowing us to safely calculate the result with known boundaries.
	public int getValue(int[] arr, int idx) {
		int n = arr.length;
		if (idx < 0 || idx > n - 1) {
			return 1;
		}
		return arr[idx];
	}
	public int maxCoins_memo(int[] arr, int si, int ei, int[][] dp) {
		int n = arr.length;
		if (si == ei) {
			return dp[si][ei] = getValue(arr, si - 1) * arr[si] * getValue(arr, ei + 1);
		}

		if (dp[si][ei] != -1) {
			return dp[si][ei];
		}

		int maxCoins = 0;
		int myCoins = 0;
		for (int cut = si; cut <= ei; cut++) {
			int lhs = 0;
			if (si <= cut - 1) {
				lhs = maxCoins_memo(arr, si , cut - 1, dp);
			}
			int rhs = 0;
			if (cut + 1 <= ei) {
				rhs = maxCoins_memo(arr, cut + 1 , ei, dp);
			}
			int self = getValue(arr, si - 1) * arr[cut] * getValue(arr, ei + 1);
			myCoins = lhs + self + rhs;

			maxCoins = Math.max(maxCoins, myCoins);
		}

		return dp[si][ei] = maxCoins;
	}
	public int maxCoins(int[] arr) {
		int n = arr.length;
		int[][] dp = new int[n][n];
		for (int[] d : dp) {
			Arrays.fill(d, -1);
		}
		int ans = maxCoins_memo(arr, 0, n - 1, dp);
		// display2D(dp);
		return ans;
	}









	// Boolean Parenthesization
	// https://www.geeksforgeeks.org/problems/boolean-parenthesization5610/1
	// faith - this method will return me the no of ways we can parenthesize the expression so that the value of expression evaluates to true & false
	static class Pair {
		int trueCount = 0;
		int falseCount = 0;
		Pair() {

		}
		Pair(int count) {
			this.trueCount = count;
			this.trueCount = count;
		}
		Pair(int trueCount, int falseCount) {
			this.trueCount = trueCount;
			this.falseCount = falseCount;
		}
	}
	static Pair evaluate(Pair lhs, Pair rhs, char op) {
		Pair ans = new Pair();

		int trueTrueCount = lhs.trueCount * rhs.trueCount;
		int trueFalseCount = lhs.trueCount * rhs.falseCount;
		int falseTrueCount = lhs.falseCount * rhs.trueCount;
		int falseFalseCount = lhs.falseCount * rhs.falseCount;
		if (op == '&') {
			ans.trueCount = trueTrueCount;
			ans.falseCount = trueFalseCount + falseTrueCount + falseFalseCount;
		} else if (op == '|') {
			ans.trueCount = trueTrueCount + trueFalseCount + falseTrueCount;
			ans.falseCount = falseFalseCount;
		} else if (op == '^') {
			ans.trueCount = trueFalseCount + falseTrueCount;
			ans.falseCount = trueTrueCount + falseFalseCount;
		}

		return ans;
	}
	static Pair countWays_memo(String str, int si, int ei, Pair[][] dp) {
		int n = str.length();
		if (si == ei) {
			char ch = str.charAt(si);
			int trueCount = ch == 'T' ? 1 : 0;
			int falseCount = ch == 'F' ? 1 : 0;
			return dp[si][ei] = new Pair(trueCount, falseCount);
		}

		if (dp[si][ei] != null) {
			return dp[si][ei];
		}

		Pair myAns = new Pair();
		for (int cut = si + 1; cut <= ei - 1; cut += 2) {
			Pair lhs = countWays_memo(str , si, cut - 1, dp);
			Pair rhs = countWays_memo(str , cut + 1, ei, dp);

			Pair self = evaluate(lhs, rhs, str.charAt(cut));

			myAns.trueCount += self.trueCount;
			myAns.falseCount += self.falseCount;
		}

		return dp[si][ei] = myAns;
	}
	static int countWays(String str) {
		int n = str.length();
		Pair[][] dp = new Pair[n][n];
		Pair ans = countWays_memo(str, 0, n - 1, dp);
		return ans.trueCount;
	}









	// Optimal binary search tree
	// MEMORIZE
	// https://www.geeksforgeeks.org/problems/optimal-binary-search-tree2214/1
	// this method will return the minimum search cost for keys in the range [si, ei]
	// T: O(n^4)
	static int optimalSearchTree_memo(int keys[], int freq[], int n, int level, int si, int ei, int[][][] dp) {
		if (si == ei) {
			return dp[si][ei][level] = freq[si] * level;
		}

		if (dp[si][ei][level] != -1) {
			return dp[si][ei][level];
		}

		int minCost = (int)1e9;
		for (int cut = si ; cut <= ei; cut++) { // try each possible node as the root of the current subtree, and recursively calculate the cost of the left and right subtrees
			int leftCost = (si <= cut - 1) ? optimalSearchTree_memo(keys, freq, n, level + 1, si, cut - 1, dp) : 0;
			int rightCost = (cut + 1 <= ei) ? optimalSearchTree_memo(keys, freq, n, level + 1, cut + 1, ei, dp) : 0;

			int currentCost = freq[cut] * level + leftCost + rightCost;
			minCost = Math.min(minCost, currentCost);
		}

		return dp[si][ei][level] = minCost;
	}
	// T: O(n^3)
	// Why This Works:
	// sum handles level effects: The sum variable effectively captures the contribution of the current depth. By adding the sum of all frequencies in the current range, we're accounting for the fact that each node's depth increases by 1 when it's placed under the chosen root
	// relative vs absolute levels: The first approach tracks absolute levels, but what matters is the relative increase in levels as we build the tree. The sum of frequencies in a range, combined with optimal costs of subtrees, implicitly handles this
	static int optimalSearchTree_memo(int keys[], int freq[], int si, int ei, int[][] dp) {
		if (si == ei) {
			return dp[si][ei] = freq[si];
		}

		if (dp[si][ei] != -1) {
			return dp[si][ei];
		}

		int sum = 0;
		for (int i = si; i <= ei; i++) {
			sum += freq[i];
		}
		int minCost = (int)1e9;
		for (int cut = si ; cut <= ei; cut++) { // try each possible node as the root of the current subtree, and recursively calculate the cost of the left and right subtrees
			int leftCost = (si <= cut - 1) ? optimalSearchTree_memo(keys, freq, si, cut - 1, dp) : 0;
			int rightCost = (cut + 1 <= ei) ? optimalSearchTree_memo(keys, freq, cut + 1, ei, dp) : 0;

			int currentCost = sum + leftCost + rightCost;
			minCost = Math.min(minCost, currentCost);
		}

		return dp[si][ei] = minCost;
	}
	static int optimalSearchTree(int keys[], int freq[], int n) {
		// freq[] is already sorted
		// int[][][] dp = new int[n][n][n + 1];
		// for (int[][] d1 : dp) {
		// 	for (int[]d2 : d1) {
		// 		Arrays.fill(d2, -1);
		// 	}
		// }
		// return optimalSearchTree_memo(keys, freq, n, 1, 0, n - 1, dp);

		int[][] dp = new int[n][n];
		for (int[]d : dp) {
			Arrays.fill(d, -1);
		}
		return optimalSearchTree_memo(keys, freq, 0, n - 1, dp);
	}









	// 1039. Minimum Score Triangulation of Polygon
	// HARD
	// MEMORIZE
	// faith - this method will return the min triangulation score for the vertices in the range [si,ei]
	public int minScoreTriangulation_memo(int[] arr, int si, int ei, int[][] dp) {
		// if (ei - si == 2) {
		// 	return dp[si][ei] = arr[si] * arr[si + 1] * arr[si + 2];
		// }

		if (dp[si][ei] != 0) {
			return dp[si][ei];
		}

		int minScore = (int)1e9;
		for (int cut = si + 1; cut <= ei - 1; cut++) {
			int leftScore = (cut - si >= 2) ? minScoreTriangulation_memo(arr, si, cut, dp) : 0;
			int rightScore = (ei - cut >= 2) ? minScoreTriangulation_memo(arr, cut, ei, dp) : 0;
			int selfScore = leftScore + rightScore + arr[si] * arr[ei] * arr[cut];

			minScore = Math.min(minScore, selfScore);
		}

		return dp[si][ei] = minScore;
	}
	public int minScoreTriangulation(int[] arr) {
		int n = arr.length;
		int[][] dp = new int[n][n];
		return minScoreTriangulation_memo(arr, 0, n - 1, dp);
	}









	// 96. Unique Binary Search Trees
	// faith - this method will return me the no of unique bst's i can construct using the ele in the range [si,ei]
	public int numTrees_memo(int si, int ei, int[][] dp) {
		if (si == ei) {
			return dp[si][ei] = 1;
		}

		if (dp[si][ei] != 0) {
			return dp[si][ei];
		}

		int count = 0;
		for (int cut = si; cut <= ei; cut++) { // no of unique bst's i can construct with cut as the root
			int leftCount = (si <= cut - 1) ? numTrees_memo(si, cut - 1, dp) : 1;
			int rightCount = (cut + 1 <= ei) ? numTrees_memo(cut + 1, ei, dp) : 1;

			int currCount = leftCount * rightCount;
			count += currCount;
		}

		return dp[si][ei] = count;
	}
	// faith - this method will return me the no of unique bst's i can construct using "n" elemets
	public int numTrees_memo(int n, int[] dp) {
		if (n <= 1) {
			return dp[n] = 1;
		}

		if (dp[n] != 0) {
			return dp[n];
		}

		int count = 0;
		for (int cut = 1; cut <= n; cut++) { // no of unique bst's i can construct with cut^(th) ele as the root
			int leftCount = numTrees_memo(cut - 1 - 1 + 1, dp); // 1...(cut-1)
			int rightCount = numTrees_memo(n - (cut + 1) + 1, dp); // (cut+1)...n

			int currCount = leftCount * rightCount;
			count += currCount;
		}

		return dp[n] = count;
	}
	public int numTrees(int n) {
		// int[][] dp = new int[n + 1][n + 1];
		// return numTrees_memo(1 , n, dp);
		int[] dp = new int[n + 1];
		return numTrees_memo(n, dp);
	}









	// 95. Unique Binary Search Trees II
	// faith - this method will return me the unique bst's i can construct using the ele in the range [si,ei]
	public List<TreeNode> generateTrees_recu(int si, int ei) {
		if (si > ei) {
			List<TreeNode> baseAns = new ArrayList<>();
			baseAns.add(null);
			return baseAns;
		}
		if (si == ei) {
			List<TreeNode> baseAns = new ArrayList<>();
			TreeNode root = new TreeNode(si);
			baseAns.add(root);
			return baseAns;
		}

		List<TreeNode> myAns = new ArrayList<>();
		for (int cut = si; cut <= ei; cut++) { // unique bst's i can construct with cut as the root
			List<TreeNode> leftAns = generateTrees_recu(si, cut - 1);
			List<TreeNode> rightAns = generateTrees_recu(cut + 1, ei);

			for (TreeNode l : leftAns) {
				for (TreeNode r : rightAns) {
					TreeNode root = new TreeNode(cut);
					root.left = l;
					root.right = r;
					myAns.add(root);
				}
			}

		}

		return myAns;
	}
	public List<TreeNode> generateTrees_memo(int si, int ei, List<TreeNode>[][] dp) {
		if (si > ei) {
			List<TreeNode> baseAns = new ArrayList<>();
			baseAns.add(null);
			return baseAns; // can't save the result in dp because si > ei is not a valid index in dp table: (0,-1)
			// ideally, we should have done proactive calling, but nonetheless, this works
		}

		if (dp[si][ei] != null) {
			return dp[si][ei];
		}

		List<TreeNode> myAns = new ArrayList<>();
		for (int cut = si; cut <= ei; cut++) { // unique bst's i can construct with cut as the root
			List<TreeNode> leftAns = generateTrees_memo(si, cut - 1, dp);
			List<TreeNode> rightAns = generateTrees_memo(cut + 1, ei, dp);

			for (TreeNode l : leftAns) {
				for (TreeNode r : rightAns) {
					TreeNode root = new TreeNode(cut);
					root.left = l;
					root.right = r;
					myAns.add(root);
				}
			}

		}

		return dp[si][ei] = myAns;
	}
	public List<TreeNode> generateTrees(int n) {
		// return generateTrees_recu(1, n);
		List<TreeNode> dp[][] = new ArrayList[n + 1][n + 1];
		return generateTrees_memo(1, n, dp);
	}









	// ======================================== PROBLEMS SET ========================================









	// 688. Knight Probability in Chessboard
	// faith - this method will return me the total no of cases where knight will be inside board after k jumps
	// MEMERIZE - use double data type variables for probabitlity related problems
	// TLE
	public double knightProbability_recu(int n, int k, int sr, int sc, int[][] dir) {
		if (k == 0) {
			return 1;
		}

		double ans = 0;
		for (int[] d : dir) {
			int nr = sr + d[0];
			int nc = sc + d[1];
			if (nr >= 0 && nr < n && nc >= 0 && nc < n) {
				double smallAns = knightProbability_recu(n, k - 1, nr, nc, dir);
				ans += smallAns;
			}
		}

		return ans;
	}
	public double knightProbability_memo(int n, int k, int sr, int sc, int[][] dir, double[][][] dp) {
		if (k == 0) {
			return dp[k][sr][sc] = 1;
		}
		// System.out.println(k + " " + sr + " " + sc);
		if (dp[k][sr][sc] != -1) {
			return dp[k][sr][sc];
		}

		double ans = 0;
		for (int[] d : dir) {
			int nr = sr + d[0];
			int nc = sc + d[1];
			if (nr >= 0 && nr < n && nc >= 0 && nc < n) {
				double smallAns = knightProbability_memo(n, k - 1, nr, nc, dir, dp);
				ans += smallAns;
			}
		}

		return dp[k][sr][sc] = ans;
	}
	public double knightProbability(int n, int k, int r, int c) {
		int[][] dir = new int[][] {{2, 1}, {2, -1}, { -2, 1}, { -2, -1}, {1, 2}, {1, -2}, { -1, 2}, { -1, -2}};
		double[][][] dp = new double[k + 1][n][n];
		for (double[][] d1 : dp) {
			for (double[] d2 : d1) {
				Arrays.fill(d2, -1);
			}
		}

		// double inside = knightProbability_recu(n, k , r , c, dir);
		double inside = knightProbability_memo(n, k , r , c, dir, dp);
		double total = Math.pow(8, k);
		return inside / total;
	}









	// 576. Out of Boundary Paths
	// faith - this method will return the total no of paths to move the ball out of the boundary
	// starting from (sr,sc) with (maxMoves) moves left
	long MOD = (int)1e9 + 7;
	public long findPaths_memo(int n, int m, int maxMove, int sr, int sc, int[][] dir, long[][][] dp) {
		if (maxMove == 0) {
			return dp[maxMove][sr][sc] = 0;
		}

		if (dp[maxMove][sr][sc] != -1) {
			return dp[maxMove][sr][sc];
		}

		long count = 0;
		for (int[] d : dir) {
			int nr = sr + d[0];
			int nc = sc + d[1];

			if (nr >= 0 && nr < n && nc >= 0 && nc < m) {
				count = (count + findPaths_memo(n , m, maxMove - 1, nr, nc, dir, dp)) % MOD;
			} else {
				count++; // i am already out of the boundary
			}
		}

		return dp[maxMove][sr][sc] = count;
	}
	public int findPaths(int n, int m, int maxMove, int sr, int sc) {
		int[][] dir = new int[][] {{0, 1}, {0, -1}, {1, 0}, { -1, 0}};
		long[][][] dp = new long[maxMove + 1][n][m];
		for (long[][] d1 : dp) {
			for (long[] d2 : d1) {
				Arrays.fill(d2, -1);
			}
		}
		return (int)findPaths_memo(n , m, maxMove, sr, sc, dir, dp);
	}









	// Mobile numeric keypad
	// https://www.geeksforgeeks.org/problems/mobile-numeric-keypad5456/1
	public long getCount(int n) {
		// TODO
	}









	// 198. House Robber
	public int rob_memo(int[] arr, int si, int[] dp) {
		int n = arr.length;

		if (dp[si] != -1) {
			return dp[si];
		}

		int pick = arr[si]; // i choose to pick the curr ele
		if (si + 2 < n) {
			pick += rob_memo(arr, si + 2, dp);
		}
		int notPick = 0; // i choose NOT to pick the curr ele
		if (si + 1 < n) {
			notPick += rob_memo(arr, si + 1, dp);
		}

		return dp[si] = Math.max(pick, notPick);
	}
	public int rob(int[] arr) {
		int n = arr.length;
		int[] dp = new int[n];
		Arrays.fill(dp, -1);
		return rob_memo(arr, 0, dp);
	}









	// 213. House Robber II
	// MEMORIZE
	// Since House[1] and House[n] are adjacent, they cannot be robbed together. Therefore, the problem becomes to rob either House[1]-House[n-1] or House[2]-House[n], depending on which choice offers more money. Now the problem has degenerated to the House Robber, which is already been solved
	// you can't use the same dp for both cases, you need 2 diff dps
	// since we can't rob both the 0th & n-1th item as they are adjacent, we make 2 diff calls [0, n-2] & [1, n-1]
	// when we make the calls [0, n-2] & [1, n-1], we're not forcing a pick at 0th / n-1th item
	// we are just breaking the circular problem into 2 linear subproblems to avoid the circular conflict
	// in each subproblem, we still have full choice to pick or skip any house for max loot
	public int rob_memo(int[] arr, int si, int EI, int[] dp) {
		if (si > EI) {
			return 0;
		}

		if (dp[si] != -1) {
			return dp[si];
		}

		int pick = arr[si] + rob_memo(arr, si + 2, EI, dp); // i choose to pick the curr ele
		int notPick = rob_memo(arr, si + 1, EI, dp);; // i choose NOT to pick the curr ele

		return dp[si] = Math.max(pick, notPick);
	}
	public int rob(int[] arr) {
		int n = arr.length;
		if (n == 1) {
			return arr[0];
		}

		int[] dp1 = new int[n];
		Arrays.fill(dp1, -1);
		int ans1 = rob_memo(arr, 0, n - 2, dp1);

		int[] dp2 = new int[n];
		Arrays.fill(dp2, -1);
		int ans2 = rob_memo(arr, 1, n - 1, dp2);

		return Math.max(ans1, ans2);
	}









	// 1388. Pizza With 3n Slices
	// MEMORIZE
	// similar to 213. House Robber II
	// rob n/3 non-adjacent houses from n houses - we are forced to rob exactly n/3 houses
	// ignore Alice & Bob’s picks explicitly — just focus on selecting n/3 non-adjacent slices
	// because the slices are in a circle & you picked non-adjacent slices, Alice & Bob automatically take the slices adjacent(left & right) to yours
	public int maxSizeSlices_memo(int[] arr, int si, int EI, int k, int[][] dp) {
		int n = arr.length;
		if (si > EI || k == 0) {
			return 0;
		}

		if (dp[si][k] != 0) {
			return dp[si][k];
		}

		int pick = arr[si] + maxSizeSlices_memo(arr, si + 2, EI, k - 1, dp); // i choose to pick the curr ele
		int notPick = maxSizeSlices_memo(arr, si + 1, EI, k, dp); // i choose NOT to pick the curr ele

		return dp[si][k] = Math.max(pick, notPick);
	}
	public int maxSizeSlices(int[] arr) {
		int n = arr.length;
		int k = n / 3; // k = no of pizza slices left to pick up

		int[][] dp1 = new int[n][k + 1];
		int ans1 = maxSizeSlices_memo(arr, 0, n - 2, k, dp1);

		int[][] dp2 = new int[n][k + 1];
		int ans2 = maxSizeSlices_memo(arr, 1, n - 1, k, dp2);

		return Math.max(ans1, ans2);
	}

















































































































	



















	public static void display1D(int[] arr) {
		for (int i : arr) {
			System.out.printf("%-4d", i);
		}
		System.out.println();
	}
	public static void display2D(int[][] arr) {
		for (int[] a : arr) {
			display1D(a);
		}
		System.out.println();
	}
	public static void main(String[] args) {
		System.out.println("Hello World");

		// ======================================== 2 POINTER SET ========================================

		// long start = System.nanoTime();

		// fibo();
		// mazePath();
		// mazePathJump();
		// boardPath();

		// long end = System.nanoTime();
		// System.out.println("Total execution time: " + (end - start) + " ns");
		// System.out.println("Total execution time: " + (end - start) / 1_000_000 + " ms");

		// ============================== TARGET SET ========================================

		int[] arr = new int[] {2, 3, 5, 7};
		int tar = 10;
		// int count = permutation(arr, tar);
		int count = combination(arr, tar);
		System.out.println("count = " + count);
	}
	dp[si][ei] = str.charAt(si) == str.charAt(ei) ? 0 : 1;
}