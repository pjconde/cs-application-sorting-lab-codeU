/**
 * 
 */
package com.flatironschool.javacs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Provides sorting algorithms.
 *
 */
public class ListSorter<T> {

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void insertionSort(List<T> list, Comparator<T> comparator) {
	
		for (int i=1; i < list.size(); i++) {
			T elt_i = list.get(i);
			int j = i;
			while (j > 0) {
				T elt_j = list.get(j-1);
				if (comparator.compare(elt_i, elt_j) >= 0) {
					break;
				}
				list.set(j, elt_j);
				j--;
			}
			list.set(j, elt_i);
		}
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void mergeSortInPlace(List<T> list, Comparator<T> comparator) {
		List<T> sorted = mergeSort(list, comparator);
		list.clear();
		list.addAll(sorted);
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * Returns a list that might be new.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public List<T> mergeSort(List<T> list, Comparator<T> comparator) {
        // Splits into two lists
        if (list.size() == 1) {
        	return list;
        }
        int mid = list.size()/2;
        List<T> left = list.subList(0, mid);
    	List<T> right = list.subList(mid, list.size());
    	// Recursive call to each half
    	mergeSort(left, comparator);
    	mergeSort(right, comparator);
    	List<T> output = merge(left, right, comparator);
        return output;
	}

	private List<T> merge(List<T> a, List<T> b, Comparator<T> comparator) {
		insertionSort(a, comparator);
		insertionSort(b, comparator);
		List<T> newList = new ArrayList<T>();
		int mergeSize = a.size() + b.size();
		int i = 0;
        int j = 0;
        while (i + j < mergeSize) {
            if (j == b.size() || (i < a.size()
                    && comparator.compare(a.get(i), b.get(j)) <= 0)) {
                newList.add(i + j, a.get(i++));
            } else {
                newList.add(i + j, b.get(j++));
            }
        }
		return newList;
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void heapSort(List<T> list, Comparator<T> comparator) {
        // FILL THIS IN!
        PriorityQueue<T> pq = new PriorityQueue<>(list.size(), comparator);
        for (T item :list) {
        	pq.offer(item);
        }
        list.clear();
        while (!pq.isEmpty()) {
        	list.add(pq.poll());
        }
	}

	
	/**
	 * Returns the largest `k` elements in `list` in ascending order.
	 * 
	 * @param k
	 * @param list
	 * @param comparator
	 * @return 
	 * @return
	 */
	public List<T> topK(int k, List<T> list, Comparator<T> comparator) {
        // FILL THIS IN!
        PriorityQueue<T> pq = new PriorityQueue<>(list.size(), comparator);
        for (T x: list) {
        	if (pq.size() < k) {
        		pq.offer(x);
        	} else {
        		int comp = comparator.compare(x, pq.peek());
        		if (comp > 0) {
        			pq.poll();
        			pq.offer(x);
        		}
        	}
        }
        List<T> output = new ArrayList<>();
        while (!pq.isEmpty()) {
        	output.add(pq.poll());
        }
        return output;
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		
		Comparator<Integer> comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer n, Integer m) {
				return n.compareTo(m);
			}
		};
		
		ListSorter<Integer> sorter = new ListSorter<Integer>();
		sorter.insertionSort(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.mergeSortInPlace(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.heapSort(list, comparator);
		System.out.println(list);
	
		list = new ArrayList<Integer>(Arrays.asList(6, 3, 5, 8, 1, 4, 2, 7));
		List<Integer> queue = sorter.topK(4, list, comparator);
		System.out.println(queue);
	}
}
