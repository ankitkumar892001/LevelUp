import java.util.*;

public class DP_TargetSet {









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
	public static int combination_opti(int[] arr, int TAR, int[] dp) {
		int n = arr.length;
		dp[0] = 0;
		for (int ele : arr) {
			for (int tar = ele; tar <= TAR; tar++) {
				dp[tar] = Math.min(dp[tar], dp[tar - ele] + 1);
			}
		}
		return dp[TAR];
	}
	public int coinChange(int[] arr, int tar) {
		int[] dp = new int[tar + 1];
		Arrays.fill(dp, (int)1e9);
		int count = combination_opti(arr, tar, dp);
		return count == (int)1e9 ? -1 : count;
	}









	// Subset Sum Problem
	// https://www.geeksforgeeks.org/problems/subset-sum-problem-1611555638/1
	// Limited Suppply
	// Subset = Subseq -> every coin has a choice it may / may not come
	static Boolean isSubsetSum_memo(int arr[], int tar, int idx) {
		int n = arr.length;
		if (tar == 0) {
			return true;
		}
		if (idx == n) {
			return false;
		}

		Boolean ans = false;
		if (arr[idx] <= tar) {
			ans = ans || isSubsetSum_memo(arr, tar - arr[idx], idx + 1); // i want to come
		}
		ans = ans || isSubsetSum_memo(arr, tar, idx + 1); // i don't want to come

		return ans;
	}
	static Boolean isSubsetSum(int arr[], int tar) {
		
		return isSubsetSum_memo(arr, tar, 0);
	}









	static int knapsack_memo(int[] val, int[] wt, int W, int idx) {
		int n = val.length;
		if (W == 0) {
			return 0;
		}
		if (idx == n) {
			return 0;
		}

		int maxVal = 0;
		if (wt[idx] <= W) {
			maxVal = Math.max(maxVal, val[idx] + knapsack_memo(val, wt, W - wt[idx], idx + 1)); // include
		}
		maxVal = Math.max(maxVal, knapsack_memo(val, wt, W, idx + 1)); // exclude

		return maxVal;
	}

	static int knapsack(int W, int[] val, int[] wt) {
		return knapsack_memo(val, wt, W, 0);
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

		int[] arr = new int[] {2, 3, 5, 7};
		int tar = 10;
		// int count = permutation(arr, tar);
		int count = combination(arr, tar);
		System.out.println("count = " + count);
	}
}