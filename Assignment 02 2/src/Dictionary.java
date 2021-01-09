public class Dictionary<AnyType extends Comparable<AnyType>> implements DictionaryInterface<AnyType> {
	/*
	 * Keeps track of the number of elements in the dictionary. Take a look at the
	 * implementation of size()
	 */
	private int size;
	/*
	 * The head reference to the linked list of Nodes. Take a look at the Node
	 * class.
	 */
	private Node head;

	/**
	 * Creates an empty dictionary.
	 */
	public Dictionary() {
		size = 0;
		head = null;
	}

	/**
	 * Adds e to the dictionary, thus making contains(e) true. Increments size so as
	 * to ensure size() is correct.
	 */
	@SuppressWarnings("rawtypes")
	public void add(AnyType e) 
	{
		if (e == null) {
			return;
		}
		Comparable[] a = {e};
		head = new Node(0, a, head);
		size++;
		mergeDown();

	}

	private void addm(Node node) {
		if (head == null || head.power > node.power)
		{
			node.next = head;
			head = node;
			return;
		}
		if (head.power == node.power) 
		{
			node.next = head;
			head = node;
			mergeDown();
			return;
		}
		int x = node.power;
		Node pref = head;
		while (pref.next != null) 
		{
			if (pref.next.power > x)
			{
				node.next = pref.next;
				pref.next = node;
				return;
			} 
			else if (pref.next.power == x) 
			{
				node.next = pref.next;
				pref.next = node;
				while (pref.next != null && pref.next.power == pref.power) 
				{
					pref.array = merge(pref.array, pref.next.array);
					pref.power++;
					pref.next = pref.next.next;
				}
				return;
			} 
			else 
			{
				pref = pref.next;
			}
		}
		pref.next = node;
	}

	public void remove(AnyType e) 
	{
		if (e == null || !contains(e))
			return;
		size --;
		int idx;
		Comparable[] star;
		Node[] a;
		if (head.contains(e)) {
			if (head.power == 0) {
				head = head.next;
				return;
			} 
			idx = head.indexOf(e);
			star = head.array;
			a = new Node[head.power];
			head = head.next;
		
		} 
		else {  
			Node pre = head;
			Node curr = head.next;
			idx = curr.indexOf(e);
			while (idx == -1) {
				pre = curr;
				curr = curr.next;
				idx = curr.indexOf(e);
			}
			star = curr.array;
			pre.next = curr.next;
			a = new Node[curr.power];
		}
		for (int r = 0; r < a.length; ++r) {
			a[r] = new Node(r, new Comparable[1 << r], null);
		}
		int x = 0;
		int y = 0;
		for (int i = 0; i < idx; i++) 
		{
			a[y].array[x] = star[i];
			x++;
			if (x == (1 << y)) 
			{
				x = 0;
				y++;
			}
		}
		for (int r = idx + 1; r < star.length; ++r) 
		{
			a[y].array[x] = star[r];
			x++;
			if (x == (1 << y)) 
			{
				x = 0;
				y++;
			}
		}
		for (int r = 0; r < a.length; ++r) 
		{
			addm(a[r]);
		}
	}

	public boolean contains(AnyType e) {

		if (e == null) { 
			return false;
		
		}
		Node c = head;
		if (c == null) {
			return false;
		}
		if (c.contains(e)) {
			return true; //checks first 
		
		}
		while (c.next != null) //checks next ones
		{
			if (c.contains(e)) 
			{
				return true;
			} 
			else {
				c = c.next;
			}
		}
		if (c.contains(e) == true) return true;
		return false;
	}

	public int frequency(AnyType e) {
		int s = 0; //initial frequency
		if(e == null)
		{
			return 0;
		}
		Node temp = head;
		if (temp == null) return 0;
		while (temp.next != null) 
		{
			s += temp.frequency(e);
			temp = temp.next;
		}
		s += temp.frequency(e);
		return s;
	}

	public int size() {
		return size;
	}

	public void combine(Dictionary<AnyType> other) {
		if(other == null || this == other)
		{
			return;
		}
		int ar = other.size;
		size += ar;
		
		if (head == null) 
		{
			head = other.head.copy();
			return;
		}
	
		Node add = other.head; 
		@SuppressWarnings("rawtypes")
		Comparable[] comp = null; 

		if (head.power > add.power) 
		{
			head = new Node(add.power, add.array, head);
			add = add.next;
		}
		Node cp = head; 
		while (add != null) {
			if (cp.next == null) {
				cp.next = add.copy();
				break;
			}
			if (comp == null) {
				if (cp.next.power < add.power) {
					cp = cp.next;
				} 
				else if (cp.next.power == add.power) {
					comp = merge(cp.next.array, add.array);
					cp.next = cp.next.next;
					add = add.next;
				} 
				else 
				{ 
					Node j = new Node(add.power, add.array, cp.next);
					cp.next = j;
					cp = cp.next;
					add = add.next;
				}
			} 
			else 
			{
				if (cp.next.array.length == comp.length) 
				{
					if (add.array.length == comp.length) 
					{
						comp = merge(comp, add.array);
						add = add.next;
						cp = cp.next;
					} 
					else 
					{
						comp = merge(cp.next.array, comp);
						cp.next = cp.next.next;
					}
				} 
				else if (add.array.length == comp.length) 
				{
					comp = merge(add.array, comp);
					add = add.next;
				} 
				else 
				{
					int g = 0;
					while (1 << g != comp.length)g++;
					cp.next = new Node(g, comp, cp.next);
					if (comp.length != 1 << g)
						System.out.println("FATAL ERROR!");
					comp = null;
					cp = cp.next;
				}
			}
		}
		if (comp != null) 
		{
			int x = 0;
			while (1 << x != comp.length) x++;
			addm(new Node(x, comp, null));
		}
	}

	
	public String toString() {
		Node temp = head;
		StringBuilder rep = new StringBuilder();
		while (temp != null) 
		{
			rep.append(temp.power);
			rep.append(": ");
			rep.append(temp.toString());
			rep.append("\n");
			temp = temp.next;
		}
		String s = rep.toString();
		return s;
	}

	/**
	 ry behind this.
	 */
	private void mergeDown() {
		// while next is an array of equal size, merge!
		while (head.next != null && head.next.power == head.power) {
			head.array = merge(head.array, head.next.array);
			head.power++;
			head.next = head.next.next;
		}
	}

	@SuppressWarnings("rawtypes")
	public static int binarySearch(Comparable[] a, Comparable item) {

		return binHelp(a,0,(a.length-1), item);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static int binHelp(Comparable[] a, int l, int r, Comparable item) 
	{
		
		if (r >= l) { 
            int mid = l + (r - l) / 2; 
  
         
            if (a[mid] == item) return mid; 
  
         
            if (a[mid].compareTo(item) > 0) return binHelp(a, l, mid - 1, item); 
  
        
            return binHelp(a, mid + 1, r, item); 
        } 
  
        // We reach here when element is not present 
        // in array 
        return -1; 
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static int frequency(Comparable[] a, Comparable item) {
		int idx = binarySearch(a, item); //gets index in fast time
		if (idx == -1)return 0; //if index is not found return -1
		int n = 1; //n is at least one if idx is not -1
		int d = idx - 1; //searches left side 
		int u = idx + 1; //searches right side
		while (d > -1 && a[d].compareTo(item) == 0) {
			n++;
			d--;
		}

		while (u < a.length && a[u].compareTo(item) == 0) {
			n++;
			u++;
		}
		return n;
	}

	/**
	 * When a and b are sorted arrays, merge(a,b) returns a sorted array that has
	 * length (a.length+b.length) than contains the elements of a and the elements
	 * of b.
	 *
	 * This is useful for implementing the mergeDown() method.
	 *
	 * O(a.length + b.length)
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Comparable[] merge(Comparable[] a, Comparable[] b) {

		Comparable[] merged = new Comparable[a.length + b.length];
		
		int avar = 0, bvar = 0;
		
		for (int i = 0; i < (a.length+b.length); i++) {
			if (avar == a.length) {
				merged[i] = b[bvar];
				bvar++;
			}
			else if (bvar == b.length) 
			{
				merged[i]=a[avar];
				avar++;
 			}
			else 
			{
				if (b[bvar].compareTo(a[avar]) > 0) 
				{
					merged[i] = a[avar];
					avar++;
				}
				else 
				{
					merged[i] = b[bvar];
					bvar++;
				}
			}
		}
		return merged;
	}

	/**
	 * Returns base^exponent. This is useful for implementing splitUp(a,k)
	 */
	@SuppressWarnings("unused")
	private static int power(int base, int exponent) {
		return (int) (Math.pow(base, exponent));
	}

	private static class Node {
		private int power;
		@SuppressWarnings("rawtypes")
		private Comparable[] array;
		private Node next;

		/**
		 
		 */
		@SuppressWarnings("rawtypes")
		public Node(int power, Comparable[] array, Node next) {
			this.power = power;
			this.array = array;
			this.next = next;
		}

		public Node copy() {
			Node a = new Node(power, array, null);
			if (next != null) a.next = next.copy();
			return a;
		}

		/**
		 * where array[k] is equal to e
		 */
		public int indexOf(@SuppressWarnings("rawtypes") Comparable e) {
			return Dictionary.binarySearch(array, e);
		}

		/**
		 * Returns true, if there is an element in the array equal to e false, otherwise
		 */
		public boolean contains(@SuppressWarnings("rawtypes") Comparable e) {
			return indexOf(e) > -1;
		}

		/**
		 * Returns the number of elements in the array equal to e
		 */
		@SuppressWarnings("rawtypes")
		public int frequency(Comparable e) {
			return Dictionary.frequency(array, e);
		}

		/**
		 * Returns a useful representation of this Node. (Note how this is used by
		 * Dictionary's toString()).
		 */
		public String toString() {
			return java.util.Arrays.toString(array);
		}
	}

}
