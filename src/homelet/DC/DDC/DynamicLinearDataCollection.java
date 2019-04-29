package homelet.DC.DDC;

import java.util.List;

/**
 * Linear Data Collection
 *
 * @param <E> the data type
 */
public interface DynamicLinearDataCollection<E> extends Iterable<E>{
	
	int size();
	
	void insert(int index, E e);
	
	void insertAll(int index, E[] e);
	
	E remove(int index);
	
	void removeAll(int... indexes);
	
	void clear();
	
	E peak(int index);
	
	E set(int index, E e);
	
	boolean has(E e);
	
	int find(E e);
	
	int[] findAll(E e);
	
	E[] asArray();
	
	List<E> asList();
	
	default boolean empty(){
		return size() == 0;
	}
	
	default void append(E e){
		insert(size(), e);
	}
	
	default void prepend(E e){
		insert(0, e);
	}
	
	default void appendAll(E[] e){
		insertAll(size(), e);
	}
	
	default void prependAll(E[] e){
		insertAll(0, e);
	}
	
	default E removeFirst(){
		return remove(0);
	}
	
	default E removeLast(){
		return remove(size() - 1);
	}
	
	default void remove(E e){
		int i = find(e);
		if(i >= 0)
			remove(i);
	}
	
	default void removeAll(E e){
		removeAll(findAll(e));
	}
	
	default E peakFirst(){
		return peak(0);
	}
	
	default E peakLast(){
		return peak(size() - 1);
	}
}
