package histogram;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

// TODO: Uncomment this and make sure to implement all the methods
public class SimpleHistogram<DT> implements Histogram<DT>, Iterable<DT> {
    private Map<DT, Integer> countMap = new HashMap<>();

    // constructs an empty histogram (with no information)
    public SimpleHistogram() {
    }

    // constructs a histogram from a list of items given by the parameter items
    public SimpleHistogram(DT items[]) {
        for (DT item : items) {
            int count = getCount(item);
            setCount(item, count + 1);
        }
    }

    // constructs a (new) histogram from an existing histogram, sharing nothing
    // internally
    public SimpleHistogram(Histogram<DT> hist) {
        for (DT item : hist) {
            int count = hist.getCount(item);
            setCount(item, count);
        }
    }

    @Override
    public Iterator<DT> iterator() {
        Set<DT> keySet = countMap.keySet();
        return keySet.iterator();
    }

    @Override
    public int getTotalCount() {
        int totalCount = 0;
        for (int count: countMap.values()){
            totalCount += count;
        }
        return totalCount;
    }

    @Override
    public int getCount(DT item) {
        // countMap.containsKey(item) bool
        // countMap.get(item)
        return countMap.getOrDefault(item, 0);
    }

    @Override
    public void setCount(DT item, int count) {
        countMap.put(item, count);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SimpleHistogram)){
            return false;
        }
        SimpleHistogram<DT> other = (SimpleHistogram<DT>) obj;
        return countMap.equals(other.countMap);
    }

    @Override
    public String toString() {
        return "SimpleHistogram: " + countMap.toString();
    }
}
