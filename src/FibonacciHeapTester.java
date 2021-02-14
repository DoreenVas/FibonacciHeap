import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Random;

public class FibonacciHeapTester {
	protected ArrayList<Integer> keysInHeap;
	protected ArrayList<FibonacciHeap.HeapNode> heapNodes;
	protected FibonacciHeap fibonacciHeapToTest;
	protected ArrayList<Integer> keysInHeapToMeld;
	
	public FibonacciHeapTester(){
		this.keysInHeap = new ArrayList<Integer>();
		this.heapNodes = new ArrayList<>();
		this.fibonacciHeapToTest = new FibonacciHeap();
		this.keysInHeapToMeld = new ArrayList<>();
	}
	
	void HeapInsretion(int size){
		int randomKey;
		Random random = new Random();
		FibonacciHeap.HeapNode newNode;
		for ( int i = 0; i < size; i++){
			randomKey = random.nextInt(100);
			newNode = this.fibonacciHeapToTest.insert(randomKey);
			this.keysInHeap.add(randomKey);
			this.heapNodes.add(newNode);
			testAfterInsertion();
		}
		
	}

	void testAfterInsertion() {
		int minKey = Collections.min(this.keysInHeap);
		int size = this.keysInHeap.size();
		if(this.fibonacciHeapToTest.findMin().key != minKey){
			System.err.println(" Error in findMin() Method, returned value should be:" + minKey);
		}
		if(fibonacciHeapToTest.size() != size){
			System.err.println(" Error in size() Method, returned value should be:" + size);
		}
		if(fibonacciHeapToTest.empty() && size != 0){
			System.err.println(" Error in empty() Method, returned value should be False");
		}
		
	}
	
	void heapDeletion(int deletions){
		Collections.sort(keysInHeap);
		Collections.sort(heapNodes, new Comparator<FibonacciHeap.HeapNode>(){
			public int compare(FibonacciHeap.HeapNode h1, FibonacciHeap.HeapNode h2){
				return new Integer(h1.key).compareTo(new Integer(h2.key));
			}
		});
		for(int i = 0; i<deletions; i++){
			fibonacciHeapToTest.deleteMin();
			keysInHeap.remove(0);
			heapNodes.remove(0);
			testAfterDeletion();
		}
		
	}
	
	private void printSingleHeap(FibonacciHeap.HeapNode x){
		if(x == null){ return; }
		ArrayList<FibonacciHeap.HeapNode> listNodesSiblings = new ArrayList<>();
		ArrayList<FibonacciHeap.HeapNode> tempListNodes;
		FibonacciHeap.HeapNode siblingNode;
		int countNull = 0;
		System.out.print("*" + x.key + "*");
		System.out.println();
		if(x.child != null){ 
			listNodesSiblings.add(x.child);
		}
		while (countNull != listNodesSiblings.size()){
			tempListNodes = new ArrayList<>();
			countNull = 0;
			for(FibonacciHeap.HeapNode root: listNodesSiblings){
				siblingNode = root;
				System.out.print("|*");
				do{
					if(siblingNode != null){
						System.out.print(siblingNode.key + " ");
						tempListNodes.add(siblingNode.child);
						if(siblingNode.child == null){
							countNull++;
						}
						siblingNode = siblingNode.next;
					}
				} while(siblingNode != root);
				System.out.print("*| ");
			}
			System.out.println();
			listNodesSiblings = tempListNodes;		
		}	
	}
	
	private void printHeap(FibonacciHeap heapToPrint){
		if(heapToPrint == null) {return; }
		FibonacciHeap.HeapNode minNode = heapToPrint.findMin();
		FibonacciHeap.HeapNode rootNode = minNode;
		int count = 1;
		do {
			System.out.println("Heap number: " + count);
			printSingleHeap(rootNode);
			System.out.println();
			count++;
			rootNode = rootNode.next;
		} while(rootNode != minNode);	
	}
	
	void fixedInsertion(){
		int[] arr = new int[]{23,9,15,33,40,20,35,45,58,67,17,50,55,60,70,80,90,95};
		FibonacciHeap.HeapNode newNode;
		for (int key: arr){
			newNode = this.fibonacciHeapToTest.insert(key);
			this.keysInHeap.add(key);
			this.heapNodes.add(newNode);
			testAfterInsertion();
		}
		Collections.sort(keysInHeap);
		Collections.sort(heapNodes, new Comparator<FibonacciHeap.HeapNode>(){
			public int compare(FibonacciHeap.HeapNode h1, FibonacciHeap.HeapNode h2){
				return new Integer(h1.key).compareTo(new Integer(h2.key));
			}
		});
		
	}
	
	void secondFixedInsertion(){
		int[] arr = new int[]{69, 9, 79, 75, 2, 18, 78, 55, 27};
		FibonacciHeap.HeapNode newNode;
		for (int key: arr){
			newNode = this.fibonacciHeapToTest.insert(key);
			this.keysInHeap.add(key);
			this.heapNodes.add(newNode);
			testAfterInsertion();
		}
		Collections.sort(keysInHeap);
		Collections.sort(heapNodes, new Comparator<FibonacciHeap.HeapNode>(){
			public int compare(FibonacciHeap.HeapNode h1, FibonacciHeap.HeapNode h2){
				return new Integer(h1.key).compareTo(new Integer(h2.key));
			}
		});
	}
	
	void fixedDeletion(){
		for(int i =0; i<4; i++){
			fibonacciHeapToTest.deleteMin();
			keysInHeap.remove(0);
			heapNodes.remove(0);
			testAfterDeletion();
			printHeap(fibonacciHeapToTest);
		}
		
	}
	
	void testAfterDeletion() {
		//TO DO
		int minKey = Collections.min(this.keysInHeap);
		int size = this.keysInHeap.size();
		if(size == 0){
			if(this.fibonacciHeapToTest.findMin() != null){
				System.err.println(" Error in findMin() Method, returned value should be: null" );
			}
		}
		else{
			if(this.fibonacciHeapToTest.findMin().key != minKey){
				System.err.println(" Error in findMin() Method, returned value should be:" + minKey);
			}
		}
		
		if(fibonacciHeapToTest.size() != size){
			System.err.println(" Error in size() Method, returned value should be:" + size);
		}
		if(fibonacciHeapToTest.empty() && size != 0){
			System.err.println(" Error in empty() Method, returned value should be False");
		}
		testRanks();
		
	}
	
	private void testRanks() {
		FibonacciHeap.HeapNode  minNode = fibonacciHeapToTest.findMin();
		FibonacciHeap.HeapNode x = minNode;
		HashSet<Integer> ranksInHeap = new HashSet<>();
		int rankNode;
		do{
			rankNode = x.rank;
			if(ranksInHeap.contains(rankNode)){
				System.err.println("error: the rank " + rankNode + "epear twice");
			}
			else{
				ranksInHeap.add(rankNode);
			}
			x = x.next;
		}while(x != minNode);
	}

	void meldTest(){
		FibonacciHeap heapToMeld = createHeapToMeld();
		System.out.println("~~~ heapToMeld ~~~");
		printHeap(heapToMeld);
		fibonacciHeapToTest.meld(heapToMeld);
		this.keysInHeap.addAll(this.keysInHeapToMeld);
		this.keysInHeapToMeld = null;
		testAfterInsertion();
		
	}

	private FibonacciHeap createHeapToMeld() {
		FibonacciHeap heapToMeld = new FibonacciHeap();
		Random rnd = new Random();
		int size = 0;
		while(size == 0){
			size = rnd.nextInt(50);
		}
		int key;
		for(int i = 0; i< size; i++){
			key = rnd.nextInt(100);
			heapToMeld.insert(key);
			this.keysInHeapToMeld.add(key);
		}
		Collections.sort(this.keysInHeapToMeld);
		//System.out.println(this.keysInHeapToMeld.toString());
		//printHeap(heapToMeld);
		heapToMeld.deleteMin();
		this.keysInHeapToMeld.remove(0);
		//System.out.println(this.keysInHeapToMeld.toString());
		return heapToMeld;
	}

	void run(){
		/*
		System.out.println("~~~~ Let's get started :)  ~~~");
		System.out.println("~~~~ Inserting nodes - round 1 ~~~");
		Random rand = new Random();
		int randSize = 0;
		while(randSize == 0){
			randSize = rand.nextInt(100);
		}
	    HeapInsretion(randSize);
		int deleteSize = 0;
		while(deleteSize ==0){
			deleteSize = rand.nextInt(randSize);
		} 
		System.out.println("~~~~ Deleting nodes - round 1 ~~~");
		heapDeletion(deleteSize);
		randSize = 0;
		while(randSize == 0){
			randSize = rand.nextInt(20);
		}
		System.out.println("~~~~ Inserting nodes - round 2 ~~~");		
		HeapInsretion(randSize);
		deleteSize = 0;
		while(deleteSize ==0){
			deleteSize = rand.nextInt(randSize);
		} 
		System.out.println("~~~~ Deleting nodes - round 2 ~~~");
		heapDeletion(deleteSize);
		printHeap(fibonacciHeapToTest);
		System.out.println("~~~~ Testing meld  ~~~");
		printHeap(fibonacciHeapToTest);
		System.out.println("~~~~ melding  ~~~");
		meldTest();
		System.out.println("~~~~ heap after melding  ~~~");
		printHeap(fibonacciHeapToTest);
		
		//fibonacciHeapToTest.findMin().print(0);
		*/
		fixedInsertion();
		heapDeletion(1);
		printHeap(fibonacciHeapToTest);
		System.out.println(keysInHeap.toString());		
		fibonacciHeapToTest.decreaseKey(heapNodes.get(9),10);
		printHeap(fibonacciHeapToTest);
		fibonacciHeapToTest.decreaseKey(heapNodes.get(14),76);
		printHeap(fibonacciHeapToTest);
		heapNodes.get(11).mark=1;
		fibonacciHeapToTest.decreaseKey(heapNodes.get(13),15);
		printHeap(fibonacciHeapToTest);
		fibonacciHeapToTest.decreaseKey(heapNodes.get(14),3);
		printHeap(fibonacciHeapToTest);
		System.out.println(fibonacciHeapToTest.findMin().key);
	}
	
	
	public static void main(String[] args){
		FibonacciHeapTester tester = new FibonacciHeapTester();
		tester.run();
		System.out.println(" ** Done :) **");
		System.out.println("Hakuna matata");
	}

}
