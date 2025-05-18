import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collections;


// try to solve BST with iteration more than recursion
public class BST {

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

		while (root.right != null) {
			root = root.right;
		}
		return root.val;
	}

	public static int minimum(TreeNode root) {
		if (root == null) {
			return (int)1e9;
		}

		while (root.left != null) {
			root = root.left;
		}
		return root.val;
	}

	public static boolean find(TreeNode root, int data) {
		if (root == null) {
			return false;
		}

		TreeNode curr = root;
		while (curr != null) {
			if (data == curr.val) {
				return true;
			} else if (data > curr.val) {
				curr = curr.right;
			} else {
				curr = curr.left;
			}
		}

		return false;
	}

	public static ArrayList<TreeNode> nodeToRootPath(TreeNode root, int data) {
		ArrayList<TreeNode> ans = rootToNodePath(root, data);
		Collections.reverse(ans);
		return ans;
	}

	public static ArrayList<TreeNode> rootToNodePath(TreeNode root, int data) {
		if (root == null) {
			return null;
		}

		ArrayList<TreeNode> ans = new ArrayList<>();
		TreeNode curr = root;
		while (curr != null) {
			ans.add(curr);
			if (data == curr.val) {
				return ans;
			} else if (data > curr.val) {
				curr = curr.right;
			} else {
				curr = curr.left;
			}
		}

		return null;
	}


	public static boolean find(TreeNode root, int data) {
		if (root == null) {
			return false;
		}

		TreeNode curr = root;
		while (curr != null) {
			if (data == curr.val) {
				return true;
			} else if (data > curr.val) {
				curr = curr.right;
			} else {
				curr = curr.left;
			}
		}

		return false;
	}

	public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
		if (root == null || p == null || q == null) {
			return null;
		}

		TreeNode curr = root;
		TreeNode lca = null;
		while (curr != null) {
			if (p.val > curr.val && q.val > curr.val) {
				curr = curr.right;
			} else if (p.val < curr.val && q.val < curr.val) {
				curr = curr.left;
			} else {
				lca = curr;
				break;
			}
		}

		// return curr;
		if (lca != null && find(lca, p.val) && find(lca, q.val)) {
			return lca;
		}

		return null;
	}









	// 701. Insert into a Binary Search Tree
	public TreeNode insertIntoBST(TreeNode root, int target) {
		if (root == null) {
			return new TreeNode(target);
		}

		TreeNode curr = root;
		while (curr != null) {
			if (target == curr.val) {
				break;
			} else if (target < curr.val) {
				if (curr.left == null) {
					curr.left = new TreeNode(target);
					break;
				}
				curr = curr.left;
			} else {
				if (curr.right == null) {
					curr.right = new TreeNode(target);
					break;
				}
				curr = curr.right;
			}
		}
		return root;
	}

	// 701. Insert into a Binary Search Tree
	// faith - BST node ko shi position pe add karke root return kar dega
	public TreeNode insertIntoBST(TreeNode root, int target) {
		if (root == null) {
			return new TreeNode(target);
		}

		if (target < root.val) {
			root.left = insertIntoBST(root.left, target);
		} else {
			root.right = insertIntoBST(root.right, target);
		}

		return root;
	}

	// MEMORIZE
	// 450. Delete Node in a BST
	// faith - BST node ko delete kar denga and modified BST return kardega
	public TreeNode getLeftMostNode(TreeNode root) {
		if (root == null) {
			return null;
		}
		TreeNode curr = root;
		while (curr.left != null) {
			curr = curr.left;
		}
		return curr;
	}
	public TreeNode deleteNode(TreeNode root, int target) {
		if (root == null) {
			return root;
		}


		if (root.val == target) {
			if (root.left == null) {
				return root.right;
			}
			if (root.right == null) {
				return root.left;
			}

			TreeNode rootKaRight = root.right;
			TreeNode lmn = getLeftMostNode(rootKaRight);
			// swap with right ka left most node
			root.val = lmn.val;
			// delete right ka left most node
			root.right = deleteNode(root.right, lmn.val);
			return root;
		}

		if (target < root.val) {
			root.left = deleteNode(root.left,  target);
		} else {
			root.right = deleteNode(root.right,  target);
		}

		return root;
	}









	// MEMORIZE
	// 3665 · Inorder Successor in BST II
	public ParentTreeNode getLeftMostNode(ParentTreeNode node) {
		if (node == null) {
			return null;
		}

		ParentTreeNode curr = node;
		while (curr.left != null) {
			curr = curr.left;
		}

		return curr;
	}
	public ParentTreeNode inorderSuccessor(ParentTreeNode node) {
		if (node == null) {
			return null;
		}

		ParentTreeNode curr = node;
		if (curr.right != null) {
			return getLeftMostNode(curr.right);
		}

		while (curr.parent != null && curr.parent.right == curr) {
			curr = curr.parent;
		}

		return curr.parent;
	}



















































	public static void displayListOfListOfInteger(ArrayList<ArrayList<Integer>> al) {
		if (al == null) {
			System.out.println("Empty List");
			return;
		}

		for (ArrayList<Integer> entry : al) {
			System.out.println(entry);
		}
	}
	public static void displayListOfTreeNodes(ArrayList<TreeNode> al) {
		if (al == null) {
			System.out.println("Empty List");
			return;
		}

		for (TreeNode entry : al) {
			System.out.print(entry.val + "->");
		}
		System.out.println();
	}
	public static void displayListOfListOfTreeNodes(ArrayList<ArrayList<TreeNode>> al) {
		if (al == null) {
			System.out.println("Empty List");
			return;
		}

		for (ArrayList<TreeNode> entry : al) {
			displayListOfTreeNodes(entry);
		}
	}



	public static void main(String[] args) {
		System.out.println("Hello World");
		TreeNode n1 = new TreeNode(1);
		TreeNode n2 = new TreeNode(2);
		TreeNode n3 = new TreeNode(3);
		TreeNode n4 = new TreeNode(4);
		TreeNode n5 = new TreeNode(5);
		TreeNode n6 = new TreeNode(6);
		TreeNode n7 = new TreeNode(7);
		TreeNode n8 = new TreeNode(8);
		TreeNode root = n4;

		n4.left = n2;
		n4.right = n6;
		n2.left = n1;
		n2.right = n3;
		n6.left = n5;
		n6.right = n8;
		n8.left = n7;
		//       4
		//     /   \ 
		//   2       6
		//  / \     / \
		// 1   3   5   8
		//            /
		//           7

		// System.out.println(size(root));
		// System.out.println(height(root));
		// System.out.println(maximum(root));
		// System.out.println(minimum(root));
		System.out.println(find(root, 4));
		displayListOfTreeNodes(rootToNodePath(root, 33));
		// displayListOfTreeNodes(nodeToRootPath(root, 3));


		int[] arr = new int[1]; arr[0] = 0;
		ArrayList<Integer> al = new ArrayList<>();
		ArrayList<TreeNode> ans = new ArrayList<>();
		ArrayList<ArrayList<TreeNode>> finalAns = new ArrayList<>();
		// rootToAllLeavesPath(root, ans, finalAns);
		// exactlyOneChild(root, al);
		// System.out.println(exactlyOneChildCount(root));
		// System.out.println(al);
		// displayListOfTreeNodes(ans);
		// displayListOfListOfTreeNodes(finalAns);

		// ArrayList<ArrayList<Integer>> bt = burningTree(root, n8);
		// HashSet<TreeNode> waterSet = new HashSet<>();
		// waterSet.add(n2);
		// ArrayList<ArrayList<Integer>> bt = burningTreeWithWater(root, n5, waterSet);
		// displayListOfListOfInteger(bt);
	}
}