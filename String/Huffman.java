package String;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.MinPQ;

/**
 * Created by hiro on 17-5-22.
 */
public class Huffman {

    private static final int R = 256;

    private Huffman() {}

    /*
    * Huffman 树的结点
    * ch：结点对应的字符信息
    * freq：字符的频率，在构建树的时候用到
    * left、right：结点的左右子结点
     */
    private static class Node implements Comparable<Node> {

        private final char ch;
        private final int freq;
        private final Node left, right;

        public Node(char ch, int freq, Node left, Node right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        private boolean isLeaf() {
            return (left == null) && (right == null);
        }

        @Override
        public int compareTo(Node o) {
            return this.freq - o.freq;
        }
    }

    /*
    * 加密操作
    * 1. 在字母表中统计输入字符的频率
    * 2. 利用最小值优先队列构造 Huffman 树
    * 3. 将键值对保存到数组中
    * 4. 加密输入的字符串
     */
    public static void compress() {
        String s = BinaryStdIn.readString();
        char[] input = s.toCharArray();

        int[] freq = new int[R];
        for (int i=0; i<input.length; i++)
            freq[input[i]] ++;

        Node root = buildTrie(freq);

        String[] st = new String[R];
        buildCode(st, root, "");

        writeTrie(root);

        BinaryStdOut.write(input.length);

        for (int i=0; i<input.length; i++) {
            String code = st[input[i]];
            for (int j=0; j<code.length(); j++) {
                if (code.charAt(j) == '0') {
                    BinaryStdOut.write(false);
                } else if (code.charAt(j) == '1') {
                    BinaryStdOut.write(true);
                } else throw new IllegalArgumentException("Illegal state");
            }
        }

        BinaryStdOut.close();
    }

    /*
    * 利用最小值优先队列构造 Huffman 树
    * 1. 将频率不为 0 的结点加入到队列中
    * 2. 队列中只有一个结点时，在加入一个结点
    * 3. 每次从队列中取出两个最小的结点合并，将合并后的新结点加入到队列中
     */
    private static Node buildTrie(int[] freq) {
        MinPQ<Node> pq = new MinPQ<Node>();
        for (char i=0; i<R; i++)
            if (freq[i] > 0)
                pq.insert(new Node(i, freq[i], null, null));

        if (pq.size() == 1) {
            if (freq['\0'] == 0) pq.insert(new Node('\0', 0, null, null));
            else pq.insert(new Node('\1', 0, null, null));
        }

        while (pq.size() > 1) {
            Node left = pq.delMin();
            Node right = pq.delMin();
            Node parent = new Node('0', left.freq+right.freq, left, right);
            pq.insert(parent);
        }
        return pq.delMin();
    }

    /*
    * 把树中的结点输出
    * 前序遍历
    * 如果是叶子结点，输出 1
    * 如果不是叶子结点，输出 0
     */
    private static void writeTrie(Node x) {
        if (x.isLeaf()) {
            BinaryStdOut.write(true);
            BinaryStdOut.write(x.ch, 8);
            return;
        }
        BinaryStdOut.write(false);
        writeTrie(x.left);
        writeTrie(x.right);
    }

    /*
    * 通过 Huffman 树构建编码
     */
    private static void buildCode(String[] st, Node x, String s) {
        if (!x.isLeaf()) {
            buildCode(st, x.left, s + '0');
            buildCode(st, x.left, s + '1');
        } else {
            st[x.ch] = s;
        }
    }

    /*
    * 解码
    * 每次读一个 bit，从 Huffman 树顶端开始查找，直到找到一个叶子结点，把该结点所带的信息打印出来
     */
    public static void expand() {
        Node root = readTrie();

        int length = BinaryStdIn.readInt();

        for (int i=0; i<length; i++) {
            Node x = root;
            while (!x.isLeaf()) {
                boolean bit = BinaryStdIn.readBoolean();
                if (bit) x = x.right;
                else x = x.left;
            }
            BinaryStdOut.write(x.ch, 8);
        }
        BinaryStdOut.close();
    }

    /*
    * 读取单词查找树
     */
    private static Node readTrie() {
        boolean isLeaf = BinaryStdIn.readBoolean();
        if (isLeaf)
            return new Node(BinaryStdIn.readChar(), -1, null, null);
        else
            return new Node('\0', -1, readTrie(), readTrie());
    }
}
