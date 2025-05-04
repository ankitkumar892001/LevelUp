public class LinkedList {


	public class ListNode {
		int val = 0;
		ListNode next = null;
		ListNode() {}
		ListNode(int val) {
			this.val = val;
		}
	}


	public void displayList(ListNode head) {
		ListNode curr = head;
		while (curr != null) {
			System.out.print(curr.val + "->");
			curr = curr.next;
		}
		System.out.println();
	}


	// 876. Middle of the Linked List
	public ListNode middleNode(ListNode head) {
		if (head == null || head.next == null)
			return head;
		ListNode slow = head;
		ListNode fast = head;
		while (fast != null && fast.next != null) {
			slow = slow.next;
			fast = fast.next.next;
		}

		return slow;
	}


	// Left Mid Node
	public ListNode leftMiddleNode(ListNode head) {
		if (head == null || head.next == null)
			return head;
		ListNode slow = head;
		ListNode fast = head;
		while (fast != null && fast.next != null && fast.next.next != null) {
			slow = slow.next;
			fast = fast.next.next;
		}

		return slow;
	}


	// 206. Reverse Linked List
	public ListNode reverseList(ListNode head) {
		if (head == null || head.next == null)
			return head;

		ListNode prev = null;
		ListNode curr = head;
		ListNode forward = curr.next;
		while (curr != null) {
			forward = curr.next;
			curr.next = prev;
			prev = curr;
			curr = forward;
		}

		return prev;
	}


	// 234. Palindrome Linked List
	public boolean isPalindrome(ListNode head) {
		if (head == null || head.next == null)
			return true;
		ListNode curr = head;
		ListNode mid = leftMiddleNode(curr);
		ListNode c2 = mid.next;
		mid.next = null;
		c2 = reverseList(c2);
		ListNode c1 = curr;

		while (c1 != null && c2 != null) {
			if (c1.val != c2.val)
				return false;
			c1 = c1.next;
			c2 = c2.next;
		}

		return true;
	}


	// 143. Reorder List
	public void reorderList(ListNode head) {
		if (head == null || head.next == null)
			return;

		ListNode curr = head;
		ListNode mid = leftMiddleNode(curr);
		ListNode c2 = mid.next;
		mid.next = null;
		c2 = reverseList(c2);
		ListNode c1 = curr;

		ListNode f1 = null;
		ListNode f2 = null;
		while (c1 != null && c2 != null) {
			f1 = c1.next;
			f2 = c2.next;
			c1.next = c2;
			c2.next = f1;
			c1 = f1;
			c2 = f2;
		}

		head = curr;
	}


	// 143. Unfold folded LinkedList
	public void unfold(ListNode head) {
		if (head == null || head.next == null)
			return;

		ListNode curr = head;
		ListNode c1 = curr;
		ListNode c1copy = c1;
		ListNode c2 = curr.next;
		ListNode c2copy = c2;
		while (c1 != null && c2 != null && c2.next != null) {
			ListNode f1 = c2.next;
			ListNode f2 = f1.next;

			c1.next = f1;
			c2.next = f2;

			c1 = f1;
			c2 = f2;
		}
		if (c1 != null) {
			c1.next = null;
		}
		if (c2 != null) {
			c2.next = null;
		}
		// displayList(c1copy);
		// displayList(c2copy);
		c2copy = reverseList(c2copy);
		c1.next = c2copy;
	}



	// 21. Merge Two Sorted Lists
	public ListNode mergeTwoLists(ListNode head1, ListNode head2) {
		if (head1 == null) {
			return head2;
		}
		if (head2 == null) {
			return head1;
		}

		ListNode dummy = new ListNode(-1);
		ListNode dummy2 = dummy;
		ListNode c1 = head1;
		ListNode c2 = head2;

		while (c1 != null && c2 != null) {
			if (c1.val <= c2.val) {
				dummy.next = c1;
				dummy = c1;
				c1 = c1.next;
			} else {
				dummy.next = c2;
				dummy = c2;
				c2 = c2.next;
			}
		}

		if (c1 != null) {
			dummy.next = c1;
		}

		if (c2 != null) {
			dummy.next = c2;
		}

		return dummy2.next;
	}


	// 912. Sort an Array
	public void mergeSort(int[] arr, int si, int ei) {
		if (si >= ei) {
			return;
		}
		int mid = (si + ei) / 2;
		mergeSort(arr, si, mid);
		mergeSort(arr, mid + 1, ei);

		int[] left = new int[mid - si + 1];
		int[] right = new int[ei - (mid + 1) + 1];

		for (int i = si, j = 0; i <= mid; i++, j++) {
			left[j] = arr[i];
		}
		for (int i = mid + 1, j = 0; i <= ei; i++, j++) {
			right[j] = arr[i];
		}

		int i = 0, j = 0, p = si;
		while (i < left.length && j < right.length) {
			if (left[i] <= right[j]) {
				arr[p++] = left[i++];
			} else {
				arr[p++] = right[j++];
			}
		}

		while (i < left.length) {
			arr[p++] = left[i++];
		}

		while (j < right.length) {
			arr[p++] = right[j++];
		}
	}

	public int[] sortArray(int[] arr) {
		int len = arr.length;
		mergeSort(arr, 0, len - 1);
		return arr;
	}


	// 148. Sort List
	public ListNode mergeSort(ListNode head) {
		if (head == null || head.next == null) {
			return head;
		}
		ListNode h1 = head;
		ListNode mid = leftMiddleNode(h1);

		ListNode h2 = mid.next;
		mid.next = null;

		h1 = mergeSort(h1);
		h2 = mergeSort(h2);

		return mergeTwoLists(h1, h2);
	}

	public ListNode sortList(ListNode head) {
		return mergeSort(head);
	}




	// 23. Merge k Sorted Lists
	public ListNode mergeKListsUtil(ListNode[] arr, int si, int ei) {
		if (si > ei) {
			return null;
		} else if (si == ei) {
			return arr[si];
		}

		int mid = (si + ei) / 2;
		ListNode left = mergeKListsUtil(arr, si, mid);
		ListNode right = mergeKListsUtil(arr, mid + 1, ei);

		return mergeTwoLists(left, right);
	}

	public ListNode mergeKLists(ListNode[] arr) {
		if (arr == null || arr.length == 0 ) {
			return null;
		}

		return mergeKListsUtil(arr, 0, arr.length - 1);
	}




	// https://www.geeksforgeeks.org/problems/segregate-even-and-odd-nodes-in-a-linked-list5035/1
	Node divide(int N, Node head) {
		if (head == null || head.next == null) {
			return head;
		}

		Node curr = head;
		Node d0 = new Node(0);
		Node d0copy = d0;

		Node d1 = new Node(1);
		Node d1copy = d1;

		while (curr != null) {
			if ((curr.data & 1) == 0) {
				d0.next = curr;
				d0 = d0.next;
			} else {
				d1.next = curr;
				d1 = d1.next;
			}
			Node n1 = curr.next;
			curr.next = null;
			curr = n1;
		}

		d0.next = d1copy.next;
		return d0copy.next;
	}



	// 25. Reverse Nodes in k-Group
	public ListNode reverseKGroup(ListNode head, int k) {
		if (head == null || head.next == null) {
			return head;
		}

		ListNode dummy = new ListNode(-1);
		ListNode prevTail = dummy;
		ListNode h1 = head;
		ListNode curr = head;
		int count = 0;
		while (curr != null) {
			count++;
			ListNode f1 = curr.next;
			if (count >= k) {
				curr.next = null;
				ListNode newHead = reverseList(h1);
				prevTail.next = newHead;
				prevTail = h1;
				h1 = f1;
				count = 0;
			}
			curr = f1;
		}
		prevTail.next = h1;
		return dummy.next;
	}




	// 92. Reverse Linked List II
	public ListNode addFirst(ListNode head, ListNode node) {
		node.next = head;
		head = node;
		return head;
	}

	public ListNode reverseBetween(ListNode head, int left, int right) {
		if (head == null || head.next == null || left >= right) {
			return head;
		}

		ListNode curr = head;
		ListNode prev = null;
		int count = 0;
		ListNode th = null;
		ListNode tt = null;
		while (curr != null) {
			count++;
			while (curr != null && count >= left && count <= right) {
				ListNode forw = curr.next;
				curr.next = null;
				if (tt == null) {
					tt = curr;
				}
				th = addFirst(th, curr);
				curr = forw;
				count++;
			}
			if (count >= right) {
				tt.next = curr;
				if (prev == null)
					return th;
				prev.next = th;
				break;
			}
			prev = curr;
			curr = curr.next;
		}

		return head;
	}



	// Sort a linked list of 0s and 1s
	public static Node segregate01(Node head) {
		if (head == null || head.next == null) {
			return head;
		}
		Node d0 = new Node(0);
		Node d0copy = d0;
		Node d1 = new Node(1);
		Node d1copy = d1;
		Node curr = head;
		while (curr != null) {
			if (curr.data == 0) {
				d0.next = curr;
				d0 = d0.next;
			} else {
				d1.next = curr;
				d1 = d1.next;
			}
			curr = curr.next;
		}
		d0.next = null;
		d1.next = null;
		d0.next = d1copy.next;

		return d0copy.next;
	}


	// Sort a linked list of 0s, 1s and 2s
	public static Node segregate012(Node head) {
		if (head == null || head.next == null) {
			return head;
		}
		Node d0 = new Node(0);
		Node d0copy = d0;
		Node d1 = new Node(1);
		Node d1copy = d1;
		Node d2 = new Node(2);
		Node d2copy = d2;
		Node curr = head;
		while (curr != null) {
			if (curr.data == 0) {
				d0.next = curr;
				d0 = d0.next;
			} else if (curr.data == 1) {
				d1.next = curr;
				d1 = d1.next;
			} else {
				d2.next = curr;
				d2 = d2.next;
			}
			curr = curr.next;
		}
		d0.next = null;
		d1.next = null;
		d2.next = null;
		d1.next = d2copy.next;
		d0.next = d1copy.next;
		return d0copy.next;
	}



	// Segregate Node Of Linkedlist Over Last Index
	public static ListNode segregateOverLastIndex(ListNode head, ListNode last) {
		if (head == null || head.next == null) {
			return head;
		}

		ListNode smaller = new ListNode(-1);
		ListNode smallerHead = smaller;
		ListNode larger = new ListNode(-1);
		ListNode largerHead = larger;
		ListNode curr = head;
		int lastNodeVal = last.val;

		while (curr != null) {
			if (curr.val <= lastNodeVal) {
				smaller.next = curr;
				smaller = smaller.next;
			} else {
				larger.next = curr;
				larger = larger.next;
			}
			curr = curr.next;
		}

		smaller.next = null;
		larger.next = null;
		smaller.next = largerHead.next;

		return smallerHead.next;
	}


	// Segregate Node Of Linkedlist Over Pivot Index
	public static ListNode segregateOverPivotIndex(ListNode head, ListNode pivot) {
		if (head == null || head.next == null) {
			return head;
		}

		ListNode smaller = new ListNode(-1);
		ListNode sp = smaller;
		ListNode equal = new ListNode(-1);
		ListNode ep = equal;
		ListNode larger = new ListNode(-1);
		ListNode lp = larger;
		ListNode curr = head;
		int x = pivot.val;

		while (curr != null) {
			if (curr.val < x) {
				sp.next = curr;
				sp = sp.next;
			} else if (curr.val == x) {
				ep.next = curr;
				ep = ep.next;
			} else {
				lp.next = curr;
				lp = lp.next;
			}
			curr = curr.next;
		}

		sp.next = null;
		ep.next = null;
		lp.next = null;
		ep.next = larger.next;
		sp.next = equal.next;

		return smaller.next;
	}




	// 912. Sort an Array
	public void swap(int[] arr, int i, int j) {
		int x = arr[i];
		arr[i] = arr[j];
		arr[j] = x;
	}

	public int partition(int[] arr, int si, int ei) {
		int val = arr[ei];
		int p = si;
		for (int i = si; i <= ei; i++) {
			if (arr[i] <= val) {
				swap(arr, p, i);
				p++;
			}
		}
		return p - 1;
	}

	public void quickSort(int[] arr, int si, int ei) {
		if (si >= ei) {
			return;
		}

		int pi = partition(arr, si, ei);

		quickSort(arr, si, pi - 1);
		quickSort(arr, pi + 1, ei);
	}

	public int[] sortArray(int[] arr) {
		quickSort(arr, 0, arr.length - 1);
		return arr;
	}



	// 148. Sort List
	// TODO
	// faith - return the head and tail of sorted list [head, tail]
	public ListNode[] quickSort(ListNode head) {
		if (head == null || head.next == null) {
			return head;
		}
	}

	public ListNode sortList(ListNode head) {
		return quickSort(ListNode head)[0];
	}





	// 19. Remove Nth Node From End of List
	public ListNode removeNthFromEnd(ListNode head, int n) {
		if (head == null)
			return null;

		ListNode d = new ListNode(-1);
		ListNode s = d;
		ListNode f = d;

		for (int i = 1; i <= n; i++) {
			f = f.next;
		}

		while (f.next != null) {
			s = s.next;
			f = f.next;
		}

		s.next = s.next.next;

		return d.next;
	}






	// 2. Add Two Numbers
	public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		ListNode h1 = l1;
		ListNode h2 = l2;
		ListNode res = new ListNode(-1);
		ListNode r = res;

		int c = 0;
		while (h1 != null && h2 != null) {
			int s = (h1.val + h2.val + c);
			r.next = new ListNode(s % 10);
			c = s / 10;
			r = r.next;
			h1 = h1.next;
			h2 = h2.next;
		}

		while (h1 != null) {
			int s = (h1.val + c);
			r.next = new ListNode(s % 10);
			c = s / 10;
			r = r.next;
			h1 = h1.next;
		}
		while (h2 != null) {
			int s = (h2.val + c);
			r.next = new ListNode(s % 10);
			c = s / 10;
			r = r.next;
			h2 = h2.next;
		}
		if (c != 0) {
			r.next = new ListNode(c);
			r = r.next;
		}

		return res.next;
	}


	// 	// 445. Add Two Numbers II
	public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		if (l1 == null) {
			return l2;
		} else if (l2 == null) {
			return l1;
		}

		l1 = reverseList(l1);
		l2 = reverseList(l2);

		ListNode h1 = l1;
		ListNode h2 = l2;
		ListNode res = new ListNode(-1);
		ListNode r = res;

		int c = 0;
		while (h1 != null || h2 != null || c != 0) {
			int h1Val = h1 != null ? h1.val : 0;
			int h2Val = h2 != null ? h2.val : 0;
			int s = (h1Val + h2Val + c);
			r.next = new ListNode(s % 10);
			c = s / 10;
			r = r.next;

			if (h1 != null) {
				h1 = h1.next;
			}
			if (h2 != null) {
				h2 = h2.next;
			}
		}

		ListNode temp = res.next;
		res.next = null;
		return reverseList(temp);
	}









	// gfg Subtraction in Linked List
	public Node removeLeadingZeros(Node head) {
		Node curr = head;
		while (curr != null && curr.data == 0 && curr.next != null) {
			curr = curr.next;
		}
		return curr;
	}
	public int getLength(Node head) {
		Node curr = head;
		int c = 0;
		while (curr != null) {
			c++;
			curr = curr.next;
		}
		return c;
	}
	// check if h1 < h2
	public boolean isSmaller(Node h1 , Node h2) {
		int l1 = getLength(h1);
		int l2 = getLength(h2);
		if (l1 < l2) {
			return true;
		} else if (l1 == l2) {
			Node c1 = h1;
			Node c2 = h2;
			while (c1 != null && c2 != null) {
				if (c1.data < c2.data ) {
					return true;
				}
				c1 = c1.next;
				c2 = c2.next;
			}
		}

		return false;
	}
	public Node reverseList(Node head) {
		if (head == null || head.next == null)
			return head;

		Node prev = null;
		Node curr = head;
		Node forward = curr.next;
		while (curr != null) {
			forward = curr.next;
			curr.next = prev;
			prev = curr;
			curr = forward;
		}

		return prev;
	}
	public Node subLinkedList(Node head1, Node head2) {
		if (head1 == null) {
			return head2;
		} else if (head2 == null) {
			return head1;
		}

		Node h1 = head1;
		Node h2 = head2;

		h1 = removeLeadingZeros(h1);
		h2 = removeLeadingZeros(h2);

		if (isSmaller(h1, h2)) {
			Node x = h1;
			h1 = h2;
			h2 = x;
		}

		h1 = reverseList(h1);
		h2 = reverseList(h2);

		Node res = new Node(-1);
		Node r = res;

		int b = 0;
		while (h1 != null || h2 != null || b != 0) {
			int h1Data = (h1 != null ? h1.data : 0);
			int h2Data = (h2 != null ? h2.data : 0);
			int d = (h1Data  - b - h2Data + 10) % 10;
			b = h1Data - b < h2Data ? 1 : 0;
			r.next = new Node(d);

			r = r.next;
			if (h1 != null) {
				h1 = h1.next;
			}
			if (h2 != null) {
				h2 = h2.next;
			}
		}

		Node temp = res.next;
		res.next = null;
		return removeLeadingZeros(reverseList(temp));
	}







	// gfg Multiply two linked lists
	public void displayList(Node head) {
		Node curr = head;
		while (curr != null) {
			System.out.print(curr.data + "->");
			curr = curr.next;
		}
		System.out.println();
	}
	public long listTolong(Node head) {
		long mod = (long) (1e9 + 7);
		long ans = 0 ;
		Node c = head;
		long multiplier = 1;
		while (c != null) {
			ans = (ans + (c.data * multiplier) % mod) % mod;
			multiplier = (multiplier * 10) % mod;
			c = c.next;
		}
		return ans;
	}
	public Node removeLeadingZeros(Node head) {
		Node curr = head;
		while (curr != null && curr.data == 0 && curr.next != null) {
			curr = curr.next;
		}
		return curr;
	}
	public Node reverseList(Node head) {
		if (head == null || head.next == null)
			return head;

		Node prev = null;
		Node curr = head;
		Node forward = curr.next;
		while (curr != null) {
			forward = curr.next;
			curr.next = prev;
			prev = curr;
			curr = forward;
		}

		return prev;
	}
	public Node addTwoList(Node l1, Node l2) {
		// null check already handled inside while loop
		Node h1 = l1;
		Node h2 = l2;
		Node res = new Node(-1);
		Node r = res;

		int c = 0;
		while (h1 != null || h2 != null || c != 0) {
			int h1Data = (h1 != null ? h1.data : 0);
			int h2Data = (h2 != null ? h2.data : 0);
			int s = (h1Data + h2Data + c);
			c = s / 10;

			r.next = new Node(s % 10);
			r = r.next;

			if (h1 != null) {
				h1 = h1.next;
			}
			if (h2 != null) {
				h2 = h2.next;
			}
		}

		return res.next;
	}
	public Node multiplySingleNode(Node head1, Node head2) {
		if (head1 == null || head2 == null) {
			return null;
		}

		Node h1 = head1;
		Node res = new Node(-1);
		Node r = res;
		int c = 0;
		while (h1 != null || c != 0) {
			int h1Data = (h1 != null ? h1.data : 0);
			int p = (h1Data * head2.data) + c;
			c = p / 10;

			r.next = new Node(p % 10);
			r = r.next;
			if (h1 != null) {
				h1 = h1.next;
			}
		}
		return res;
	}
	public long multiplyTwoLists(Node head1, Node head2) {
		if (head1 == null || head2 == null) {
			return 0;
		}

		Node h1 = head1;
		Node h2 = head2;

		h1 = reverseList(h1);
		h2 = reverseList(h2);

		Node ans = new Node(0);
		int count = 0;
		while (h2 != null) {
			Node res = multiplySingleNode(h1, h2);
			Node r = res;
			Node forw = res.next;
			for (int i = 1; i <= count; i++) {
				r.next = new Node(0);
				r = r.next;
			}
			r.next = forw;
			count++;
			ans = addTwoList(ans, res.next);
			h2 = h2.next;
		}

		// displayList(removeLeadingZeros(reverseList(ans)));
		// return 0;
		return listTolong(ans);
	}




	// 138. Copy List with Random Pointer
	public Node copyRandomList(Node head) {
		if (head == null) {
			return head;
		}

		Node curr = head;
		Node dummy = new Node(-1);
		Node prev = dummy;

		// original vs duplicate address
		// 1 -> 1'
		// 2 -> 2'
		// 9 -> 9'
		HashMap<Node, Node> map = new HashMap<>();

		while (curr != null) {
			Node n = new Node(curr.val);
			prev.next = n;

			map.put(curr, n);

			prev = prev.next;
			curr = curr.next;
		}

		Node c1 = head;
		Node c2 = dummy.next;
		map.put(null, null);
		while (c1 != null && c2 != null) {
			c2.random = map.get(c1.random);
			c1 = c1.next;
			c2 = c2.next;
		}

		return dummy.next;
	}


	// 138. Copy List with Random Pointer with O(1) space
	// Method to copy nodes and insert them in between the original nodes
	// A -> B -> C
	// A -> A' -> B -> B' -> C -> C'
	public void copyList(Node head) {
		Node c = head;
		while (c != null) {
			Node dupNode = new Node(c.val);
			Node f = c.next;
			c.next = dupNode;
			dupNode.next = f;
			c = f;
		}
	}

	// Method to copy the random pointers for the new nodes
	// A -> A' -> B -> B' -> C -> C'
	// suppose A.random = F
	// then A'.random = F'
	// A.next.random = A.random.next
	public void copyRandomPointer(Node head) {
		Node c = head;
		while (c != null) {
			if (c.random != null) {
				c.next.random = c.random.next;
			}
			c = c.next.next;
		}
	}

	// Method to separate the intertwined list into the original and the copied list
	public Node extractLL(Node head) {
		Node dummy = new Node(-1);
		Node p = dummy;
		Node c = head;
		while (c != null) {
			Node f = c.next.next;
			p.next = c.next;
			p = p.next;
			c.next = f;
			c = f;
		}

		return dummy.next;
	}

	public Node copyRandomList(Node head) {
		if (head == null) {
			return head;
		}
		copyList(head);
		copyRandomPointer(head);
		return extractLL(head);
	}



	// 141. Linked List Cycle
	public boolean hasCycle(ListNode head) {
		if (head == null) {
			return false;
		}

		ListNode s = head;
		ListNode f = head;
		while (f != null &&  f.next != null) {
			s = s.next;
			f = f.next.next;
			if (s == f) {
				return true;
			}
		}

		return false;
	}


	// 142. Linked List Cycle II
	public ListNode detectCycle(ListNode head) {
		if (head == null) {
			return null;
		}

		ListNode s = head;
		ListNode f = head;
		while (f != null &&  f.next != null) {
			s = s.next;
			f = f.next.next;
			if (s == f) {
				s = head;
				while (s != f) {
					s = s.next;
					f = f.next;
				}
				return f;
			}
		}

		return null;
	}

	// 160. Intersection of Two Linked Lists
	public ListNode getIntersectionNode(ListNode head1, ListNode head2) {
		if (head1 == null) {
			return null;
		} else if (head2 == null) {
			return null;
		}

		ListNode tail1 = null;
		ListNode c1 = head1;
		while (c1.next != null) {
			c1 = c1.next;
		}

		tail1 = c1;
		tail1.next = head2;
		return detectCycle(head1);
	}


	// 83. Remove Duplicates from Sorted List
	public ListNode deleteDuplicates(ListNode head) {
		if (head == null || head.next == null) {
			return head;
		}
		// faith
		// p is always at the head of potential duplicates
		// c
		// intitally next to p 		p -> c
		// finally at the first non duplicate value
		// pp -> p -> . . . . -> c
		ListNode p = head;
		ListNode c = head.next;
		while (c != null) {
			while (c != null && c.val == p.val) {
				c = c.next;
			}

			p.next = c;
			p = c;
			if (c != null) {
				c = c.next;
			}
		}

		return head;
	}


	// 82. Remove Duplicates from Sorted List II
	public ListNode deleteDuplicates(ListNode head) {
		if (head == null || head.next == null) {
			return head;
		}
		// faith
		// pp is always one step prev to p 		pp -> p
		// p is always at the head of potential duplicates
		// c
		// intitally next to p 		p -> c
		// finally at the first non duplicate value
		// pp -> p -> . . . . -> c
		ListNode p = head;
		ListNode c = head.next;
		ListNode dummy = new ListNode(-1);
		ListNode pp = dummy;
		pp.next = p;
		while (c != null) {
			boolean isLoopRun = false;
			while (c != null && c.val == p.val) {
				c = c.next;
				isLoopRun = true;
			}

			if (isLoopRun == true) {
				pp.next = c;
				p = c;
			} else {
				// if c != p , just move each pointer 1 step ahead
				pp.next = p;
				pp = p;
				p.next = c;
				p = c;
			}
			if (c != null) {
				c = c.next;
			}
		}

		return dummy.next;
	}













	public static void main(String[] args) {
		System.out.println("Hello, World!");

		ListNode n1 = new ListNode(1);
		ListNode n2 = new ListNode(2);
		ListNode n3 = new ListNode(3);
		ListNode n4 = new ListNode(4);
		ListNode n5 = new ListNode(5);
		ListNode n6 = new ListNode(6);
		ListNode n7 = new ListNode(7);
		ListNode n8 = new ListNode(8);
		n1.next = n2;
		n2.next = n3;
		n3.next = n4;
		n4.next = n5;
		n5.next = n6;
		n6.next = n7;
		n7.next = n8;
		n8.next = null;

		displayList(n1);
		// reorderList(n1);
		// unfold(n1);
		displayList(n1);
	}
}
