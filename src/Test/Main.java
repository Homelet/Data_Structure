package Test;

import java.util.ArrayList;
import java.util.Arrays;

public class Main{
	
	public static void main(String[] args){
		ArrayList<Integer> arrayList = new ArrayList<>();
//		LinkedList<Integer> integers = new LinkedList<>();
//		DynamicArray<Integer> integers = new DynamicArray<>();
//		System.out.println(integers.peakFirst());
//		System.out.println(integers.peakLast());
//		integers.append(1);
//		integers.append(11);
//		integers.append(111);
//		integers.append(11111);
//		integers.append(111111);
//		integers.append(1111111);
//		integers.remove(Integer.valueOf("11"));
//		integers.append(11);
//		integers.append(11111111);
//		integers.append(1);
//		integers.append(11);
//		integers.append(111);
//		integers.append(11111);
//		integers.append(111111);
//		integers.append(1111111);
//		integers.remove(Integer.valueOf("11"));
//		integers.append(11);
//		integers.append(11111111);
//		integers.each(((index, integer)->{
//			System.out.println(index + " | " + integer);
//		}));
//		System.out.println(Arrays.toString(integers.asArray()));
	}
	
	public static int fleetCount = 0;
	
	public int carFleet(int target, int[] position, int[] speed){
		int[][] arr      = mergeSort(position, speed);
		boolean finished = false;
		do{
			finished = move(target, arr);
		}while(!finished);
		return fleetCount;
	}
	
	private boolean move(int target, int[][] arr){
		boolean finished = false;
		for(int index = 0; index < arr.length; index++){
			int nextPos = arr[index][0] + arr[index][1];
			if(nextPos >= arr[index + 1][0])
				nextPos = arr[index + 1][0];
			boolean fin = nextPos >= target;
			finished = finished | fin;
			if(finished)
				continue;
			arr[index][0] = nextPos;
		}
		return finished;
	}
	
	private int[][] mergeSort(int[] pos, int[] speed){
		int[][] sorted = new int[pos.length][2];
		for(int i = 0; i < pos.length; i++){
			sorted[i][0] = pos[i];
			sorted[i][1] = speed[i];
		}
		split(sorted, 0, pos.length - 1);
		return sorted;
	}
	
	private void split(int[][] arr, int left, int right){
		if(left >= right)
			return;
		int middle = (left + right) / 2;
		split(arr, left, middle);
		split(arr, middle + 1, right);
		merge(arr, left, middle, right);
	}
	
	private void merge(int[][] data, int leftBounds, int mid, int rightBounds){
		int[][] subData       = Arrays.copyOfRange(data, leftBounds, rightBounds + 1);
		int     left_pointer  = leftBounds;
		int     right_pointer = mid + 1;
		for(int index = leftBounds; index <= rightBounds; index++){
			if(left_pointer > mid){
				data[index] = subData[right_pointer - leftBounds];
				right_pointer++;
			}else if(right_pointer > rightBounds){
				data[index] = subData[left_pointer - leftBounds];
				left_pointer++;
			}else if(subData[left_pointer - leftBounds][0] < subData[right_pointer - leftBounds][0]){
				data[index] = subData[left_pointer - leftBounds];
				left_pointer++;
			}else{
				data[index] = subData[right_pointer - leftBounds];
				right_pointer++;
			}
		}
	}
}
