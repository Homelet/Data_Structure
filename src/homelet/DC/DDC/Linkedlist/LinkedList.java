package homelet.DC.DDC.Linkedlist;

import homelet.DC.Consumer;
import homelet.DC.DDC.DynamicLinearDataCollection;
import homelet.DC.DataStructure;
import homelet.DC.Producer;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class LinkedList<E> extends DataStructure<E> implements DynamicLinearDataCollection<E>{
	
	private LinkedNode head;
	private int        size;
	
	public LinkedList(){
		head = null;
		size = 0;
	}
	
	private void checkIndex(int index){
		if(index < 0){
			throw new IllegalArgumentException("The Index Can't be Negative");
		}else if(index >= size){
			throw new IllegalArgumentException("The Index Can't be Greater or equals Than Size");
		}
	}
	
	private LinkedNode scrollTo(int index){
		if(index < 0) // if index is smaller than 0 means requesting the very head's parent so returns null
			return null;
		LinkedNode node = head;
		for(int i = 0; i < index; i++){
			node = node.next;
		}
		return node;
	}
	
	@Override
	public int size(){
		return size;
	}
	
	@Override
	public void insert(int index, E e){
		checkIndex(index);
		// scroll to index - 1 so that we can fix the pointer
		LinkedNode localHead = scrollTo(index - 1);
		// if null means processing the head
		if(localHead == null){
			head = new LinkedNode(e, head);
		}else{
			localHead.next = new LinkedNode(e, localHead.next);
		}
		size++;
	}
	
	@Override
	public void insertAll(int index, E[] e){
		if(e.length == 0)
			return;
		checkIndex(index);
		// scroll to index - 1 so that we can fix the pointer
		LinkedNode localHead = scrollTo(index - 1);
		// if null means processing the head
		if(localHead == null){
			localHead = new LinkedNode(e[0], null);
			head = localHead;
			LinkedNode tail = head;
			for(int i = 1; i < e.length; i++){
				// here next is null because it is will to filled by the next newNode;
				LinkedNode newNode = new LinkedNode(e[i], null);
				// here fill the next value
				localHead.next = newNode;
				// reAssign the localHead to newNode
				localHead = newNode;
			}
			// here the trailing local head should has next value equals to null, so here fill that
			localHead.next = tail;
		}else{
			LinkedNode tail = localHead.next;
			for(int i = 0; i < e.length; i++){
				// here next is null because it is will to filled by the next newNode;
				LinkedNode newNode = new LinkedNode(e[i], null);
				// here fill the next value
				localHead.next = newNode;
				// reAssign the localHead to newNode
				localHead = newNode;
			}
			// here the trailing local head should has next value equals to null, so here fill that
			localHead.next = tail;
		}
		size += e.length;
	}
	
	@Override
	public E remove(int index){
		checkIndex(index);
		LinkedNode localHead = scrollTo(index - 1);
		// if null means processing head
		E returned;
		if(localHead == null){
			returned = head.element;
			head = head.next;
		}else{
			returned = localHead.next.element;
			// reRoute the next to the nextNode's next
			localHead.next = localHead.next.next;
		}
		size--;
		return returned;
	}
	
	@Override
	public void removeAll(int... indexes){
		for(int index : indexes)
			remove(index);
	}
	
	@Override
	public void clear(){
		LinkedNode node = head;
		while(node != null){
			LinkedNode next = node.next;
			node.element = null;
			node.next = null;
			node = next;
		}
		size = 0;
	}
	
	@Override
	public E peak(int index){
		checkIndex(index);
		return scrollTo(index).element;
	}
	
	@Override
	public E set(int index, E e){
		checkIndex(index);
		LinkedNode node     = scrollTo(index);
		E          returned = node.element;
		node.element = e;
		return returned;
	}
	
	@Override
	public boolean has(E e){
		for(LinkedNode node = head; node != null; ){
			if(node.element.equals(e))
				return true;
			node = node.next;
		}
		return false;
	}
	
	@Override
	public int find(E e){
		int index = 0;
		for(LinkedNode node = head; node != null; index++){
			if(node.element.equals(e))
				return index;
			node = node.next;
		}
		return -1;
	}
	
	@Override
	public int[] findAll(E e){
		int[] indexes = new int[size];
		int   size    = 0;
		int   index   = 0;
		for(LinkedNode node = head; node != null; index++){
			if(node.element.equals(e))
				indexes[size++] = index;
			node = node.next;
		}
		return Arrays.copyOfRange(indexes, 0, size);
	}
	
	@Override
	public <T> T[] map(Producer<? super E, T> producer, Class<? extends T[]> classes){
		Object[] returned = new Object[size];
		int      index    = 0;
		for(LinkedNode node = head; node != null; index++){
			returned[index] = producer.produce(index, node.element);
			node = node.next;
		}
		return Arrays.copyOf(returned, returned.length, classes);
	}
	
	@Override
	public void each(Consumer<? super E> consumer){
		int index = 0;
		for(LinkedNode node = head; node != null; index++){
			consumer.consume(index, node.element);
			node = node.next;
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public E[] asArray(){
		E[] arr = (E[]) new Object[size];
		each((index, e)->arr[index] = e);
		return arr;
	}
	
	@Override
	public List<E> asList(){
		return Arrays.asList(asArray());
	}
	
	@Override
	public Iterator<E> iterator(){
		return new Itr();
	}
	
	private class Itr implements Iterator<E>{
		
		LinkedNode currentNode;
		
		Itr(){
			this.currentNode = head;
		}
		
		@Override
		public boolean hasNext(){
			return currentNode.next != null;
		}
		
		@Override
		public E next(){
			currentNode = currentNode.next;
			return currentNode.element;
		}
	}
	
	private class LinkedNode{
		
		E          element;
		LinkedNode next;
		
		LinkedNode(E element, LinkedNode next){
			this.element = element;
			this.next = next;
		}
	}
}
