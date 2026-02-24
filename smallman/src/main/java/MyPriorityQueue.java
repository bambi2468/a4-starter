import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class MyPriorityQueue<T> implements IPriorityQueue<T> {
    
    private List<T> queueItems;
    private CompareWith<T> cc;

    public MyPriorityQueue(CompareWith<T> cc) {
        this.cc = cc;
        this.queueItems = new ArrayList<>();
    }

    @Override
    public void add(T item) {
        // Find the correct position to keep the list sorted from smallest to largest
        int i = 0;
        while (i < queueItems.size()) {
            T current = queueItems.get(i);
            // If the new item is less than the current item, we insert it here
            if (cc.lessThan(item, current)) {
                break;
            }
            i++;
        }
        queueItems.add(i, item);
    }

    @Override
    public void addAll(List<T> items) {
        for (T item : items) {
            this.add(item);
        }
    }

    @Override
    public T getMinimum() {
        if (queueItems.isEmpty()) {
            return null; // Or throw a runtime exception depending on your interface's strictness
        }
        return queueItems.get(0);
    }

    @Override
    public void removeMinimum() {
        if (!queueItems.isEmpty()) {
            queueItems.remove(0);
        }
    }

    @Override
    public int size() {
        return queueItems.size();
    }

    @Override
    public Iterator<T> iterator() {
        // The standard ArrayList iterator goes from index 0 to size-1 (Small to Large)
        return queueItems.iterator();
    }

    @Override
    public Iterator<T> revIterator() {
        // A custom iterator to traverse from size-1 down to 0 (Large to Small)
        return new Iterator<T>() {
            private int currentIndex = queueItems.size() - 1;

            @Override
            public boolean hasNext() {
                return currentIndex >= 0;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return queueItems.get(currentIndex--);
            }
        };
    }
}