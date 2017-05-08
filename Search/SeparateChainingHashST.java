package Search;

import edu.princeton.cs.algs4.Queue;

/**
 * Created by hiro on 17-5-8.
 */
public class SeparateChainingHashST<Key, Value> {

    private static final int INIT_CAPACITY = 4;

    private int m;
    private int n;
    private SequentialSearchST<Key, Value>[] st;

    public SeparateChainingHashST() {
        this(INIT_CAPACITY);
    }

    public SeparateChainingHashST(int m) {
        this.m = m;
        st = new SequentialSearchST[m];
        for (int i=0; i<m; i++) {
            st[i] = new SequentialSearchST<>();
        }
    }

    private void resize(int chains) {
        SeparateChainingHashST<Key, Value> temp = new SeparateChainingHashST<>(chains);
        for (int i=0; i<m; i++) {
            for (Key key : st[i].keys()) {
                temp.put(key, get(key));
            }
        }

        this.m = temp.m;
        this.n = temp.n;
        this.st = temp.st;
    }

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % m;
    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("Key is null");
        return get(key) != null;
    }

    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("Key is null");
        int i = hash(key);
        return st[i].get(key);
    }

    public void put(Key key, Value value) {
        if (key == null) throw new IllegalArgumentException("Key is null");
        if (value == null) {
            delete(key);
            return;
        }

        if (n >= 10*m) resize(2*m);
        int i = hash(key);
        if (!contains(key)) n ++;
        st[i].put(key, value);
    }

    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");

        int i = hash(key);
        if (st[i].contains(key)) n--;
        st[i].delete(key);

        if (m > INIT_CAPACITY && n <= 2*m) resize(m/2);
    }

    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<>();
        for (int i=0; i<m; i++) {
            for (Key key : st[i].keys()) {
                queue.enqueue(key);
            }
        }
        return queue;
    }
}
