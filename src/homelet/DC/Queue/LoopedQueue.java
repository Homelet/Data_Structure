package homelet.DC.Queue;

import homelet.DC.Consumer;
import homelet.DC.DataStructure;
import homelet.DC.Producer;

import java.util.Arrays;

public class LoopedQueue<E> extends DataStructure<E> implements Queue<E>{
	
	private E[] queue;
	private int front, tail;
	
	@SuppressWarnings("unchecked")
	public LoopedQueue(int capacity){
		this.queue = (E[]) new Object[capacity + 1];
		front = 0;
		tail = 0;
	}
	
	public LoopedQueue(){
		this(10);
	}
	
	@Override
	public void enqueue(E e){
		checkSize();
		queue[tail] = e;
		tail = (tail + 1) % queue.length;
	}
	
	@Override
	public E dequeue(){
		checkEmpty();
		checkSize();
		E result = queue[front];
		queue[front] = null;
		front = (front + 1) % queue.length;
		return result;
	}
	
	private void checkEmpty(){
		if(empty())
			throw new IllegalStateException("Queue Is Empty");
	}
	
	private void checkSize(){
		if((tail + 1) % queue.length == front)
			resize(capacity() * 2);
		if(size() == capacity() / 4)
			resize(capacity() / 2);
	}
	
	@SuppressWarnings("unchecked")
	private void resize(int requiredSize){
		if(requiredSize == 0)
			return;
		E[] newQueue = (E[]) new Object[requiredSize + 1];
		for(int i = 0; i < size(); i++)
			newQueue[i] = queue[(i + front) % queue.length];
		queue = newQueue;
		tail = size();
		front = 0;
	}
	
	@Override
	public E peak(){
		checkEmpty();
		return queue[front];
	}
	
	@Override
	public int size(){
		// the size equals to the item between the front the tail
		// if the tail smaller than front means tail has been placed before front,
		// so the size will be |~tail front~|
		// else will be | front~tail |
		if(tail < front)
			return (tail) + (capacity() - front + 1);
		else
			return tail - front;
	}
	
	@Override
	public int capacity(){
		return queue.length - 1;
	}
	
	@Override
	public boolean empty(){
		return front == tail;
	}
	
	@Override
	public <T> T[] map(Producer<? super E, T> producer, Class<? extends T[]> classes){
		int      size     = size();
		Object[] returned = new Object[size];
		for(int index = 0; index < size; index++){
			returned[index] = producer.produce(index, queue[(index + front) % queue.length]);
		}
		return Arrays.copyOf(returned, returned.length, classes);
	}
	
	@Override
	public void each(Consumer<? super E> consumer){
		for(int index = 0, size = size(); index < size; index++){
			consumer.consume(index, queue[(index + front) % queue.length]);
		}
	}
}
