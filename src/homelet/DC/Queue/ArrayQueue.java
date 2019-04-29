package homelet.DC.Queue;

import homelet.DC.Consumer;
import homelet.DC.DDC.Array.DynamicArray;
import homelet.DC.DataStructure;
import homelet.DC.Producer;

public class ArrayQueue<E> extends DataStructure<E> implements Queue<E>{
	
	private DynamicArray<E> queue;
	
	public ArrayQueue(int stackSize){
		queue = new DynamicArray<>(stackSize);
	}
	
	public ArrayQueue(){
		queue = new DynamicArray<>();
	}
	
	@Override
	public void enqueue(E e){
		queue.append(e);
	}
	
	@Override
	public E dequeue(){
		return queue.removeFirst();
	}
	
	@Override
	public E peak(){
		return queue.peakFirst();
	}
	
	@Override
	public int size(){
		return queue.size();
	}
	
	@Override
	public int capacity(){
		return queue.capacity();
	}
	
	@Override
	public boolean empty(){
		return queue.empty();
	}
	
	public DynamicArray<E> getQueue(){
		return queue;
	}
	
	@Override
	public <T> T[] map(Producer<? super E, T> producer, Class<? extends T[]> classes){
		return queue.map(producer, classes);
	}
	
	@Override
	public void each(Consumer<? super E> consumer){
		queue.each(consumer);
	}
}
