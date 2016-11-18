package Search;

/**
 * Created by hiro on 16-11-18.
 * 有序数组的二分查找
 */
public class BinarySearchST <Key extends Comparable<Key>, Value> {
    private Key[] keys;
    private Value[] values;
    private int N;

    //初始化时候最好可以再加一个自动增加 capacity 的方法
    public BinarySearchST(int capacity) {
        keys = (Key[]) new Comparable[capacity];
        values = (Value[]) new Object[capacity];
    }

    //数组的大小
    public int size() {
        return N;
    }

    //得到某一键的值
    public Value get(Key key) {
        if (isEmpty()) {
            return null;
        }

        int i = rank(key);
        if (i < N && keys[i].compareTo(key) == 0) {
            return values[i];
        } else {
            return null;
        }
    }

    //查找某一键的数组序列
    private int rank(Key key) {
        int low = 0;
        int high = N - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            int cmp = key.compareTo(keys[mid]);

            if (cmp < 0) {
                high = mid - 1;
            } else if (cmp > 0) {
                low = mid + 1;
            } else {
                return mid;
            }
        }

        return low;
    }

    //添加键值
    public void put(Key key, Value value) {
        int i = rank(key);

        //更新键值
        if (i < N && keys[i].compareTo(key) == 0) {
            values[i] = value;
            return;
        }

        //从插入位置向后顺移
        for (int j=N; j>i; j--) {
            keys[j] = keys[j-1];
            values[j] = values[j-1];
        }

        keys[i] = key;
        values[i] = value;
        N ++;
    }

    //删除指定键的值
    public void delete(Key key) {
        int i = rank(key);
        if (i < N && keys[i].compareTo(key) == 0) {
            for (int j=i; j<=N; j++) {
                keys[j] = keys[j+1];
                values[j] = values[j+1];
            }
        }

        N --;
    }

    public Key min() {
        return keys[0];
    }

    public Key max() {
        return keys[N];
    }

    public Key select(int k) {
        return keys[k];
    }

    public Key ceiling(Key key) {
        int i = rank(key);
        return keys[i];
    }

    public Key floor(Key key) {
        int i = rank(key);
        return keys[i];
    }

    public Iterable<Key> keys(Key low, Key high) {
        edu.princeton.cs.algs4.Queue<Key> q = new edu.princeton.cs.algs4.Queue<Key>();
        for (int i=rank(low); i<rank(high); i++) {
            q.enqueue(keys[i]);
        }

        if (contains(high)) {
            q.enqueue(keys[rank(high)]);
        }

        return q;
    }

    public boolean contains(Key key) {
        if (rank(key) > N) {
            return false;
        }

        return key.compareTo(keys[rank(key)]) == 0;
    }

    private boolean isEmpty() {
        return N == 0;
    }
}
