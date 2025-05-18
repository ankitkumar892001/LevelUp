import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.PriorityQueue;

public class ViewSet {

	public static class TreeNode {
		int val = 0;
		TreeNode left = null;
		TreeNode right = null;
		TreeNode(int val) {
			this.val = val;
		}
	}

	public static class TreeNodeVerticalLevel {
		TreeNode treeNode = null;
		int vl = 0;
		TreeNodeVerticalLevel(TreeNode treeNode, int vl) {
			this.treeNode = treeNode;
			this.vl = vl;
		}
	}

	// TreeNode HorizontalLevel VerticalLevel
	public static class THVPair {
		TreeNode treeNode = null;
		int hl = 0;
		int vl = 0;
		THVPair(TreeNode treeNode, int hl, int vl) {
			this.treeNode = treeNode;
			this.hl = hl;
			this.vl = vl;
		}
	}

	public static ArrayList<TreeNode> levelOrderTraversal(TreeNode root) {
		if (root == null) {
			return null;
		}

		ArrayList<TreeNode> ans = new ArrayList<>();
		LinkedList<TreeNode> qu = new LinkedList<>();
		qu.addLast(root);
		int level = 0;
		while (qu.size() != 0) {
			int size = qu.size();
			System.out.print("level " + level++ + ": ");
			while (size-- > 0) {
				TreeNode rn = qu.removeFirst();
				System.out.print(rn.val + " ");
				ans.add(rn);

				if (rn.left != null) {
					qu.addLast(rn.left);
				}
				if (rn.right != null) {
					qu.addLast(rn.right);
				}
			}
			System.out.println();
		}

		return ans;
	}









	public static ArrayList<TreeNode> leftView(TreeNode root) {
		ArrayList<TreeNode> ans = new ArrayList<>();
		if (root == null) {
			return ans;
		}

		LinkedList<TreeNode> qu = new LinkedList<>();
		qu.addLast(root);
		int level = 0;
		while (qu.size() != 0) {
			ans.add(qu.getFirst());
			int size = qu.size();
			while (size-- > 0) {
				TreeNode rn = qu.removeFirst();

				if (rn.left != null) {
					qu.addLast(rn.left);
				}
				if (rn.right != null) {
					qu.addLast(rn.right);
				}
			}
		}

		return ans;
	}









	public static ArrayList<TreeNode> rightView(TreeNode root) {
		ArrayList<TreeNode> ans = new ArrayList<>();
		if (root == null) {
			return ans;
		}

		LinkedList<TreeNode> qu = new LinkedList<>();
		qu.addLast(root);
		int level = 0;
		while (qu.size() != 0) {
			ans.add(qu.getFirst());
			int size = qu.size();
			while (size-- > 0) {
				TreeNode rn = qu.removeFirst();

				if (rn.right != null) {
					qu.addLast(rn.right);
				}
				if (rn.left != null) {
					qu.addLast(rn.left);
				}
			}
		}

		return ans;
	}









	// 0th index -> max left position of any node in my tree
	// 1st index -> max right position of any node in my tree
	// vl - vertical level
	public static void width_helper(TreeNode root, int vl, int[] arr) {
		if (root == null) {
			return;
		}

		arr[0] = Math.min(arr[0], vl);
		arr[1] = Math.max(arr[1], vl);

		width_helper(root.left, vl - 1, arr);
		width_helper(root.right, vl + 1, arr);
	}
	public static int[] width(TreeNode root) {
		int[] arr = new int[] {0, 0};
		width_helper(root, 0, arr);
		return arr;
	}

	// if both horizontal & vertical level are same(nodes are overlapping) then we give left -> right preference
	public static ArrayList<ArrayList<TreeNode>> verticalOrderTraversal(TreeNode root) {
		if (root == null) {
			return null;
		}

		ArrayList<ArrayList<TreeNode>> ans = new ArrayList<>();
		int[] arr = width(root);
		int w = arr[1] - arr[0] + 1;
		for (int i = 0; i < w; i++) {
			ans.add(new ArrayList<>());
		}

		LinkedList<TreeNodeVerticalLevel> qu = new LinkedList<>();
		int minusMaxNegativePosition = -arr[0];
		// set root with minus mostNegtiePostiton to avoid negative index for any node
		// becaose if root is at vertialLevel: minus mostNegtiePostiton -(-3) = 3
		// then the actual node at mostNegtiePostiton will be at vertialLevel: 0
		qu.addLast(new TreeNodeVerticalLevel(root, minusMaxNegativePosition));

		while (qu.size() != 0) {
			int size = qu.size();
			while (size-- > 0) {
				TreeNodeVerticalLevel rn = qu.removeFirst();
				TreeNode tn = rn.treeNode;
				int vl = rn.vl;
				ans.get(vl).add(tn);

				if (tn.left != null) {
					qu.addLast(new TreeNodeVerticalLevel(tn.left, vl - 1));
				}
				if (tn.right != null) {
					qu.addLast(new TreeNodeVerticalLevel(tn.right, vl + 1));
				}
			}
		}

		return ans;
	}









	public static ArrayList<TreeNode> topView(TreeNode root) {
		ArrayList<TreeNode> ans = new ArrayList<>();
		if (root == null) {
			return ans;
		}

		int[] arr = width(root);
		int w = arr[1] - arr[0] + 1;
		for (int i = 0; i < w; i++) {
			ans.add(null);
		}

		LinkedList<TreeNodeVerticalLevel> qu = new LinkedList<>();
		int minusMaxNegativePosition = -arr[0];
		qu.addLast(new TreeNodeVerticalLevel(root, minusMaxNegativePosition));

		while (qu.size() != 0) {
			int size = qu.size();
			while (size-- > 0) {
				TreeNodeVerticalLevel rn = qu.removeFirst();
				TreeNode tn = rn.treeNode;
				int vl = rn.vl;
				if (ans.get(vl) == null) {
					ans.set(vl, tn);
				}

				if (tn.left != null) {
					qu.addLast(new TreeNodeVerticalLevel(tn.left, vl - 1));
				}
				if (tn.right != null) {
					qu.addLast(new TreeNodeVerticalLevel(tn.right, vl + 1));
				}
			}
		}

		return ans;
	}









	public static ArrayList<TreeNode> bottomView(TreeNode root) {
		ArrayList<TreeNode> ans = new ArrayList<>();
		if (root == null) {
			return ans;
		}

		int[] arr = width(root);
		int w = arr[1] - arr[0] + 1;
		for (int i = 0; i < w; i++) {
			ans.add(null);
		}

		LinkedList<TreeNodeVerticalLevel> qu = new LinkedList<>();
		int minusMaxNegativePosition = -arr[0];
		qu.addLast(new TreeNodeVerticalLevel(root, minusMaxNegativePosition));

		while (qu.size() != 0) {
			int size = qu.size();
			while (size-- > 0) {
				TreeNodeVerticalLevel rn = qu.removeFirst();
				TreeNode tn = rn.treeNode;
				int vl = rn.vl;
				ans.set(vl, tn);

				if (tn.left != null) {
					qu.addLast(new TreeNodeVerticalLevel(tn.left, vl - 1));
				}
				if (tn.right != null) {
					qu.addLast(new TreeNodeVerticalLevel(tn.right, vl + 1));
				}
			}
		}

		return ans;
	}









	public static ArrayList<Integer> verticalSum(TreeNode root) {
		ArrayList<Integer> ans = new ArrayList<>();
		if (root == null) {
			return ans;
		}

		int[] arr = width(root);
		int w = arr[1] - arr[0] + 1;
		for (int i = 0; i < w; i++) {
			ans.add(0);
		}

		LinkedList<TreeNodeVerticalLevel> qu = new LinkedList<>();
		int minusMaxNegativePosition = -arr[0];
		qu.addLast(new TreeNodeVerticalLevel(root, minusMaxNegativePosition));

		while (qu.size() != 0) {
			int size = qu.size();
			while (size-- > 0) {
				TreeNodeVerticalLevel rn = qu.removeFirst();
				TreeNode tn = rn.treeNode;
				int vl = rn.vl;
				ans.set(vl, ans.get(vl) + tn.val);

				if (tn.left != null) {
					qu.addLast(new TreeNodeVerticalLevel(tn.left, vl - 1));
				}
				if (tn.right != null) {
					qu.addLast(new TreeNodeVerticalLevel(tn.right, vl + 1));
				}
			}
		}

		return ans;
	}










	// dl - diagonal level
	public static void diagonalWidth_helper(TreeNode root, int dl, int[] arr) {
		if (root == null) {
			return;
		}

		arr[0] = Math.min(arr[0], dl);
		arr[1] = Math.max(arr[1], dl);

		diagonalWidth_helper(root.left, dl - 1, arr);
		diagonalWidth_helper(root.right, dl, arr);
	}
	public static int[] diagonalWidth(TreeNode root) {
		int[] arr = new int[] {0, 0};
		diagonalWidth_helper(root, 0, arr);
		return arr;
	}

	public static ArrayList<ArrayList<TreeNode>> leftDiagonalOrderTraversal(TreeNode root) {
		if (root == null) {
			return null;
		}

		ArrayList<ArrayList<TreeNode>> ans = new ArrayList<>();
		int[] arr = diagonalWidth(root);
		int dw = 0 - arr[0] + 1;
		for (int i = 0; i < dw; i++) {
			ans.add(new ArrayList<>());
		}

		LinkedList<TreeNodeVerticalLevel> qu = new LinkedList<>();
		int minusMaxNegativePosition = -arr[0];
		qu.addLast(new TreeNodeVerticalLevel(root, minusMaxNegativePosition));

		while (qu.size() != 0) {
			int size = qu.size();
			while (size-- > 0) {
				TreeNodeVerticalLevel rn = qu.removeFirst();
				TreeNode tn = rn.treeNode;
				int vl = rn.vl;
				ans.get(vl).add(tn);

				if (tn.left != null) {
					qu.addLast(new TreeNodeVerticalLevel(tn.left, vl - 1));
				}
				if (tn.right != null) {
					qu.addLast(new TreeNodeVerticalLevel(tn.right, vl));
				}
			}
		}

		return ans;
	}









	// MEMORIZE
	// NOT IMPORTANT
	// gfg Diagonal Traversal of Binary Tree
	public static ArrayList<Integer> diagonal(TreeNode root) {
		if (root == null) {
			return null;
		}

		ArrayList<ArrayList<TreeNode>> ans = new ArrayList<>();
		LinkedList<TreeNode> qu = new LinkedList<>();
		qu.addLast(root);
		while (qu.size() != 0) {
			ArrayList<TreeNode> smallAns = new ArrayList<>();
			int size = qu.size();
			while (size-- > 0) {
				TreeNode rn = qu.removeFirst();
				while (rn != null) {
					smallAns.add(rn);

					if (rn.left != null) {
						qu.addLast(rn.left);
					}

					rn = rn.right;
				}
			}
			ans.add(smallAns);
		}

		// printListOfListOfTreeNode(ans);
		ArrayList<Integer> finalAns = new ArrayList<>();
		for (var x : ans) {
			for (var y : x) {
				finalAns.add(y.val);
			}
		}
		return finalAns;
	}









	public static ArrayList<Integer> diagonalSum(TreeNode root) {
		ArrayList<Integer> ans = new ArrayList<>();
		if (root == null) {
			return ans;
		}

		int[] arr = diagonalWidth(root);
		int dw = 0 - arr[0] + 1;
		for (int i = 0; i < dw; i++) {
			ans.add(0);
		}

		LinkedList<TreeNodeVerticalLevel> qu = new LinkedList<>();
		int minusMaxNegativePosition = -arr[0];
		qu.addLast(new TreeNodeVerticalLevel(root, minusMaxNegativePosition));

		while (qu.size() != 0) {
			int size = qu.size();
			while (size-- > 0) {
				TreeNodeVerticalLevel rn = qu.removeFirst();
				TreeNode tn = rn.treeNode;
				int vl = rn.vl;
				ans.set(vl, ans.get(vl) + tn.val);

				if (tn.left != null) {
					qu.addLast(new TreeNodeVerticalLevel(tn.left, vl - 1));
				}
				if (tn.right != null) {
					qu.addLast(new TreeNodeVerticalLevel(tn.right, vl));
				}
			}
		}

		return ans;
	}









	// 987. Vertical Order Traversal of a Binary Tree
	// if both horizontal & vertical level are same(nodes are overlapping) then we give min -> max preference
	public List<List<Integer>> verticalTraversal(TreeNode root) {
		if (root == null) {
			return null;
		}

		List<List<Integer>> ans = new ArrayList<>();
		int[] arr = width(root);
		int w = arr[1] - arr[0] + 1;
		for (int i = 0; i < w; i++) {
			ans.add(new ArrayList<>());
		}

		PriorityQueue<THVPair> qu = new PriorityQueue<>((a, b) -> {
			if (a.hl == b.hl) {
				if (a.vl == b.vl) {
					return a.treeNode.val - b.treeNode.val; // 3rd min node value will come first
				} else {
					return a.vl - b.vl; // 2nd min vertical level will come first
				}
			} else {
				return a.hl - b.hl; // 1st min horizontal level will come first
			}
		});
		int minusMaxNegativePosition = -arr[0];
		// root horizontal level = 0
		// root vertical level = minusMaxNegativePosition
		qu.add(new THVPair(root, 0, minusMaxNegativePosition));

		while (qu.size() != 0) {
			int size = qu.size();
			while (size-- > 0) {
				THVPair rn = qu.poll();
				TreeNode tn = rn.treeNode;
				int hl = rn.hl;
				int vl = rn.vl;
				ans.get(vl).add(tn.val);

				if (tn.left != null) {
					qu.add(new THVPair(tn.left, hl + 1 , vl - 1));
				}
				if (tn.right != null) {
					qu.add(new THVPair(tn.right, hl + 1 , vl + 1));
				}
			}
		}

		return ans;
	}









	// 103. Binary Tree Zigzag Level Order Traversal
	public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
		List<List<Integer>> ans = new ArrayList<>();
		if (root == null) {
			return ans;
		}

		LinkedList<TreeNode> qu = new LinkedList<>();
		qu.addLast(root);
		boolean leftToRight = true;
		while (qu.size() != 0) {
			int size = qu.size();
			LinkedList<Integer> smallAns = new LinkedList<>();
			while (size-- > 0) {
				TreeNode rn = qu.removeFirst();
				if (leftToRight) {
					smallAns.addLast(rn.val);
				} else {
					smallAns.addFirst(rn.val);
				}

				if (rn.left != null) {
					qu.addLast(rn.left);
				}
				if (rn.right != null) {
					qu.addLast(rn.right);
				}
			}
			ans.add(smallAns);
			leftToRight = !leftToRight;
		}

		return ans;
	}














































































































	// TODO
	// bottom view print all if node are overalpping













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
		n3.left = n8;
		n5.left = n6;
		n5.right = n7;
		//     1
		//    / \
		//   2   3
		//  / \	/
		// 4   58
		//    / \
		//   6   7

		// printTree(root);
		// levelOrderTraversal(root);
		// printListOfTreeNode(leftView(root));
		// printListOfTreeNode(rightView(root));
		// int[] width = width(root);
		// System.out.println(width[0] + " " + width[1]);
		// printListOfListOfTreeNode(verticalOrderTraversal(root));
		// printListOfTreeNode(topView(root));
		// printListOfTreeNode(bottomView(root));
		// System.out.println(verticalSum(root));
		// printListOfListOfTreeNode(leftDiagonalOrderTraversal(root));
		// System.out.println(diagonalSum(root));
		// System.out.println(diagonal(root));
	}
}