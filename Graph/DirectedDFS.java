package Graph;

/**
 * Created by hiro on 17-5-12.
 */
public class DirectedDFS {

    private boolean[] marked;
    private int count;

    public DirectedDFS(Digraph G, int s) {
        marked = new boolean[G.V()];
        dfs(G, s);
    }

    /*
    * 有向图的 dfs 和无向图的差不多
     */
    private void dfs(Digraph g, int v) {
        count ++;
        marked[v] = true;
        for (int w : g.adj(v)) {
            if (!marked[w])
                dfs(g, w);
        }
    }

    public boolean marked(int v) {
        return marked[v];
    }

    public int count() {
        return count;
    }
}
