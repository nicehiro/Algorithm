package String;

import edu.princeton.cs.algs4.Queue;

/**
 * Created by hiro on 17-5-21.
 */
public class TrieST<Value> {

    private static int R = 256;

    private Node root;
    private int n;

    /*
    * 结点的数据结构
    * value：如果这个结点是一个单词的尾结点，他的值不为 null
    * next：维护一个字母表指向单词的下一个字母
     */
    private static class Node {
        private Object value;
        private Node[] next = new Node[R];
    }

    public Value get(String key) {
        Node x = get(root, key, 0);
        if (x == null) return null;
        return (Value) x.value;
    }

    public boolean contains(String key) {
        return get(key) != null;
    }

    /*
    * 获取结点字符串 key 的值
    * 如果已经查询到了 key 的结尾，将对应的结点返回
    * 否则，查询对应的下一个结点
     */
    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = key.charAt(d);
        return get(x.next[c], key, d+1);
    }

    public void put(String key, Value value) {
        if (value == null) delete(key);
        else root = put(root, key, value, 0);
    }

    /*
    * 如果 key 存在于树中，更新此结点的 value，且如果此树原来没有这个单词，增加 n，
    * 找到第 d 个字符所对应的树
     */
    private Node put(Node x, String key, Value value, int d) {
        if (x == null) x = new Node();
        if (d == key.length()) {
            if (x.value == null)
                n ++;
            x.value = value;
            return x;
        }
        char c = key.charAt(d);
        x.next[c] = put(x.next[c], key, value, d+1);
        return x;
    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public Iterable<String> keys() {
        return keysWithPrefix("");
    }

    /*
    * 查找所有前缀是 prefix 的单词
     */
    public Iterable<String> keysWithPrefix(String prefix) {
        Queue<String> results = new Queue<>();
        Node x = get(root, prefix, 0);
        collect(x, new StringBuilder(prefix), results);
        return results;
    }

    /*
    * 通配符匹配
     */
    public Iterable<String> keysThatMatch(String pattern) {
        Queue<String> results = new Queue<>();
        collect(root, new StringBuilder(pattern), pattern, results);
        return results;
    }

    /*
    * 如果此结点的 value 不为 null，把保存的 prefix 单词加入队列
    * 如果此结点不是单词的尾结点，将这个单词附在 prefix 后面继续遍历
    * 这里使用递归很有趣的一点是，prefix 不需要保存，就可以多次重复的加入到队列中
     */
    private void collect(Node x, StringBuilder prefix, Queue<String> results) {
        if (x == null) return;
        if (x.value != null)
            results.enqueue(prefix.toString());
        for (char c = 0; c < R; c ++) {
            prefix.append(c);
            collect(x.next[c], prefix, results);
            prefix.deleteCharAt(prefix.length()-1);
        }
    }

    /*
    * 这里较上一个算法多了一个 pattern 参数
    * 通配符这里只有 '.'
    * 如果查找到最后一个字符，且那个字符是一个单词的尾结点，表明匹配到了，返回那个单词
    * 否则，需要继续查找
    * 如果遇到通配符，那么需要递归调用处理所有链接
    * 否则，则只调用 pattern 中指定的特殊字符
     */
    private void collect(Node x, StringBuilder prefix, String pattern, Queue<String> results) {
        if (x == null) return;
        int d = prefix.length();
        if (d == pattern.length() && x.value != null)
            results.enqueue(prefix.toString());
        if (d == pattern.length())
            return;
        char c = pattern.charAt(d);
        if (c == '.') {
            for (char ch = 0; ch < R; ch ++) {
                prefix.append(ch);
                collect(x.next[ch], prefix, pattern, results);
                prefix.deleteCharAt(prefix.length()-1);
            }
        } else {
            prefix.append(c);
            collect(x.next[c], prefix, pattern, results);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }

    /*
    * 最长前缀
    * 查找给定字符串的最长键前缀
     */
    public String longestPrefixOf(String query) {
        int length = longestPrefixOf(root, query, 0, -1);
        if (length == -1) return null;
        else return query.substring(0, length);
    }

    /*
    * 使用 length 来记录最长前缀的单词的长度
    * 只有当遇到一个尾结点时，才会更新 length 的值
     */
    private int longestPrefixOf(Node x, String query, int d, int length) {
        if (x == null) return length;
        if (x.value != null) length = d;
        if (d == query.length()) return length;
        char c = query.charAt(d);
        return longestPrefixOf(x.next[c], query, d+1, length);
    }

    public void delete(String key) {
        root = delete(root, key, 0);
    }

    /*
    * 删除操作
    * 1. 找到所要删除的单词的尾结点，将其 value 置为 null
    * 2. 判定是否删除这个结点
    *       1. 该结点是否有 value
    *       2. 该节点是否有子结点
     */
    private Node delete(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) {
            if (x.value != null)
                n --;
            x.value = null;
        } else {
            char c = key.charAt(d);
            x.next[c] = delete(x.next[c], key, d+1);
        }

        if (x.value != null) return x;
        for (int c = 0; c < R; c ++) {
            if (x.next[c] != null)
                return x;
        }
        return null;
    }
}
