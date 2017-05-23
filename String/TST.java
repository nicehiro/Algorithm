package String;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by hiro on 17-5-21.
 */
public class TST<Value> {

    private int n;

    /*
    * 三向单词查找树的结点的数据结构
    * 左、中、右子结点
    * 结点字符 c
    * 结点值 value
     */
    private Node<Value> root;

    private static class Node<Value> {
        private char c;
        private Node<Value> left, mid, right;
        private Value value;
    }

    public TST() {}

    public int size() {
        return n;
    }

    public boolean contains(String key) {
        return get(key) != null;
    }

    public Value get(String key) {
        if (key == null)
            throw new IllegalArgumentException("Key is null");
        if (key.length() == 0)
            throw new IllegalArgumentException("key must have length >= 1");
        Node<Value> x = get(root, key, 0);
        if (x == null) return null;
        return x.value;
    }

    /*
    * 如果小于当前字符，递归左子结点
    * 如果大于当前字符，递归右子结点
    * 如果等于当前字符，且该字符长度小于查找字符串的长度，递归中间子结点
     */
    private Node<Value> get(Node<Value> x, String key, int d) {
        if (x == null) return null;
        char c = key.charAt(d);
        if (c < x.c) return get(x.left, key, d);
        else if (c > x.c) return get(x.right, key, d);
        else if (d < key.length() - 1) return get(x.mid, key, d);
        else return x;
    }

    public void put(String key, Value value) {
        if (key == null)
            throw new IllegalArgumentException("key is null");
        if (!contains(key)) n ++;
        root = put(root, key, value, 0);
    }

    /*
    * 如果当前字符小于结点字符，递归左子结点
    * 如果当前字符大于结点字符，递归右子结点
    * 如果当前字符等于结点字符，且长度小于被插入字符的长度，递归中间的结点
    * 如果当前结点不存在，新建一个结点
     */
    private Node<Value> put(Node<Value> x, String key, Value value, int d) {
        char c = key.charAt(d);
        if (x == null) {
            x = new Node<Value>();
            x.c = c;
        }
        if (c < x.c) x.left = put(x.left, key, value, d);
        else if (c > x.c) x.right = put(x.right, key, value, d);
        else if (d < key.length()-1) x.mid = put(x.mid, key, value, d+1);
        else x.value = value;
        return x;
    }

    /*
    * 字符串的最长单词匹配
    * 当且仅当匹配到一个 value 不为空的结点时，才更新 length 的值
     */
    public String longestPrefixOf(String queue) {
        if (queue == null)
            throw new IllegalArgumentException("queue is null");
        if (queue.length() == 0) return null;
        int length = 0;
        Node<Value> x = root;
        int i = 0;
        while (x != null && i < queue.length()) {
            char c = queue.charAt(i);
            if (c < x.c) x = x.left;
            else if (c > x.c) x = x.right;
            else {
                i ++;
                if (x.value != null) length = i;
                x = x.mid;
            }
        }
        return queue.substring(0, length);
    }

    public Iterable<String> keys() {
        Queue<String> queue = new Queue<>();
        collect(root, new StringBuilder(), queue);
        return queue;
    }

    /*
    * 以 prefix 为前缀的单词
    * 注意 collect 中的参数是 x.mid
     */
    public Iterable<String> keysWithPrefix(String prefix) {
        if (prefix == null)
            throw new IllegalArgumentException("prefix is null");
        Queue<String> queue = new Queue<>();
        Node<Value> x = get(root, prefix, 0);
        if (x == null) return queue;
        if (x.value != null)
            queue.enqueue(prefix);
        collect(x.mid, new StringBuilder(prefix), queue);
        return queue;
    }

    /*
    * 需要遍历当前结点的左、中、右三个子结点
    * 首先遍历左结点，因为遍历之前没有将 x.mid 加入 prefix，所以不需要删去
    * 接着遍历中间结点，将 prefix 加上 x.mid 接着遍历
    * 再遍历右结点，实现需要将 prefix 的最后的 x.mid 删掉
    *
    * 加入 queue 的操作只有在遍历完左结点时且当前结点的 value 不为 null 才执行
     */
    private void collect(Node<Value> x, StringBuilder prefix, Queue<String> queue) {
        if (x == null) return;
        collect(x.left, prefix, queue);
        if (x.value != null) queue.enqueue(prefix.toString() + x.c);
        collect(x.mid, prefix.append(x.c), queue);
        prefix.deleteCharAt(prefix.length() - 1);
        collect(x.right, prefix, queue);
    }

    public Iterable<String> keysThatMatch(String pattern) {
        Queue<String> queue = new Queue<>();
        collect(root, new StringBuilder(), 0, pattern, queue);
        return queue;
    }

    /*
    * 通配符是 '.'
     */
    private void collect(Node<Value> x, StringBuilder prefix, int i, String pattern, Queue<String> queue) {
        if (x == null) return;
        char c = pattern.charAt(i);
        if (c == '.' || c < x.c) collect(x.left, prefix, i, pattern, queue);
        if (c == '.' || c == x.c) {
            if (i == pattern.length()-1 && x.value != null)
                queue.enqueue(prefix.toString() + x.c);
            if (i < pattern.length()-1) {
                collect(x.mid, prefix.append(x.c), i+1, pattern, queue);
                prefix.deleteCharAt(prefix.length()-1);
            }
        }
        if (c == '.' || c > x.c)
            collect(x.right, prefix, i, pattern, queue);
    }

    public static void main(String[] args) {

        // build symbol table from standard input
        TST<Integer> st = new TST<Integer>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }

        // print results
        if (st.size() < 100) {
            StdOut.println("keys(\"\"):");
            for (String key : st.keys()) {
                StdOut.println(key + " " + st.get(key));
            }
            StdOut.println();
        }

        StdOut.println("longestPrefixOf(\"shellsort\"):");
        StdOut.println(st.longestPrefixOf("shellsort"));
        StdOut.println();

        StdOut.println("longestPrefixOf(\"shell\"):");
        StdOut.println(st.longestPrefixOf("shell"));
        StdOut.println();

        StdOut.println("keysWithPrefix(\"shor\"):");
        for (String s : st.keysWithPrefix("shor"))
            StdOut.println(s);
        StdOut.println();

        StdOut.println("keysThatMatch(\".he.l.\"):");
        for (String s : st.keysThatMatch(".he.l."))
            StdOut.println(s);
    }
}
