import java.util.Iterator;
import java.util.List;

public interface IPriorityQueue<T> extends Iterable<T> {
    void add(T item);

    void addAll(List<T> items);

    T getMinimum();

    void removeMinimum();

    int size();

    Iterator<T> revIterator();
}
