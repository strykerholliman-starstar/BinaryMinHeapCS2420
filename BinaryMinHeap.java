package assign10;

import java.util.*;

public class BinaryMinHeap<E> implements PriorityQueue<E> {
    private static final int DEFAULT_CAPACITY = 100;
    
    private E[] heap;
    private int size;
    private Comparator<? super E> comparator;
    
    // Constructors
    @SuppressWarnings("unchecked")
    public BinaryMinHeap() {
        this.comparator = null;
        this.size = 0;
        this.heap = (E[]) new Object[DEFAULT_CAPACITY];
    }
    
    @SuppressWarnings("unchecked")
    public BinaryMinHeap(Comparator<? super E> comparator) {
        this.comparator = comparator;
        this.size = 0;
        this.heap = (E[]) new Object[DEFAULT_CAPACITY];
    }
    
    @SuppressWarnings("unchecked")
    public BinaryMinHeap(List<? extends E> list) {
        this.comparator = null;
        this.size = list.size();
        this.heap = (E[]) new Object[size + DEFAULT_CAPACITY];
        
        int i = 0;
        for (E item : list) {  
            heap[i] = item;
            i++;
        }
        
        buildHeap();
    }
    
    @SuppressWarnings("unchecked")
    public BinaryMinHeap(List<? extends E> list, Comparator<? super E> comparator) {
        this.comparator = comparator;
        this.size = list.size();
        this.heap = (E[]) new Object[size + DEFAULT_CAPACITY];
        
        int i = 0;
        for (E item : list) {  
            heap[i] = item;
            i++;
        }
        
        buildHeap();
    }
    
    
    /**
     * Builds a heap from the current array in O(N) time.
     * Starts from the last non-leaf node and percolates down.
     */
    private void buildHeap() {
        for (int i = (size / 2) - 1; i >= 0; i--) {
            percolateDown(i);
        }
    }
    
    /**
     * Moves the element at the given index up to restore heap property.
     * Used after inserting a new element.
     */
    private void percolateUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            
            if (innerCompare(heap[index], heap[parentIndex]) < 0) {
                E temp = heap[index];
                heap[index] = heap[parentIndex];
                heap[parentIndex] = temp;
                index = parentIndex;
            } else {
                break;
            }
        }
    }
    
    /**
     * Moves the element at the given index down to restore heap property.
     * Used after extracting the minimum or during buildHeap.
     * Compares both children to find the smaller one before deciding to swap.
     */
    private void percolateDown(int index) {
        int childIndex = 2 * index + 1; // Left child
        
        while (childIndex < size) {
            // Find the smaller child (if right child exists and is smaller)
            if (childIndex + 1 < size && 
                innerCompare(heap[childIndex + 1], heap[childIndex]) < 0) {
                childIndex++;
            }
            
            // If current element is larger than the smaller child, swap
            if (innerCompare(heap[index], heap[childIndex]) > 0) {
                E temp = heap[index];
                heap[index] = heap[childIndex];
                heap[childIndex] = temp;
                index = childIndex;
                childIndex = 2 * index + 1;
            } else {
                break;
            }
        }
    }
    
    /**
     * Unified comparison method that uses either the Comparator or natural ordering.
     * This isolates the decision of which comparison mechanism to use.
     * 
     * @SuppressWarnings is needed because we're casting to Comparable when comparator is null
     */
    @SuppressWarnings("unchecked")
    private int innerCompare(E e1, E e2) {
        if (comparator != null) {
            return comparator.compare(e1, e2);
        } else {
            return ((Comparable<? super E>) e1).compareTo(e2);
        }
    }
    
    /**
     * Ensures there is enough capacity in the backing array.
     */
    @SuppressWarnings("unchecked")
    private void ensureCapacity() {
        if (size == heap.length) {
            E[] newHeap = (E[]) new Object[heap.length * 2 + 1];
            for (int i = 0; i < size; i++) {
                newHeap[i] = heap[i];
            }
            heap = newHeap;
        }
    }
    
    // Interface methods
    
    @Override
    public void add(E element) {
        ensureCapacity();
        heap[size] = element;
        size++;
        percolateUp(size - 1);
    }
    
    @Override
    public E peek() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority queue is empty");
        }
        return heap[0];
    }
    
    @Override
    public E extract() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority queue is empty");
        }
        
        E minItem = heap[0];
        heap[0] = heap[size - 1];
        heap[size - 1] = null; // Help garbage collection
        size--;
        
        if (size > 0) {
            percolateDown(0);
        }
        
        return minItem;
    }
    
    @Override
    public int size() {
        return size;
    }
    
    @Override
    public boolean isEmpty() {
        return size == 0;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public void clear() {
        heap = (E[]) new Object[DEFAULT_CAPACITY];  
        size = 0;
    }
    
    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        for (int i = 0; i < size; i++) {
            result[i] = heap[i];
        }
        return result;
    }
}