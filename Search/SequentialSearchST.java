package Search;

import edu.princeton.cs.algs4.Queue;

/**
 * Created by hiro on 16-11-18.
 * 基于无序列表的顺序查找
 */
public class SequentialSearchST <Key, Value> {
    private Node first;

    private int number = 0;

    private class Node {
        Key key;
        Value value;
        Node next;

        Node(Key key, Value value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;

            number ++;
        }
    }

    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<>();
        for (Node node = first; node != null; node = node.next) {
            queue.enqueue(node.key);
        }
        return queue;
    }

    public void put(Key key, Value value) {
        for (Node x = first; x != null; x = x.next) {
            if (key.equals(x.key)) {
                x.value = value;
                return;
            }
        }

        first = new Node(key, value, first);
    }

    public Value get(Key key) {
        for (Node x = first; x != null; x = x.next) {
            if (key.equals(x.key)) {
                return x.value;
            }
        }

        return null;
    }

    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("Key is null");
        return get(key) != null;
    }

    private int size() {
        return number;
    }

    public Value delete(Key key) {
        for (Node x = first; x.next != null; x = x.next) {
            if (key.equals(x.next.key)) {
                x.next = x.next.next;
                return x.next.value;
            }
        }

        return null;
    }

    public static void main(String[] args) {
        SequentialSearchST<String, Integer> st = new SequentialSearchST<>();

        for (int i = 0; i<args.length; i++) {
            String key = args[i];
            st.put(key, i);
        }

        for (String s : st.keys()) {
            System.out.println(s + " " + st.get(s));
        }
    }
}
