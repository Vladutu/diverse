package ro.ucv.ace;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Geo on 05.11.2016.
 */
public class LinkedPriorityQueue<T extends Comparable<T>> {

    List<T> list = new LinkedList<T>();

    public void add(T newItem) {
        int index = 0;

        for (T item : list) {
            if (item.compareTo(newItem) > 0) {
                break;
            }

            index++;
        }

        list.add(index, newItem);
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public T poll() {
        return list.remove(0);
    }

    public boolean remove(T item) {
        return list.remove(item);
    }

    public T get(int index) {
        if (index >= list.size()) {
            return null;
        }

        return list.get(index);
    }

}
