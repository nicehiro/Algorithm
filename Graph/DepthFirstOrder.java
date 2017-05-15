package Graph;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by hiro on 17-5-12.
 */
public class DepthFirstOrder {

    private boolean marked[];
    private int[] pre;
    private int[] post;
    private Queue<Integer> preorder;
    private Queue<Integer> postorder;
    private int preCounter;
    private int postCounter;

    public DepthFirstOrder(Digraph g) {
        pre = new int[g.V()];
        post = new int[g.V()];
        postorder = new Queue<>();
        preorder = new Queue<>();
        marked = new boolean[g.V()];
        for (int v=0; v<g.V(); v++)
            if (!marked[v])
                dfs(g, v);
    }

    /*
    * 新增 pre 用来记录前序遍历得到的数组
    * 新增 post 用来记录后序遍历得到的数组
    * 前序遍历：每记录（marked）一个结点，便向 pre 中添加这个记录
    * 后序遍历：每遍历完成一个结点，向 post 中添加这个记录
    * 逆后序遍历：后序遍历的逆
     */
    private void dfs(Digraph g, int v) {
        marked[v] = true;
        pre[v] = preCounter ++;
        preorder.enqueue(v);
        for (int w : g.adj(v))
            if (!marked[w])
                dfs(g, w);
        postorder.enqueue(v);
        post[v] = postCounter ++;
    }

    private int pre(int v) {
        validDataVertex(v);
        return pre[v];
    }

    public int post(int v) {
        validDataVertex(v);
        return post[v];
    }

    public Iterable<Integer> pre() {
        return preorder;
    }

    /*
    * 直接使用 stack 替换原来使用的 queue 即可实现逆
     */
    public Iterable<Integer> reversePost() {
        Stack<Integer> reverse = new Stack<>();
        for (int v : postorder)
            reverse.push(v);
        return reverse;
    }

    public Iterable<Integer> post() {
        return postorder;
    }

    private void validDataVertex(int v) {
        if (v<0 || v>=marked.length)
            throw new IllegalArgumentException("numebr must between 0 and V");
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);

        DepthFirstOrder dfs = new DepthFirstOrder(G);
        StdOut.println("   v  pre post");
        StdOut.println("--------------");
        for (int v = 0; v < G.V(); v++) {
            StdOut.printf("%4d %4d %4d\n", v, dfs.pre(v), dfs.post(v));
        }

        StdOut.print("Preorder:  ");
        for (int v : dfs.pre()) {
            StdOut.print(v + " ");
        }
        StdOut.println();

        StdOut.print("Postorder: ");
        for (int v : dfs.post()) {
            StdOut.print(v + " ");
        }
        StdOut.println();

        StdOut.print("Reverse postorder: ");
        for (int v : dfs.reversePost()) {
            StdOut.print(v + " ");
        }
        StdOut.println();
    }
}
