package Graph;

import edu.princeton.cs.algs4.Stack;

/**
 * Created by hiro on 17-5-12.
 * whether a Digraph has self-cycle
 */
public class DirectedCycle {

    private boolean[] marked;
    private int[] edgeTo;
    private boolean[] onStack;
    private Stack<Integer> cycle;

    public DirectedCycle(Digraph g) {
        marked = new boolean[g.V()];
        edgeTo = new int[g.V()];
        onStack = new boolean[g.V()];
        for (int v=0; v<g.V(); v++)
            if (!marked[v] && cycle == null)
                dfs(g, v);
    }

    /*
    * 如果未标记，dfs 遍历这个结点
    * 如果已标记，查看这个结点是否在 stack 中，如果在，成环
     */
    private void dfs(Digraph g, int v) {
        onStack[v] = true;
        marked[v] = true;
        for (int w : g.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(g, w);
            } else if (onStack[w]) {
                cycle = new Stack<>();
                for (int x=v; x!=w; x=edgeTo[x]) {
                    cycle.push(x);
                }
                cycle.push(w);
                cycle.push(v);
            }
        }
        onStack[v] = false;
    }

    public boolean hasCycle() {
        return cycle != null;
    }

    public Iterable<Integer> cycle() {
        return cycle;
    }
}
