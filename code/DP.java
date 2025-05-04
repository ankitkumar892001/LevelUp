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









	// ============================== 2 POINTER SET ========================================









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









	// ============================== STRING SET ========================================
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
				ans += isMatch_memo(s, w, i + 1, j, dp); // * = match current char and stay on *
			} else {
				ans = 0;
			}
		}

		return dp[i][j] = (ans == 0 ? 0 : 1);
	}
	public int isMatch_tabu(String s, String w, int i, int j, int[][] dp) {
		// TODO
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
	public int isMatch_memo(String s, String w, int i, int j) {
		// TODO
	}
	public boolean isMatch(String s, String w) {
		int n = s.length();
		int m = w.length();
		int[][] dp = new int[n + 1][m + 1];
		for (int[] d : dp) {
			Arrays.fill(d, -1);
		}
		return isMatch_memo(s, w, 0, 0) == 1;
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









	// 132. Palindrome Partitioning II
	// MEMORIZE
	// faith - this method will return me the
	// min no of cuts needed for palindrome partitioning the string from [si,n-1]
	// test case - a...10 b 1...5
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









	// 1278. Palindrome Partitioning III
	public int palindromePartition(String s, int k) {
		// TODO
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









	// ============================== LIS SET ========================================









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



































































































	public static void display(int[] arr) {
		for (int i : arr) {
			System.out.print(i + " ");
		}
		System.out.println();
	}

	public static void display2D(int[][] arr) {
		for (int[] a : arr) {
			display(a);
		}
		System.out.println();
	}

	public static void main(String[] args) {
		System.out.println("Hello World");
		// long start = System.nanoTime();

		// fibo();
		// mazePath();
		// mazePathJump();
		// boardPath();

		// long end = System.nanoTime();
		// System.out.println("Total execution time: " + (end - start) + " ns");
		// System.out.println("Total execution time: " + (end - start) / 1_000_000 + " ms");

		int[] arr = new int[] {2, 3, 5, 7};
		int tar = 10;
		permutation(arr, tar);
	}
}