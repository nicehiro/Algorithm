package Graph;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by hiro on 17-5-14.
 */
public class EdgeWeightDigraph {

    private static final String NEWLINE = System.getProperty("line.separator");

    private final int V;
    private int E;
    private Bag<DirectedEdge>[] adj;
    private int[] indegree;

    public EdgeWeightDigraph(int V) {
        if (V < 0)
            throw new IllegalArgumentException("V must be > 0");
        this.V = V;
        this.E = 0;
        this.indegree = new int[V];
        adj = new Bag[V];
        for (int v=0; v<V; v++)
            adj[v] = new Bag<DirectedEdge>();
    }

    public EdgeWeightDigraph(In in) {
        this(in.readInt());
        int E = in.readInt();
        if (E < 0)
            throw new IllegalArgumentException("E must be > 0");
        for (int i=0; i<E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            double weight = in.readDouble();
            validDataVertex(v);
            validDataVertex(w);
            addEdge(new DirectedEdge(v, w, weight));
        }
    }

    private void validDataVertex(int v) {
        if (v<0 || v>=V)
            throw new IllegalArgumentException("number must between 0 and V");
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public void addEdge(DirectedEdge edge) {
        int v = edge.from();
        int w = edge.to();
        validDataVertex(v);
        validDataVertex(w);
        adj[v].add(edge);
        indegree[w] ++;
        E ++;
    }

    public Iterable<DirectedEdge> adj(int v) {
        validDataVertex(v);
        return adj[v];
    }

    public int outdegree(int v) {
        return adj[v].size();
    }

    public int indegree(int v) {
        return indegree[v];
    }

    public Iterable<DirectedEdge> edges() {
        Bag<DirectedEdge> temp = new Bag<DirectedEdge>();
        for (int v=0; v<V; v++) {
            for (DirectedEdge e : adj(v))
                temp.add(e);
        }
        return temp;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " " + E + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (DirectedEdge e : adj[v]) {
                s.append(e + "  ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        EdgeWeightDigraph g = new EdgeWeightDigraph(in);
        StdOut.println(g);

        in = new In(args[0]);
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
        StdOut.println(G);
    }
}
