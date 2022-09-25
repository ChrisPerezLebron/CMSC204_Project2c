import java.util.ArrayList;

/**
 * a class that implements a generic array based ADT Stack 
 * @author Christopher Perez Lebron
 *
 * @param <T> a generic type representing any object type
 */
public final class MyStack<T> implements StackInterface<T> {
	private T[] stack;
	private int topIndex;
	private boolean integrityOK; 
	private static final int DEFAULT_CAPACITY = 50;
	private static final int MAX_CAPACITY = 10000;
	
	public MyStack() {
		this(DEFAULT_CAPACITY); 
	}
	
	public MyStack(int initialCapacity) {
		integrityOK = false; 
		checkCapacity(initialCapacity);
		
		//case is safe because new array contains null entries
		@SuppressWarnings("unchecked")
		T[] tempStack = (T[]) new Object[initialCapacity];
		stack = tempStack;
		topIndex = -1; 
		integrityOK = true;
	}
	
	/**
	 * checks to ensure the parameter is smaller than the max 
	 * allowable capacity. If it is greater than the max 
	 * allowable capacity an IllegalStateException is thrown
	 * @throws IllegalStateException if capacity is too large
	 * @param initialCapacity an int representing the capacity 
	 * to be checked
	 */
	private void checkCapacity(int desiredCapacity) {
		if(desiredCapacity > MAX_CAPACITY)
			throw new IllegalStateException();
	}
	
	/**
	 * Adds an element to the top of the Stack
	 * @param newEntry the element to add to the top of the Stack
	 * @return true if the add was successful, false if not
	 * @throws StackOverflowException if stack is full
	 */
	public boolean push(T newEntry) {
		checkIntegrity();
		if(isFull())
			throw new StackOverflowException(); 
		stack[topIndex + 1] = newEntry;
		topIndex++;
		return true;
	}
	
	/**
	 * checks to see if integrity is false if so it throws an exception
	 * @throws IllegalStateException if integrity is false
	 */
	private void checkIntegrity() {
		if(!integrityOK) {
			throw new IllegalStateException(); 
		}
	}
	
	/**
	 * Returns the element at the top of the Stack, does not pop it off the Stack
	 * @return the element at the top of the Stack
	 * @throws StackUnderflowException if stack is empty
	 */
	public T top() throws StackUnderflowException {
		checkIntegrity();
		if(isEmpty()) 
			throw new StackUnderflowException();
		else 
			return stack[topIndex];
	}
	
	
	/**
	 * Deletes and returns the element at the top of the Stack
	 * @return the element at the top of the Stack
	 * @throws StackUnderflowException if stack is empty
	 */
	public T pop() throws StackUnderflowException {
		checkIntegrity();
		if(isEmpty())
			throw new StackUnderflowException();
		else {
			T top = stack[topIndex];
			stack[topIndex] = null;
			topIndex--;
			return top;
		}
	}
	
	
	/**
	 * Determines if Stack is empty
	 * @return true if Stack is empty, false if not
	 */
	public boolean isEmpty() {
		return topIndex < 0;
	}
	
	
	/**
	 * Determines if Stack is full
	 * @return true if Stack is full, false if not
	 */
	public boolean isFull() {
		return topIndex >= stack.length -1; 
	}
	
	/**
	 * Number of elements in the Stack
	 * @return the number of elements in the Stack
	 */
	public int size() {
		return topIndex + 1;
	}
	
	/**
	 * Returns the elements of the Stack in a string from bottom to top, the beginning 
	 * of the String is the bottom of the stack
	 * @return an string which represent the Objects in the Stack from bottom to top
	 */
	public String toString() {
		//call other toString method with an empty string as a delimiter 
		String stackStr = toString("");
		
		return stackStr;
	}
	
	/**
	 * Returns the string representation of the elements in the Stack, the beginning of the 
	 * string is the bottom of the stack
	 * Place the delimiter between all elements of the Stack
	 * @return string representation of the Stack from bottom to top with elements 
	 * separated with the delimiter
	 */
	public String toString(String delimiter) {
		checkIntegrity(); 
		
		String stackStr = "";
		
		/*
		 * if stack is empty then topIndex is -1 and this for loop will not iterate 
		 * hence it will return an empty string which is reasonable
		 */
		for(int count = 0; count <= topIndex; count++) {
			stackStr += stack[count];
			
			//do not add delimiter after the last item
			if(count != topIndex) 
				stackStr += delimiter;
		}
		
		return stackStr;
	}
	
	 /**
	  * Fills the Stack with the elements of the ArrayList, First element in the ArrayList
	  * is the first bottom element of the Stack
	  * YOU MUST MAKE A COPY OF LIST AND ADD THOSE ELEMENTS TO THE STACK, if you use the
	  * list reference within your Stack, you will be allowing direct access to the data of
	  * your Stack causing a possible security breech.
	  * @param list elements to be added to the Stack from bottom to top
	  * @throws StackOverflowException if stack gets full
	  */
	public void fill(ArrayList<T> list) {
		
		/*
		 * I am confused why making a copy of the array list is necessary? Making a copy of 
		 * the ArrayList will simply generate a new ArrayList object holding the SAME 
		 * references. Therefore, client will still have a reference to the objects 
		 * contained within the Stack. 
		 * 
		 * Also, I cannot make a copy of the individual objects so that cannot 
		 * possibly be an option. Unless I enforce that the objects MUST have 
		 * a copy constructor or copy method. 
		 * 
		 *  I also understand that deleting items from the ArrayList using the 
		 *  reference that gets passed in will affect the client's object. But, 
		 *  we have no reason to care about that. The only thing we should care 
		 *  about is outside code having a reference to objects in the stack & 
		 *  copying the ArrayList will not prevent that.
		 * 
		 */
		checkIntegrity();
		
		@SuppressWarnings("unchecked")
		ArrayList<T> listClone = (ArrayList<T>)list.clone();
		
		for(int count = 0; count < listClone.size(); count++)
			push(listClone.get(count)); //this will throw StackOverflowException if the stack gets full
		
		
	}
	
	

}
