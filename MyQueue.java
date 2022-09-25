import java.util.ArrayList;

/**
 * a class that implements a generic array based ADT Queue 
 * @author Christopher Perez Lebron
 *
 * @param <T> a generic type representing any object type
 */
public final class MyQueue<T> implements QueueInterface<T> {
	
	private T[] queue;
	private int frontIndex;
	private int backIndex; 
	private int numEntries; 
	private boolean integrityOK;
	private static final int DEFAULT_CAPACITY = 75; 
	private static final int MAX_CAPACITY = 10000;
	
	public MyQueue() {
		this(DEFAULT_CAPACITY);
	}
	
	
	public MyQueue(int initialCapacity) {
		integrityOK = false;
		checkCapacity(initialCapacity); 
		
		
		@SuppressWarnings("unchecked")
		T[] tempQueue = (T[]) new Object[initialCapacity + 1];
		
		queue = tempQueue;
		frontIndex = 0; 
		backIndex = initialCapacity; 
		
		numEntries = 0;
		integrityOK = true;
	}
	
	/**
	 * checks to ensure the parameter is smaller than the max 
	 * allowable capacity. If it is greater than the max 
	 * allowable capacity an IllegalStateException is thrown
	 * @throws IllegalStateException if capacity is too large
	 * @param initialCapacity an int representing the capacity 
	 * to be checked
	 * 
	 */
	private void checkCapacity(int initialCapacity) {
		if(initialCapacity > MAX_CAPACITY)
			throw new IllegalStateException();
		
	}
	
	/** 
	 * Adds a new entry to the back of this queue.
	 * @param newEntry  An object to be added. 
	 * @return true if operation is sucessful
	 * @throws QueueOverflowException if queue is full
	 * 
	 */
	public boolean enqueue(T newEntry) {
		checkIntegrity(); 
		if(isFull())
			throw new QueueOverflowException();
		backIndex = (backIndex + 1) % queue.length; 
		queue[backIndex] = newEntry; 
		numEntries++;
		return true;
	}
	
	/**
	 * checks to ensure integrity is ok 
	 * @throws IllegalStateException if the integrity of the object 
	 * is compromised
	 */
	private void checkIntegrity() {
		if(!integrityOK) {
			throw new IllegalStateException(); 
		}
	}
	
	/**
	 * Determines if Queue is empty
	 * @return true if Queue is empty, false if not
	 */
	public boolean isFull() {
		return (frontIndex == (backIndex + 2) % queue.length);
	}
	
	 /**  Retrieves the entry at the front of this queue.
    @return  The object at the front of the queue.
    @throws  EmptyQueueException if the queue is empty. */
	public T getFront() {
		checkIntegrity(); 
		if(isEmpty())
			throw new QueueUnderflowException();
		else
			return queue[frontIndex];
	}
	
	/** Detects whether this queue is empty.
    @return  True if the queue is empty, or false otherwise. */
	public boolean isEmpty() {
		return frontIndex == ((backIndex + 1) % queue.length);
	}
	
	/**
	 * Deletes and returns the element at the front of the Queue
	 * @return the element at the front of the Queue
	 * @throws QueueUnderflowException if queue is empty
	 */
	public T dequeue() {
		checkIntegrity(); 
		if(isEmpty()) 
			throw new QueueUnderflowException();
		else {
			T front = queue[frontIndex];
			queue[frontIndex] = null;
			frontIndex = (frontIndex + 1) % queue.length;
			numEntries--;
			return front;
		}		
	}
	
	/** Removes all entries from this queue. */
	public void clear() {
		while(!isEmpty())
			dequeue();
	}
	
	/**
	 * Returns number of elements in the Queue
	 * @return the number of elements in the Queue
	 */
	public int size() {
		
		return numEntries;
	}

	
	/**
	 * Returns the string representation of the elements in the Queue, 
	 * the beginning of the string is the front of the queue
	 * @return string representation of the Queue with elements
	 */
	public String toString() {
		//call other toString method with an empty string as a delimiter 
		String stackStr = toString("");
		
		return stackStr;
	}
	
	/**
	 * Returns the string representation of the elements in the Queue, the beginning of the string is the front of the queue
	 * Place the delimiter between all elements of the Queue
	 * @return string representation of the Queue with elements separated with the delimiter
	 */
	public String toString(String delimiter) {
		checkIntegrity(); 
		
		String stackStr = "";
		
		/*
		 * if stack is empty then topIndex is -1 and this for loop will not iterate 
		 * hence it will return an empty string which is reasonable
		 */
		for(int count = frontIndex; count < frontIndex + numEntries; count++) {
			
			stackStr += queue[count % queue.length];
			
			//do not add delimiter after the last item
			if(count != (frontIndex + numEntries -1)) 
				stackStr += delimiter;
		}
		
		return stackStr;
	}


	 /**
	  * Fills the Queue with the elements of the ArrayList, First element in the ArrayList
	  * is the first element in the Queue
	  * YOU MUST MAKE A COPY OF LIST AND ADD THOSE ELEMENTS TO THE QUEUE, if you use the
	  * list reference within your Queue, you will be allowing direct access to the data of
	  * your Queue causing a possible security breech.
	  * @param list elements to be added to the Queue
	  * @throws QueueOverflowException if queue is full
	 
	  */
	public void fill(ArrayList<T> list) {
		checkIntegrity();
		
		@SuppressWarnings("unchecked")
		ArrayList<T> listClone = (ArrayList<T>)list.clone();
		
		for(int count = 0; count < listClone.size(); count++)
			enqueue(listClone.get(count)); //this will throw StackOverflowException if the stack gets full
		
	}
	
	
}
