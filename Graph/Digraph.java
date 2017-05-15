package Graph;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.NoSuchElementException;

/**
 * Created by hiro on 17-5-12.
 */
public class Digraph {

    private static final String NEWLINE = System.getProperty("line.separator");

    private int V;
    private int E;
    private Bag<Integer>[] adj;
    private int[] indegree;

    public Digraph(int V) {
        if (V < 0) throw new IllegalArgumentException("Number of verticles in a Digraph must > 0");
        this.V = V;
        this.E = 0;
        indegree = new int[V];
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v=0; v<V; v++) {
            adj[v] = new Bag<>();
        }
    }

    /*
    * 有向图的数据结构
     */
    public Digraph(In in) {
        try {
            this.V = in.readInt();
            if (V < 0) throw new IllegalArgumentException("number must be > 0");
            indegree = new int[V];
            adj = (Bag<Integer>[]) new Bag[V];
            for (int v=0; v<V; v++) {
                adj[v] = new Bag<>();
            }
            int E = in.readInt();
            if (E < 0) throw new IllegalArgumentException("number must be > 0");
            for (int i=0; i<E; i++) {
                int v = in.readInt();
                int w = in.readInt();
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
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("must between 0 and V");
    }

    public Iterable<Integer> adj(int v) {
        validDateVertex(v);
        return adj[v];
    }

    /*
    * 出度：即结点 v 所连的 Bag 的大小
     */
    public int outDegree(int v) {
        validDateVertex(v);
        return adj[v].size();
    }

    public int inDegree(int v) {
        validDateVertex(v);
        return indegree[v];
    }

    /*
    * 维护一个记录入度的数组 indegree
    * 每添加一条到 w 的边，为 w 的入度加 1
     */
    public void addEdge(int v, int w) {
        validDateVertex(v);
        validDateVertex(w);
        adj[v].add(w);
        indegree[w] ++;
        E ++;
    }

    public Digraph reverse() {
        Digraph temp = new Digraph(V);
        for (int v=0; v<V; v++) {
            for (int w : adj(v))
                temp.addEdge(w, v);
        }
        return temp;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " vertices, " + E + " edges " + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(String.format("%d: ", v));
            for (int w : adj[v]) {
                s.append(String.format("%d ", w));
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        StdOut.println(G);
        System.out.println(G.V);
        StdOut.println(G.reverse());
    }
}
