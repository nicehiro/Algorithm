package Graph;

import edu.princeton.cs.algs4.*;

/**
 * Created by hiro on 17-5-14.
 */
public class BellmanFordSP {

    private double[] distTo;
    private DirectedEdge[] edgeTo;
    private boolean onQueue[];
    private Queue<Integer> queue;
    private int cost;
    private Iterable<DirectedEdge> cycle;

    /*
    * 这个算法的操作和 Prim 算法的延时实现有些类似
    * queue 保存即将被放松的顶点队列
    * onQueue 检查元素是否已经在队列
     */
    public BellmanFordSP(EdgeWeightDigraph g, int s) {
        distTo = new double[g.V()];
        edgeTo = new DirectedEdge[g.V()];
        onQueue = new boolean[g.V()];
        for (int v=0; v<g.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;

        queue = new Queue<>();
        queue.enqueue(s);
        onQueue[s] = true;
        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            onQueue[v] = false;
            relax(g, v);
        }
    }

    private void relax(EdgeWeightDigraph g, int v) {
        for (DirectedEdge e : g.adj(v)) {
            int w = e.to();
            if (distTo[w] > distTo(v) + e.weight()) {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
                if (!onQueue[w]) {
                    queue.enqueue(w);
                    onQueue[w] = true;
                }
            }
            if (cost++ % g.V() == 0) {
                // findNegativeCycle();
                if (hasNegativeCycle())
                    return;
            }
        }
    }

    public boolean hasNegativeCycle() {
        return cycle != null;
    }

    public Iterable<DirectedEdge> negativeCycle() {
        return cycle;
    }

    /*
    * 此方法要使用需要用到 EdgeWeightDirectedCycle 需要改动的地方太多，因而不实现了=。=
    * 理解思想才重要嘛

    private void findNegativeCycle() {
        int V = edgeTo.length;
        EdgeWeightedDigraph spt = new EdgeWeightedDigraph(V);
        for (int v=0; v<V; v++) {
            if (edgeTo[v] != null)
                spt.addEdge(edgeTo[v]);
        }
        EdgeWeightedDirectedCycle finder = new EdgeWeightedDirectedCycle(spt);
        cycle = finder.cycle();
    }
     */

    public double distTo(int v) {
        validDataVertex(v);
        if (hasNegativeCycle())
            throw new IllegalArgumentException("Negative cost cycle exists");
        return distTo[v];
    }

    public boolean hasPathTo(int v) {
        validDataVertex(v);
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    public Iterable<DirectedEdge> pathTo(int v) {
        validDataVertex(v);
        if (hasNegativeCycle())
            throw new IllegalArgumentException("Negative cost cycle exist");
        if (!hasPathTo(v)) return null;
        Stack<DirectedEdge> path = new Stack<DirectedEdge>();
        for (DirectedEdge e = edgeTo[v]; e!=null; e=edgeTo[e.from()])
            path.push(e);
        return path;
    }

    private void validDataVertex(int v) {
        int V = distTo.length;
        if (v<0 || v>= V)
            throw new IllegalArgumentException("number must between 0 and V");
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int s = Integer.parseInt(args[1]);
        EdgeWeightDigraph G = new EdgeWeightDigraph(in);

        BellmanFordSP sp = new BellmanFordSP(G, s);

        // print negative cycle
        if (sp.hasNegativeCycle()) {
            for (DirectedEdge e : sp.negativeCycle())
                StdOut.println(e);
        }

        // print shortest paths
        else {
            for (int v = 0; v < G.V(); v++) {
                if (sp.hasPathTo(v)) {
                    StdOut.printf("%d to %d (%5.2f)  ", s, v, sp.distTo(v));
                    for (DirectedEdge e : sp.pathTo(v)) {
                        StdOut.print(e + "   ");
                    }
                    StdOut.println();
                }
                else {
                    StdOut.printf("%d to %d           no path\n", s, v);
                }
            }
        }
    }
}
