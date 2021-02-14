
/**
 * FibonacciHeap
 *
 * An implementation of fibonacci heap over non-negative integers.
 */
public class FibonacciHeap
{
	HeapNode min;
	static int totalLinks =0;
	static int totalCuts= 0; 
	int size;
	int numberOfTrees;
	int numberOfMarked;
	
	public FibonacciHeap(){
		this.min = null;  
		this.size = 0;
		this.numberOfMarked = 0;
		this.numberOfTrees = 0;
		
	}

   /**
    * public boolean empty()
    *
    * precondition: none
    * 
    * The method returns true if and only if the heap
    * is empty.
    *   
    */
    public boolean empty()
    {
    	return (this.min == null); 
    }
		
   /**
    * public HeapNode insert(int key)
    *
    * Creates a node (of type HeapNode) which contains the given key, and inserts it into the heap. 
    */
    public HeapNode insert(int key)
    {
    	HeapNode newNode = new HeapNode(key);
    	if(empty()){    // the heap is empty
    		this.min = newNode;
    		newNode.next = newNode;
    		newNode.prev = newNode;
    	}
    	else{ // Concatenating the new node to the heap
    		insertAfter(this.min, newNode);
    	}
    	this.size++;
    	this.numberOfTrees++;
    	if(key < this.min.key){
    		this.min = newNode;
    	}
    	
    	return newNode; 
    }

   /**
    * public void deleteMin()
    *
    * Delete the node containing the minimum key.
    *
    */
    public void deleteMin()
    {
    	if(this.size == 0 ) {return;} 
    	this.size--;
    	if (this.size == 0) //the heap contains only one node
    	{
    		this.min=null;
    		this.numberOfTrees=0;
    		return;
    	}
    	
    		HeapNode itemToDelete = this.min;
    		HeapNode leftChild= itemToDelete.child;
    		if(itemToDelete.next == itemToDelete){//  itemToDelete has no siblings
    			checkForMarked(leftChild);
    			succesiveLinking(leftChild, itemToDelete);
    		}
    			
    		else if (leftChild == null)
    		{
    			// deleting the min and connecting the linked list
    			HeapNode prev=itemToDelete.prev;
    			HeapNode next=itemToDelete.next;
    			prev.next=next;
    			next.prev=prev;
    			succesiveLinking(prev,itemToDelete);
    		}
    		else // leftChild!=null
    		{
    			checkForMarked(leftChild);
    			HeapNode prev= itemToDelete.prev;
    			HeapNode next= itemToDelete.next;
    			prev.next= leftChild;
    			HeapNode lastChild= leftChild.prev;
    			leftChild.prev= prev;
    			lastChild.next= next;
    			next.prev= lastChild;
    			succesiveLinking(prev,itemToDelete);
    		}
  	
     	return;
     	
    }
    // if the nodes are marked - the function will change them to unmarked
    private void checkForMarked(HeapNode node) {
	HeapNode x = node;
	do{
		if(x.mark == 1){
			x.mark = 0;
			numberOfMarked--;
		}
		x = x.next;
	}while(x != node);
	
	
}

	private void succesiveLinking (HeapNode x, HeapNode itemToDelete){
    	int listSize=(int) Math.ceil(1.45*((int) Math.log(this.size)/Math.log(2)));
    	HeapNode[] buckets = new HeapNode [listSize+5];  
    	HeapNode y = x;
    	do{ 
    		if(y.parent == itemToDelete) //removing the parents pointers to the deleted item 
    			y.parent = null;
    		HeapNode root = y;
  		    y = y.next;
  		    // removing the node from the list by updating his next and prev pointers
    		root.next = root; 
    		root.prev = root;
    		while(buckets[root.rank]!= null){ // linking
    			root= link(buckets[root.rank], root);
    			buckets[root.rank-1] = null; 
    			totalLinks++;
    		}
    		buckets[root.rank]= root;
    		
    	}while(y!= x);
    	arrayToHeap(buckets);
    }
    //the function converts buckets array to a heap 
   private void arrayToHeap(HeapNode[] buckets) {
	   HeapNode x=null;
	   numberOfTrees=0;
	   for (int i=0;i<buckets.length;i++)
	   {
		   if(buckets[i]!=null)
		   {   
			   numberOfTrees++;
			   if(x==null)
			   {
				   x=buckets[i];
				   x.next=x;
				   x.prev=x;
			   }
			   else 
			   {
				 insertAfter(x, buckets[i]);
				 if(buckets[i].key<x.key)
					 x = buckets[i];
			   }
		   }
	   }
		this.min=x;
	}
   // inserts afterNode after currentNode 
   private void insertAfter(HeapNode currentNode, HeapNode afterNode) {
	    HeapNode y= currentNode.next;
	    currentNode.next = afterNode;
	    afterNode.prev = currentNode;
	    afterNode.next = y;
	    y.prev = afterNode;
	
}

    private HeapNode link(HeapNode smallestNode, HeapNode nextNode) {
	   if(nextNode.key<smallestNode.key) 
	   {
		   HeapNode temp= smallestNode;
		   smallestNode= nextNode;
		   nextNode= temp;
	   } 
	   // now smallestNode is the minimum
	   if(smallestNode.rank== 0){ // simple link
		   smallestNode.child= nextNode;
		   nextNode.parent= smallestNode;
	   }
	   else { // linking 2 nodes with ranks bigger than 0
		   HeapNode x= smallestNode.child;
		   insertAfter(x, nextNode);
		   smallestNode.child= nextNode;
		   nextNode.parent= smallestNode;
	   }
	    smallestNode.rank++;
		return smallestNode;
	}

/**
    * public HeapNode findMin()
    *
    * Return the node of the heap whose key is minimal. 
    *
    */
    public HeapNode findMin()
    {
    	return this.min;
    } 
    
   /**
    * public void meld (FibonacciHeap heap2)
    *
    * Meld the heap with heap2
    *
    */
    public void meld (FibonacciHeap heap2)
    {
    	if(heap2 == null || heap2.empty()){ return; }
    	if(this.min == null){
    		this.min =  heap2.findMin();
    	}
    	else{
    		HeapNode minNextNode = this.min.next;
        	HeapNode extMinNextNode = heap2.findMin().next;
        	// melding the heaps
        	this.min.next = extMinNextNode;
        	extMinNextNode.prev = this.min;
        	minNextNode.prev = heap2.findMin();
        	heap2.findMin().next = minNextNode;
        	if(this.min.key > heap2.findMin().key){  // updating the min pointer
        		this.min = heap2.findMin();
        	}
    	}
    	
    	this.numberOfTrees += heap2.getNumberOfTrees();
    	this.numberOfMarked += heap2.getNumberOfMarked();
    	this.size+= heap2.getSize();
    	  		
    }

   /**
    * public int size()
    *
    * Return the number of elements in the heap
    *   
    */
    public int size()
    {
    	return this.size; // should be replaced by student code
    }
    	
    /**
    * public int[] countersRep()
    *
    * Return a counters array, where the value of the i-th entry is the number of trees of order i in the heap. 
    * 
    */
    public int[] countersRep(){
    	if(this.size == 0){
    		return new int[0];
    	}
    	int listSize=(int) Math.ceil(1.45*((int) Math.log(this.size)/Math.log(2)));
    	int[] arr = new int[listSize+5];
    	HeapNode x=this.min;
    	do
    	{
    		arr[x.rank]++;
    		x=x.next;
    	}while (x!=this.min);
        return arr;
    }

	
   /**
    * public void delete(HeapNode x)
    *
    * Deletes the node x from the heap. 
    *
    */
    public void delete(HeapNode x) 
    { 
    	decreaseKey(x, (int) Double.NEGATIVE_INFINITY);
    	deleteMin();
    	
    }

   /**
    * public void decreaseKey(HeapNode x, int delta)
    *
    * The function decreases the key of the node x by delta. The structure of the heap should be updated
    * to reflect this chage (for example, the cascading cuts procedure should be applied if needed).
    */
    public void decreaseKey(HeapNode x, int delta)
    {
    	x.key-= delta;
    	if(x.parent==null || x.key>=x.parent.key) //no cuts needed
    	{
    		if(x.key<this.min.key)// if the root changed updating the min if needed
    			this.min=x;
    		return;
    	}
    	HeapNode y = x.parent;
    	cut(x,y);
    	while(y.mark ==1){ // cascading cuts
    		HeapNode z = y.parent;
    		cut(y,y.parent);
    		y= z;
    	}
    	if(y.parent!=null){
    		y.mark= 1;
    		numberOfMarked++;
    	}
    	if(x.key<this.min.key) // updating the min if needed
    		this.min=x;
    	return; 
    }
   // cutting x from his parent y
   private void cut(HeapNode x, HeapNode y) {
	   totalCuts++;
	   x.parent = null;
	   if(x.mark == 1){
		   numberOfMarked--;
		   x.mark = 0;
	   }
	   y.rank = y.rank-1;
	   if (x.next == x) // x is an only child
		   y.child = null;
	   else 
	   {
		   y.child = x.next;
		   x.prev.next = x.next;
		   x.next.prev = x.prev;
		   x.next = x;
		   x.prev = x;
	   }
	   insertAfter(this.min,x); // adding x to the root list
	   numberOfTrees++;
}

/**
    * public int potential() 
    *
    * This function returns the current potential of the heap, which is:
    * Potential = #trees + 2*#marked
    * The potential equals to the number of trees in the heap plus twice the number of marked nodes in the heap. 
    */
    public int potential() 
    {    
    	return (this.numberOfTrees + 2*this.numberOfMarked); 
    }

   /**
    * public static int totalLinks() 
    *
    * This static function returns the total number of link operations made during the run-time of the program.
    * A link operation is the operation which gets as input two trees of the same rank, and generates a tree of 
    * rank bigger by one, by hanging the tree which has larger value in its root on the tree which has smaller value 
    * in its root.
    */
    public static int totalLinks()
    {    
    	return totalLinks; 
    }

   /**
    * public static int totalCuts() 
    *
    * This static function returns the total number of cut operations made during the run-time of the program.
    * A cut operation is the operation which diconnects a subtree from its parent (during decreaseKey/delete methods). 
    */
    public static int totalCuts()
    {    
    	return totalCuts; 
    }
    

    
   public int getSize() {
	return size;
    }

   public int getNumberOfTrees() {
    	return numberOfTrees;
    }

    public int getNumberOfMarked() {
	return numberOfMarked;
    }



/**
    * public class HeapNode
    * 
    * If you wish to implement classes other than FibonacciHeap
    * (for example HeapNode), do it in this file, not in 
    * another file 
    *  
    */
    public class HeapNode{
    	public int key;
    	protected int rank;
    	protected int mark;
    	protected HeapNode child;
    	protected HeapNode next;
    	protected HeapNode prev;
    	protected HeapNode parent;
    	
    	public HeapNode(int key){
    		this.key = key;
    		this.rank = 0;
    		this.mark = 0;
    		this.child = null;
    		this.next = null;
    		this.prev = null;
    		this.parent = null;
    	}
    	
    	public int getKey() {
    	    return this.key;
          }
    	
        void print(int level) {// to remove
        	HeapNode curr = this;
        	do {
        		StringBuilder sb = new StringBuilder();
        		for (int i = 0; i < level; i++) {
        			sb.append("  ");
        		}
        		sb.append(curr.key);
        		System.out.println(sb.toString());
        		if (curr.child != null) {
        			curr.child.print(level + 1);
        		}
        		curr = curr.next;
        	} while (curr != this);
        }
  	
    }
}

