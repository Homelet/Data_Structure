package homelet.DC.DDC.Array;

import homelet.DC.Consumer;
import homelet.DC.DDC.DynamicLinearDataCollection;
import homelet.DC.DataStructure;
import homelet.DC.Producer;

import java.util.*;

/**
 * put data into one Continuous sequence
 */
public class DynamicArray<E> extends DataStructure<E> implements DynamicLinearDataCollection<E>, RandomAccess{
	
	private E[] element;
	// pointing to the next available data in array
	private int size;
	
	public DynamicArray(){
		this(10);
	}
	
	@SuppressWarnings("unchecked")
	public DynamicArray(int capacity){
		element = (E[]) new Object[capacity];
		size = 0;
	}
	
	@SuppressWarnings("unchecked")
	public DynamicArray(Collection<E> collection){
		this(collection.toArray((E[]) new Object[0]));
	}
	
	public DynamicArray(E[] element){
		this.element = element;
		size = capacity();
	}
	
	// check is the index legal
	private void checkIndex(int index){
		if(size == index && size == 0)
			return;
		if(index < 0){
			throw new IllegalArgumentException("The Index Can't be Negative");
		}else if(index > size){
			throw new IllegalArgumentException("The Index Can't be Greater or equals Than Size");
		}
	}
	
	private void checkCapacity(int preserve){
		int requiredCap = size + preserve;
		if(requiredCap > capacity())
			resize(requiredCap * 2);
		else if(requiredCap <= capacity() / 4)
			resize(capacity() / 2);
	}
	
	@SuppressWarnings("unchecked")
	private void resize(int newCap){
		if(newCap == 0)
			return;
		E[] newElement = (E[]) new Object[newCap];
		for(int index = 0; index < size; index++)
			newElement[index] = element[index];
		element = newElement;
	}
	
	public int capacity(){
		return element.length;
	}
	
	@Override
	public int size(){
		return size;
	}
	
	@Override
	public void insert(int index, E e){
		checkIndex(index);
		checkCapacity(1);
		for(int i = size - 1; i >= index; i--)
			element[i + 1] = element[i];
		element[index] = e;
		size++;
	}
	
	@Override
	public void insertAll(int index, E[] e){
		checkIndex(index);
		checkCapacity(e.length);
		for(int i = size - 1; i >= index; i--)
			element[i + e.length] = element[i];
		for(E ele : e)
			element[index++] = ele;
		size += e.length;
	}
	
	@Override
	public E remove(int index){
		checkIndex(index);
		E buffer = element[index];
		for(int i = index; i < size; i++)
			element[i] = element[i + 1];
		element[size] = null;
		size--;
		return buffer;
	}
	
	@Override
	public void removeAll(int... indexes){
		if(indexes.length == 0)
			return;
		int indexBefore = 1;
		for(int index = indexes[0] + 1; index < size; index++){
			if(indexBefore < indexes.length && index == indexes[indexBefore]){
				indexBefore++;
			}else{
				element[index - indexBefore] = element[index];
				element[index] = null;
			}
		}
		size -= indexBefore;
	}
	
	@Override
	public void clear(){
		if(empty())
			return;
		for(int index = 0; index < size; index++){
			element[index] = null;
		}
		size = 0;
	}
	
	@Override
	public E peak(int index){
		checkIndex(index);
		return element[index];
	}
	
	@Override
	public E set(int index, E e){
		checkIndex(index);
		E buffer = element[index];
		element[index] = e;
		return buffer;
	}
	
	@Override
	public boolean has(E e){
		for(int index = 0; index < size; index++){
			if(element[index].equals(e))
				return true;
		}
		return false;
	}
	
	@Override
	public int find(E e){
		for(int index = 0; index < size; index++){
			if(element[index] == e)
				return index;
		}
		return -1;
	}
	
	@Override
	public int[] findAll(E e){
		int[] indexes = new int[size];
		int   size    = 0;
		for(int index = 0; index < this.size; index++){
			if(element[index].equals(e))
				indexes[size++] = index;
		}
		return Arrays.copyOfRange(indexes, 0, size);
	}
	
	@Override
	public <T> T[] map(Producer<? super E, T> producer, Class<? extends T[]> classes){
		Object[] returned = new Object[size];
		for(int index = 0; index < size; index++){
			returned[index] = producer.produce(index, element[index]);
		}
		return Arrays.copyOf(returned, returned.length, classes);
	}
	
	@Override
	public void each(Consumer<? super E> consumer){
		for(int index = 0; index < size; index++){
			consumer.consume(index, element[index]);
		}
	}
	
	@Override
	public E[] asArray(){
		return Arrays.copyOf(element, size);
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
		
		private int cursor = 0;
		
		@Override
		public boolean hasNext(){
			return cursor < size();
		}
		
		@Override
		public E next(){
			return element[cursor++];
		}
	}
}
