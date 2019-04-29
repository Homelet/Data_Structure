package homelet.DC;

public abstract class DataStructure<E>{
	
	public abstract <T> T[] map(Producer<? super E, T> producer, Class<? extends T[]> classes);
	
	public abstract void each(Consumer<? super E> consumer);
}