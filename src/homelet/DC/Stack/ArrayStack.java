package homelet.DC.Stack;

import homelet.DC.DDC.Array.DynamicArray;

public class ArrayStack<E> implements Stack<E>{
	
	private DynamicArray<E> stack;
	
	public ArrayStack(int stackSize){
		stack = new DynamicArray<>(stackSize);
	}
	
	public ArrayStack(){
		stack = new DynamicArray<>();
	}
	
	@Override
	public void push(E e){
		stack.append(e);
	}
	
	@Override
	public E pop(){
		return stack.removeLast();
	}
	
	@Override
	public E peak(){
		return stack.peakLast();
	}
	
	@Override
	public int size(){
		return stack.size();
	}
	
	@Override
	public boolean empty(){
		return stack.empty();
	}
	
	@Override
	public int capacity(){
		return stack.capacity();
	}
	
	public DynamicArray<E> getStack(){
		return stack;
	}
}
