package Utils;

/**
 * Basic implementation of Tuple
 *
 * @author V.U.Kurhei
 * @version 1.0
 */
public class Tuple<K, V> {
    private K first;
    private V second;

    /**
     * Constructor for creating an object
     */
    public Tuple(K first, V second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Returns first element of Tuple
     */
    public K getFirst() {
        return first;
    }

    /**
     * Returns second element of Tuple
     */
    public V getSecond() {
        return second;
    }
}
