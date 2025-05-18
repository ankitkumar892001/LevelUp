import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;

public class Tree {

	public static class TreeNode {
		int val = 0;
		TreeNode left = null;
		TreeNode right = null;
		TreeNode(int val) {
			this.val = val;
		}
	}


	public static int size(TreeNode root) {
		if (root == null) {
			return 0;
		}

		int leftSize = size(root.left);
		int rightSize = size(root.right);

		return leftSize + rightSize + 1;
	}

	public static int height(TreeNode root) {
		if (root == null) {
			return -1;
		}

		return Math.max(height(root.left), height(root.right)) + 1;
	}

	public static int maximum(TreeNode root) {
		if (root == null) {
			return -(int)1e9;
		}

		return Math.max(root.val, Math.max(maximum(root.left), maximum(root.right)));
	}


	public static int minimum(TreeNode root) {
		if (root == null) {
			return (int)1e9;
		}

		return Math.min(root.val, Math.min(minimum(root.left), minimum(root.right)));
	}

	public static boolean find(TreeNode root, int data) {
		if (root == null) {
			return false;
		} else if (root.val == data) {
			return true;
		}

		return find(root.left, data) || find(root.right, data);
	}

	// faith
	// if data is present in your subtree then return the nodeToRootPath i will simply append myself
	// else return null
	public static ArrayList<TreeNode> nodeToRootPath(TreeNode root, int data) {
		if (root == null) {
			return null;
		}
		if (root.val == data) {
			ArrayList<TreeNode> myAns = new ArrayList<>();
			myAns.add(root);
			return myAns;
		}

		ArrayList<TreeNode> leftAns = nodeToRootPath(root.left, data);
		if (leftAns != null) {
			leftAns.add(root);
			return leftAns;
		}
		ArrayList<TreeNode> rightAns = nodeToRootPath(root.right, data);
		if (rightAns != null) {
			rightAns.add(root);
			return rightAns;
		}
		return null;
	}

	// faith
	// first add ypurself then check if the data is present then return true
	// else remove yourself & return false
	public static boolean rootToNodePath(TreeNode root, int data, ArrayList<TreeNode> ans) {
		if (root == null) {
			return false;
		}
		if (root.val == data) {
			ans.add(root);
			return true;
		}

		ans.add(root);
		boolean myAns = rootToNodePath(root.left, data, ans) || rootToNodePath(root.right, data, ans);
		if (myAns == false) {
			ans.remove(ans.size() - 1);
		}
		return myAns;
	}




	public static void rootToAllLeavesPath(TreeNode root, ArrayList<TreeNode> ans, ArrayList<ArrayList<TreeNode>> finalAns) {
		if (root == null) {
			return;
		}
		if (root.left == null && root.right == null) {
			ans.add(root);
			ArrayList<TreeNode> deepCopyAns = new ArrayList<>(ans);
			finalAns.add(deepCopyAns);
			ans.remove(ans.size() - 1);
			return;
		}

		ans.add(root);
		rootToAllLeavesPath(root.left, ans, finalAns);
		rootToAllLeavesPath(root.right, ans, finalAns);
		ans.remove(ans.size() - 1);
	}




	public static void exactlyOneChild(TreeNode root, ArrayList<Integer> ans) {
		if (root == null) {
			return;
		}
		if (root.left == null && root.right == null) { // if leaf then return
			return;
		}
		if (root.left == null || root.right == null) { // both left & right can't be null now because of above line
			ans.add(root.val);
		}

		exactlyOneChild(root.left, ans);
		exactlyOneChild(root.right, ans);
	}


	public static int exactlyOneChildCount(TreeNode root) {
		if (root == null) {
			return 0;
		}
		if (root.left == null && root.right == null) {
			return 0;
		}

		int left = exactlyOneChildCount(root.left);
		int right = exactlyOneChildCount(root.right);
		int ans = left + right;
		// both can't be null because we already checked for leaf base case
		// it means i am a single child node
		if (root.left == null || root.right == null) {
			ans += 1;
		}

		return ans;
	}



	// 257. Binary Tree Paths
	public static String deepCopyAns(ArrayList<Integer> ans) {
		if (ans == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < ans.size(); i++) {
			sb.append(ans.get(i));
			if (i < ans.size() - 1) {
				sb.append("->");
			}
		}
		return sb.toString();
	}
	public static void rootToAllLeavesPath_02(TreeNode root, ArrayList<Integer> ans, ArrayList<String> finalAns) {
		if (root == null) {
			return;
		}
		if (root.left == null && root.right == null) {
			ans.add(root.val);
			finalAns.add(deepCopyAns(ans));
			ans.remove(ans.size() - 1);
			return;
		}

		ans.add(root.val);
		rootToAllLeavesPath_02(root.left, ans, finalAns);
		rootToAllLeavesPath_02(root.right, ans, finalAns);
		ans.remove(ans.size() - 1);
	}
	public List<String> binaryTreePaths(TreeNode root) {
		if (root == null) {
			return null;
		}
		ArrayList<Integer> ans = new ArrayList<>();
		ArrayList<String> finalAns = new ArrayList<>();
		rootToAllLeavesPath_02(root, ans, finalAns);
		return finalAns;
	}



	// MEMORIZE
	// 863. All Nodes Distance K in Binary Tree
	// public static ArrayList<TreeNode> nodeToRootPath(TreeNode root, TreeNode target) {
	// 	if (root == null) {
	// 		return null;
	// 	}
	// 	if (root == target) {
	// 		ArrayList<TreeNode> base = new ArrayList<>();
	// 		base.add(root);
	// 		return base;
	// 	}

	// 	ArrayList<TreeNode> leftAns = nodeToRootPath(root.left, target);
	// 	if (leftAns != null) {
	// 		leftAns.add(root);
	// 		return leftAns;
	// 	}
	// 	ArrayList<TreeNode> rightAns = nodeToRootPath(root.right, target);
	// 	if (rightAns != null) {
	// 		rightAns.add(root);
	// 		return rightAns;
	// 	}
	// 	return null;
	// }
	// public static void storeNodesKDown(TreeNode root, int k, TreeNode blocked, ArrayList<Integer> ans) {
	// 	if (root == null) {
	// 		return;
	// 	}
	// 	if (root == blocked) {
	// 		return;
	// 	}
	// 	if (k == 0) {
	// 		ans.add(root.val);
	// 		return;
	// 	}

	// 	storeNodesKDown(root.left, k - 1, blocked, ans);
	// 	storeNodesKDown(root.right, k - 1, blocked, ans);
	// }
	// public static List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
	// 	ArrayList<TreeNode> path = nodeToRootPath(root, target);
	// 	TreeNode blocked = null;
	// 	ArrayList<Integer> ans = new ArrayList<>();
	// 	for (int i = 0; i < path.size(); i++) {
	// 		TreeNode x = path.get(i);
	// 		if (k - i < 0) {
	// 			break;
	// 		}
	// 		storeNodesKDown(x, k - i, blocked, ans);
	// 		blocked = path.get(i);
	// 	}
	// 	return ans;
	// }




	// MEMORIZE
	// 863. All Nodes Distance K in Binary Tree
	// faith - store all the node that are k distance down from root
	// ask left & right subtree to store all the nodes that are k - 1 distance down from them
	public void kDown(TreeNode root, int k, TreeNode blockedNode, ArrayList<Integer> ans) {
		if (root == null) {
			return;
		}
		if (root == blockedNode) {
			return;
		}
		if (k == 0) {
			ans.add(root.val);
			return;
		}

		kDown(root.left, k - 1, blockedNode, ans);
		kDown(root.right, k - 1, blockedNode, ans);
	}

	// returns the distance away from target
	// negative value => target not found
	public int find(TreeNode root, TreeNode target, int k, ArrayList<Integer> ans) {
		if (root == null) {
			return -1;
		}
		if (root == target) {
			kDown(root, k, null, ans);
			// target is 0 distance away target
			return 0;
		}
		// my left subtree will return its distance from target
		int ld = find(root.left, target, k, ans);
		if (ld != -1) {
			// since my left child is ld distance away target then i must be ld + 1 distance away from target
			ld += 1;
			kDown(root, k - ld, root.left, ans);
			return ld;
		}
		int rd = find(root.right, target, k, ans);
		if (rd != -1) {
			// since my right child is rd distance away target then i must be rd + 1 distance away from target
			rd += 1;
			kDown(root, k - rd, root.right, ans);
			return rd;
		}
		return -1;
	}
	public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
		ArrayList<Integer> ans = new ArrayList<>();
		find(root, target, k, ans);
		return ans;
	}










	// burn all the nodes down root & and store them
	// at index = timeOfBurn
	// which is = distance from target
	public static void burnNodesDown(TreeNode root, int timeOfBurn, TreeNode blockedNode, ArrayList<ArrayList<Integer>> ans) {
		if (root == null || root == blockedNode) {
			return;
		}
		if (ans.size() < timeOfBurn + 1) {
			ans.add(new ArrayList<>());
		}
		ArrayList<Integer> kthIndexElement = ans.get(timeOfBurn);
		kthIndexElement.add(root.val);

		burnNodesDown(root.left, timeOfBurn + 1, blockedNode, ans);
		burnNodesDown(root.right, timeOfBurn + 1, blockedNode, ans);
	}

	// returns the distance away from target
	// negative value => target not found
	public static int find(TreeNode root, TreeNode target, ArrayList<ArrayList<Integer>> ans) {
		if (root == null) {
			return -1;
		}
		if (root == target) {
			burnNodesDown(root, 0, null, ans);
			return 0;
		}

		int ld = find(root.left, target, ans);
		if (ld != -1) {
			// since my left child is ld distance away target then i must be ld + 1 distance away from target
			ld += 1;
			burnNodesDown(root, ld, root.left, ans);
			return ld;
		}
		int rd = find(root.right, target, ans);
		if (rd != -1) {
			// since my left child is rd distance away target then i must be rd + 1 distance away from target
			rd += 1;
			burnNodesDown(root, rd, root.right, ans);
			return rd;
		}

		return -1;
	}

	public static ArrayList<ArrayList<Integer>> burningTree(TreeNode root, TreeNode target) {
		ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
		find(root, target, ans);
		return ans;
	}









	// burn all the nodes down root & and store them
	// at index = timeOfBurn
	// which is = distance from target
	public static void burnNodesDown(TreeNode root, int timeOfBurn, TreeNode blockedNode, HashSet<TreeNode> waterSet, ArrayList<ArrayList<Integer>> ans) {
		if (root == null || root == blockedNode || waterSet.contains(root)) {
			return;
		}

		if (ans.size() < timeOfBurn + 1) {
			ans.add(new ArrayList<>());
		}
		ArrayList<Integer> kthIndexElement = ans.get(timeOfBurn);
		kthIndexElement.add(root.val);

		burnNodesDown(root.left, timeOfBurn + 1, blockedNode, waterSet, ans);
		burnNodesDown(root.right, timeOfBurn + 1, blockedNode, waterSet, ans);
	}


	// returns the distance away from target
	// -1 => target not found
	// -2 => found the target but I am water so fire can't be spread
	public static int find(TreeNode root, TreeNode target, HashSet<TreeNode> waterSet, ArrayList<ArrayList<Integer>> ans) {
		if (root == null) {
			return -1;
		}
		if (root == target) {
			// found the target but I am water so fire can't be spread
			if (waterSet.contains(root)) {
				return -2;
			}
			burnNodesDown(root, 0, null, waterSet, ans);
			return 0;
		}

		int ld = find(root.left, target, waterSet, ans);
		if (ld >= 0) {
			// since ld is non-negative so we
			// found the target but I am water so fire can't be spread
			if (waterSet.contains(root)) {
				return -2;
			}
			ld += 1;
			burnNodesDown(root, ld, root.left, waterSet, ans);
			return ld;
		}
		// if ld is -1 then we should try to find the target atleast
		// but if ld is -2 then just return no point in traversing
		if (ld == -2) {
			return -2;
		}

		int rd = find(root.left, target, waterSet, ans);
		if (rd >= 0) {
			// since rd is non-negative so we
			// found the target but I am water so fire can't be spread
			if (waterSet.contains(root)) {
				return -2;
			}
			rd += 1;
			burnNodesDown(root, rd, root.left, waterSet, ans);
			return rd;
		}
		// if rd is -1 then we should try to find the target atleast
		// but if rd is -2 then just return no point in traversing
		if (rd == -2) {
			return -2;
		}

		return -1;
	}

	public static ArrayList<ArrayList<Integer>> burningTreeWithWater(TreeNode root, TreeNode target, HashSet<TreeNode> waterSet) {
		ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
		find(root, target, waterSet, ans);
		return ans;
	}






	// // Burning Tree gfg
	// public static int minTimeToBurnNodesDown(Node root, Node blockedNode) {
	// 	if (root == null) {
	// 		return -1;
	// 	}
	// 	if (root == blockedNode) {
	// 		return -1;
	// 	}
	// 	int l = minTimeToBurnNodesDown(root.left, blockedNode);
	// 	int r = minTimeToBurnNodesDown(root.right, blockedNode);

	// 	return Math.max(l, r) + 1;
	// }

	// // returns the distance away from target
	// // negative value => target not found
	// public static int find(Node root, int target, int[] ans) {
	// 	if (root == null) {
	// 		return -1;
	// 	}
	// 	if (root.data == target) {
	// 		int x = minTimeToBurnNodesDown(root, null);
	// 		ans[0] = Math.max(ans[0], x);
	// 		return 0;
	// 	}

	// 	int ld = find(root.left, target, ans);
	// 	if (ld != -1) {
	// 		// since my child is ld distance away from target then I must be ld + 1 distace away from target
	// 		ld += 1;
	// 		int x = minTimeToBurnNodesDown(root, root.left);
	// 		ans[0] = Math.max(ans[0], x + ld);
	// 		return ld;
	// 	}

	// 	int rd = find(root.right, target, ans);
	// 	if (rd != -1) {
	// 		// since my child is rd distance away from target then I must be rd + 1 distace away from target
	// 		rd += 1;
	// 		int x = minTimeToBurnNodesDown(root, root.right);
	// 		ans[0] = Math.max(ans[0], x + rd);
	// 		return rd;
	// 	}
	// 	return - 1;
	// }

	// public static int minTime(Node root, int target) {
	// 	int[] ans = new int[1];
	// 	ans[0] = -(int)1e9;
	// 	find(root, target, ans);
	// 	return ans[0];
	// }





	// 236. Lowest Common Ancestor of a Binary Tree
	public boolean rootToNodePath(TreeNode root, TreeNode target, ArrayList<TreeNode> ans) {
		if (root == null) {
			return false;
		}
		if (root == target) {
			ans.add(root);
			return true;
		}

		ans.add(root);
		boolean found = rootToNodePath(root.left, target, ans) || rootToNodePath(root.right, target, ans);
		if (!found) {
			ans.remove(root);
		}

		return found;
	}

	public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
		if (root == null || p == null || q == null) {
			return null;
		}

		ArrayList<TreeNode> al1 = new ArrayList<>();
		rootToNodePath(root, p, al1);
		ArrayList<TreeNode> al2 = new ArrayList<>();
		rootToNodePath(root, q, al2);

		TreeNode lca = null;
		for (int i = 0; i < al1.size() && i < al2.size() ; i++) {
			if (al1.get(i) == al2.get(i)) {
				lca = al1.get(i);
			} else {
				break;
			}
		}

		return lca;
	}










	// 236. Lowest Common Ancestor of a Binary Tree
	public boolean rootToNodePath(TreeNode root, TreeNode target, ArrayList<TreeNode> ans) {
		if (root == null) {
			return false;
		}
		if (root == target) {
			ans.add(root);
			return true;
		}

		ans.add(root);
		boolean found = rootToNodePath(root.left, target, ans) || rootToNodePath(root.right, target, ans);
		if (!found) {
			ans.remove(root);
		}

		return found;
	}

	public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
		if (root == null || p == null || q == null) {
			return null;
		}

		ArrayList<TreeNode> al1 = new ArrayList<>();
		rootToNodePath(root, p, al1);
		ArrayList<TreeNode> al2 = new ArrayList<>();
		rootToNodePath(root, q, al2);

		TreeNode lca = null;
		for (int i = 0; i < al1.size() && i < al2.size() ; i++) {
			if (al1.get(i) != al2.get(i)) {
				break;
			}

			lca = al1.get(i);
		}

		return lca;
	}









	// intuition - just figire out the cases where you can become the LCA
	public boolean find(TreeNode root, TreeNode p, TreeNode q, TreeNode[] arr) {
		if (root == null) {
			return false;
		}

		boolean selfFound = (root == p || root == q) ? true : false;
		boolean leftFound = find(root.left, p , q, arr);
		boolean rightFound = find(root.right, p , q, arr);

		if ((leftFound && rightFound) || (leftFound && selfFound) || (rightFound && selfFound)) {
			arr[0] = root;
		}

		return leftFound || rightFound || selfFound;
	}

	public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
		if (root == null || p == null || q == null) {
			return null;
		}

		TreeNode[] arr = new TreeNode[1];
		arr[0] = null;
		find(root, p , q, arr);
		return arr[0];
	}









// 337. House Robber III
// max amount i can rob
// {including me, excluding me}
	public int[] rob_(TreeNode root) {
		if (root == null) {
			return new int[] {0, 0};
		}
		if (root.left == null && root.right == null) {
			return new int[] {root.val, 0};
		}

		int[] lr = rob_(root.left);
		int[] rr = rob_(root.right);

		int[] mr = new int[2];
		// if we include ourself then we have to exclude the child
		mr[0] = root.val + lr[1] + rr[1];
		// but if we exclude ourself then we can either include/exclude the child
		mr[1] = Math.max(lr[0], lr[1]) + Math.max(rr[0], rr[1]);
		// System.out.println(mr[0] + " " + mr[1]);
		return mr;
	}
	public int rob(TreeNode root) {
		if (root == null) {
			return 0;
		}

		int[] ans = rob_(root);
		return Math.max(ans[0], ans[1]);
	}









	// 1372. Longest ZigZag Path in a Binary Tree
	// longest forward sloping / path including me
	// longest backward sloping \ path including me
	// longest ziz zag path in the whole tree
	public int max(int... arr) {
		int ans = arr[0];
		for (var x : arr) {
			ans = Math.max(ans, x);
		}
		return ans;
	}
	public int[] longestZigZag_(TreeNode root) {
		if (root == null) {
			return new int[] { -1, -1, -1};
		}
		if (root.left == null && root.right == null) {
			return new int[] {0, 0, 0};
		}

		int[] lr = longestZigZag_(root.left);
		int[] rr = longestZigZag_(root.right);

		int[] mr = new int[3];
		mr[0] = lr[1] + 1;
		mr[1] = rr[0] + 1;
		mr[2] = max(mr[0], mr[1], lr[2], rr[2]);

		return mr;
	}
	public int longestZigZag(TreeNode root) {
		if (root == null) {
			return 0;
		}
		return longestZigZag_(root)[2];
	}









	// 979. Distribute Coins in Binary Tree
	// return extra coins i have -> left extra coins + right extra coins + node.val - 1
	int ans = 0;
	public int distributeCoins_(TreeNode root) {
		if (root == null) {
			return 0;
		}

		int lr = distributeCoins_(root.left);
		int rr = distributeCoins_(root.right);

		int mr = lr + rr + root.val - 1;
		ans += Math.abs(mr);
		return mr;
	}
	public int distributeCoins(TreeNode root) {
		distributeCoins_(root);

		return ans;
	}









	// 1443. Minimum Time to Collect All Apples in a Tree
	// returns min time to collect all the apples in its subtree
	public int dfs(int node, ArrayList<Integer>[] tree, List<Boolean> hasApple, boolean[] visited) {
		if (visited[node] == true) {
			return -1;
		}

		visited[node] = true;

		int myTime = 0;
		for (var child : tree[node]) {
			int t = dfs(child, tree, hasApple, visited);
			if (t != -1) {
				myTime += t + 2;
			}
		}

		if (myTime > 0) {
			return myTime;
		}
		if (hasApple.get(node) == true) {
			return 0;
		}

		return -1;
	}
	public int minTime(int n, int[][] edges, List<Boolean> hasApple) {
		if (edges == null || hasApple == null || hasApple.size() == 0) {
			return 0;
		}

		ArrayList<Integer>[] tree = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			tree[i] = new ArrayList<>();
		}
		for (var x : edges) {
			tree[x[0]].add(x[1]);
			tree[x[1]].add(x[0]);
		}
		boolean[] visited = new boolean[n];


		int ans = dfs(0, tree, hasApple, visited);
		return  ans == -1 ? 0 : ans;
	}









	// 687. Longest Univalue Path
	// {LUP root2node, LUP node2node}
	public int max(int... arr) {
		int ans = arr[0];
		for (var x : arr) {
			ans = Math.max(ans, x);
		}
		return ans;
	}
	public int[] longestUnivaluePath_(TreeNode root) {
		if (root == null) {
			return new int[] { -1, -1};
		}

		int[] lr = longestUnivaluePath_(root.left);
		int[] rr = longestUnivaluePath_(root.right);

		int[] mr = new int[2];
		mr[0] = 0;
		if (root.left != null && root.left.val == root.val) {
			mr[0] = Math.max(mr[0], lr[0] + 1);
		}
		if (root.right != null && root.right.val == root.val) {
			mr[0] = Math.max(mr[0], rr[0] + 1);
		}

		mr[1] = max(mr[0], lr[1], rr[1]);
		if (root.left != null && root.left.val == root.val && root.right != null && root.right.val == root.val) {
			mr[1] = Math.max(mr[1], lr[0] + rr[0] + 2);
		}

		return mr;
	}
	public int longestUnivaluePath(TreeNode root) {
		if (root == null) {
			return 0;
		}
		return longestUnivaluePath_(root)[1];
	}









	// 1339. Maximum Product of Splitted Binary Tree
	long totalSum = 0;
	public void totalSum(TreeNode root) {
		if (root == null) {
			return;
		}
		totalSum += root.val;
		totalSum(root.left);
		totalSum(root.right);
	}
	long maxProduct = 0;
	long MOD = (long)1e9 + 7;
	public long sum(TreeNode root) {
		if (root == null) {
			return 0;
		}

		long leftSum = sum(root.left);
		long rightSum = sum(root.right);
		long subtreeSum = leftSum + rightSum + root.val;

		maxProduct = Math.max(maxProduct, subtreeSum * (totalSum - subtreeSum));

		return subtreeSum;
	}
	public int maxProduct(TreeNode root) {
		if (root == null) {
			return 0;
		}
		totalSum(root);
		sum(root);
		return (int) (maxProduct % MOD);
	}












































































	public static void displayListOfListOfInteger(ArrayList<ArrayList<Integer>> al) {
		for (ArrayList<Integer> entry : al) {
			System.out.println(entry);
		}
	}
	public static void displayListOfTreeNodes(ArrayList<TreeNode> al) {
		for (TreeNode entry : al) {
			System.out.print(entry.val + "->");
		}
		System.out.println();
	}
	public static void displayListOfListOfTreeNodes(ArrayList<ArrayList<TreeNode>> al) {
		for (ArrayList<TreeNode> entry : al) {
			displayListOfTreeNodes(entry);
		}
	}



	public static void main(String[] args) {
		System.out.println("Hello World");
		TreeNode root = new TreeNode(1);
		TreeNode n2 = new TreeNode(2);
		TreeNode n3 = new TreeNode(3);
		TreeNode n4 = new TreeNode(4);
		TreeNode n5 = new TreeNode(5);
		TreeNode n6 = new TreeNode(6);
		TreeNode n7 = new TreeNode(7);
		TreeNode n8 = new TreeNode(8);

		root.left = n2;
		root.right = n3;
		n2.left = n4;
		n2.right = n5;
		n3.right = n8;
		n5.left = n6;
		n5.right = n7;
		//     1
		//    / \
		//   2   3
		//  / \	  \
		// 4   5   8
		//    / \
		//   6   7

		// System.out.println(size(root));
		// System.out.println(height(root));
		// System.out.println(maximum(root));
		// System.out.println(minimum(root));
		// System.out.println(find(root, 44));
		int[] arr = new int[1]; arr[0] = 0;
		ArrayList<Integer> al = new ArrayList<>();
		ArrayList<TreeNode> ans = new ArrayList<>();
		ArrayList<ArrayList<TreeNode>> finalAns = new ArrayList<>();
		// ans = nodeToRootPath(root, 4);
		// rootToNodePath(root, 5, ans);
		// rootToAllLeavesPath(root, ans, finalAns);
		// exactlyOneChild(root, al);
		// System.out.println(exactlyOneChildCount(root));
		// System.out.println(al);
		// displayListOfTreeNodes(ans);
		// displayListOfListOfTreeNodes(finalAns);

		// ArrayList<ArrayList<Integer>> bt = burningTree(root, n8);
		HashSet<TreeNode> waterSet = new HashSet<>();
		waterSet.add(n2);
		ArrayList<ArrayList<Integer>> bt = burningTreeWithWater(root, n5, waterSet);
		displayListOfListOfInteger(bt);
	}
}