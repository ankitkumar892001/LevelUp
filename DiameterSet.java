import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.PriorityQueue;

public class DiameterSet {

	public static class TreeNode {
		int val = 0;
		TreeNode left = null;
		TreeNode right = null;
		TreeNode(int val) {
			this.val = val;
		}
	}

	public static int height(TreeNode root) {
		if (root == null) {
			return -1;
		}

		int lh = height(root.left);
		int rh = height(root.right);

		return Math.max(lh, rh) + 1;
	}

	// public static int diameter(TreeNode root) {
	// 	if (root == null) {
	// 		return 0;
	// 	}

	// 	int ld = diameter(root.left);
	// 	int rd = diameter(root.right);

	// 	int lh = height(root.left);
	// 	int rh = height(root.right);

	// 	return Math.max(Math.max(dl, dr), lh + rh + 2);
	// }

	// returns {diameter, height}
	public int[] diameter(TreeNode root) {
		if (root == null) {
			return new int[] {0, -1};
		}
		if (root.left == null && root.right == null) {
			return new int[] {0, 0};
		}

		int[] leftAns = diameter(root.left);
		int[] rightAns = diameter(root.right);

		int[] myAns = new int[2];
		myAns[0] = Math.max(leftAns[1] + rightAns[1] + 2, Math.max(leftAns[0], rightAns[0]));
		myAns[1] = Math.max(leftAns[1], rightAns[1]) + 1;
		return myAns;
	}









	// 543. Diameter of Binary Tree
	public int diameterOfBinaryTree(TreeNode root) {
		return diameter(root)[0];
	}









	// 112. Path Sum
	public boolean hasPathSum(TreeNode root, int target) {
		if (root == null) {
			return false;
		}
		if (root.left == null && root.right == null) {
			return target == root.val;
		}

		return hasPathSum(root.left, target - root.val) || hasPathSum(root.right, target - root.val);
	}









	// 113. Path Sum II
	public void pathSum(TreeNode root, int target, List<Integer> smallAns, List<List<Integer>> ans) {
		if (root == null) {
			return;
		}
		if (root.left == null && root.right == null) {
			if (target == root.val) {
				smallAns.add(root.val);
				ans.add(new ArrayList(smallAns)); // deep copy
				smallAns.remove(smallAns.size() - 1);
			}
			return;
		}

		smallAns.add(root.val);
		pathSum(root.left, target - root.val, smallAns, ans);
		pathSum(root.right, target - root.val, smallAns, ans);
		smallAns.remove(smallAns.size() - 1);
	}

	public List<List<Integer>> pathSum(TreeNode root, int target) {
		List<Integer> smallAns = new ArrayList<>();
		List<List<Integer>> ans = new ArrayList<>();
		pathSum(root, target, smallAns, ans);
		return ans;
	}









	// {leaf2leafMaxPathSum, root2leafMaxPathSum}
	// always contradict yourself in the null base case
	// for maximum return -∞
	// for maximum return +∞
	// assuming signle node can't make leaf2leaf
	// but it can become root2leaf
	public static int[] maxPathSumBetween2LeafNodes_(TreeNode root) {
		if (root == null) {
			return new int[] { -(int)1e9, -(int)1e9 };
		}
		if (root.left == null && root.right == null) {
			return new int[] { -(int)1e9, root.val };
		}

		int[] lr = maxPathSumBetween2LeafNodes_(root.left);
		int[] rr = maxPathSumBetween2LeafNodes_(root.right);

		int[] myRes = new int[2];
		myRes[0] = Math.max(lr[0], rr[0]);
		// we will consider leaf2leaf max path sum going through root (root.val + lr[1] + rr[1])
		// only if both left & right child are not null
		// if any one of them is null then this (root.val + lr[1] + rr[1]) won't be leaf2leaf
		if (root.left != null && root.right != null) {
			myRes[0] = Math.max(Math.max(lr[0], rr[0]), root.val + lr[1] + rr[1]);
		}
		myRes[1] = Math.max(lr[1], rr[1]) + root.val;
		return myRes;
	}
	public static int maxPathSumBetween2LeafNodes(TreeNode root) {
		int ans = 0;
		if (root == null) {
			return ans;
		}

		return maxPathSumBetween2LeafNodes_(root)[0];
	}









	// 124. Binary Tree Maximum Path Sum
	// {node2node, root2node}
	public static int max(int... values) {
		int maxValue = values[0];
		for (int value : values) {
			if (value > maxValue) {
				maxValue = value;
			}
		}
		return maxValue;
	}
	public int[] maxPathSum_(TreeNode root) {
		if (root == null) {
			return new int[] { -(int)1e9, -(int)1e9};
		}
		if (root.left == null && root.right == null) {
			return new int[] {root.val , root.val};
		}

		int[] lr = maxPathSum_(root.left);
		int[] rr = maxPathSum_(root.right);

		int[] myRes = new int[2];
		myRes[0] = max(lr[0], rr[0], root.val + lr[1] + rr[1], root.val, root.val + lr[1], root.val + rr[1]);
		myRes[1] = max(root.val, root.val + lr[1], root.val + rr[1]);
		return myRes;
	}

	public int maxPathSum(TreeNode root) {
		if (root == null) {
			return -(int)1e9;
		}

		return maxPathSum_(root)[0];
	}









	// MEMORIZE
	// 662. Maximum Width of Binary Tree
	class Pair {
		TreeNode node = null;
		int index = 0;
		Pair() {
		}
		Pair(TreeNode node, int index) {
			this.node = node;
			this.index = index;
		}
	}
	public int widthOfBinaryTree(TreeNode root) {
		if (root == null) {
			return 0;
		}

		int ans = 0;
		LinkedList<Pair> qu = new LinkedList<>();
		qu.addLast(new Pair(root, 0));

		while (qu.size() != 0) {
			int size = qu.size();
			int firstIndex = 0;
			int lastIndex = 0;
			while (size-- > 0) {
				Pair p = qu.removeFirst();
				TreeNode n = p.node;
				int idx = p.index;

				if (firstIndex == 0) {
					firstIndex = idx;
				}
				lastIndex = idx;

				if (n.left != null) {
					qu.addLast(new Pair(n.left, 2 * idx + 1));
				}
				if (n.right != null) {
					qu.addLast(new Pair(n.right, 2 * idx + 2));
				}
			}
			ans = Math.max(ans, lastIndex - firstIndex + 1);
		}

		return ans;
	}









	// 968. Binary Tree Cameras
	// return values meaning
	// 0 -> i am camera
	// 1 -> i am covered by camera, i don't need camera
	// 2 -> i need the coverage
	int count = 0;
	public int minCameraCover_(TreeNode root) {
		if (root == null) {
			// i don't need camera
			return 1;
		}
		if (root.left == null && root.right == null) {
			return 2;
		}
		int lr = minCameraCover_(root.left);
		int rr = minCameraCover_(root.right);

		if (lr == 2 || rr == 2) {
			count++;
			return 0;
		}
		if (lr == 0 || rr == 0) {
			return 1;
		}
		// both are 1 -> both are covered by camera
		return 2;
	}
	public int minCameraCover(TreeNode root) {
		int ans = minCameraCover_(root);
		if (ans == 2) {
			count++;
		}
		return count;
	}







































































	public static void printListOfTreeNode(ArrayList<TreeNode> al) {
		System.out.print("al : ");
		for (var x : al) {
			System.out.print(x.val + " ");
		}
		System.out.println(" ");
	}

	public static void printListOfListOfTreeNode(ArrayList<ArrayList<TreeNode>> al) {
		for (var x : al) {
			printListOfTreeNode(x);
		}
	}
	public static void printTree(TreeNode root) {
		if (root == null) {
			return;
		}

		System.out.print(root.left != null ? root.left.val : ".");
		System.out.print("->" + root.val + "<-");
		System.out.print(root.right != null ? root.right.val : ".");
		System.out.println();

		printTree(root.left);
		printTree(root.right);
	}

	public static void main(String[] args) {
		System.out.println("Hello World");
		TreeNode root = new TreeNode(-15);
		TreeNode node5 = new TreeNode(5);
		TreeNode node6 = new TreeNode(6);
		TreeNode nodeMinus8 = new TreeNode(-8);
		TreeNode node1 = new TreeNode(1);
		TreeNode node2 = new TreeNode(2);
		TreeNode node6Dup = new TreeNode(6);
		TreeNode node3 = new TreeNode(3);
		TreeNode node9 = new TreeNode(9);
		TreeNode node0 = new TreeNode(0);
		TreeNode node4 = new TreeNode(4);
		TreeNode nodeMinus1 = new TreeNode(-1);
		TreeNode node10 = new TreeNode(10);

		// root.right = new TreeNode(-3);

		root.left = node5;
		root.right = node6;
		node5.left = nodeMinus8;
		node5.right = node1;
		nodeMinus8.left = node2;
		nodeMinus8.right = node6Dup;
		node6.left = node3;
		node6.right = node9;
		node9.right = node0;
		node0.left = node4;
		node0.right = nodeMinus1;
		nodeMinus1.left = node10;

		printTree(root);
		System.out.println(maxPathSumBetween2LeafNodes(root));
	}
}