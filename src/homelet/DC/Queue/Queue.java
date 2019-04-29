package homelet.DC.Queue;

public interface Queue<E>{
	
	void enqueue(E e);
	
	E dequeue();
	
	E peak();
	
	int size();
	
	int capacity();
	
	boolean empty();
}
