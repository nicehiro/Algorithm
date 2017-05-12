package Graph;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;

import java.util.NoSuchElementException;

/**
 * Created by hiro on 17-5-11.
 */
public class Graph {
    private static final String NEWLINWE = System.getProperty("line.separator");

    private final int V;            // 顶点个数
    private int E;                  // 边的个数
    private Bag<Integer>[] adj;

    public Graph(int V) {
        if (V < 0) throw new IllegalArgumentException("Number of vertices must not be 0");
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v=0; v<V; v++)
            adj[v] = new Bag<Integer>();
    }

    public Graph(In in) {
        try {
            this.V = in.readInt();
            if (V < 0) throw new IllegalArgumentException("number of vertices must not be 0");
            adj = (Bag<Integer>[]) new Bag[V];
            for (int v=0; v<V; v++) {
                adj[v] = new Bag<Integer>();
            }
            int E = in.readInt();
            if (E < 0) throw new IllegalArgumentException("number of eages must not be 0");
            for (int i=0; i<E; i++) {
                int v = in.readInt();
                int w = in.readInt();
                validDateVertex(v);
                validDateVertex(w);
                addEdge(v, w);
            }
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("invalid input format");
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    private void validDateVertex(int v) {
        if (v<0 || v>V)
            throw new IllegalArgumentException("verticles & edges must between 0 and V");
    }

    private void addEdge(int v, int w) {
        validDateVertex(v);
        validDateVertex(w);
        E ++;
        adj[v].add(w);
        adj[w].add(v);
    }

    public Iterable<Integer> adj(int v) {
        validDateVertex(v);
        return adj[v];
    }

    public int degree(int v) {
        validDateVertex(v);
        return adj[v].size();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(V + " verticles, " + E + " edges" + NEWLINWE);
        for (int v=0; v<V; v++) {
            stringBuilder.append(v + ": ");
            for (int w : adj(v)) {
                stringBuilder.append(w + " ");
            }
            stringBuilder.append(NEWLINWE);
        }
        return stringBuilder.toString();
    }
}
