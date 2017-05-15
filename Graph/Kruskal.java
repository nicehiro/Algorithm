package Graph;

import edu.princeton.cs.algs4.*;

/**
 * Created by hiro on 17-5-13.
 */
public class Kruskal {

    private static final double FLOATING_POINT_EPSILON = 1E-12;

    private double weight;
    private Queue<Edge> mst = new Queue<Edge>();

    /*
    * 对图中所有的边进行筛选
    * 每次取出最小权重的边，如果这条边的两个顶点还没有被连接，就新建这个连接
    * 否则，查看下一条边
     */
    public Kruskal(EdgeWeightedGraph g) {
        MinPQ<Edge> pq = new MinPQ<Edge>();
        for (Edge e : g.edges())
            pq.insert(e);

        UF uf = new UF(g.V());
        while (!pq.isEmpty() && mst.size() < g.V()-1) {
            Edge e = pq.delMin();
            int v = e.either();
            int w = e.other(v);
            if (!uf.connected(v, w)) {
                uf.union(v, w);
                mst.enqueue(e);
                weight += e.weight();
            }
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
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        Kruskal mst = new Kruskal(G);
        for (Edge e : mst.edges()) {
            StdOut.println(e);
        }
        StdOut.printf("%.5f\n", mst.weight());
    }
}
