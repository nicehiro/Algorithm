package Graph;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;

/**
 * Created by hiro on 17-5-13.
 */
public class EdgeWeightedGraph {

    private static final String NEWLINE = System.getProperty("line.separator");

    private final int V;
    private int E;
    private Bag<Edge>[] adj;

    public EdgeWeightedGraph(int V) {
        if (V < 0) throw new IllegalArgumentException("number of vertices must > 0");
        this.V = V;
        this.E = 0;
        adj = new Bag[V];
        for (int v=0; v<V; v++) {
            adj[v] = new Bag<Edge>();
        }
    }

    public EdgeWeightedGraph(In in) {
        this(in.readInt());
        int E = in.readInt();
        if (E < 0)
            throw new IllegalArgumentException("number of edges must > 0");
        for (int i=0; i<E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            validDataVertex(v);
            validDataVertex(w);
            double weight = in.readDouble();
            Edge e = new Edge(v, w, weight);
            addEdge(e);
        }
    }

    private void validDataVertex(int v) {
        if (v<0 || v>V)
            throw new IllegalArgumentException("number must between 0 and V");
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public void addEdge(Edge edge) {
        int v = edge.either();
        int w = edge.other(v);
        validDataVertex(v);
        validDataVertex(w);
        adj[v].add(edge);
        adj[w].add(edge);
        E ++;
    }

    public int degree(int v) {
        validDataVertex(v);
        return adj[v].size();
    }

    public Iterable<Edge> adj(int v) {
        return adj[v];
    }

    public Iterable<Edge> edges() {
        Bag<Edge> list = new Bag<Edge>();
        for (int v=0; v<V; v++) {
            int selfLoops = 0;
            for (Edge e : adj[v]) {
                if (e.other(v) > v) {
                    list.add(e);
                } else if (e.other(v) == v) {
                    if (selfLoops % 2 == 0)
                        list.add(e);
                    selfLoops ++;
                }
            }
        }
        return list;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " " + E + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (Edge e : adj[v]) {
                s.append(e + "  ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }
}
