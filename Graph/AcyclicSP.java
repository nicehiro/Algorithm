package Graph;

import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.DirectedEdge;

/**
 * Created by hiro on 17-5-14.
 */
public class AcyclicSP {

    private double[] distTo;
    private edu.princeton.cs.algs4.DirectedEdge[] edgeTo;

    /*
    * 使用拓扑排序形成的队列，即逆后序遍历得到的队列，对队列中每一个元素进行 relax 操作
    * 拓扑排序形成的队列中，排在前面的元素 relax 操作不会影响后面的元素
     */
    public AcyclicSP(EdgeWeightedDigraph g, int s) {
        distTo = new double[g.V()];
        edgeTo = new edu.princeton.cs.algs4.DirectedEdge[g.V()];

        validDataVertex(s);

        for (int v=0; v<g.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;

        edu.princeton.cs.algs4.Topological t = new edu.princeton.cs.algs4.Topological(g);
        if (!t.hasOrder())
            throw new IllegalArgumentException("Digraph is not acyclic");
        for (int v : t.order())
            for (edu.princeton.cs.algs4.DirectedEdge e : g.adj(v))
                relax(e);
    }

    private void relax(edu.princeton.cs.algs4.DirectedEdge e) {
        int v = e.from();
        int w = e.to();
        if (distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
        }
    }

    public double distTo(int v) {
        validDataVertex(v);
        return distTo[v];
    }

    public boolean hasPathTo(int v) {
        validDataVertex(v);
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    public Iterable<edu.princeton.cs.algs4.DirectedEdge> pathTo(int v) {
        validDataVertex(v);
        if (!hasPathTo(v)) return null;
        Stack<DirectedEdge> path = new Stack<>();
        for (DirectedEdge e=edgeTo[v]; e!=null; e=edgeTo[e.from()])
            path.push(e);
        return path;
    }

    private void validDataVertex(int v) {
        if (v<0 || v>distTo.length)
            throw new IllegalArgumentException("number must between 0 and V");
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int s = Integer.parseInt(args[1]);
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);

        // find shortest path from s to each other vertex in DAG
        AcyclicSP sp = new AcyclicSP(G, s);
        for (int v = 0; v < G.V(); v++) {
            if (sp.hasPathTo(v)) {
                StdOut.printf("%d to %d (%.2f)  ", s, v, sp.distTo(v));
                for (DirectedEdge e : sp.pathTo(v)) {
                    StdOut.print(e + "   ");
                }
                StdOut.println();
            }
            else {
                StdOut.printf("%d to %d         no path\n", s, v);
            }
        }
    }
}
