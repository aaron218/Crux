package net.newstring.crux.core.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * standard heap interface
 */
public interface Heap<E extends Comparable<E>> {

    /**
     * Set heap type (true for max,false for min)
     * <p>If heap type changed (eg. max to min). the implementation must rebuild heap to fit new type
     *
     * @param isMaxHeap is max heap.
     */
    void setHeapType(boolean isMaxHeap);

    //void heapSort();

    /**
     * add new element to heap,must keep heapify
     * <ul>
     * <li>return value is index of element in heap underlying structure;
     * <li>but in some implementation return 0 also used to simplify the results and process
     * <li>return -1 or throw exception means error
     * </ul>
     *
     * @param e element
     * @return index of element in heap underlying structure(or return 0 || -1)
     */
    int add(E e);

    /**
     * Adds a batch of data to the heap. after that, heap must be adjusted.
     *
     * @param collection collection to add
     */
    void addAll(Collection<E> collection);

    /**
     * Removes an element from the heap and guarantees the legality of the heap
     * <ul>
     * <li>return value is index of element in heap underlying structure;
     * <li>but in some implementation return 0 also used to simplify the results and process
     * <li>return -1 or throw exception means error
     * </ul>
     *
     * @param e Element to remove
     * @return index of element before remove (or 0 || -1)
     */
    int remove(E e);

    /**
     * @return
     */
    Iterator<E> getIterator();

    /**
     * Return top(k) as an list. The results must be ordered
     * <p> The order of results depends on the heap type
     *
     * @param k top value
     * @return list
     */
    List<E> head(int k);
}
