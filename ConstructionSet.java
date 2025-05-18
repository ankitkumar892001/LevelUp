import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.Collections;
import java.util.Queue;


public class ConstructionSet {

	public static class TreeNode {
		int val = 0;
		TreeNode left = null;
		TreeNode right = null;
		TreeNode(int val) {
			this.val = val;
		}
	}


	public static TreeNode getMidNode(TreeNode head) {
		if (head == null || head.right == null) {
			return head;
		}

		TreeNode s = head;
		TreeNode f = head;
		while (f != null && f.right != null && f.right.right != null) {
			s = s.right;
			f = f.right.right;
		}

		return s;
	}

	public static TreeNode dllToBT(TreeNode head) {
		if (head == null) {
			return null;
		}

		TreeNode mid = getMidNode(head);
		TreeNode l = mid.left;
		TreeNode r = mid.right;
		mid.left = null;
		mid.right = null;

		TreeNode root = mid;
		// MEMORIZE
		if (l != null) {
			l.right = null;
			root.left = dllToBT(head);
		}
		if (r != null) {
			r.left = null;
			root.right = dllToBT(r);
		}

		return root;
	}










	// // gfg Binary Tree to DLL
	// public static TreeNode getRightMostTreeNode(TreeNode root, TreeNode curr) {
	// 	if (root == null) {
	// 		return null;
	// 	}

	// 	TreeNode n = root;
	// 	while (n.right != null && n.right != curr) {
	// 		n = n.right;
	// 	}

	// 	return n;
	// }
	// public static TreeNode bToDLL(TreeNode root) {
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
	// 			TreeNode rightMostTreeNode = getRightMostTreeNode(currKaLeft, curr);
	// 			if (rightMostTreeNode.right == null) { // entry
	// 				rightMostTreeNode.right = curr; // thread creation
	// 				curr = curr.left;
	// 			} else { // rightMostTreeNode.right == curr // exit
	// 				rightMostTreeNode.right = null; // thread deletion
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
	// // Merge Sort
	// public static TreeNode merge2SortedLinkedList(TreeNode h1, TreeNode h2) {
	// 	if (h1 == null) {
	// 		return h2;
	// 	}
	// 	if (h2 == null) {
	// 		return h1;
	// 	}
	// 	TreeNode dummy = new TreeNode(-1);
	// 	TreeNode p = dummy;
	// 	TreeNode c1 = h1;
	// 	TreeNode c2 = h2;
	// 	while (c1 != null && c2 != null) {
	// 		if (c1.val <= c2.val) {
	// 			p.right = c1;
	// 			c1.left = p;
	// 			c1 = c1.right;
	// 		} else {
	// 			p.right = c2;
	// 			c2.left = p;
	// 			c2 = c2.right;
	// 		}

	// 		p = p.right;
	// 	}

	// 	if (c1 != null) {
	// 		p.right = c1;
	// 		c1.left = p;
	// 	}
	// 	if (c2 != null) {
	// 		p.right = c2;
	// 		c2.left = p;
	// 	}

	// 	TreeNode ans = dummy.right;
	// 	ans.left = null;
	// 	dummy.right = null;
	// 	return ans;
	// }
	// public static TreeNode mergeSort(TreeNode head) {
	// 	if (head == null || head.right == null) {
	// 		return head;
	// 	}

	// 	TreeNode mid = getMidNode(head);
	// 	TreeNode midKaRight = mid.right;
	// 	mid.right = null;
	// 	midKaRight.left = null;

	// 	TreeNode h1 = mergeSort(head);
	// 	TreeNode h2 = mergeSort(midKaRight);

	// 	return merge2SortedLinkedList(h1, h2);
	// }
	// // DLL to Binary Tree
	// public static TreeNode getMidNode(TreeNode head) {
	// 	if (head == null || head.right == null) {
	// 		return head;
	// 	}

	// 	TreeNode s = head;
	// 	TreeNode f = head;
	// 	while (f != null && f.right != null && f.right.right != null) {
	// 		s = s.right;
	// 		f = f.right.right;
	// 	}

	// 	return s;
	// }
	// public static TreeNode dllToBT(TreeNode head) {
	// 	if (head == null) {
	// 		return null;
	// 	}

	// 	TreeNode mid = getMidNode(head);
	// 	TreeNode l = mid.left;
	// 	TreeNode r = mid.right;
	// 	mid.left = null;
	// 	mid.right = null;

	// 	TreeNode root = mid;
	// 	// MEMORIZE
	// 	if (l != null) {
	// 		l.right = null;
	// 		root.left = dllToBT(head);
	// 	}
	// 	if (r != null) {
	// 		r.left = null;
	// 		root.right = dllToBT(r);
	// 	}

	// 	return root;
	// }
	// // MEMORIZE
	// // gfg Binary Tree to BST
	// // BT -> morrisInOrderTraversal -> DLL -> mergesort-> > Sorted DLL -> construction -> BST
	// public static TreeNode binaryTreeToBST(TreeNode root) {
	// 	if (root == null) {
	// 		return null;
	// 	}

	// 	TreeNode head = bToDLL(root);
	// 	head = mergeSort(head);
	// 	return dllToBT(head);
	// }




	public static TreeNode constructTreeFromInOrder(int[] arr, int si, int ei) {
		if (si > ei) {
			return null;
		}
		if (si == ei) {
			return new TreeNode(arr[si]);
		}

		int mid = (si + ei) / 2;
		TreeNode root = new TreeNode(arr[mid]);
		root.left = constructTreeFromInOrder(arr, si, mid - 1);
		root.right = constructTreeFromInOrder(arr, mid + 1, ei);

		return root;
	}


	// 1008. Construct Binary Search Tree from Preorder Traversal
	public static int getIndexOfNodeGreaterThanRoot(int[] arr, int si, int ei, int rootVal) {
		for (int i = si; i <= ei; i++) {
			if (arr[i] > rootVal) {
				return i;
			}
		}

		return ei + 1;
	}
	public static TreeNode constructBSTFromPreOrder(int[] arr, int si, int ei) {
		if (si > ei) {
			return null;
		}
		if (si == ei) {
			return new TreeNode(arr[si]);
		}

		TreeNode root = new TreeNode(arr[si]);
		int idx = getIndexOfNodeGreaterThanRoot(arr, si + 1, ei, root.val);
		root.left = constructBSTFromPreOrder(arr, si + 1, idx - 1);
		root.right = constructBSTFromPreOrder(arr, idx, ei);

		return root;
	}


	// 1008. Construct Binary Search Tree from Preorder Traversal
	// faith
	// aap jo bhi range pass karoge ye uska BST bna kar dedega
	public static TreeNode constructBSTFromPreOrder(int[] preorder, int lr, int rr, int[] idx) {
		int i = idx[0];
		if (i > preorder.length - 1 || preorder[i] < lr || preorder[i] > rr) {
			return null;
		}

		TreeNode root = new TreeNode(preorder[i]);
		idx[0]++;

		// mere preorder array me se jitne elements mujhse chote honge wo mere left subarray ka part honge
		root.left = constructBSTFromPreOrder(preorder, lr, root.val, idx);
		// mere preorder array me se jitne elements mujhse bade honge wo mere right subarray ka part honge
		root.right = constructBSTFromPreOrder(preorder, root.val, rr, idx);

		return root;
	}
	public TreeNode bstFromPreorder(int[] preorder) {
		int[] idx = new int[] {0};
		return constructBSTFromPreOrder(preorder, -(int)1e9, (int)1e9, idx);
	}




	// gfg Construct BST from Postorder
	public static TreeNode constructBSTFromPostOrder(int[] postorder, int lr, int rr, int[] idx) {
		int i = idx[0];
		if (i < 0 || postorder[i] < lr || postorder[i] > rr) {
			return null;
		}

		TreeNode root = new TreeNode(postorder[i]);
		idx[0]--;

		root.right = constructBSTFromPostOrder(postorder, root.val, rr, idx);
		root.left = constructBSTFromPostOrder(postorder, lr, root.val, idx);

		return root;
	}
	public static TreeNode constructTree(int postorder[], int n) {
		if (postorder == null || postorder.length == 0) {
			return null;
		}
		int[] idx = new int[] {postorder.length - 1};
		return constructBSTFromPostOrder(postorder, -(int)1e9, (int)1e9, idx);
	}




	// TODO
	public static TreeNode constructBSTFromLevelOrder(int[] levelOrder, int si, int ei) {
		return null;
	}


	// 1307 · Verify Preorder Sequence in Binary Search Tree
	int firstGreaterValue(int[] preorder, int si, int ei) {
		for (int i = si + 1; i <= ei; i++) {
			if (preorder[i] > preorder[si]) {
				return i;
			}
		}

		return ei + 1;
	}
	boolean verifyPreorder_(int[] preorder, int si, int ei, int lr, int ur) {
		if (si > ei) {
			return true;
		}

		if (preorder[si] >= ur || preorder[si] <= lr) {
			return false;
		}

		int idx = firstGreaterValue(preorder, si, ei);

		return verifyPreorder_(preorder, si + 1, idx - 1, lr , preorder[si]) && verifyPreorder_(preorder, idx, ei, preorder[si], ur);
	}
	boolean verifyPreorder(int[] preorder) {
		if (preorder == null || preorder.length <= 1) {
			return true;
		}

		return verifyPreorder_(preorder, 0, preorder.length - 1, -(int)1e9, (int)1e9);
	}








	// 449. Serialize and Deserialize BST
	// Encodes a tree to a single string.
	public void preOrder(TreeNode root, sb) {
		if (root == null) {
			return;
		}

		sb.append(root.val + ",");

		preOrder(root.left);
		preOrder(root.right);
	}
	public String serialize(TreeNode root) {
		if (root == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		preOrder(root, sb);
		return sb.toString();
	}

	// Decodes your encoded data to tree.
	public static TreeNode constructBSTFromPreOrder(int[] preorder, int lr, int rr, int[] idx) {
		int i = idx[0];
		if (i > preorder.length - 1 || preorder[i] < lr || preorder[i] > rr) {
			return null;
		}

		TreeNode root = new TreeNode(preorder[i]);
		idx[0]++;

		// mere preorder array me se jitne elements mujhse chote honge wo mere left subarray ka part honge
		root.left = constructBSTFromPreOrder(preorder, lr, root.val, idx);
		// mere preorder array me se jitne elements mujhse bade honge wo mere right subarray ka part honge
		root.right = constructBSTFromPreOrder(preorder, root.val, rr, idx);

		return root;
	}
	public TreeNode bstFromPreorder(int[] preorder) {
		int[] idx = new int[] {0};
		return constructBSTFromPreOrder(preorder, -(int)1e9, (int)1e9, idx);
	}

	public TreeNode deserialize(String str) {
		if (str == "") {
			return null;
		}
		String[] arr1 = str.split(",");
		int[] arr2 = new int[arr1.length];
		for (int i = 0; i < arr1.length; i++) {
			arr2[i] = Integer.parseInt(arr[i]);
		}

		return bstFromPreorder(arr2);
	}







	// 105. Construct Binary Tree from Preorder and Inorder Traversal
	// faith
	// construc tree from given preorder array, preorder range & inorder array, inorder range
	// Best Case - Complete Tree - T: O(nlogn)
	// Worst Case - Skewed Tree - T: O(n^2)
	public TreeNode constructFromPreOrderInOrder(int[] preorder, int psi, int pei, int[] inorder, int isi, int iei) {
		if (psi > pei) {
			return null;
		}
		if (psi == pei) {
			return new TreeNode(preorder[psi]);
		}

		TreeNode root = new TreeNode(preorder[psi]);
		int idx = isi;
		for (int i = isi; i <= iei; i++) {
			if (inorder[i] == root.val) {
				idx = i;
				break;
			}
		}

		// total no of elements in left subtree
		int t = idx - isi;

		root.left = constructFromPreOrderInOrder(preorder, psi + 1, psi + t, inorder, isi, idx - 1);
		root.right = constructFromPreOrderInOrder(preorder, psi + t + 1, pei, inorder, idx + 1, iei);

		return root;
	}
	public TreeNode buildTree(int[] preorder, int[] inorder) {
		if (preorder == null || preorder.length == 0) {
			return null;
		}
		return constructFromPreOrderInOrder(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
	}


	// 106. Construct Binary Tree from Inorder and Postorder Traversal
	public TreeNode constructFromPostOrderInOrder(int[] postorder, int psi, int pei, int[] inorder, int isi, int iei) {
		if (psi > pei) {
			return null;
		}
		if (psi == pei) {
			return new TreeNode(postorder[pei]);
		}

		TreeNode root = new TreeNode(postorder[pei]);
		int idx = isi;
		for (int i = isi; i <= iei; i++) {
			if (inorder[i] == root.val) {
				idx = i;
				break;
			}
		}

		// total no of elements in left subtree
		int t = idx - isi;

		root.left = constructFromPostOrderInOrder(postorder, psi, psi + t - 1, inorder, isi, idx - 1);
		root.right = constructFromPostOrderInOrder(postorder, psi + t, pei - 1, inorder, idx + 1, iei);

		return root;
	}
	public TreeNode buildTree(int[] inorder, int[] postorder) {
		if (postorder == null || postorder.length == 0) {
			return null;
		}
		return constructFromPostOrderInOrder(postorder, 0, postorder.length - 1, inorder, 0, inorder.length - 1);
	}



	// 889. Construct Binary Tree from Preorder and Postorder Traversal
	// we can construct multiple BT from given preorder and postorder
	// preorder 8, 9 | postorder 9, 8
	// 		8 		|		8
	// 	   /		|		 \
	// 	  9 		| 		  9
	// basically we are constructing binary tree from the preorder only
	// but the one that satisfies the postorder array
	// root - preorder first index
	// my left child will come first in preorder
	// my left child will come last in postorder
	public TreeNode constructFromPreOrderPostOrder(int[] preorder, int presi, int preei, int[] postorder, int posi, int poei) {
		if (presi > preei) {
			return null;
		}
		if (presi == preei) {
			return new TreeNode(preorder[presi]);
		}

		TreeNode root = new TreeNode(preorder[presi]);
		int target = preorder[presi + 1];
		int idx = posi;
		for (int i = posi; i <= poei; i++) {
			if (postorder[i] == target) {
				idx = i;
				break;
			}
		}

		// total no of elements in my left subtree
		int t = idx - posi + 1;

		root.left = constructFromPreOrderPostOrder(preorder, presi + 1, presi + t, postorder, posi, posi + t - 1);
		root.right = constructFromPreOrderPostOrder(preorder, presi + t + 1, preei, postorder, posi + t, poei - 1);

		return root;
	}
	public TreeNode constructFromPrePost(int[] preorder, int[] postorder) {
		if (preorder == null || preorder.length == 0 || preorder.length != postorder.length) {
			return null;
		}

		return constructFromPreOrderPostOrder(preorder, 0, preorder.length - 1, postorder , 0, postorder.length - 1);
	}






	// 297. Serialize and Deserialize Binary Tree
	// Encodes a tree to a single string.
	public String serialize(TreeNode root) {
		if (root == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		LinkedList<TreeNode> qu = new LinkedList<>();
		qu.addLast(root);
		while (qu.size() != 0) {
			int size = qu.size();
			while (size-- > 0) {
				TreeNode node = qu.removeFirst();

				if (node == null) {
					sb.append("#" + " ");
					continue;
				}
				sb.append(node.val + " ");

				qu.addLast(node.left);
				qu.addLast(node.right);
			}
		}

		return sb.toString();
	}

	// Decodes your encoded data to tree.
	public TreeNode deserialize(String str) {
		if (str.equals("")) {
			return null;
		}

		String[] arr = str.split(" ");
		int i = 0;
		TreeNode root = new TreeNode(Integer.parseInt(arr[i++]));
		LinkedList<TreeNode> qu = new LinkedList<>();
		qu.addLast(root);

		while (qu.size() != 0) {
			int size  = qu.size();
			while (size-- > 0) {
				TreeNode node = qu.removeFirst();

				if (!arr[i].equals("#")) {
					node.left = new TreeNode(Integer.parseInt(arr[i]));
					qu.addLast(node.left);
				}
				i++;

				if (!arr[i].equals("#")) {
					node.right = new TreeNode(Integer.parseInt(arr[i]));
					qu.addLast(node.right);
				}
				i++;
			}
		}

		return root;
	}



	// 110. Balanced Binary Tree
	public int height(TreeNode root) {
		if (root == null) {
			return 0;
		}

		int l = height(root.left);
		if (l < 0) {
			return -1;
		}
		int r = height(root.right);
		if (r < 0) {
			return -1;
		}

		if (Math.abs(l - r) > 1) {
			return -1;
		}

		return Math.max(l, r) + 1;
	}
	public boolean isBalanced(TreeNode root) {
		if (height(root) < 0) {
			return false;
		}

		return true;
	}



	// gfg Largest BST
	static class TreePair {
		Node node = null;
		int size = 0;
		// for min max always contradict yourself for default dataue
		int min = (int)1e9;
		int max = -(int)1e9;

		TreePair() {
		}

		TreePair(Node node, int size, int min , int max) {
			this.node = node;
			this.size = size;
			this.min = min;
			this.max = max;
		}
	}

	static TreePair largestBst_helper(Node root) {
		if (root == null) {
			return new TreePair(root, 0, (int)1e9, -(int)1e9);
		}
		if (root.left == null && root.right == null) {
			return new TreePair(root, 1, root.data, root.data);
		}

		TreePair l = largestBst_helper(root.left);
		TreePair r = largestBst_helper(root.right);

		if (l.node == root.left && r.node == root.right &&  l.max < root.data && r.min > root.data) {
			// never assume my.min = left.min , because if left was null it will return min = +infinity
			// return new TreePair(root, l.size + r.size + 1, l.min, r.max);
			return new TreePair(root, l.size + r.size + 1, Math.min(l.min, root.data), Math.max(r.max, root.data));
		}

		TreePair myAns = new TreePair();
		if (l.size > r.size) {
			myAns.node = l.node;
			myAns.size = l.size;
		} else {
			myAns.node = r.node;
			myAns.size = r.size;
		}

		return myAns;
	}

	static int largestBst(Node root) {
		if (root == null) {
			return 0;
		}

		return largestBst_helper(root).size;
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
	public static void printDoublyLinkedList(TreeNode head) {
		if (head == null) {
			System.out.println("Empty List");
			return;
		}

		TreeNode curr = head;
		TreeNode tail = null;
		while (curr != null) {
			System.out.print(curr.val);
			tail = curr;
			curr = curr.right;
			if (curr != null) {
				System.out.print(" -> ");
			}
		}

		System.out.println();
		curr = tail;
		while (curr != null) {
			System.out.print(curr.val);
			curr = curr.left;
			if (curr != null) {
				System.out.print(" -> ");
			}
		}
		System.out.println();
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



		// int[] arr = new int[] {1, 2, 3 , 4,  5, 6, 7, 8};
		// printTree(constructTreeFromInOrder(arr, 0 , arr.length - 1));

		// printTree(binaryTreeToBST(root));

		int[] arr = new int[] {4, 2, 1, 3, 6 , 5 , 8 , 7};
		printTree(constructBSTFromPreOrder(arr, 0 , arr.length - 1));


	}


}