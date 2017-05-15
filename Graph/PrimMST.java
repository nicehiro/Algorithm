package Graph;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by hiro on 17-5-13.
 */
public class PrimMST {

    private static final double FLOATING_POINT_EPILON = 1E-12;

    private Edge[] edgeTo;
    private double[] distTo;
    private boolean[] marked;
    private IndexMinPQ<Double> pq;

    /*
    * Prim 算法的即时实现
    * edgeTo[w] 距 w 最近的顶点
    * distTo[w] = edgeTo[w].weight
     */
    public PrimMST(EdgeWeightedGraph g) {
        edgeTo = new Edge[g.V()];
        distTo = new double[g.V()];
        marked = new boolean[g.V()];
        pq = new IndexMinPQ<>(g.V());

        for (int v=0; v<g.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;

        for (int v=0; v<g.V(); v++)
            if (!marked[v])
                prim(g, v);
    }

    /*
    * 如果两个顶点都被 mark，则跳过
    * 如果 distTo[w] < e.weight，对 distTo 和 edgeTo 的值替换
     */
    private void prim(EdgeWeightedGraph g, int s) {
        distTo[s] = 0.0;
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            scan(g, v);
        }
    }

    private void scan(EdgeWeightedGraph g, int v) {
        marked[v] = true;
        for (Edge e : g.adj(v)) {
            int w = e.other(v);
            if (marked[w]) continue;
            if (e.weight() < distTo[w]) {
                edgeTo[w] = e;
                distTo[w] = e.weight();
                if (pq.contains(w))
                    pq.decreaseKey(w, distTo[w]);
                else
                    pq.insert(w, distTo[w]);
            }
        }
    }

    public Iterable<Edge> edges() {
        Queue<Edge> mst = new Queue<Edge>();
        for (int v=0; v<marked.length; v++) {
            Edge e = edgeTo[v];
            if (e != null)
                mst.enqueue(e);
        }
        return mst;
    }

    public double weight() {
        double weight = 0.0;
        for (Edge e : edges())
            weight += e.weight();
        return weight;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        PrimMST mst = new PrimMST(G);
        for (Edge e : mst.edges()) {
            StdOut.println(e);
        }
        StdOut.printf("%.5f\n", mst.weight());
    }
}
