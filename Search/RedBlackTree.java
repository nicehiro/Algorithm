package Search;

/**
 * Created by hiro on 17-5-6.
 */
public class RedBlackTree<Key extends Comparable<Key>, Value> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private Node root;

    private class Node {
        private Key key;
        private Value value;
        private Node left;
        private Node right;
        private boolean color;
        private int size;

        public Node(Key key, Value value, boolean color, int size) {
            this.key = key;
            this.value = value;
            this.color = color;
            this.size = size;
        }
    }

    private boolean isRed(Node node) {
        if (node == null) return false;
        return node.color;
    }

    private int size(Node node) {
        if (node == null) return 0;
        return node.size;
    }

    public int size() {
        return size(root);
    }

    public boolean isEmpty() {
        return root == null;
    }

    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node node, Key key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp == 0) return node.value;
        else if (cmp > 0) return get(node.right, key);
        else return get(node.left, key);
    }

    public void put(Key key, Value value) {
        if (key == null) throw new IllegalArgumentException("key is null");
        if (value == null) {
            delete(key);
            return;
        }

        root = put(root, key, value);
    }

    private Node put(Node node, Key key, Value value) {
        if (node == null) return new Node(key, value, RED, 1);

        int cmp = key.compareTo(node.key);
        if (cmp == 0) node.value = value;
        else if (cmp > 0) node.right = put(node.right, key, value);
        else node.left = put(node.left, key, value);

        if (isRed(node.right) && !isRed(node.left)) node = rotateLeft(node);
        if (isRed(node.left) && isRed(node.left.left)) node = rotateRight(node);
        if (isRed(node.left) && isRed(node.right)) flipColors(node);

        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    private Node rotateLeft(Node node) {
        Node temp = node.right;
        node.right = temp.left;
        temp.left = node;
        temp.color = node.color;
        node.color = RED;
        temp.size = node.size;
        node.size = size(node.left) + size(node.right) + 1;
        return temp;
    }

    private Node rotateRight(Node node) {
        Node temp = node.left;
        node.left = temp.right;
        temp.right = node;
        temp.color = node.color;
        node.color = RED;
        temp.size = node.size;
        node.size = size(node.left) + size(node.right) + 1;
        return temp;
    }

    private void flipColors(Node node) {
        node.left.color = !node.left.color;
        node.right.color = !node.right.color;
        node.color = !node.color;
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    public void deleteMin() {
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = deleteMin(root);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node deleteMin(Node node) {
        if (node.left == null) return null;
        if (!isRed(node.left) && !isRed(node.left.left))
            node = moveRedLeft(node);
        node.left = deleteMin(node.left);
        return balance(node);
    }

    public void deleteMax() {
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = deleteMax(root);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node deleteMax(Node node) {
        if (isRed(node.left))
            node = rotateRight(node);
        if (node.right == null)
            return null;
        if (!isRed(node.right) && !isRed(node.right.left))
            node = moveRedRight(node);
        node.right = deleteMax(node.right);
        return balance(node);
    }

    private Node moveRedLeft(Node node) {
        flipColors(node);
        if (isRed(node.right.left)) {
            node.right = rotateRight(node.right);
            node = rotateLeft(node.left);
        }
        return node;
    }

    private Node moveRedRight(Node node) {
        flipColors(node);
        if (!isRed(node.left.left))
            node = rotateRight(node);
        return node;
    }

    private Node balance(Node node) {
        if (isRed(node.right)) node = rotateLeft(node);
        if (isRed(node.left) && isRed(node.left.left)) node = rotateRight(node);
        if (isRed(node.left) && isRed(node.right)) flipColors(node);

        node.size = size(node.left) + size(node.right) + 1;
        return node;
    }

    public void delete(Key key) {
        if (!isRed(root.left) && !isRed(root.right)) root.color = RED;
        root = delete(root, key);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node delete(Node node, Key key) {
        if (key.compareTo(node.key) < 0) {
            if (!isRed(node.left) && !isRed(node.left.left))
                node = moveRedLeft(node);
            node.left = delete(node.left, key);
        } else {
            if (isRed(node.left))
                node = rotateRight(node);
            if (key.compareTo(node.key) == 0 && (node.right == null))
                return null;
            if (!isRed(node.right) && !isRed(node.right.left))
                node = moveRedRight(node);
            if (key.compareTo(node.key) == 0) {
                node.value = get(node.right, min(node.right).key);
                node.key = min(node.right).key;
                node.right = deleteMin(node.right);
            }
            else
                node.right = delete(node.right, key);
        }
        return balance(node);
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
}
