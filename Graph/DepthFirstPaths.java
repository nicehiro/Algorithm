package Graph;

import edu.princeton.cs.algs4.Stack;

/**
 * Created by hiro on 17-5-12.
 */
public class DepthFirstPaths {
    private boolean[] marked;
    private int[] edgeTo;
    private final int s;

    public DepthFirstPaths(Graph g, int s) {
        this.s = s;
        marked = new boolean[g.V()];
        edgeTo = new int[g.V()];
        dfs(g, s);
    }

    /*
    * 使用深度优先搜索将连通图的所有结点串起来
    * 新增 edgeTo 用来记录 w 结点的前一个结点 v
     */
    private void dfs(Graph g, int v) {
        marked[v] = true;
        for (int w : g.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(g, w);
            }
        }
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    /*
    * 使用 stack 记录从起始点 s 到 v 的路径
    * 利用 edgeTo[v] 指向 v 的前一个结点的特性
     */
    public Iterable<Integer> pathTo(int v) {
        if (hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<>();
        for (int x=v; x!=s; x=edgeTo[x]) {
            path.push(x);
        }
        path.push(s);
        return path;
    }
}
