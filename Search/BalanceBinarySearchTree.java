package Search;

/**
 * Created by hiro on 17-5-5.
 */
public class BalanceBinarySearchTree<Key extends Comparable<Key>, Value> {

    private Node root;

    public static final int LH = 1;
    public static final int EH = 0;
    public static final int RH = -1;

    private class Node {
        private Node left;
        private Node right;
        private int size;
        private Key key;
        private Value value;

        /*
        * 平衡因子
        * 有三种状态：
        *   1：左子树比右子树大 1 个高度
        *   0：左右子树高度相同
        *   -1：右子树比左子树大 1 个高度
         */
        private int bf = EH; //平衡因子

        public Node(int size, Key key, Value value) {
            this.size = size;
            this.key = key;
            this.value = value;
        }
    }

    public Node get(Key key) {
        return get(root, key);
    }

    private Node get(Node node, Key key) {
        // same as BinarySearchTree.java
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp > 0) return get(node.right, key);
        else if (cmp < 0) return get(node.left, key);
        else return node;
    }

    public void put(Key key, Value value) {
        root = put(root, key, value);
    }

    /*
    * 插入元素的时候判断 taller 是否为 true，如果插入使得高度增加，taller 为 true
     */
    private static boolean taller = false;

    private Node put(Node node, Key key, Value value) {

        // 只有插入新节点时 taller 才等于 true
        if (node == null) {
            taller = true;
            return new Node(1, key, value);
        }

        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            node.value = value;
            taller = false;
        } else if (cmp > 0) {           // 右子树
            node.right = put(node.right, key, value);
            if (taller) {
                switch (node.bf) {
                    case LH:            // 原本左子树高于右子树
                        node.bf = EH;
                        taller = false;
                        break;
                    case EH:            // 原来左右子树相等
                        node.bf = RH;
                        taller = true;
                    case RH:            // 原来右子树高于左子树，此时需要平衡操作
                        rightBalance(node);
                        taller = false;
                        break;
                }
            }
        } else if (cmp < 0) {
            node.left = put(node.left, key, value);
            if (taller) {
                switch (node.bf) {
                    case LH:
                        leftBalance(node);
                        taller = false;
                        break;
                    case EH:
                        node.bf = LH;
                        taller = true;
                        break;
                    case RH:
                        node.bf = EH;
                        taller = false;
                        break;
                }
            }
        }

        node.size = node.left.size + node.right.size + 1;
        return node;
    }

    public void rigthRotate() {
        root = rightRotate(root);
    }

    /*
    * 右旋操作
     */
    private Node rightRotate(Node node) {
        Node temp = node.left;
        node.left = temp.right;
        temp.right = node;
        return temp;
    }

    public void leftRotate() {
        root = leftRotate(root);
    }

    /*
    * 左旋操作
     */
    private Node leftRotate(Node node) {
        Node temp = node.right;
        node.right = temp.left;
        temp.left = node;
        return temp;
    }

    /*
    * 左平衡
     */
    private void leftBalance(Node node) {
        Node temp = node.left;
        Node temp2;
        switch (temp.bf) {
            case LH:                        // 新节点在 node 的左子树的左子结点上 直接单右旋
                node.bf = temp.bf = EH;
                rightRotate(node);
                break;
            case RH:                        // 新节点在 node 的左子树的右子节点上 先单左旋 再单右旋
                temp2 = temp.right;
                switch (temp2.bf) {         // 改变结点的 bf
                    case LH:
                        node.bf = RH;
                        temp.bf = EH;
                        break;
                    case EH:
                        node.bf = temp.bf = EH;
                        break;
                    case RH:
                        node.bf = EH;
                        temp.bf = LH;
                        break;
                }
                temp2.bf = EH;
                leftRotate(node.left);
                rightRotate(node);
        }
    }

    private void rightBalance(Node node) {
        Node temp = node.right;
        Node temp2;
        switch (temp.bf) {
            case RH:
                node.bf = temp.bf = EH;
                leftBalance(node);
                break;
            case LH:
                temp2 = temp.left;
                switch (temp2.bf) {
                    case LH:
                        node.bf = EH;
                        temp.bf = RH;
                        break;
                    case EH:
                        node.bf = temp.bf = EH;
                        break;
                    case RH:
                        node.bf = LH;
                        temp.bf = EH;
                        break;
                }
                temp2.bf = EH;
                rightRotate(node.right);
                leftRotate(node);
        }
    }
}
