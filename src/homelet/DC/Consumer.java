package homelet.DC;

/**
 * Consume E
 *
 * @param <E>
 */
public interface Consumer<E>{
	
	void consume(int index, E e);
}
