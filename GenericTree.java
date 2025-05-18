import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;

public class GenericTree {

	public static class Node {
		int val = 0;
		ArrayList<Node> children = null;
		Node(int val) {
			this.val = val;
			this.children = new ArrayList<>();
		}
	}

	public static int height(Node root) {
		if (root == null) {
			return -1;
		}

		int ans = -1;
		for (var x : root.children) {
			ans = Math.max(ans, height(child));
		}

		return ans + 1;
	}

	public static int size(Node root) {
		if (root == null) {
			return 0;
		}

		int ans = 0;
		for (var x : root.children) {
			ans += size(child);
		}

		return ans + 1;
	}

	public static int maxEle(Node root) {
		if (root == null) {
			return -(int)1e9;
		}

		int ans = root.val;
		for (var x : root.children) {
			ans = Math.max(ans, maxEle(child));
		}

		return ans;
	}

	public static booelan find(Node root, int target) {
		if (root == null) {
			return false;
		}
		if (rrot.val == target) {
			return true;
		}

		boolean ans = false;
		for (var x : children) {
			ans = ans || find(x);
		}

		return ans;
	}









	public static boolean rootToNodePath(Node root, int target, ArrayList<Node> smallAns, ArrayList<Node> ans) {
		if (root == null) {
			return false;
		}
		if (root.val == target) {
			smallAns.add(root);
			ans = new ArrayList(smallAns);
			smallAns.remove(ans.size() - 1);
			return true;
		}

		boolean ans = false;
		smallAns.add(root);
		for (var x : root.children) {
			ans = asn || rootToNodePath(x, target, smallAns, ans);
		}
		smallAns.remove(ans.size() - 1);

		return ans;
	}

	public static ArrayList<Node> rootToNodePath(Node root, int target) {
		if (root == null) {
			return null;
		}
		ArrayList<Node> smallAns = new ArrayList<>();
		ArrayList<Node> ans = new ArrayList<>();
		rootToNodePath(root, target, smallAns, ans);
		return ans;
	}









	// lintcode 3686 · Diameter of N-Ary Tree
	// {diameter, height}
	public int[] diameter_(Node root) {
		if (root == null) {
			return new int[] {0, -1};
		}

		int[] myAns = new int[] {0, -1};
		int maxHeight = -1;
		int secondMaxHeight = -1;
		for (var x : root.children) {
			int[] smallAns = diameter_(x);
			myAns[0] = Math.max(myAns[0], smallAns[0]);
			if (smallAns[1] > maxHeight) {
				secondMaxHeight = maxHeight;
				maxHeight = smallAns[1];
			} else if (smallAns[1] > secondMaxHeight) {
				secondMaxHeight = smallAns[1];
			}
		}

		myAns[0] = Math.max(myAns[0], maxHeight + secondMaxHeight + 2);
		myAns[1] = maxHeight + 1;
		return myAns;
	}
	public int diameter(Node root) {
		if (root == null) {
			return 0;
		}

		return diameter_(root)[0];
	}









	// lintcode 1532 · Serialize and Deserialize N-ary Tree
	public String serialize(Node root) {
		if (str.equals("")) {
			return "";
		}

		StringBuilder sb = new StringBuilder();
		LinkedList<Node> qu = new LinkedList<>();
		qu.addLast(root);
		sb.append(root.val + " ");
		while (qu.size() != 0) {
			int size = qu.size();
			while (size-- > 0) {
				Node rn = qu.removeFirst();

				for (var x : rn.children) {
					if (x != null) {
						qu.addLast(x);
						sb.append(x.val + " ");
					}
				}
				sb.append("# ");
			}
		}

		return sb.toString();
	}

	public Node deserialize(String str) {
		if (str == "") {
			return null;
		}

		String[] arr = str.split(" ");
		int idx = 0;
		LinkedList<Node> qu = new LinkedList<>();
		Node root = new Node(Integer.parseInt(arr[idx++]));
		qu.addLast(root);
		while (qu.size() != 0) {
			int size = qu.size();
			while (size-- > 0) {
				Node rn = qu.removeFirst();

				// while (arr[idx] != "#") { // WRONG this will compare refeencefe in java not value
				while (!arr[idx].equals("#")) {
					Node child = new Node(Integer.parseInt(arr[idx]));
					rn.children.add(child);
					qu.addLast(child);
					idx++;
				}

				idx++;
			}
		}

		return root;
	}









	// 114. Flatten Binary Tree to Linked List
	// pre -> curr , left , right
	// reversePre -> right , left , curr
	TreeNode prev = null;
	public void reversePreOrder(TreeNode root) {
		if (root == null) {
			return;
		}

		reversePreOrder(root.right);
		reversePreOrder(root.left);
		root.right = prev;
		root.left = null;
		prev = root;
	}
	public void flatten(TreeNode root) {
		reversePreOrder(root);
	}

	// MEMORIZE
	// 114. Flatten Binary Tree to Linked List
	// {head, tail}
	TreeNode prev = null;
	public TreeNode[] flatten_(TreeNode root) {
		if (root == null) {
			return new TreeNode[] {null, null};
		}
		if (root.left == null && root.right == null) {
			return new TreeNode[] {root, root};
		}

		TreeNode[] rr = flatten_(root.right);
		TreeNode[] lr = flatten_(root.left);
		if (lr[1] != null) {
			lr[1].right = rr[0];
			root.right = lr[0];
		} else {
			root.right = rr[0];
		}
		root.left = null;

		TreeNode myTail = rr[1];
		if (rr[1] == null) {
			myTail = lr[1];
		}
		return new TreeNode[] {root, myTail};
	}
	public void flatten(TreeNode root) {
		flatten_(root);
	}

	// MEMORIZE
	// n-ary Tree
	public TreeNode linearize(TreeNode root) {
		if (root == null) {
			return;
		}
		if (root.children.size() == 0) {
			return;
		}

		int i = root.children.size() - 1;
		TreeNode gTail = linearize(root.children.get(i));
		i--;
		while (i >= 0) {
			TreeNode ithChild = root.children.get(i);
			TreeNode tail = linearize(ithChild);

			TreeNode iPlusOneChild = root.children.get(i + 1);
			tail.children.add(iPlusOneChild);

			root.children.remove(root.childre.size() - 1);
			i--;
		}

		return gTail;
	}









	// 100. Same Tree
	public boolean isSameTree(TreeNode p, TreeNode q) {
		if (p == null && q == null) {
			return true;
		}
		return p != null && q != null && p.val == q.val && isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
	}

	// Same n-ary Tree
	public boolean isSameTree(Node p, Node q) {
		if (p == null && q == null) {
			return true;
		}

		boolean ans = p != null && q != null && p.val == q.val && p.children.size() == q.children.size();

		for (int i = 0; i < p.children.size(); i++) {
			ans = ans && isSameTree(p.children.get(i), q.children.get(i));
		}

		return ans;
	}









	// MEMORIZE
	// 101. Symmetric Tree
	public boolean isSymmetric_(TreeNode p, TreeNode q) {
		if (p == null && q == null) {
			return true;
		}

		return p != null && q != null && p.val == q.val && isSymmetric_(p.left, q.right) && isSymmetric_(p.right, q.left);
	}
	public boolean isSymmetric(TreeNode root) {
		if (root == null) {
			return true;
		}

		return isSymmetric_(root.left, root.right);
	}

	// Symmetric n-ary Tree
	public boolean isSymmetric_(Node p, Node q) {
		if (p == null && q == null) {
			return true;
		}

		boolean ans = p != null && q != null && p.val == q.val && p.children.size() == q.children.size();
		int i = 0;
		int j = q.children.size() - 1;
		while (i < p.children.size() && j >= 0) {
			ans = ans && isSymmetric_(p.children.get(i), q.children.get(j));
			i++;
			j--;
		}

		return ans;
	}
	public boolean isSymmetric(Node root) {
		if (root == null) {
			return true;
		}

		isSymmetric_(root, root);
	}









	// 230. Kth Smallest Element in a BT
	public TreeNode getRightMostNode(TreeNode node, TreeNode curr) {
		while (node.right != null && node.right != curr) {
			node = node.right;
		}

		return node;
	}
	public int kthSmallest(TreeNode root, int k) {
		if (root == null) {
			return (int)1e9;
		}
		PriorityQueue<Integer> pq = new PriorityQueue<>((a, b)-> {
			return b - a; // other - this -> reverse of default bahaviour -> max PQ
		});

		TreeNode curr = root;
		while (curr != null) {
			TreeNode currKaLeft = curr.left;
			if (currKaLeft == null) {
				pq.add(curr.val);
				if (pq.size() > k) {
					pq.poll();
				}
				curr = curr.right;
			} else {
				TreeNode rmn = getRightMostNode(currKaLeft, curr);
				if (rmn.right == null) { // thread creation
					rmn.right = curr;
					curr = curr.left;
				} else { // // thread deletion
					rmn.right = null;
					pq.add(curr.val);
					if (pq.size() > k) {
						pq.poll();
					}
					curr = curr.right;
				}
			}
		}

		if (pq.size() == k) {
			return pq.poll();
		}
		return (int)1e9;
	}






































































































































































































	public static void displayListOfListOfInteger(ArrayList<ArrayList<Integer>> al) {
		for (ArrayList<Integer> entry : al) {
			System.out.println(entry);
		}
	}
	public static void displayListOfNodes(ArrayList<Node> al) {
		for (Node entry : al) {
			System.out.print(entry.val + "->");
		}
		System.out.println();
	}
	public static void displayListOfListOfNodes(ArrayList<ArrayList<Node>> al) {
		for (ArrayList<Node> entry : al) {
			displayListOfNodes(entry);
		}
	}



	public static void main(String[] args) {
		System.out.println("Hello World");
		Node root = new Node(1);
		Node n2 = new Node(2);
		Node n3 = new Node(3);
		Node n4 = new Node(4);
		Node n5 = new Node(5);
		Node n6 = new Node(6);
		Node n7 = new Node(7);
		Node n8 = new Node(8);

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

	}
}