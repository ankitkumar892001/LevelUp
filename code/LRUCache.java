class LRUCache {
	// 3 edge cases
	// head == null
	// head = tail
	// 2 node
	int capacity = 0;
	public class ListNode {
		int key = 0;
		int val = 0;
		ListNode prev = null;
		ListNode next = null;
		ListNode() {}
		ListNode(int key, int val) {
			this.key = key;
			this.val = val;
		}
	}
	ListNode head = null;
	ListNode tail = null;
	int size = 0;
	// HashMap<Integer, ListNode> map = null;
	ListNode[] map = null;

	public LRUCache(int capacity) {
		this.capacity = capacity;
		this.head = null;
		this.tail = null;
		this.size = 0;
		// this.map = new HashMap<>();
		this.map = new ListNodep[(int)1e4 + 1];
	}


	public void displayList() {
		ListNode x = head;
		while (x != null) {
			System.out.print(x.key + "," + x.val + "->");
			x = x.next;
		}
		System.out.println();
	}

	public void addFirst(int key, int value) {
		ListNode t = new ListNode(key, value);
		if (head == null) {
			head = tail = t;
		} else {
			t.next = head;
			head.prev = t;
			head = t;
		}

		// map.put(key, t);
		map[key] = t;
		this.size++;
	}

	public void removeFirst() {
		if (head == null) {
			return;
		}

		int k = head.key;

		if (head == tail) {
			head = tail = null;
		} else {
			ListNode n = head.next;
			head.next = null;
			n.prev = null;
			head = n;
		}

		// map.remove(k);
		map[k] = null;
		this.size--;
	}

	public void removeLast() {
		if (head == null) {
			return;
		}

		int k = tail.key;

		if (head == tail) {
			head = tail = null;
		} else {
			ListNode s = tail.prev;
			tail.prev = null;
			s.next = null;
			tail = s;
		}

		// map.remove(k);
		map[k] = null;
		this.size--;
	}

	public void removeNode(int key) {
		if (head == null) {
			return;
		}

		// ListNode keyNode = map.get(key);
		ListNode keyNode = map[key];
		if (keyNode == null) {
			return;
		}

		if (keyNode == head) {
			removeFirst();
			return;
		} else if (keyNode == tail) {
			removeLast();
			return;
		}


		ListNode p = keyNode.prev;
		ListNode n = keyNode.next;
		p.next = n;
		n.prev = p;

		keyNode.prev = null;
		keyNode.next = null;

		// map.remove(key);
		map[key] = null;
		this.size--;
	}

	public int get(int key) {
		// ListNode x = map.get(key);
		ListNode x = map[key];
		if (x == null) {
			return -1;
		}
		int v = x.val;
		removeNode(key);
		addFirst(key, v);
		// displayList();
		return v;
	}

	public void put(int key, int value) {
		// if (map.get(key) != null) {
		if (map[key] != null) {
			removeNode(key);
		} else if (this.size == this.capacity) {
			removeLast();
		}
		addFirst(key, value);
		// displayList();
	}
}
