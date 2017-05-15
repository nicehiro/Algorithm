package Graph;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by hiro on 17-5-13.
 */
public class LazyPrimMST {

    private static final double FLOATING_POINT_EPSILON = 1E-12;

    private double weight;
    private Queue<Edge> mst;
    private boolean[] marked;
    private MinPQ<Edge> pq;

    /*
    * 延时 Prim 算法
    * 寻找无向加权图中的最小生成树，但会保留失效的边
    * marked 数组保存每个结点是否已经遍历
    * 维护一个最小值的优先序列，用来得到每次的最短路径
     */
    public LazyPrimMST(EdgeWeightedGraph g) {
        mst = new Queue<Edge>();
        pq = new MinPQ<Edge>();
        marked = new boolean[g.V()];
        for (int v=0; v<g.V(); v++)
            if (!marked[v]) prim(g, v);
    }

    /*
    * 每次从 pq 中取出一个最小权重的边
    * 如果 pq 的顶点没有被 mark，将这条边加入 mst 中，继续扫描其顶点
    * 如果都被 mark，则删除这个顶点
     */
    private void prim(EdgeWeightedGraph g, int s) {
        scan(g, s);

        // 改进：优先序列 pq 为空的条件可以改成 mst 中边的个数是否为 V-1
        while (!pq.isEmpty()) {
            Edge e = pq.delMin();
            int v = e.either();
            int w = e.other(v);
            if (marked[v] && marked[w]) continue;
            mst.enqueue(e);
            weight += e.weight();
            if (!marked[v]) scan(g, v);
            if (!marked[w]) scan(g, w);
        }
    }

    private void scan(EdgeWeightedGraph g, int v) {
        marked[v] = true;
        for (Edge e : g.adj(v)) {
            if (!marked[e.other(v)])
                pq.insert(e);
        }
    }

    public Iterable<Edge> edges() {
        return mst;
    }

    public double weight() {
        return weight;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedGraph g = new EdgeWeightedGraph(in);
        LazyPrimMST mst = new LazyPrimMST(g);
        for (Edge e : mst.edges()) {
            StdOut.println(e);
        }
    }
}
