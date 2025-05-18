import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.Collections;


public class TraversalSet {

	public static class TreeNode {
		int val = 0;
		TreeNode left = null;
		TreeNode right = null;
		TreeNode(int val) {
			this.val = val;
		}
	}

	public static class Node {
		int data = 0;
		Node left = null;
		Node right = null;
		Node(int data) {
			this.data = data;
		}
	}

	public static TreeNode getRightMostNode(TreeNode root, TreeNode curr) {
		if (root == null) {
			return null;
		}

		TreeNode n = root;
		while (n.right != null && n.right != curr) {
			n = n.right;
		}

		return n;
	}
	// thinking order
	// 1. left
	// 		1.1 if null
	// 		1.2 else
	// 2. rightMost
	// 		2.1 if null
	// 		2.2 else
	public static ArrayList<TreeNode> morrisInOrderTraversal(TreeNode root) {
		if (root == null) {
			return null;
		}

		ArrayList<TreeNode> ans = new ArrayList<>();
		TreeNode curr = root;

		while (curr != null) {
			TreeNode currKaLeft = curr.left;
			if (currKaLeft == null) {
				ans.add(curr);
				curr = curr.right;
			} else {
				TreeNode rightMostNode = getRightMostNode(currKaLeft, curr);
				if (rightMostNode.right == null) { // entry
					rightMostNode.right = curr; // thread creation
					curr = curr.left;
				} else { // rightMostNode.right == curr // exit
					rightMostNode.right = null; // thread deletion
					ans.add(curr);
					curr = curr.right;
				}
			}
		}

		return ans;
	}

	// 1 change from inorder -> add node at the time of thread creation not deletion
	public static ArrayList<TreeNode> morrisPreOrderTraversal(TreeNode root) {
		if (root == null) {
			return null;
		}

		ArrayList<TreeNode> ans = new ArrayList<>();
		TreeNode curr = root;

		while (curr != null) {
			TreeNode currKaLeft = curr.left;
			if (currKaLeft == null) {
				ans.add(curr);
				curr = curr.right;
			} else {
				TreeNode rightMostNode = getRightMostNode(currKaLeft, curr);
				if (rightMostNode.right == null) { // entry
					rightMostNode.right = curr; // thread creation
					ans.add(curr);
					curr = curr.left;
				} else { // rightMostNode.right == curr // exit
					rightMostNode.right = null; // thread deletion
					// ans.add(curr);
					curr = curr.right;
				}
			}
		}

		return ans;
	}









	// // 98. Validate Binary Search Tree
	// // [0] -> 1 if valid , 0 if invalid
	// // [1] -> min ele of Tree
	// // [2] -> max ele of Tree
	// public long[] isValidBSTminMax(TreeNode root) {
	// 	if (root == null) {
	// 		return new long[] {1, (long)1e18, -(long)1e18};
	// 	}
	// 	if (root.left == null && root.right == null) {
	// 		return new long[] {1, root.val, root.val};
	// 	}

	// 	long[] l = isValidBSTminMax(root.left);
	// 	if (l[0] == 0) {
	// 		return new long[] {0, 0, 0};
	// 	}
	// 	long[] r = isValidBSTminMax(root.right);
	// 	if (r[0] == 0) {
	// 		return new long[] {0, 0, 0};
	// 	}

	// 	if (l[2] < root.val && r[1] > root.val) {
	// 		return new long[] {1, Math.min(root.val, l[1]), Math.max(root.val, r[2])};
	// 	}

	// 	return new long[] {0, 0, 0};
	// }
	// public boolean isValidBST(TreeNode root) {
	// 	long[] ans = isValidBSTminMax(root);
	// 	if (ans[0] == 0) {
	// 		return false;
	// 	}
	// 	return true;
	// }

	// // 98. Validate Binary Search Tree
	// // inorder of BST is sorted
	// public static TreeNode getRightMostNode(TreeNode root, TreeNode curr) {
	// 	if (root == null) {
	// 		return null;
	// 	}

	// 	TreeNode n = root;
	// 	while (n.right != null && n.right != curr) {
	// 		n = n.right;
	// 	}

	// 	return n;
	// }
	// public boolean isValidBST(TreeNode root) {
	// 	if (root == null) {
	// 		return true;
	// 	}

	// 	boolean flag = true;
	// 	long prev = -(long)1e18;
	// 	// ArrayList<TreeNode> ans = new ArrayList<>();
	// 	TreeNode curr = root;

	// 	while (curr != null) {
	// 		TreeNode currKaLeft = curr.left;
	// 		if (currKaLeft == null) {
	// 			// ans.add(curr);
	// 			if (prev >= curr.val) {
	// 				flag = false;
	// 			}
	// 			prev = curr.val;
	// 			curr = curr.right;
	// 		} else {
	// 			TreeNode rightMostNode = getRightMostNode(currKaLeft, curr);
	// 			if (rightMostNode.right == null) { // entry
	// 				rightMostNode.right = curr; // thread creation
	// 				curr = curr.left;
	// 			} else { // rightMostNode.right == curr // exit
	// 				rightMostNode.right = null; // thread deletion
	// 				// ans.add(curr);
	// 				if (prev >= curr.val) {
	// 					flag = false;
	// 				}
	// 				prev = curr.val;
	// 				curr = curr.right;
	// 			}
	// 		}
	// 	}

	// 	return flag;
	// }

	// // 98. Validate Binary Search Tree
	// // replicate recursion stack of inorder traversal in normal stack
	// public void insertAllLeftNodes(TreeNode root, LinkedList<TreeNode> st) {
	// 	TreeNode curr = root;
	// 	while (curr != null) {
	// 		st.addLast(curr);
	// 		curr = curr.left;
	// 	}
	// }
	// public boolean isValidBST(TreeNode root) {
	// 	if (root == null) {
	// 		return true;
	// 	}

	// 	LinkedList<TreeNode> st = new LinkedList<>();
	// 	insertAllLeftNodes(root, st);
	// 	long prev = -(long)1e18;

	// 	while (st.size() != 0) {
	// 		TreeNode rNode = st.removeLast();

	// 		// now the top element will give inorder traversal
	// 		// System.out.println(rNode.val);
	// 		if (prev >= rNode.val) {
	// 			return false;
	// 		}
	// 		prev = rNode.val;

	// 		insertAllLeftNodes(rNode.right, st);
	// 	}

	// 	return true;
	// }









	// // MEMORIZE
	// // 173. Binary Search Tree Iterator
	// // replicate recursion stack of inorder traversal in normal stack
	// class BSTIterator {
	// 	TreeNode root = null;
	// 	LinkedList<TreeNode> st = null;

	// 	public BSTIterator(TreeNode root) {
	// 		this.root = root;
	// 		this.st = new LinkedList<>();
	// 		insertAllLeftNodes(this.root, this.st);
	// 	}

	// 	public void insertAllLeftNodes(TreeNode root, LinkedList<TreeNode> st) {
	// 		TreeNode curr = root;
	// 		while (curr != null) {
	// 			st.addLast(curr);
	// 			curr = curr.left;
	// 		}
	// 	}

	// 	public int next() {
	// 		TreeNode ans = st.removeLast();
	// 		insertAllLeftNodes(ans.right, this.st);
	// 		return ans.val;
	// 	}

	// 	public boolean hasNext() {
	// 		return st.size() != 0;
	// 	}
	// }

	// // MEMORIZE
	// // 173. Binary Search Tree Iterator
	// // morris inorder traversal
	// class BSTIterator {
	// 	TreeNode curr = null;

	// 	public BSTIterator(TreeNode root) {
	// 		this.curr = root;
	// 	}

	// 	public TreeNode getRightMostNode(TreeNode root, TreeNode curr) {
	// 		if (root == null) {
	// 			return null;
	// 		}

	// 		TreeNode n = root;
	// 		while (n.right != null && n.right != curr) {
	// 			n = n.right;
	// 		}

	// 		return n;
	// 	}
	// 	public int morrisInOrderTraversal() {
	// 		int ans = -1;
	// 		// ArrayList<TreeNode> ans = new ArrayList<>();

	// 		while (curr != null) {
	// 			TreeNode currKaLeft = curr.left;
	// 			if (currKaLeft == null) {
	// 				// ans.add(curr);
	// 				ans = curr.val;
	// 				curr = curr.right;
	// 				break;
	// 			} else {
	// 				TreeNode rightMostNode = getRightMostNode(currKaLeft, curr);
	// 				if (rightMostNode.right == null) { // entry
	// 					rightMostNode.right = curr; // thread creation
	// 					curr = curr.left;
	// 				} else { // rightMostNode.right == curr // exit
	// 					rightMostNode.right = null; // thread deletion
	// 					// ans.add(curr);
	// 					ans = curr.val;
	// 					curr = curr.right;
	// 					break;
	// 				}
	// 			}
	// 		}

	// 		return ans;
	// 	}

	// 	public int next() {
	// 		return morrisInOrderTraversal();
	// 	}

	// 	public boolean hasNext() {
	// 		return this.curr != null;
	// 	}
	// }









	// // 230. Kth Smallest Element in a BST
	// public static TreeNode getRightMostNode(TreeNode root, TreeNode curr) {
	// 	if (root == null) {
	// 		return null;
	// 	}

	// 	TreeNode n = root;
	// 	while (n.right != null && n.right != curr) {
	// 		n = n.right;
	// 	}

	// 	return n;
	// }
	// public int kthSmallest(TreeNode root, int k) {
	// 	if (root == null) {
	// 		return 0;
	// 	}

	// 	int ans = 0;
	// 	// ArrayList<TreeNode> ans = new ArrayList<>();
	// 	TreeNode curr = root;

	// 	while (curr != null) {
	// 		TreeNode currKaLeft = curr.left;
	// 		if (currKaLeft == null) {
	// 			// ans.add(curr);
	// 			if (--k == 0) {
	// 				ans = curr.val;
	// 			}
	// 			curr = curr.right;
	// 		} else {
	// 			TreeNode rightMostNode = getRightMostNode(currKaLeft, curr);
	// 			if (rightMostNode.right == null) { // entry
	// 				rightMostNode.right = curr; // thread creation
	// 				curr = curr.left;
	// 			} else { // rightMostNode.right == curr // exit
	// 				rightMostNode.right = null; // thread deletion
	// 				// ans.add(curr);
	// 				if (--k == 0) {
	// 					ans = curr.val;
	// 				}
	// 				curr = curr.right;
	// 			}
	// 		}
	// 	}

	// 	return ans;
	// }

	// // gfg Kth largest element in BST
	// public TreeNode getLeftMostNode(TreeNode currKaRight, TreeNode curr) {
	// 	TreeNode n = currKaRight;
	// 	while (n.left != null && n.left != curr) {
	// 		n = n.left;
	// 	}
	// 	return n;
	// }
	// public int kthLargest(TreeNode root, int k) {
	// 	int ans = 0;
	// 	TreeNode curr = root;
	// 	// ArrayList<TreeNode> ans = new ArrayList<>();
	// 	while (curr != null) {
	// 		TreeNode currKaRight = curr.right;
	// 		// ans.add(root.data);
	// 		if (currKaRight == null) {
	// 			if (--k == 0) {
	// 				ans = curr.data;
	// 			}
	// 			curr = curr.left;
	// 		} else {
	// 			TreeNode leftMostNode = getLeftMostNode(currKaRight, curr);
	// 			if (leftMostNode.left == null) { // entry
	// 				leftMostNode.left = curr; // thread creation
	// 				curr = curr.right;
	// 			} else { // exit
	// 				leftMostNode.left = null; // thread deletion
	// 				// ans.add(root.data);
	// 				if (--k == 0) {
	// 					ans = curr.data;
	// 				}
	// 				curr = curr.left;
	// 			}
	// 		}
	// 	}

	// 	return ans;
	// }









	// // gfg Binary Tree to DLL
	// TreeNode getRightMostNode(TreeNode root, TreeNode curr) {
	// 	if (root == null) {
	// 		return null;
	// 	}

	// 	TreeNode n = root;
	// 	while (n.right != null && n.right != curr) {
	// 		n = n.right;
	// 	}

	// 	return n;
	// }
	// TreeNode bToDLL(TreeNode root) {
	// 	if (root == null) {
	// 		return null;
	// 	}

	// 	TreeNode dummy = new TreeNode(-1);
	// 	TreeNode prev = dummy;

	// 	// ArrayList<TreeNode> ans = new ArrayList<>();
	// 	TreeNode curr = root;

	// 	while (curr != null) {
	// 		TreeNode currKaLeft = curr.left;
	// 		if (currKaLeft == null) {
	// 			// ans.add(curr);
	// 			prev.right = curr;
	// 			curr.left = prev;
	// 			prev = curr;
	// 			curr = curr.right;
	// 		} else {
	// 			TreeNode rightMostNode = getRightMostNode(currKaLeft, curr);
	// 			if (rightMostNode.right == null) { // entry
	// 				rightMostNode.right = curr; // thread creation
	// 				curr = curr.left;
	// 			} else { // rightMostNode.right == curr // exit
	// 				rightMostNode.right = null; // thread deletion
	// 				// ans.add(curr);
	// 				prev.right = curr;
	// 				curr.left = prev;
	// 				prev = curr;
	// 				curr = curr.right;
	// 			}
	// 		}
	// 	}


	// 	TreeNode ans = dummy.right;
	// 	dummy.right = null;
	// 	ans.left = null;
	// 	return ans;
	// }

	// // gfg Binary Tree to CDLL
	// TreeNode getRightMostNode(TreeNode root, TreeNode curr) {
	// 	if (root == null) {
	// 		return null;
	// 	}

	// 	TreeNode n = root;
	// 	while (n.right != null && n.right != curr) {
	// 		n = n.right;
	// 	}

	// 	return n;
	// }
	// TreeNode bToDLL(TreeNode root) {
	// 	if (root == null) {
	// 		return null;
	// 	}

	// 	TreeNode dummy = new TreeNode(-1);
	// 	TreeNode prev = dummy;

	// 	// ArrayList<TreeNode> ans = new ArrayList<>();
	// 	TreeNode curr = root;

	// 	while (curr != null) {
	// 		TreeNode currKaLeft = curr.left;
	// 		if (currKaLeft == null) {
	// 			// ans.add(curr);
	// 			prev.right = curr;
	// 			curr.left = prev;
	// 			prev = curr;
	// 			curr = curr.right;
	// 		} else {
	// 			TreeNode rightMostNode = getRightMostNode(currKaLeft, curr);
	// 			if (rightMostNode.right == null) { // entry
	// 				rightMostNode.right = curr; // thread creation
	// 				curr = curr.left;
	// 			} else { // rightMostNode.right == curr // exit
	// 				rightMostNode.right = null; // thread deletion
	// 				// ans.add(curr);
	// 				prev.right = curr;
	// 				curr.left = prev;
	// 				prev = curr;
	// 				curr = curr.right;
	// 			}
	// 		}
	// 	}


	// 	TreeNode ans = dummy.right;
	// 	dummy.right = null;
	// 	ans.left = null;

	// 	// for circular
	// 	prev.right = ans;
	// 	ans.left = prev;

	// 	return ans;
	// }









	// public static TreeNode getRightMostNode(TreeNode root, TreeNode curr) {
	// 	if (root == null) {
	// 		return null;
	// 	}

	// 	TreeNode n = root;
	// 	while (n.right != null && n.right != curr) {
	// 		n = n.right;
	// 	}

	// 	return n;
	// }
	public static void inorderPredecessorSuccessorBT(TreeNode root, int target) {
		TreeNode prev = null;
		// ArrayList<TreeNode> ans = new ArrayList<>();
		TreeNode pred = null;
		TreeNode succ = null;

		TreeNode curr = root;
		while (curr != null) {
			TreeNode currKaLeft = curr.left;
			if (currKaLeft == null) {
				// ans.add(curr);
				if (curr.val == target) {
					pred = prev;
				}
				if (prev != null && prev.val == target) {
					succ = curr;
				}
				prev = curr;
				curr = curr.right;
			} else {
				TreeNode rightMostNode = getRightMostNode(currKaLeft, curr);
				if (rightMostNode.right == null) { // thread creation
					rightMostNode.right = curr;
					curr = curr.left;
				} else { // thread deletion
					rightMostNode.right = null;
					// ans.add(curr);
					if (curr.val == target) {
						pred = prev;
					}
					if (prev != null && prev.val == target) {
						succ = curr;
					}
					prev = curr;
					curr = curr.right;
				}
			}
		}

		System.out.println("target: " + target);
		System.out.println("pred: " + (pred != null ? pred.val : "null"));
		System.out.println("succ: " + (succ != null ? succ.val : "null"));
	}

	// gfg Inorder Successor in BST
	// for succ
	public static TreeNode getLeftMostNode(TreeNode root) {
		if (root == null) {
			return null;
		}
		TreeNode curr = root;
		while (curr.left != null) {
			curr = curr.left;
		}
		return curr;
	}
	// for pred
	public static TreeNode getRightMostNode(TreeNode root) {
		if (root == null) {
			return null;
		}
		TreeNode curr = root;
		while (curr.right != null) {
			curr = curr.right;
		}
		return curr;
	}
	public static void inorderPredecessorSuccessorBST(TreeNode root, int target) {
		if (root == null) {
			return;
		}

		TreeNode pred = null;
		TreeNode succ = null;
		TreeNode curr = root;
		while (curr != null) {
			if (curr.val == target) {
				TreeNode currKaRight = curr.right;
				TreeNode lmn = getLeftMostNode(currKaRight);
				if (lmn != null) {
					succ = lmn;
				}

				TreeNode currKaLeft = curr.left;
				TreeNode rmn = getRightMostNode(currKaLeft);
				if (rmn != null) {
					pred = rmn;
				}

				break;
			} else if (target < curr.val) {
				// jab bhi left me jao to curr potential successor ban sakta hai kyuki curr > target
				succ = curr;
				curr = curr.left;
			} else {
				// jab bhi right me jao to curr potential predecesor ban sakta hai kyuki curr < target
				pred = curr;
				curr = curr.right;
			}
		}

		System.out.println("target: " + target);
		System.out.println("pred: " + (pred != null ? pred.val : "null"));
		System.out.println("succ: " + (succ != null ? succ.val : "null"));
	}

	// gfg Ceil & Floor BST
	public static void ceilFloorBST(TreeNode root, int target) {
		if (root == null) {
			return;
		}

		TreeNode floor = null;
		TreeNode ceil = null;
		TreeNode curr = root;
		while (curr != null) {
			if (curr.val == target) {
				ceil = curr;
				floor = curr;
				break;
			} else if (target < curr.val) {
				// jab bhi left me jao to curr potential ceilessor ban sakta hai kyuki curr > target
				ceil = curr;
				curr = curr.left;
			} else {
				// jab bhi right me jao to curr potential floorecesor ban sakta hai kyuki curr < target
				floor = curr;
				curr = curr.right;
			}
		}

		System.out.println("target: " + target);
		System.out.println("floor: " + (floor != null ? floor.val : "null"));
		System.out.println("ceil: " + (ceil != null ? ceil.val : "null"));
	}









	// 99. Recover Binary Search Tree
	public TreeNode getRightMostNode(TreeNode node, TreeNode curr) {
		while (node.right != null && node.right != curr) {
			node = node.right;
		}

		return node;
	}

	public void recoverTree(TreeNode root) {
		if (root == null) {
			return;
		}

		TreeNode prev = null;
		TreeNode curr = root;
		TreeNode first = null;
		TreeNode second = null;
		while (curr != null) {
			TreeNode currKaLeft = curr.left;
			if (currKaLeft == null) {
				// ans.add(curr);
				if (prev != null && prev.val > curr.val) {
					if (first == null) {
						first = prev;
						second = curr; // it can potentially be second but not exactly
					} else {
						second = curr;
					}
				}
				prev = curr;
				curr = curr.right;
			} else {
				TreeNode rmn = getRightMostNode(currKaLeft, curr);
				if (rmn.right == null) { // thread creation
					rmn.right = curr;
					curr = curr.left;
				} else { // thread deletion
					rmn.right = null;
					// ans.add(curr);
					if (prev != null && prev.val > curr.val) {
						if (first == null) {
							first = prev;
							second = curr; // it can potentially be second but not exactly
						} else {
							second = curr;
						}
					}
					prev = curr;
					curr = curr.right;
				}
			}
		}

		int x = first.val;
		first.val = second.val;
		second.val = x;
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
		printTree(root);

		// System.out.println(size(root));
		// System.out.println(height(root));
		// System.out.println(maximum(root));
		// System.out.println(minimum(root));
		// System.out.println(find(root, 4));

		// displayListOfTreeNodes(rootToNodePath(root, 33));
		// displayListOfTreeNodes(nodeToRootPath(root, 3));

		// displayListOfTreeNodes(morrisInOrderTraversal(root));
		// displayListOfTreeNodes(morrisPreOrderTraversal(root));

		// int[] arr = new int[1]; arr[0] = 0;
		// ArrayList<Integer> al = new ArrayList<>();
		// ArrayList<TreeNode> ans = new ArrayList<>();
		// ArrayList<ArrayList<TreeNode>> finalAns = new ArrayList<>();
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

		// inorderPredecessorSuccessorBT(root, 0);
		// inorderPredecessorSuccessorBST(root, 0);
		// ceilFloorBST(root, 9);
	}
}