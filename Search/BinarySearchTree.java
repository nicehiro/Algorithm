package Search;

import edu.princeton.cs.algs4.Queue;

/**
 * Created by hiro on 17-5-4.
 */
public class BinarySearchTree<Key extends Comparable<Key>, Value> {

    private Node root;

    private class Node {
        private Key key;
        private Value value;
        private Node left;
        private Node right;
        private int size;

        Node(Key key, Value value, int size) {
            this.key = key;
            this.value = value;
            this.size = size;
        }
    }

    public int size() {
        return size(root);
    }

    public int size(Node node) {
        return node.size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public Value get(Key key) {
        return get(root, key);
    }

    /*
    * 如果 key 和 node 的 key 相等，返回该 node
    * 如果 key 小于 node 的 key，递归左子树
    * 如果 key 大于 node 的 key，递归右子树
     */
    private Value get(Node node, Key key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp > 0) return get(node.right, key);
        else if (cmp < 0) return get(node.left, key);
        else return node.value;
    }

    public void put(Key key, Value value) {
        if (value == null) {
            delete(key);
            return;
        }
        root = put(root, key, value);
    }

    /*
    * 如果 key 等于 node 的 key，更新 value
    * 如果 key 小于 node 的 key，递归 node 的左子树，且将 node 的左子树更新
    * 如果 key 大于 node 的 key，递归 node 的右子树，且将 node 的右子树更新
    * 如果 node 为 null，新建结点返回
     */
    private Node put(Node node, Key key, Value value) {
        if (node == null) return new Node(key, value, 1);
        int cmp = key.compareTo(node.key);
        if (cmp > 0) node.right = put(node.right, key, value);
        else if (cmp < 0) node.left = put(node.left, key, value);
        else node.value = value;
        node.size = node.left.size + node.right.size + 1;
        return node;
    }

    public void deleteMin() {
        if (!isEmpty()) root = deleteMin(root);
    }

    /*
    * 如果 node 的左子树为 null，返回右子树
    * 如果 node 的左子树不为 null，递归更新左子树
     */
    private Node deleteMin(Node node) {
        if (node.left == null) return node.right;
        node.left = deleteMin(node.left);
        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    public void deleteMax() {
        if (!isEmpty()) root = deleteMax(root);
    }

    /*
    * 如果 node 的右子树为 null，返回左子树
    * 如果 node 的右子树不为 null，递归更新右子树
     */
    private Node deleteMax(Node node) {
        if (node.right == null) return node.left;
        node.right = deleteMax(node.right);
        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    public void delete(Key key) {
        delete(root, key);
    }

    /*
    * 先查找 key 的那个结点，如果不存在返回 null
    * 找到之后，有两种办法。取右子树的最小节点作为根节点，删除原来的根节点
    * 取左子树的最大节点作为根节点，删除原来的根节点
     */
    private Node delete(Node node, Key key) {
        if (node == null) return null;

        int cmp = key.compareTo(node.key);
        if (cmp > 0) return delete(node.right, key);
        else if (cmp < 0) return delete(node.left, key);
        else {
            if (node.right == null) return node.left;
            else if (node.left == null) return node.right;

            Node temp = node;
            node = min(temp.right);
            node.right = deleteMin(temp.right);
            node.left = temp.left;
        }

        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    public Key min() {
        return min(root).key;
    }

    private Node min(Node node) {
        if (node.left == null) return node;
        else return min(node.left);
    }

    public Key max() {
        return max(root).key;
    }

    private Node max(Node node) {
        if (node.right == null) return node;
        else return max(node.right);
    }

    public Node floor(Key key) {
        return floor(root, key);
    }

    /*
    * 返回小于等于 key 的最大结点
    * 如果 node 的 key 等于 key，返回 node
    * 如果 node 的 key 大于 key，答案肯定在左子树，递归 node 的左子树
    * 如果 node 的 key 小于 key，答案有可能在右子树，递归右子树，如果右子树中有小于 key 的，返回它；如果右子树中没有，返回 node
     */
    private Node floor(Node node, Key key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp == 0) return node;
        else if (cmp < 0) return floor(node.left, key);
        else {
            Node temp = floor(node.right, key);
            if (temp != null) return temp;
            else return node;
        }
    }

    public Node celling(Key key) {
        return celling(root, key);
    }

    /*
    * 返回大于等于 key 的最小结点
    * 如果 node 的 key 等于 key，返回 node
    * 如果 node 的 key 小于 key，答案肯定在右子树，递归 node 的右子树
    * 如果 node 的 key 大于 key，答案有可能在左子树，递归左子树，如果左子树中有大于 key 的，返回它；如果右子树中没有，返回 node
     */
    private Node celling(Node node, Key key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp == 0) return node;
        else if (cmp > 0) return celling(node.right, key);
        else {
            Node temp = celling(node.left, key);
            if (temp != null) return temp;
            else return node;
        }
    }

    public Node select(int k) {
        if (k>=0 && k<root.size) {
            return select(root, k);
        }
        return null;
    }

    /*
    * 返回第 k 个元素，从 0 开始计；即小于该元素的有 k 个元素
     */
    private Node select(Node node, int k) {
        if (node == null) return null;
        int t = size(node.left);
        if (t == k) return node;
        else if (t > k) return select(node.left, k);
        else return select(node.right, k-t-1);
    }

    public int rank(Key key) {
        return rank(root, key);
    }

    /*
    * 返回 key 的排名
     */
    private int rank(Node node, Key key) {
        if (node == null) return 0;
        int cmp = key.compareTo(node.key);
        if (cmp == 0) return size(node.left);
        else if (cmp < 0) return rank(node.left, key);
        else return rank(node.right, key) + size(node.left) + 1;
    }

    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    public Iterable<Key> keys(Key low, Key high) {
        Queue<Key> queue = new Queue<>();
        keys(root, queue, low, high);
        return queue;
    }

    private void keys(Node node, Queue<Key> queue, Key low, Key high) {
        if (node == null) return;
        int cmplow = low.compareTo(node.key);
        int cmphigh = high.compareTo(node.key);

        if (cmplow < 0) keys(node.left, queue, low, high);
        if (cmplow <= 0 && cmphigh >= 0) queue.enqueue(node.key);
        if (cmphigh > 0) keys(node.right, queue, low, high);
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    public int size(Key low, Key high) {
        if (low.compareTo(high) > 0) return 0;
        else if (contains(high)) return rank(high)-rank(low)+1;
        else return rank(high)-rank(low);
    }

    public int height() {
        return height(root);
    }

    private int height(Node node) {
        if (node == null) return -1;
        return 1 + height(node.left) + height(node.right);
    }
}
