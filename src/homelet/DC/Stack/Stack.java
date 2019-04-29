package homelet.DC.Stack;

public interface Stack<E>{
	
	void push(E e);
	
	E pop();
	
	E peak();
	
	int size();
	
	int capacity();
	
	boolean empty();
}
