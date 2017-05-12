package Graph;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;

/**
 * Created by hiro on 17-5-12.
 */
public class BreadthFirstSearch {
    private static final int INFINITY = Integer.MAX_VALUE;
    private boolean[] marked;
    private int[] edgeTo;
    private int[] distTo;

    public BreadthFirstSearch(Graph g, int s) {
        marked = new boolean[g.V()];
        edgeTo = new int[g.V()];
        distTo = new int[g.V()];
        validDataVertex(s);
        bfs(g, s);
    }

    private void bfs(Graph g, int s) {
        Queue<Integer> queue = new Queue<>();
        for (int v=0; v<marked.length; v++) {
            distTo[v] = INFINITY;
        }
        distTo[s] = 0;
        marked[s] = true;
        queue.enqueue(s);

        while (!queue.isEmpty()) {
            int temp = queue.dequeue();
            for (int w : g.adj(temp)) {
                if (!marked[w]) {
                    marked[w] = true;
                    distTo[w] = distTo[temp] + 1;
                    edgeTo[w] = temp;
                    queue.enqueue(w);
                }
            }
        }
    }

    public boolean hasPathTo(int v) {
        validDataVertex(v);
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v) {
        validDataVertex(v);
        if (!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<>();
        int x;
        for (x=v; distTo[x]!=0; x=edgeTo[x]) {
            path.push(x);
        }
        path.push(x);
        return path;
    }

    private void validDataVertex(int v) {
        if (v<0 || v>marked.length)
            throw new IllegalArgumentException("number must between 0 and V");
    }
}
