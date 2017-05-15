package Graph;

import edu.princeton.cs.algs4.*;

/**
 * Created by hiro on 17-5-14.
 */
public class DijkstraSP {

    private double[] distTo;
    private DirectedEdge[] edgeTo;
    private IndexMinPQ<Double> pq;

    /*
    * 有向图的最短路径算法
    * 和 Prim 的即时实现原理一样
    * 每次从最小值的优先队列中取出最小值，relax 每条和 v 相连的边
     */
    public DijkstraSP(EdgeWeightDigraph g, int s) {
        for (DirectedEdge e : g.edges()) {
            if (e.weight() < 0)
                throw new IllegalArgumentException("e has negative weight");
        }

        distTo = new double[g.V()];
        edgeTo = new DirectedEdge[g.V()];
        pq = new IndexMinPQ<>(g.V());

        validDataVertex(s);

        for (int v=0; v<g.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[0] = 0.0;
        pq.insert(0, distTo(s));
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            for (DirectedEdge e : g.adj(v))
                relax(e);
        }
    }

    /*
    * 即时更新 distTo 和 edgeTo
     */
    private void relax(DirectedEdge e) {
        int v = e.from();
        int w = e.to();
        if (distTo[w] > distTo[v] + e.weight()) {
            edgeTo[w] = e;
            distTo[w] = distTo[v] + e.weight();
            if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
            else pq.insert(w, distTo[w]);
        }
    }

    public double distTo(int v) {
        validDataVertex(v);
        return distTo[v];
    }

    public boolean hasPathTo(int v) {
        validDataVertex(v);
        return distTo[v] != Double.POSITIVE_INFINITY;
    }

    public Iterable<DirectedEdge> pathTo(int v) {
        validDataVertex(v);
        if (!hasPathTo(v)) return null;
        Stack<DirectedEdge> path = new Stack<DirectedEdge>();
        for (DirectedEdge e=edgeTo[v]; e!=null; e=edgeTo[e.from()])
            path.push(e);
        return path;
    }

    private boolean check(EdgeWeightDigraph g, int s) {
        for (DirectedEdge e : g.edges()) {
            if (e.weight() < 0) {
                System.err.println("negative edge weight detected");
                return false;
            }
        }

        if (distTo[s] != 0.0 || edgeTo[s] != null) {
            System.err.println("distTo s or edgeTo s incorrect");
            return false;
        }

        for (int v=0; v<g.V(); v++) {
            if (v==s) continue;
            if (edgeTo[v] == null && distTo[v] != Double.POSITIVE_INFINITY) {
                System.err.println("distTo[] and edgeTo[] inconsistent");
            }
        }

        for (int v = 0; v < g.V(); v++) {
            for (DirectedEdge e : g.adj(v)) {
                int w = e.to();
                if (distTo[v] + e.weight() < distTo[w]) {
                    System.err.println("edge " + e + " not relaxed");
                    return false;
                }
            }
        }

        // check that all edges e = v->w on SPT satisfy distTo[w] == distTo[v] + e.weight()
        for (int w = 0; w < g.V(); w++) {
            if (edgeTo[w] == null) continue;
            DirectedEdge e = edgeTo[w];
            int v = e.from();
            if (w != e.to()) return false;
            if (distTo[v] + e.weight() != distTo[w]) {
                System.err.println("edge " + e + " on shortest path not tight");
                return false;
            }
        }
        return true;
    }

    private void validDataVertex(int v) {
        if (v<0 || v>edgeTo.length)
            throw new IllegalArgumentException("v must between 0 and V");
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightDigraph G = new EdgeWeightDigraph(in);
        int s = Integer.parseInt(args[1]);

        // compute shortest paths
        DijkstraSP sp = new DijkstraSP(G, s);


        // print shortest path
        for (int t = 0; t < G.V(); t++) {
            if (sp.hasPathTo(t)) {
                StdOut.printf("%d to %d (%.2f)  ", s, t, sp.distTo(t));
                for (DirectedEdge e : sp.pathTo(t)) {
                    StdOut.print(e + "   ");
                }
                StdOut.println();
            }
            else {
                StdOut.printf("%d to %d         no path\n", s, t);
            }
        }
    }
}
