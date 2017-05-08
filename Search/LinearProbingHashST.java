package Search;

import edu.princeton.cs.algs4.Queue;

/**
 * Created by hiro on 17-5-6.
 * Hash table
 */
public class LinearProbingHashST<Key, Value> {

    private static final int INIT_CAPACITY = 4;

    private int n;                          // 键值对数
    private int m;                          // 散列表的大小
    private Key[] keys;
    private Value[] values;

    public LinearProbingHashST() {
        this(INIT_CAPACITY);
    }

    public LinearProbingHashST(int capacity) {
        m = capacity;
        n = 0;
        keys = (Key[]) new Object[m];
        values = (Value[]) new Object[m];
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

    // Hash 函数，使用 hashcode 实现
    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % m;
    }

    // 扩容
    private void resize(int capacity) {
        LinearProbingHashST<Key, Value> temp = new LinearProbingHashST(capacity);
        for (int i=0; i<m; i++) {
            if (keys[i] != null)
                temp.put(keys[i], values[i]);
        }
        keys = temp.keys;
        values = temp.values;
        m = temp.m;
    }

    /*
    * value 为 null 时，做删除操作
    * 如果此时位置不为 null，继续查看下一个位置
     */
    public void put(Key key, Value value) {
        if (key == null) throw new IllegalArgumentException("Key is null");

        if (value == null) {
            delete(key);
            return;
        }

        if (n > m/2) resize(2*m);
        int i = 0;
        for (i=hash(key); keys[i] != null; i=(i+1)%m) {
            if (keys[i] == key) {
                values[i] = value;
                return;
            }
        }

        keys[i] = key;
        values[i] = value;
        n ++;
    }

    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("Key is null");
        for (int i=hash(key); keys[i] != null; i=(i+1)%m) {
            if (keys[i] == key) {
                return values[i];
            }
        }
        return null;
    }

    /*
    * 删除该元素后不能简单的将原来的位置置为 null
    * 将与该元素相连的之后的元素都重新进行 Hash 排列
     */
    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("Key is null");
        if (!contains(key)) return;

        int i = hash(key);
        while (!key.equals(keys[i])) {
            i = (i+1) % m;
        }

        keys[i] = null;
        values[i] = null;

        i = (i+1) % m;
        while (keys[i] != null) {
            Key tempKey = keys[i];
            Value tempValue = values[i];
            keys[i] = null;
            values[i] = null;
            n --;
            put(tempKey, tempValue);
            i = (i + 1) % m;
        }

        n --;
        if (n > 0 && n <= m/8) resize(m/2);
    }

    private boolean check() {
        if (m < 2*n) {
            System.err.println("size problem");
        }

        for (int i=0; i<m; i++) {
            if (keys[i] == null) continue;
            else if (get(keys[i]) != values[i]) {
                return false;
            }
        }
        return true;
    }

    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<>();
        for (int i=0; i<m; i++) {
            if (keys[i] != null)
                queue.enqueue(keys[i]);
        }
        return queue;
    }

    public static void main(String[] args) {
        LinearProbingHashST<String, Integer> st = new LinearProbingHashST<>();
        for (int i=0; i<args.length; i++) {
            String key = args[i];
            st.put(key, i);
        }

        for (String s : st.keys()) {
            System.out.println(s + " " + st.get(s));
        }
    }
}
